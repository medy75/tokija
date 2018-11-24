package cz.tokija.tokija;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

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
        TextView binNumber = convertView.findViewById(R.id.binNumberLabel);
        TextView binFirm = convertView.findViewById(R.id.binFirm);
        TextView binFrequency = convertView.findViewById(R.id.binFrequency);
        TextView binCollectDate = convertView.findViewById(R.id.binCollectDate);
        TextView exclamationView = convertView.findViewById(R.id.exclamationMarksTextView);
        // Populate the data into the template view using the data object

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/YY");

        binNumber.setText(String.valueOf(bin.getNumber()));
        binFirm.setText(bin.getFirmName());
        binFrequency.setText(bin.getFrequency());
        binCollectDate.setText(bin.getCollectDate().toString(formatter));
        if (isLessThanTwoDays(bin.getCollectDate())){
            exclamationView.setVisibility(View.VISIBLE);
//            binNumber.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }
        // Return the completed view to render on screen
        return convertView;
    }

    private boolean isLessThanTwoDays(DateTime collectDate){
        DateTime now = DateTime.now();
        Period period = new Period(now, collectDate);
        int days = period.getDays();
        return period.getYears() <= 0 && period.getWeeks() <= 0 && period.getDays() <= 2;
    }
}
