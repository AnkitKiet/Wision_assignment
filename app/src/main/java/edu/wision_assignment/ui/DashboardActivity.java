package edu.wision_assignment.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.wision_assignment.R;
import edu.wision_assignment.adapter.PlaylistAdapter;

public class DashboardActivity extends AppCompatActivity {

    private ArrayList<String> urlimage;
    private RecyclerView rvPlaylist;
    private PlaylistAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvPlaylist = (RecyclerView) findViewById(R.id.rvPlaylist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        parseJsonData(loadJsonData());

    }

    //Method to parse json data
    private void parseJsonData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int data_counts = jsonObject.getJSONObject("pageInfo").optInt("resultsPerPage");
            JSONArray data_array = jsonObject.getJSONArray("items");
            urlimage = new ArrayList<>();
            for (int i = 0; i < data_counts; i++) {
                JSONObject item_data = data_array.getJSONObject(i);
                String url = item_data.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                urlimage.add(url);
            }
            initRV();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initRV() {
        adapter = new PlaylistAdapter(DashboardActivity.this, urlimage);
        rvPlaylist.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this);
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

}

