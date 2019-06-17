package dtu.app.visito;

import android.app.Application;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class GlobalData extends Application {

    private ArrayList<DataSnapshot> dsArrayList = new ArrayList<>();

    public ArrayList<DataSnapshot> getDsArrayList() {
        return dsArrayList;
    }
}
