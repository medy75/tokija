package cz.tokija.tokija;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.tokija.tokija.client.Client;
import cz.tokija.tokija.client.model.Bin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinsListActivity extends BaseActivity {


    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SaveSharedPreference.getPrefEmail(getBaseContext()).length() == 0) {
            Intent intent = new Intent(BinsListActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Client.setEmail(SaveSharedPreference.getPrefEmail(getBaseContext()));
            Client.setToken(SaveSharedPreference.getPrefToken(getBaseContext()));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bins_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setRefreshing(true);
        pullToRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        pullToRefresh.setOnRefreshListener(this::loadBinData);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            // intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        });

        if (SaveSharedPreference.getPrefEmail(getApplicationContext()).length() == 0) {
            Intent intent = new Intent(BinsListActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            loadBinData();
        }
    }

    private void loadBinData() {
        getClient().getBins().enqueue(new Callback<List<Bin>>() {
            @Override
            public void onResponse(Call<List<Bin>> call, Response<List<Bin>> response) {
                if (response.isSuccessful()) {
                    //hideProgressBar();
                    pullToRefresh.setRefreshing(false);
                    ArrayList<Bin> bins = new ArrayList<>(response.body());
                    BinsAdapter adapter = new BinsAdapter(getApplicationContext(), bins);
                    ListView listView = (ListView) findViewById(R.id.binsList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(clickListener(bins));
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
            public void onFailure(Call<List<Bin>> call, Throwable t) {
                showToast(t.getMessage());
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //statusMessage.setText(R.string.barcode_success);
                    //barcodeValue.setText(barcode.displayValue);
                    Intent myIntent = new Intent(BinsListActivity.this, BinDetailActivity.class);
                    myIntent.putExtra("binId", barcode.displayValue);
                    startActivity(myIntent);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    showToast(getString(R.string.barcode_failure));
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                showToast(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private AdapterView.OnItemClickListener clickListener(ArrayList<Bin> bins) {
        return (adapterView, view, i, l) -> {
//            showToast("starting bin detail activity: " + i);
            Intent myIntent = new Intent(BinsListActivity.this, BinDetailActivity.class);
            myIntent.putExtra("binId", String.valueOf(bins.get(i).getId()));
            myIntent.putExtra("firmId", String.valueOf(bins.get(i).getFirmId()));
            startActivity(myIntent);
        };
    }

}
