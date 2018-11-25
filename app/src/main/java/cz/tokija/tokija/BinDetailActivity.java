package cz.tokija.tokija;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import cz.tokija.tokija.client.model.Bin;
import cz.tokija.tokija.client.model.Firm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinDetailActivity extends BaseActivity {

    private Bin bin;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setRefreshing(true);
        pullToRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        pullToRefresh.setOnRefreshListener(this::loadDetail);
        //showProgressBar();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        loadDetail();

        Button replaceButton = findViewById(R.id.replaceButton);
        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button takeoutButton = findViewById(R.id.takeoutButton);
        takeoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTime now = DateTime.now();
                showToast("Taken date set to:" + now.toString());
                bin.setTaken(now.toDate());
                getClient().updateBin(bin.getId(), bin).enqueue(new Callback<Bin>() {
                    @Override
                    public void onResponse(Call<Bin> call, Response<Bin> response) {
                        if (response.isSuccessful()) {
                            setBinFields(response.body());
                        } else {
                            try {
                                showToast(response.errorBody().string());
                                gotoLoginIfUnauthorized(response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Bin> call, Throwable t) {
                        showToast(t.getMessage());
                    }
                });
            }
        });

        Button placeButton = findViewById(R.id.placeBinButton);
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BinDetailActivity.this, PlaceBinActivity.class);
                intent.putExtra("bin", (new Gson()).toJson(bin));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bin_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_remove_bin) {
            new AlertDialog.Builder(this)
                    .setTitle("Odebrání binu")
                    .setMessage("Opravdu odebrat bin a přemístit na sklad?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            bin.setFirmId(1);
                            bin.setTaken(DateTime.now().toDate());
                            bin.setFrequency(1000);
                            updateBin(bin);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateBin(Bin bin) {
        getClient().updateBin(bin.getId(), bin).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    showToast("Bin přiřazen na Sklad");
                    Intent intent = new Intent(BinDetailActivity.this, BinsListActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        showToast(response.errorBody().string());
                        gotoLoginIfUnauthorized(response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Bin> call, Throwable t) {
                showToast(t.getMessage());
            }
        });

    }

    private void loadDetail() {
        Intent mIntent = getIntent();
        int binId = Integer.parseInt(mIntent.getStringExtra("binId"));
        //int firmId = Integer.parseInt(mIntent.getStringExtra("firmId"));
        getClient().getBin(binId).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    bin = response.body();
                    loadFirmDetail(bin.getFirmId());
                    setBinFields(bin);
                } else {
                    try {
                        pullToRefresh.setRefreshing(false);
                        showToast(response.errorBody().string());
                        gotoLoginIfUnauthorized(response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Bin> call, Throwable t) {
                pullToRefresh.setRefreshing(false);
                showToast(t.getMessage());
            }
        });
    }

    private void loadFirmDetail(int firmId) {
        getClient().getFirm(firmId).enqueue(new Callback<Firm>() {
            @Override
            public void onResponse(Call<Firm> call, Response<Firm> response) {
                if (response.isSuccessful()) {
                    Firm firm = response.body();
                    pullToRefresh.setRefreshing(false);
                    setFirmFields(firm);
                } else {
                    try {
                        showToast(response.errorBody().string());
                        gotoLoginIfUnauthorized(response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Firm> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void setBinFields(Bin bin) {
        //hideProgressBar();

        TextView binNumber = findViewById(R.id.binNumber);
        TextView binCollect = findViewById(R.id.binCollectDate);
        TextView binFrequency = findViewById(R.id.binFrequency);
        TextView binPlaced = findViewById(R.id.binPlaced);
        TextView binTken = findViewById(R.id.binTaken);
        TextView binNote = findViewById(R.id.binNote);

        if ("sklad".equalsIgnoreCase(bin.getFirmName())) {
            findViewById(R.id.notNewBinLayout).setVisibility(View.GONE);
            findViewById(R.id.newBinLayout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.newBinLayout).setVisibility(View.GONE);
            findViewById(R.id.notNewBinLayout).setVisibility(View.VISIBLE);
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");

        binNumber.setText(String.valueOf(bin.getNumber()));
        binCollect.setText(bin.getCollectDate().toString(formatter));
        binFrequency.setText(bin.getFrequency());
        binPlaced.setText(bin.getPlaced().toString(formatter));
        binTken.setText(bin.getTaken().toString(formatter));
        binNote.setText(bin.getNote());
    }

    private void setFirmFields(Firm firm) {
        //hideProgressBar();

        TextView firmName = findViewById(R.id.firmName);
        TextView firmAddress = findViewById(R.id.firmAddress);
        TextView firmCity = findViewById(R.id.firmCity);

        firmName.setText(firm.getName());
        firmAddress.setText(firm.getAddress());
        firmCity.setText(firm.getCity());
    }
}
