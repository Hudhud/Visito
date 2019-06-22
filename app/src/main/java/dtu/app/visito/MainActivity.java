package dtu.app.visito;

import android.app.Activity;
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

public class MainActivity extends Activity {

    private Button topAttractionsBtn, weatherBtn, currencyBtn;
    private TextView title;
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        topAttractionsBtn = findViewById(R.id.topAttractionsBtn);
        weatherBtn = findViewById(R.id.weatherBtn);
        currencyBtn = findViewById(R.id.currencyBtn);
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

        ShakeDetector sd = new ShakeDetector(getApplicationContext(), MainActivity.this);
        sd.detectShake();
    }
}