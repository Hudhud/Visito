package dtu.app.visito;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class GlobalClass extends Application {
    private FirebaseDatabase firebaseDatabaseRef;
    private DatabaseReference databaseRef;
    private ArrayList<DataSnapshot> dsArrayList = new ArrayList<>();


    public ArrayList<DataSnapshot> getDsArrayList() {
        return dsArrayList;
    }

    public ArrayList<DataSnapshot> updateDsArrayList() {
        final ArrayList<DataSnapshot> tempArray = new ArrayList<>();

        getDatabaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                if (tempArray != null && dsArrayList != null) {
                    tempArray.clear();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        tempArray.add(child);
                    }

                    dsArrayList.clear();
                    if (!dsArrayList.equals(tempArray)) {
                        dsArrayList.addAll(tempArray);
                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return dsArrayList;
    }

    public FirebaseDatabase getFirebaseDatabaseRef(){
        firebaseDatabaseRef = FirebaseDatabase.getInstance();
        return firebaseDatabaseRef;
    }

    public DatabaseReference getDatabaseRef(){
        databaseRef = getFirebaseDatabaseRef().getReference();
        return databaseRef;
    }

    public boolean checkConnectivity(String errorMsg){

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null || activeNetwork.isConnected() == false || activeNetwork.isConnectedOrConnecting() == false) {
            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void loadImageFromURL(ImageView iv, String imgURL) {
        Picasso.get().load(imgURL).into(iv);
    }

}
