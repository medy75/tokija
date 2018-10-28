package cz.tokija.tokija;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

import cz.tokija.tokija.client.APIInterface;
import cz.tokija.tokija.client.Client;
import cz.tokija.tokija.client.model.Bin;
import cz.tokija.tokija.client.model.Firm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinDetailActivity extends MainActivity {

    APIInterface client;
    private Bin bin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_detail);
        client = new Client().getClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showProgressBar();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Intent mIntent = getIntent();
        String binId = mIntent.getStringExtra("binId");
        String firmId = mIntent.getStringExtra("firmId");

        client.getBin(Integer.parseInt(binId)).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    bin = response.body();
                    setBinFields(bin);
                } else {
                    try {
                        showToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Bin> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });

        client.getFirm(Integer.parseInt(firmId)).enqueue(new Callback<Firm>() {
            @Override
            public void onResponse(Call<Firm> call, Response<Firm> response) {
                if (response.isSuccessful()) {
                    Firm firm = response.body();
                    setFirmFields(firm);
                } else {
                    try {
                        showToast(response.errorBody().string());
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
                client.updateBin(bin.getId(), bin).enqueue(new Callback<Bin>() {
                    @Override
                    public void onResponse(Call<Bin> call, Response<Bin> response) {
                        if (response.isSuccessful()) {
                            setBinFields(response.body());
                        } else {
                            try {
                                showToast(response.errorBody().string());
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
    }

    private void setBinFields(Bin bin) {
        hideProgressBar();

        TextView binNumber = findViewById(R.id.binNumber);
        TextView binCollect = findViewById(R.id.binCollectDate);
        TextView binFrequency = findViewById(R.id.binFrequency);
        TextView binPlaced = findViewById(R.id.binPlaced);
        TextView binTken = findViewById(R.id.binTaken);
        TextView binNote = findViewById(R.id.binNote);

        if ("sklad".equalsIgnoreCase(bin.getFirmName())){
            findViewById(R.id.notNewBinLayout).setVisibility(View.GONE);
            findViewById(R.id.newBinLayout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.newBinLayout).setVisibility(View.GONE);
            findViewById(R.id.notNewBinLayout).setVisibility(View.VISIBLE);
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");

        binNumber.setText(String.valueOf(bin.getId()));
        binCollect.setText(bin.getCollectDate().toString(formatter));
        binFrequency.setText(bin.getFrequency());
        binPlaced.setText(bin.getPlaced().toString(formatter));
        binTken.setText(bin.getTaken().toString(formatter));
        binNote.setText(bin.getNote());
    }

    private void setFirmFields(Firm firm) {
        hideProgressBar();

        TextView firmName = findViewById(R.id.firmName);
        TextView firmAddress = findViewById(R.id.firmAddress);
        TextView firmCity = findViewById(R.id.firmCity);

        firmName.setText(firm.getName());
        firmAddress.setText(firm.getAddress());
        firmCity.setText(firm.getCity());
    }
}
