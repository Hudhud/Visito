package dtu.app.visito;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by clairnorahmutebi on 18/06/2019.
 */

public class AttractionDescription extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attract_description);

        int index = 4;




        final GlobalData globalData = (GlobalData) getApplicationContext();


        final TextView attractionDescription = (TextView) findViewById(R.id.attractionDescription);
        attractionDescription.setText(globalData.getDsArrayList().get(index).child("body").getValue().toString());

        final TextView attractionTitle = (TextView) findViewById(R.id.attractionTitle);
        attractionTitle.setText(globalData.getDsArrayList().get(index).child("title").getValue().toString());

        final ImageView attractionImage = (ImageView) findViewById(R.id.imageAttraction);
        String imgURL = (globalData.getDsArrayList().get(index).child("img").getValue().toString());
        loadImageFromURL(attractionImage,imgURL);

        final Button button = (Button) findViewById(R.id.map);




    }

    public void loadImageFromURL(ImageView attractionImage, String imgURL) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build());
        try {
            URL url = new URL(imgURL);
            attractionImage.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

