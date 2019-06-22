package dtu.app.visito;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

public class Weather extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("https://weather.com/weather/tenday/l/Copenhagen+Denmark+DAXX0009");
    }
}
