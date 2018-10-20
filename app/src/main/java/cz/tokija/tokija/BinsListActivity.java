package cz.tokija.tokija;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.tokija.tokija.client.APIInterface;
import cz.tokija.tokija.client.Client;
import cz.tokija.tokija.client.model.Bin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinsListActivity extends AppCompatActivity {

    APIInterface client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client.setEmail("info@tokija.cz");
        Client.setToken("5Sbupp-Sk5wnDYZqvpz_");
        client = new Client().getClient();
        setContentView(R.layout.activity_bins_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        client.getBins().enqueue(new Callback<List<Bin>>() {
            @Override
            public void onResponse(Call<List<Bin>> call, Response<List<Bin>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Bin> bins = new ArrayList<>(response.body());
                    BinsAdapter adapter = new BinsAdapter(getApplicationContext(), bins);
                    ListView listView = (ListView) findViewById(R.id.binsList);
                    listView.setAdapter(adapter);
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
            public void onFailure(Call<List<Bin>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
