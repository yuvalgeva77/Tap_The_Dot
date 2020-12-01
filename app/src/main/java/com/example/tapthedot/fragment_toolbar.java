package com.example.tapthedot;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_toolbar extends Fragment {
    private FragmentAListener listener;
    private TextView wifiText,gpsText,timeText,shekelText;
    private Button buttonOk;
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
        return v;

    }
    public void updateEditText(CharSequence newText) {
//        wifiText.setText(getCurrentSsid(getContext()));
        wifiText.setText("wifi");
        gpsText.setText("wifi");
        timeText.setText("wifi");
        shekelText.setText("wifi");


    }
    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
             WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
             WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
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
}
