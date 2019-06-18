package dtu.app.visito;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import dtu.app.visito.R;


public class AttractionsList extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<DataSnapshot> lstAttractionInfo;
    private ArrayList<String> lstAttractions = new ArrayList<>();
    private ArrayList<String> lstAttractionIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        final GlobalData globalData = (GlobalData) getApplicationContext();

        lstAttractionInfo = globalData.getDsArrayList();

        for (DataSnapshot i: lstAttractionInfo){
            lstAttractions.add(i.child("title").getValue().toString());
            lstAttractionIcons.add(i.child("img").getValue().toString());
        }

        CustomListAdapter adapter=new CustomListAdapter(this, lstAttractions, lstAttractionIcons);
        mListView=(ListView)findViewById(R.id.lvAttractionsList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //String Slecteditem = lstAttractions.indexOf(+position);
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

    }


}
