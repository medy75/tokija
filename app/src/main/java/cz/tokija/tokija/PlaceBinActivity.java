package cz.tokija.tokija;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.tokija.tokija.client.model.Bin;
import cz.tokija.tokija.client.model.Firm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceBinActivity extends BaseActivity {

    SwipeRefreshLayout pullToRefresh;
    Bin bin;
    Map<String, Integer> firmsMap = new HashMap<>();
    //List<Firm> firmsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bin);

        bin = (new Gson()).fromJson(getIntent().getStringExtra("bin"), Bin.class);
        setBinFields(bin);

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setRefreshing(true);
        pullToRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        pullToRefresh.setOnRefreshListener(this::loadFirms);
        loadFirms();

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = findViewById(R.id.spinner);
                EditText binNote = findViewById(R.id.newNote);
                EditText binFrequency = findViewById(R.id.binFrequency);

                bin.setFirmId(firmsMap.get(spinner.getSelectedItem().toString()));
                bin.setFirmName(spinner.getSelectedItem().toString());
                bin.setNote(binNote.getText().toString());
                bin.setPlaced(DateTime.now().toDate());
                bin.setFrequency(binFrequency.getText().toString());
                bin.setCollectDate(bin.getTaken().plusDays(Integer.parseInt(binFrequency.getText().toString())).toDate());
                updateBin(bin);
            }
        });
    }

    private void loadFirms(){
        getClient().getFirms().enqueue(new Callback<List<Firm>>() {
            @Override
            public void onResponse(Call<List<Firm>> call, Response<List<Firm>> response) {
                if (response.isSuccessful()){
                    //firmsList = response.body();
                    fillSpinner(response.body());
                } else {
                    try {
                        showToast(response.errorBody().string());
                        gotoLoginIfUnauthorized(response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                pullToRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Firm>> call, Throwable t) {

            }
        });
    }

    private void updateBin(Bin bin){
        getClient().updateBin(bin.getId(), bin).enqueue(new Callback<Bin>() {
            @Override
            public void onResponse(Call<Bin> call, Response<Bin> response) {
                if (response.isSuccessful()) {
                    showToast("Bin umístěn");
                    Intent intent = new Intent(PlaceBinActivity.this, BinsListActivity.class);
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

    private void fillSpinner(List<Firm> firmsList){
        Spinner spinner = findViewById(R.id.spinner);
        List<String> names = new ArrayList<>();
        for (Firm firm : firmsList){
            names.add(firm.getName());
            firmsMap.put(firm.getName(), firm.getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,names);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showToast(names.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setBinFields(Bin bin) {
        //hideProgressBar();

        TextView binNumber = findViewById(R.id.binNumber);
        //TextView binCollect = findViewById(R.id.binCollectDate);
        EditText binFrequency = findViewById(R.id.binFrequency);
        TextView binPlaced = findViewById(R.id.binPlaced);
        //TextView binTaken = findViewById(R.id.binTaken);
        EditText binNote = findViewById(R.id.newNote);
//
//        if ("sklad".equalsIgnoreCase(bin.getFirmName())){
//            findViewById(R.id.notNewBinLayout).setVisibility(View.GONE);
//            findViewById(R.id.newBinLayout).setVisibility(View.VISIBLE);
//        } else {
//            findViewById(R.id.newBinLayout).setVisibility(View.GONE);
//            findViewById(R.id.notNewBinLayout).setVisibility(View.VISIBLE);
//        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YYYY");

        binNumber.setText(String.valueOf(bin.getNumber()));
        //binCollect.setText(bin.getCollectDate().toString(formatter));
        //binFrequency.setText(bin.getFrequency());
        binPlaced.setText(DateTime.now().toString(formatter));
        //binTaken.setText(bin.getTaken().toString(formatter));
        binNote.setText(bin.getNote());
    }

}
