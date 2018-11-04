package cz.tokija.tokija;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cz.tokija.tokija.client.model.Firm;

/**
 * Author: jaroslavmedek on 04/11/2018.
 */
public class FirmsAdapter extends ArrayAdapter<Firm> {
    public FirmsAdapter(Context context, ArrayList<Firm> firms) {
        super(context, 0, firms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        return convertView;
    }
}
