package edu.wision_assignment.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.wision_assignment.BaseActivity;
import edu.wision_assignment.R;
import edu.wision_assignment.adapter.PlaylistAdapter;

public class DashboardActivity extends BaseActivity {

    private ArrayList<String> urlimage;
    private RecyclerView rvPlaylist;
    private PlaylistAdapter adapter;
    private Toolbar toolbar;
    private TextView txtUsername;
    private boolean doubleBackToExit = false;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    int count = 0, data_counts = 0;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvPlaylist = (RecyclerView) findViewById(R.id.rvPlaylist);
        txtUsername = (TextView) toolbar.findViewById(R.id.txtUsername);
        progressBar = findViewById(R.id.progressBar);
        txtUsername.setText(initSharedPref().getString("username",""));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        checkSession(DashboardActivity.this);
        parseJsonData(loadJsonData(), 0);
        initRV();

        rvPlaylist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            count += 10;
                            if (count <= data_counts - 10) {
                                progressBar.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);

                                        parseJsonData(loadJsonData(), count);
                                    }
                                }, 2000);
                                //Do pagination.. i.e. fetch new data
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(DashboardActivity.this, "End Of Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

    }

    //Method to parse json data
    private void parseJsonData(String data, int starting_count) {
        try {
            loading=true;
            JSONObject jsonObject = new JSONObject(data);
            data_counts = jsonObject.getJSONObject("pageInfo").optInt("resultsPerPage");
            JSONArray data_array = jsonObject.getJSONArray("items");
            if (urlimage == null)
                urlimage = new ArrayList<>();
            for (int i = starting_count; i < starting_count + 10; i++) {
                JSONObject item_data = data_array.getJSONObject(i);
                String url = item_data.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                urlimage.add(url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initRV() {
        progressBar.setVisibility(View.GONE);
        adapter = new PlaylistAdapter(DashboardActivity.this, urlimage);
        rvPlaylist.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(DashboardActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlaylist.setLayoutManager(layoutManager);


    }

    //Method to read dummy data from Assets
    private String loadJsonData() {

        String json = null;
        try {
            InputStream is = this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logout) {
            Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
            logout(initSharedPref());
            checkSession(DashboardActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExit = true;
        Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        }, 2000);
    }
}

