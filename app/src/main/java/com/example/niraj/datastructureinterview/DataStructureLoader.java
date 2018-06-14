package com.example.niraj.datastructureinterview;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class DataStructureLoader extends AsyncTaskLoader<List<DataStructure>> {

    /** Tag for log messages */
    private static final String LOG_TAG = DataStructureLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link DataStructureLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public DataStructureLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<DataStructure> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of DataStructures.
        List<DataStructure> DataStructures = QueryUtils.fetchDataStructureData(mUrl);
        return DataStructures;
    }
}

