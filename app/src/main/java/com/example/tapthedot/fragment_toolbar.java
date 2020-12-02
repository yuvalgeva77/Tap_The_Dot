package com.example.tapthedot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class fragment_toolbar extends Fragment {
    private String TAG = "TOPBAR";
    private FragmentAListener listener;
    private TextView wifiText, gpsText, timeText, shekelText;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallBack;

    public interface FragmentAListener {
        void onInputASent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_toolbar, container, false);
        wifiText = v.findViewById(R.id.wifiName);
        gpsText = v.findViewById(R.id.gpsText);
        timeText = v.findViewById(R.id.TimeText);
        shekelText = v.findViewById(R.id.ShekelRateText);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        wifiText.setText("wifi");
        gpsText.setText("wifi");
        timeText.setText("wifi");
        shekelText.setText("wifi");


        //gps
        //location check definitions
        locationRequest = new LocationRequest();
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //event that's triggerd whenever the update interval is met
        locationCallBack=new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                updateGPSText(location);

            }
        };

        updateGPS();
        startLocationUpdate();


        //wifi
        String ssidName = getCurrentSsid(getContext());
        wifiText.setText(ssidName);


        return v;

    }


    public static String getCurrentSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        return ssid;


    }

    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
            if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                //do stuff
            } else {
                // wifi connection was lost
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gpsText.setText("now got premission");
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack,null);
        updateGPS();
    }
    private void updateGPSText(Location location){
    if (location != null) {
        Double lat = location.getAltitude();
        Double longt = location.getLongitude();
        gpsText.setText(lat + ", " + longt);
    } else {
        gpsText.setText("location is not available");
        Log.e(TAG, "Can't get location");
    }

}
    private void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //get location
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
               public void onSuccess(Location location) {
                    updateGPSText(location);
               }
           }
            );
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                gpsText.setText("no premission");

            }
        }
    }
}
