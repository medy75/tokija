package cz.tokija.tokija;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Author: jaroslavmedek on 28/10/2018.
 */
public class MainActivity extends AppCompatActivity {

    protected void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }
}
