package dtu.app.visito;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


public class LoadingScreen extends Activity{

    private TextView title, slogan, loadingText;
    private GlobalClass globalClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        globalClass = (GlobalClass) getApplicationContext();

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

            } else{
                startMainAct();
            }
        }
        startMainAct();
    }

    private void startMainAct(){
        final Intent i = new Intent(LoadingScreen.this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
                finish();
            }
        }, 5000);

    }
}
