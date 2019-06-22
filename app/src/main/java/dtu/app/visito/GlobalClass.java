package dtu.app.visito;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GlobalClass extends Application {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    private boolean showLoading;

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

    public void showLoadingDialog(){
        showLoading = true;
    }

    public boolean isShowLoading() {
        return showLoading;
    }

}
