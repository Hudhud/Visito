package dtu.app.visito;

import android.app.Application;
import android.content.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GlobalData extends Application {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    private ArrayList<DataSnapshot> dsArrayList = new ArrayList<>();

    public ArrayList<DataSnapshot> getDsArrayList() {
        return dsArrayList;
    }

    public void enableFirebaseOfflineCapabilities(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
        mDatabase = mFirebaseDatabase.getReference();;
        mDatabase.keepSynced(true);
    }

    public DatabaseReference getmDatabase(){
        return mDatabase;
    }
}
