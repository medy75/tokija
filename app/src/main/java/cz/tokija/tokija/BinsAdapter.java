package cz.tokija.tokija;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.tokija.tokija.client.model.Bin;

/**
 * Author: jaroslavmedek on 18/10/2018.
 */
public class BinsAdapter extends ArrayAdapter<Bin> {
    public BinsAdapter(Context context, ArrayList<Bin> bins) {
        super(context, 0, bins);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bin bin = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bin, parent, false);
        }
        // Lookup view for data population
        TextView binNumber = (TextView) convertView.findViewById(R.id.binNumber);
        TextView binFirm = (TextView) convertView.findViewById(R.id.binFirm);
        TextView binFrequency = (TextView) convertView.findViewById(R.id.binFrequency);
        TextView binCollectDate = (TextView) convertView.findViewById(R.id.binCollectDate);
        // Populate the data into the template view using the data object

        binNumber.setText(bin.getNumber()+ " - " + position);
//        binFirm.setText(bin.getFirmId());
        binFrequency.setText(bin.getFrequency());
//        binCollectDate.setText(bin.getCollectDate());
        // Return the completed view to render on screen
        return convertView;
    }
}
