package dtu.app.visito;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button topAttractionsBtn, weatherBtn, currencyBtn;
    private TextView title;
    private Intent intent;
    private ProgressDialog pd;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShakeDetector sd = new ShakeDetector(getApplicationContext(), MainActivity.this);
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        pd = new ProgressDialog(MainActivity.this);

        title = findViewById(R.id.title);
        topAttractionsBtn = findViewById(R.id.topAttractionsBtn);
        weatherBtn = findViewById(R.id.weatherBtn);
        currencyBtn = findViewById(R.id.currencyBtn);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
        title.setTypeface(tf);

        topAttractionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalClass.checkConnectivity("You cannot view new attractions without" +
                        "an internet connection");

                    intent = new Intent(MainActivity.this, AttractionsList.class);

                     pd.setMessage("Please wait...");
                     pd.show();

                startActivity(intent);

            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (globalClass.checkConnectivity("You cannot view new attractions without" +
                        "an internet connection")) {
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

        sd.detectShake();
    }
}