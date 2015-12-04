package com.cardinalblue.trek;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;

import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StartPageActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener,
               SeekBar.OnSeekBarChangeListener,
               CompoundButton.OnCheckedChangeListener,
               View.OnClickListener{

    public static final String KEY_LOC_TITLE = "location_title";
    public static final String KEY_SRC_LINK = "source_link";
    public static final String KEY_ID = "key_id";
    private final int EDIT_REQUEST = 100;
    public static final int RESULT_CONFIRM = 200;
    public static final int RESULT_OPEN_BROWSER = 201;

    private ListView mListView;
    private CustomListViewAdapter mAdapter;
    private MapView mMapView;
    private SeekBar mSeekBar;

    private TripPlans.Folder mTripFolder;
    private TripPlans mTripPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = (ListView) findViewById(R.id.main_listview);
        mSeekBar = (SeekBar) findViewById(R.id.main_seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);

        mAdapter = new CustomListViewAdapter(this);
        mAdapter.setCheckChangedListener(this, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        // Map view.
        mMapView = (MapView) findViewById(R.id.mapview);

        // Trip plans.
        mTripPlans = new TripPlans(this);
        mTripFolder = mTripPlans.mData.get(0);

        // Testing model.
        for (int i = 0; i < mTripFolder.mFolder.size(); ++i) {
            TripPlans.Spot spot = mTripFolder.mFolder.get(i);
            Marker marker = new Marker(mMapView, spot.mLocation, "description", spot.mLatLng);

            marker.setIcon(new Icon(this, Icon.Size.SMALL, "marker-stroked", "FF0000"));


            mMapView.addMarker(marker);

            if (i == 0) {
                mMapView.setCenter(spot.mLatLng);
            } else {
                // Path overlay.
                TripPlans.Spot prevSpot = mTripFolder.mFolder.get(i - 1);
                PathOverlay path = new PathOverlay();

                path.getPaint().setStyle(Paint.Style.STROKE);
                path.getPaint().setColor(getPathColor(i));

                path.addPoint(prevSpot.mLatLng);
                path.addPoint(spot.mLatLng);

                mMapView.addOverlay(path);
            }
        }

        mMapView.setZoom(7);

        // (TODO) get JSON string from DB.
        try {
            String jsonStr = getJSONStr();
            addItem(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //(TODO) Change the mark image here
    public void changeMarkerImage(int id, boolean isChecked) {

    }

    public String getJSONStr() throws JSONException {
        JSONArray arr = new JSONArray();

        for (int i = 0 ; i < mTripFolder.mFolder.size() ; ++i) {
            TripPlans.Spot spot = mTripFolder.mFolder.get(i);
            JSONObject json = new JSONObject();
            json.put(KEY_LOC_TITLE, spot.mLocation);
            json.put(KEY_SRC_LINK, spot.mSourceLink);
            arr.put(json);
        }

        return arr.toString();
    }

    public void addItem(String jsonStr) throws JSONException {
        JSONArray arr = new JSONArray(jsonStr);
        for (int i = 0 ; i < arr.length() ; ++i) {
            JSONObject json = arr.getJSONObject(i);
            mAdapter.addItem(json.getString(KEY_LOC_TITLE), json.getString(KEY_SRC_LINK));
        }
        mListView.invalidate();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listview_title_textview:
                int position = (int) v.getTag();
                Intent intent = new Intent();
                intent.setClass(this, EditActivity.class);
                intent.putExtra(KEY_LOC_TITLE, mAdapter.getLocationTitle(position));
                intent.putExtra(KEY_SRC_LINK, mAdapter.getUrl(position));
                intent.putExtra(KEY_ID, position);
                startActivityForResult(intent, EDIT_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_REQUEST:
                if (resultCode == RESULT_OPEN_BROWSER) {
                    String url = data.getStringExtra(KEY_SRC_LINK);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (resultCode == RESULT_CONFIRM) {
                    mAdapter.update(data);
                    mListView.invalidate();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = (int) buttonView.getTag();
        if (isChecked && id > mSeekBar.getProgress()) {
            mSeekBar.setProgress(id);
        }
        changeMarkerImage(id, isChecked);
        mAdapter.setChecked(id, isChecked);
        mListView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (int i = 0 ; i < mAdapter.getCount() ; ++i) {
            mAdapter.setChecked(i, i < progress);
        }
        mListView.invalidate();
        mListView.invalidateViews();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getPathColor(int position) {
        switch (position) {
            case 1:
                return 0xFFE64545;
            case 2:
                return 0xFFE69545;
            case 3:
                return 0xFF66CC66;
            case 4:
                return 0xFF3DCCCC;
            case 5:
                return 0xFF6699CC;
            case 6:
                return 0xFF7E6CD9;
            case 7:
                return 0xFFCC66CC;
        }

        return Color.LTGRAY;
    }
}
