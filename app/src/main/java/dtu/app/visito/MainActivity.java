package dtu.app.visito;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button topAttractionsBtn, weatherBtn, currencyBtn;
    private TextView title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalData globalData = (GlobalData) getApplicationContext();

        title = findViewById(R.id.title);
        topAttractionsBtn = findViewById(R.id.topAttractionsBtn);
        weatherBtn = findViewById(R.id.weatherBtn);
        currencyBtn = findViewById(R.id.currencyBtn);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
        title.setTypeface(tf);

        topAttractionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalData.checkConnectivity("You cannot view the images of the attractions without" +
                        "an internet connection");
                    intent = new Intent(MainActivity.this, AttractionsList.class);
                    startActivity(intent);

            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (globalData.checkConnectivity("You cannot view the weather without an internet" +
                        "connectivity")) {
                    intent = new Intent(MainActivity.this, Weather.class);
                    startActivity(intent);
                }
            }
        });

        currencyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Currency.class);
                startActivity(intent);
            }
        });

        ShakeDetector sd = new ShakeDetector(getApplicationContext(), MainActivity.this);
        sd.detectShake();
    }
}