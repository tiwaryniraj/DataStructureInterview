package com.example.niraj.datastructureinterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class DataStructureAdapter extends ArrayAdapter<DataStructure> {


    /**
     * Constructs a new {@link DataStructureAdapter}.
     *
     * @param context of the app
     * @param DataStructures is the list of DataStructures, which is the data source of the adapter
     */
    public DataStructureAdapter(Context context, List<DataStructure> DataStructures) {
        super(context, 0, DataStructures);
    }

    /**
     * Returns a list item view that displays information about the DataStructure at the given position
     * in the list of DataStructures.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.datastructure_list_item, parent, false);
        }

        // Find the DataStructure at the given position in the list of DataStructures
        DataStructure currentDataStructure = getItem(position);

        // Find the TextView with view ID magnitude
        TextView questionView = listItemView.findViewById(R.id.question);
        // Display the magnitude of the current DataStructure in that TextView
        questionView.setText(currentDataStructure.getmQuestion());

        TextView answerView = listItemView.findViewById(R.id.answer);
        // Display the magnitude of the current DataStructure in that TextView
        answerView.setText(currentDataStructure.getmAnswer());
        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
