package dtu.app.visito;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoadingScreen extends Activity {

    private TextView title, slogan, loadingText;
    private GlobalClass globalClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        globalClass = (GlobalClass) getApplicationContext();
        globalClass.getFirebaseDatabaseRef().setPersistenceEnabled(true);
        globalClass.getDatabaseRef().keepSynced(true);

        final boolean initialRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("initialRun", true);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");

        title = findViewById(R.id.title);
        title.setTypeface(tf);

        slogan = findViewById(R.id.slogan);
        slogan.setTypeface(tf);

        loadingText = findViewById(R.id.loadingText);
        loadingText.setTypeface(tf);

        if (initialRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().
                    putBoolean("initialRun", false).commit();

            if (globalClass.checkConnectivity("You have to enable an internet connection on the initial run of the app")){
                downloadDataFromFirebase();
            }
        }

        downloadDataFromFirebase();

    }

    public void downloadDataFromFirebase(){

        globalClass.getDatabaseRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    globalClass.getDsArrayList().add(child);
                }
                Intent i = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(i);
                finish();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
