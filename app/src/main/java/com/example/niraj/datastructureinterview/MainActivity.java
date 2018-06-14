package com.example.niraj.datastructureinterview;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<DataStructure>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** URL for DataStructure data from the USGS dataset */
    private static final String LCO_REQUEST_URL =
            "https://learncodeonline.in/api/android/datastructure.json";

    /**
     * Constant value for the DataStructure loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int DataStructure_LOADER_ID = 1;

    /** Adapter for the list of DataStructures */
    private DataStructureAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://courses.learncodeonline.in/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        // Find a reference to the {@link ListView} in the layout
        ListView DataStructureListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        DataStructureListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of DataStructures as input
        mAdapter = new DataStructureAdapter(this, new ArrayList<DataStructure>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        DataStructureListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected DataStructure.
        DataStructureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current DataStructure that was clicked on
                DataStructure currentDataStructure = mAdapter.getItem(position);

                Intent i = new Intent(MainActivity.this ,QuestionActivity.class);
                i.putExtra("question", currentDataStructure.getmQuestion());
                i.putExtra("answer", currentDataStructure.getmAnswer());
                startActivity(i);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(DataStructure_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<DataStructure>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new DataStructureLoader(this, LCO_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DataStructure>> loader, List<DataStructure> DataStructures) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No DataStructures found."
        mEmptyStateTextView.setText(R.string.no_DataStructures);

        // Clear the adapter of previous DataStructure data
        mAdapter.clear();

        // If there is a valid list of {@link DataStructure}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (DataStructures != null && !DataStructures.isEmpty()) {
            mAdapter.addAll(DataStructures);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DataStructure>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
