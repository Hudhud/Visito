package dtu.app.visito;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShakeDetector {


    private Context mContext;
    private SensorManager sensorManager;
    private float acelCurrentValue, acelLastValue, accelExGravity;
    private Activity act;
    private int dialogCounter, databaseChildrenCounter, attractionLimit;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();;
    private ArrayList<DataSnapshot> dataSnapshots = new ArrayList<>();

    public ShakeDetector(Context mContext, Activity act) {
        this.mContext = mContext;
        this.act = act;
    }

    public void detectShake() {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelCurrentValue = SensorManager.GRAVITY_EARTH;
        acelLastValue = SensorManager.GRAVITY_EARTH;
        accelExGravity = 0.00f;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLastValue = acelCurrentValue;
            acelCurrentValue = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelCurrentValue - acelLastValue;
            accelExGravity = accelExGravity * 0.9f + delta;

            if (accelExGravity > 20) {
                /*intent = new Intent(act, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);*/

              if (dialogCounter < 1){
                  dialogCounter++;
                  displayDialog();
              }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void displayDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(act);
        final View dialog = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);
        alertDialogBuilder.setView(dialog);

        final EditText attractionTitle = dialog.findViewById(R.id.attractionTitle);
        final EditText attractionImageURL =  dialog.findViewById(R.id.attractionURL);
        final EditText attractionDescription = dialog.findViewById(R.id.attractionDescription);

        final EditText attractionLatitude = dialog.findViewById(R.id.attractionlattitude);
        final EditText attractionLongitude = dialog.findViewById(R.id.attractionlongitude);

        final TextView errorText = dialog.findViewById(R.id.error);
        final Button okBtn = dialog.findViewById(R.id.okBtn);
        final Button cancelBtn = dialog.findViewById(R.id.cancelBtn);


        final AlertDialog alert = alertDialogBuilder.create();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                dialogCounter = 0;
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attractionTitle.getText().length() < 1 ||
                        attractionImageURL.getText().length() < 1 ||
                        attractionDescription.getText().length() < 1 ||
                        attractionLatitude.getText().length() < 1 ||
                        attractionLongitude.getText().length() < 1) {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("All fields must be filled out");
                } else if (Float.valueOf(attractionLatitude.getText().toString()) < -90 || Float.valueOf(attractionLatitude.getText().toString()) > 90 ||
                        Float.valueOf(attractionLongitude.getText().toString()) < -180 || Float.valueOf(attractionLongitude.getText().toString()) > 180) {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Incorrect latitude and/or longitude");
                } else {

                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                databaseChildrenCounter = (int) dataSnapshot.getChildrenCount();
                            }

                            System.out.println("Key1 " + databaseChildrenCounter);

                            Attraction attraction = new Attraction(attractionTitle.getText().toString(),
                                    attractionDescription.getText().toString(), attractionImageURL.getText().toString(),
                                    Float.valueOf(attractionLatitude.getText().toString()),
                                    Float.valueOf(attractionLongitude.getText().toString()));


                            for (DataSnapshot item : dataSnapshot.getChildren()) {

                                if (item.child("title").getValue().toString().equals(attractionTitle.getText().toString())) {
                                    errorText.setVisibility(View.VISIBLE);
                                    errorText.setText("Attraction already exists");
                                    System.out.println("DADADADA");
                                } else if (!item.child("title").getValue().toString().equals(attractionTitle.getText().toString())){
                                    mDatabaseReference.child(Integer.toString(databaseChildrenCounter+10)).setValue(attraction).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    alert.dismiss();
                                                    dialogCounter = 0;
                                                }
                                            });

                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        alert.show();

    }
}
