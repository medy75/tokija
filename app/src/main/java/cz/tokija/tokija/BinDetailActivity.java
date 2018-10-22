package cz.tokija.tokija;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.tokija.tokija.client.APIInterface;
import cz.tokija.tokija.client.Client;
import cz.tokija.tokija.client.model.Bin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinDetailActivity extends AppCompatActivity {

    APIInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_detail);
        client = new Client().getClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent mIntent = getIntent();
        String binId = mIntent.getStringExtra("binId");

        client.getBin(Integer.parseInt(binId)).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    Bin bin = response.body();
                    setFields(bin);
                } else {
                    Toast toast = null;
                    try {
                        toast = Toast.makeText(getApplicationContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_LONG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    toast.show();
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
    }

    private void setFields(Bin bin){
        TextView binNumber = findViewById(R.id.binNumber);
        TextView binFirm = findViewById(R.id.binFirm);
        TextView binCollect = findViewById(R.id.binCollectDate);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YY");

        binNumber.setText(String.valueOf(bin.getId()));
        binFirm.setText(bin.getFirmName());
        binCollect.setText(bin.getCollectDate().toString(formatter));

    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }

}
