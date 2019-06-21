package dtu.app.visito;

import android.content.Intent;
import android.graphics.Typeface;
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

public class MainActivity extends AppCompatActivity {

    private Button topAttractionsBtn, weatherBtn, currencyBtn,btn2;
    private TextView title;
    private Intent intent;
    private LinearLayout mapFragment;

    private Boolean isMapOpen = false;

    @Override
    public void onBackPressed() {
        if (isMapOpen){
            currencyBtn.setVisibility(View.VISIBLE);
            weatherBtn.setVisibility(View.VISIBLE);
            topAttractionsBtn.setVisibility(View.VISIBLE);
            mapFragment.setVisibility(View.GONE);
            btn2.setText("Show map");
            isMapOpen=false;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        topAttractionsBtn = findViewById(R.id.topAttractionsBtn);
        weatherBtn = findViewById(R.id.weatherBtn);
        currencyBtn = findViewById(R.id.currencyBtn);
        btn2 = findViewById(R.id.mapBTN);
        mapFragment = findViewById(R.id.mapFragment);
        btn2.setTag(1);
        btn2.setText("Show map");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
        title.setTypeface(tf);

        topAttractionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AttractionsList.class);
                startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Weather.class);
                startActivity(intent);
            }
        });

        currencyBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Currency.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMapOpen) {
                    addFragment(new MapFragment(), false, "one");
                    currencyBtn.setVisibility(View.GONE);
                    weatherBtn.setVisibility(View.GONE);
                    topAttractionsBtn.setVisibility(View.GONE);
                    mapFragment.setVisibility(View.VISIBLE);
                    btn2.setText("Close map");
                    isMapOpen=true;
                } else {
                    currencyBtn.setVisibility(View.VISIBLE);
                    weatherBtn.setVisibility(View.VISIBLE);
                    topAttractionsBtn.setVisibility(View.VISIBLE);
                    mapFragment.setVisibility(View.GONE);
                    btn2.setText("Show map");
                    isMapOpen=false;
                }
            }
        });

        ShakeDetector sd = new ShakeDetector(getApplicationContext(), MainActivity.this);
        sd.detectShake();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager =getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.mapFragment, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}