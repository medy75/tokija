package cz.tokija.tokija;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import cz.tokija.tokija.client.APIInterface;
import cz.tokija.tokija.client.Client;

/**
 * Author: jaroslavmedek on 28/10/2018.
 */
public class MainActivity extends AppCompatActivity {

    private APIInterface client;

    public MainActivity() {
        Client.setEmail("info@tokija.cz");
        Client.setToken("5Sbupp-Sk5wnDYZqvpz_");
        client = new Client().getClient();
    }

    protected APIInterface getClient(){
        return client;
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }
}
