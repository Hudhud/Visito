package dtu.app.visito;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class AttractionsList extends AppCompatActivity {

    private ListView mListView;
    private LinearLayout mAttractionDescriptionLayout;
    private ScrollView mScrollView;
    private ImageView mImageAttraction;
    private TextView mTitle;
    private TextView mAttractionDescription;

    private ArrayList<DataSnapshot> lstAttractionInfo;
    private ArrayList<String> lstAttractionTitles = new ArrayList<>();
    private ArrayList<String> lstAttractionIcons = new ArrayList<>();
    private ArrayList<String> lstAttractionDescription = new ArrayList<>();
    private ArrayList<Float> lstAttractionLat = new ArrayList<>();
    private ArrayList<Float> lstAttractionLong = new ArrayList<>();

    private Button map,mapDirection;
    private LinearLayout mapFragment;


    private Boolean isDescriptionOpen = false;
    private Boolean isMapOpen = false;


    @Override
    public void onBackPressed() {
        if (isDescriptionOpen){
            mListView.setVisibility(View.VISIBLE);
            mAttractionDescriptionLayout.setVisibility(View.GONE);
            isDescriptionOpen=false;
            mapFragment.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            map.setVisibility(View.VISIBLE);
            return;
        } else if (isMapOpen){
            mListView.setVisibility(View.VISIBLE);
            mapFragment.setVisibility(View.GONE);
            map.setText("Show map");
            getSupportActionBar().show();
            isMapOpen=false;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);


        getSupportActionBar().setTitle("Top attractions");
        mListView=(ListView)findViewById(R.id.lvAttractionsList);
        mAttractionDescriptionLayout = (LinearLayout) findViewById(R.id.description_layout);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mImageAttraction = (ImageView) findViewById(R.id.imageAttraction);
        mTitle = (TextView) findViewById(R.id.title);
        mAttractionDescription = (TextView) findViewById(R.id.attractionDescription);
        map = findViewById(R.id.mapBTN);
        mapDirection =  findViewById(R.id.directionBtn);
        mapFragment = findViewById(R.id.mapFragment);
        map.setTag(1);
        map.setText("Show map");
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final ProgressDialog pd = new ProgressDialog(AttractionsList.this);


        lstAttractionInfo = globalClass.getDsArrayList();

        for (DataSnapshot i: lstAttractionInfo){
            lstAttractionTitles.add(i.child("title").getValue().toString());
            lstAttractionIcons.add(i.child("img").getValue().toString());
            lstAttractionDescription.add(i.child("body").getValue().toString());
            lstAttractionLat.add(Float.valueOf(i.child("latitude").getValue().toString()));
            lstAttractionLong.add(Float.valueOf(i.child("longitude").getValue().toString()));

        }


        final CustomListAdapter adapter=new CustomListAdapter(this, lstAttractionTitles, lstAttractionIcons);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                globalClass.checkConnectivity("You cannot view the images of the attractions without" +
                        "an internet connection");

                pd.setMessage("Please wait...");
                pd.show();

                mListView.setVisibility(View.GONE);
                map.setVisibility(View.GONE);
                mapFragment.setVisibility(View.GONE);
                mTitle.setText(lstAttractionTitles.get(position));
                mAttractionDescription.setText(lstAttractionDescription.get(position));
                getSupportActionBar().hide();

                mImageAttraction.setImageBitmap(null);

                mapDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDirection(lstAttractionLat.get(position), lstAttractionLong.get(position));
                    }
                });

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadImageFromURL(mImageAttraction, lstAttractionIcons.get(position));
                    }
                });

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        pd.dismiss();
                        t.cancel();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAttractionDescriptionLayout.setVisibility(View.VISIBLE);
                                mScrollView.scrollTo(0,0);
                            }
                        });
                        isDescriptionOpen=true;
                    }
                }, 3000);




            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMapOpen) {
                    addFragment(new MapFragment(), false, "one");
                    mListView.setVisibility(View.GONE);
                    mapFragment.setVisibility(View.VISIBLE);
                    getSupportActionBar().hide();
                    map.setText("Close map");
                    isMapOpen=true;
                } else {
                    mListView.setVisibility(View.VISIBLE);
                    mapFragment.setVisibility(View.GONE);
                    map.setText("Show map");
                    getSupportActionBar().show();
                    isMapOpen=false;
                }
            }
        });
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.mapFragment, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    public void showDirection (float latitude, float longitude){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude));
        startActivity(intent);
    }

}
