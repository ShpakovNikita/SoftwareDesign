package com.example.shaft.softwaredesign;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class About extends Fragment {

    private final int REQUEST_CODE = 1;
    private final String[] PERMISSIONS = new String[]{android.Manifest.permission.READ_PHONE_STATE};
    private final String projectVersion = BuildConfig.VERSION_NAME;

    // it is not necessary to be permissions granted to call this method
    private void setImei(View view){
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getImei();

            TextView imei_info_tv = (TextView) view.findViewById(R.id.imei_info_tv);
            imei_info_tv.setText(getString(R.string.imei_info, imei));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setImei(getView());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.about_permission))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.clear),
                                    (DialogInterface dialogInterface, int emptyArg) -> {requestPermissions(PERMISSIONS, REQUEST_CODE);})
                            .setNegativeButton(getString(R.string.deny),
                                    (DialogInterface dialogInterface, int emptyArg) -> {return;}
                            ).create().show();
                }
                break;
            default:
                throw new RuntimeException("No such request code!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);


        TextView text_version = (TextView) view.findViewById(R.id.text_version_id);
        text_version.setText(getString(R.string.version_info, projectVersion));

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PERMISSIONS, REQUEST_CODE);
        } else {
            setImei(view);
        }

        return view;
    }
}
