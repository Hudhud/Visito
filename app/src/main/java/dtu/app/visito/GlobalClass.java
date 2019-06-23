package dtu.app.visito;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private ArrayList<Attraction> dsArrayList = new ArrayList<>();
    private boolean listIsReady;

    @Override
    public void onCreate() {
        super.onCreate();

        getFirebaseDatabaseRef().setPersistenceEnabled(true);
        getDatabaseRef().keepSynced(true);

        getDatabaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                final ArrayList<Attraction> tempArray = new ArrayList<>();

                if (tempArray != null && dsArrayList != null) {
                    tempArray.clear();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {

                        String title =child.child("title").getValue().toString();
                        String img =child.child("img").getValue().toString();
                        String body =child.child("body").getValue().toString();
                        float latitude = Float.valueOf(child.child("latitude").getValue().toString());
                        float longitude =Float.valueOf(child.child("longitude").getValue().toString());

                        Attraction attraction = new Attraction(title, body, img, latitude, longitude);

                        tempArray.add(attraction);
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

    }

    public ArrayList<Attraction> getDsArrayList() {
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
