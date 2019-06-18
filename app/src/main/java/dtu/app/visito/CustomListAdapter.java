package dtu.app.visito;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> lstAttractions;
    private final ArrayList<String> lstAttractionIcons;

    StorageReference gsReference;

    public CustomListAdapter(Activity context, ArrayList<String> lstAttractions, ArrayList<String> lstAttractionIcons) {
        super(context, R.layout.list_layout, lstAttractions);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.lstAttractions=lstAttractions;
        this.lstAttractionIcons=lstAttractionIcons;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_layout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvAttractionTitle);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.ivIcon);

        txtTitle.setText(lstAttractions.get(position));
        imageView.setImageDrawable(null);

        return rowView;
    }
}
