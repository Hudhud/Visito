package dtu.app.visito;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> lstAttractions;
    private final ArrayList<String> lstAttractionIcons;
    private GlobalClass globalClass;

    public CustomListAdapter(Activity context, ArrayList<String> lstAttractions, ArrayList<String> lstAttractionIcons) {
        super(context, R.layout.list_layout, lstAttractions);

        this.context=context;
        this.lstAttractions=lstAttractions;
        this.lstAttractionIcons=lstAttractionIcons;
        globalClass = (GlobalClass) context.getApplicationContext();
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvAttractionTitle);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.ivIcon);

        txtTitle.setText(lstAttractions.get(position));

        loadImageFromURL(imageView, lstAttractionIcons.get(position));

        if (position == lstAttractions.size() - 1) {
            globalClass.showLoadingDialog();
        }


        return rowView;
    }

    public void loadImageFromURL(ImageView iv, String imgURL) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .build());
        try {
            URL url = new URL(imgURL);
            iv.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }
}
