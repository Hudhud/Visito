package dtu.app.visito;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.net.URL;

public class ShakeDetector {

    private Context mContext;
    private SensorManager sensorManager;
    private float acelCurrentValue, acelLastValue, accelExGravity;
    private Activity act;
    private boolean canOpenLoginDialog = true;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = mFirebaseDatabase.getReference();
    private GlobalClass globalClass;
    private boolean attractionExists;

    public ShakeDetector(Context mContext, Activity act) {
        this.mContext = mContext;
        this.act = act;
        this.globalClass = (GlobalClass) act.getApplicationContext();
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
            acelCurrentValue = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = acelCurrentValue - acelLastValue;
            accelExGravity = accelExGravity * 0.9f + delta;

            if (accelExGravity > 20) {
                displayDialog();
                canOpenLoginDialog = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void displayDialog() {

        if (globalClass.checkConnectivity("You cannot create a new attraction without an internet connection")){

            final GlobalClass globalClass = (GlobalClass) mContext.getApplicationContext();

            LayoutInflater layoutInflater = LayoutInflater.from(act);
            final View inputDialog = layoutInflater.inflate(R.layout.input_dialog, null);
            final View loginDialog  = layoutInflater.inflate(R.layout.login_dialog, null);

            AlertDialog.Builder loginDialogBuilder = new AlertDialog.Builder(act);
            loginDialogBuilder.setView(loginDialog);

            AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(act);
            inputDialogBuilder.setView(inputDialog);

            final EditText attractionTitle = inputDialog.findViewById(R.id.attractionTitle);
            final EditText attractionImageURL = inputDialog.findViewById(R.id.attractionURL);
            final EditText attractionDescription = inputDialog.findViewById(R.id.attractionDescription);

            final EditText attractionLatitude = inputDialog.findViewById(R.id.attractionlattitude);
            final EditText attractionLongitude = inputDialog.findViewById(R.id.attractionlongitude);

            final TextView errorText = inputDialog.findViewById(R.id.error);
            final Button okBtn = inputDialog.findViewById(R.id.okBtn);
            final Button cancelBtn = inputDialog.findViewById(R.id.cancelBtn);

            final EditText passwordField = loginDialog.findViewById(R.id.passwordField);
            final TextView error = loginDialog.findViewById(R.id.error);

            final Button loginBtn = loginDialog.findViewById(R.id.signin);
            final Button cancelLoginBtn = loginDialog.findViewById(R.id.cancelBtnLogin);

            final AlertDialog loginAlert = loginDialogBuilder.create();

            final AlertDialog inputAlert = inputDialogBuilder.create();

            cancelLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginAlert.dismiss();
                    canOpenLoginDialog = true;
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (passwordField.getText().toString().equals("admin")){

                        loginAlert.dismiss();
                        inputAlert.show();

                    } else {
                        error.setVisibility(View.VISIBLE);
                        error.setText("You are not the admin");

                    }
                }
            });

            if (canOpenLoginDialog)
            loginAlert.show();


            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputAlert.dismiss();
                    canOpenLoginDialog = true;
                }
            });


            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (globalClass.checkConnectivity("You cannot create a new attraction without an internet connection")){
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

                            Attraction attraction = new Attraction(attractionTitle.getText().toString().trim(),
                                    attractionDescription.getText().toString(), attractionImageURL.getText().toString().trim(),
                                    Float.valueOf(attractionLatitude.getText().toString()),
                                    Float.valueOf(attractionLongitude.getText().toString()));

                            for (Attraction attr : globalClass.getDsArrayList()) {
                                if (attr.getTitle().equals(attractionTitle.getText().toString().trim())) {
                                    attractionExists = true;
                                }
                            }

                            if (attractionExists == false) {

                                try {
                                    URL url = new URL(attractionImageURL.getText().toString().trim());
                                    Picasso.get().load(url.toString());

                                    mDatabase.child(attractionTitle.getText().toString().trim())
                                            .setValue(attraction)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            inputAlert.dismiss();
                                            canOpenLoginDialog = true;
                                        }
                                    });
                                } catch (Exception e){
                                    errorText.setVisibility(View.VISIBLE);
                                    errorText.setText("Invalid image URL");
                                }

                            } else {
                                errorText.setVisibility(View.VISIBLE);
                                errorText.setText("Attraction already exists");
                            }
                        }
                    }
                }
            });

        }
    }
}
