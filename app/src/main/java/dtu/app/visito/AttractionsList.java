package dtu.app.visito;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import dtu.app.visito.R;


public class AttractionsList extends Activity {

    private ListView mListView;
    private LinearLayout mAttractionDescriptionLayout;
    private ImageView mImageAttraction;
    private TextView mTitle;
    private TextView mAttractionDescription;



    private ArrayList<DataSnapshot> lstAttractionInfo;
    private ArrayList<String> lstAttractionTitles = new ArrayList<>();
    private ArrayList<String> lstAttractionIcons = new ArrayList<>();
    private ArrayList<String> lstAttractionDescription = new ArrayList<>();

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        mListView=(ListView)findViewById(R.id.lvAttractionsList);
        mAttractionDescriptionLayout = (LinearLayout) findViewById(R.id.description_layout);
        mImageAttraction = (ImageView) findViewById(R.id.imageAttraction);
        mTitle = (TextView) findViewById(R.id.title);
        mAttractionDescription = (TextView) findViewById(R.id.attractionDescription);

        final GlobalData globalData = (GlobalData) getApplicationContext();

        lstAttractionInfo = globalData.getDsArrayList();

        for (DataSnapshot i: lstAttractionInfo){
            lstAttractionTitles.add(i.child("title").getValue().toString());
            lstAttractionIcons.add(i.child("img").getValue().toString());
            lstAttractionDescription.add(i.child("body").getValue().toString());
        }

        final CustomListAdapter adapter=new CustomListAdapter(this, lstAttractionTitles, lstAttractionIcons);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mListView.setVisibility(View.GONE);
                mTitle.setText(lstAttractionTitles.get(position));
                mAttractionDescription.setText(lstAttractionDescription.get(position));
                adapter.loadImageFromURL(mImageAttraction, lstAttractionIcons.get(position));
                mAttractionDescriptionLayout.setVisibility(View.VISIBLE);

                //String selectedItem = lstAttractionTitles.get(+position);
                //Toast.makeText(getApplicationContext(), "Going to fragment for " + selectedItem, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
