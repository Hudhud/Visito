package dtu.app.visito;

import android.content.Intent;
import android.net.Uri;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class AttractionsList extends AppCompatActivity {

    private ListView mListView;
    private LinearLayout mAttractionDescriptionLayout;
    private ScrollView mScrollView;
    private ImageView mImageAttraction;
    private TextView mAttractionDescription;
    private ArrayList<Attraction> lstAttractionInfo;
    private ArrayList<String> lstAttractionTitles = new ArrayList<>();
    private ArrayList<String> lstAttractionIcons = new ArrayList<>();
    private ArrayList<String> lstAttractionDescription = new ArrayList<>();
    private ArrayList<Float> lstAttractionLat = new ArrayList<>();
    private ArrayList<Float> lstAttractionLong = new ArrayList<>();
    private Button map,mapDirection;
    private LinearLayout mapFragment;
    private Boolean isDescriptionOpen = false;
    private Boolean isMapOpen = false;
    private GlobalClass globalClass;
    private CustomListAdapter adapter;


    @Override
    public void onBackPressed() {
        if (isDescriptionOpen){
            mListView.setVisibility(View.VISIBLE);
            mAttractionDescriptionLayout.setVisibility(View.GONE);
            isDescriptionOpen=false;
            mapFragment.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            map.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Top Attractions");
            return;
        } else if (isMapOpen){
            mListView.setVisibility(View.VISIBLE);
            mapFragment.setVisibility(View.GONE);
            map.setText("Show map");
            getSupportActionBar().show();
            isMapOpen=false;
            return;
        }

        Intent i = new Intent(AttractionsList.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        getSupportActionBar().setTitle("Top Attractions");
        mListView= findViewById(R.id.lvAttractionsList);
        mAttractionDescriptionLayout = findViewById(R.id.description_layout);
        mScrollView = findViewById(R.id.scrollView);
        mImageAttraction = findViewById(R.id.imageAttraction);
        mAttractionDescription = findViewById(R.id.attractionDescription);
        map = findViewById(R.id.mapBTN);
        mapDirection =  findViewById(R.id.direction);
        mapFragment = findViewById(R.id.mapFragment);
        map.setTag(1);
        map.setText("Show map");
        globalClass = (GlobalClass) getApplicationContext();

        lstAttractionInfo = Utils.clone(globalClass.getDsArrayList(), new TypeReference<ArrayList<Attraction>>() {});

        for (Attraction attr: lstAttractionInfo){
            lstAttractionTitles.add(attr.getTitle());
            lstAttractionIcons.add(attr.getImg());
            lstAttractionDescription.add(attr.getBody());
            lstAttractionLat.add(attr.getLatitude());
            lstAttractionLong.add(attr.getLongitude());
        }


        adapter = new CustomListAdapter(this, lstAttractionTitles, lstAttractionIcons);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                globalClass.checkConnectivity("You cannot view the images of the attractions without" +
                        "an internet connection");

                mListView.setVisibility(View.GONE);
                map.setVisibility(View.GONE);
                mapFragment.setVisibility(View.GONE);

                getSupportActionBar().setTitle(lstAttractionTitles.get(position));
                mAttractionDescription.setText(lstAttractionDescription.get(position));
                mAttractionDescriptionLayout.setVisibility(View.VISIBLE);
                mScrollView.scrollTo(0,0);

                mImageAttraction.setImageBitmap(null);
                Picasso.get().load(lstAttractionIcons.get(position)).into(mImageAttraction);


                isDescriptionOpen=true;

                mapDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDirection(lstAttractionLat.get(position), lstAttractionLong.get(position));
                    }
                });

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
