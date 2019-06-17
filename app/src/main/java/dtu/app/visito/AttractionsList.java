package dtu.app.visito;

import android.app.Activity;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dtu.app.visito.R;


public class AttractionsList extends Activity {

    private static final String TAG = "ViewDatabase";

    //add Firebase Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    ListView mListView;
    ArrayList<String> lstAttractions;
    ArrayList<String> lstImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_list);

        mListView=(ListView)findViewById(R.id.attraction_listview);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                Intent intent = new Intent(AttractionsList.this, AttractionsList.class);
                startActivity(intent);
            }
        });

    }

    private void showList(DataSnapshot dataSnapshot) {
        lstAttractions = new ArrayList<>();
        lstImages = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            lstAttractions.add(ds.child("title").getValue().toString());
            lstImages.add(ds.child("img").getValue().toString());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstAttractions);

        mListView.setAdapter(adapter);
    }


}
