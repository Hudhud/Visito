package dtu.app.visito;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapFragment extends Fragment {

    private FusedLocationProviderClient locationProviderClient;
    private double currentLat, currentLong;
    private CameraPosition googlePlex;
    private GlobalClass globalClass;
    private SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {


            case 1 : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMap(mapFragment, globalClass);
                } else {
                    showMap(mapFragment, globalClass);
                }
                return;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestPermission();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        globalClass = (GlobalClass) getActivity().getApplicationContext();


        return rootView;
    }

    private void requestPermission(){
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void showMap(SupportMapFragment map, final GlobalClass gd){
        map.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear();

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(getActivity(), "Your current location won't be shown. Grant Location permission",
                            Toast.LENGTH_LONG).show();
                    animateMapCamera(55.676098, 12.568337, mMap);
                } else{
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            isLocationServiceEnabled();
                            return false;
                        }
                    });

                    isLocationServiceEnabled();

                    locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = null;
                                location = task.getResult();
                            if (location != null) {
                                currentLat = location.getLatitude();
                                currentLong = location.getLongitude();
                                animateMapCamera(currentLat, currentLong, mMap);
                            }
                        }
                    });
                }

                    for (DataSnapshot dataSnapShot: gd.getDsArrayList()) {
                        float lat =Float.valueOf(dataSnapShot.child("latitude").getValue().toString());
                        float long1 = Float.valueOf(dataSnapShot.child("longitude").getValue().toString());
                        String tit = dataSnapShot.getKey();
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat,long1))
                                .title(tit));
                    }
                }

        });
    }

    private void animateMapCamera(double lat, double long1, GoogleMap gm){
        googlePlex = CameraPosition.builder()
                .target(new LatLng(lat, long1))
                .zoom(10)
                .bearing(0)
                .tilt(10)
                .build();
        gm.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null);
    }

    public void isLocationServiceEnabled(){
        LocationManager locationManager = null;
        boolean isGPSEnabled= false;
        boolean isNetworkEnabled = false;

        if(locationManager == null)
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled == false && isNetworkEnabled == false)
            Toast.makeText(getActivity(), "Enable Location",
                    Toast.LENGTH_LONG).show();
    }
}
