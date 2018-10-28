package cz.tokija.tokija;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Author: jaroslavmedek on 28/10/2018.
 */
public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    protected void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    protected void showProgressBar(){
        progressBar = findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}
