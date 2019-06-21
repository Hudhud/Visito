package dtu.app.visito;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingScreen extends Activity {

    private TextView title, slogan, loadingText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        final GlobalData globalData = (GlobalData) getApplicationContext();

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");

        title = findViewById(R.id.title);
        title.setTypeface(tf);

        slogan = findViewById(R.id.slogan);
        slogan.setTypeface(tf);

        loadingText = findViewById(R.id.loadingText);
        loadingText.setTypeface(tf);

        globalData.enableFirebaseOfflineCapabilities();

        globalData.getmDatabase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    globalData.getDsArrayList().add(child);
                }
                Intent i = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(i);
                finish();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(10000);  //Delay of 10 seconds
                    Intent i = new Intent(LoadingScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        welcomeThread.start();

        */
    }

}
