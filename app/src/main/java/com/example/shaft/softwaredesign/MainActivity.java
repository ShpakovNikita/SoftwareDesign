package com.example.shaft.softwaredesign;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog.Builder;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import com.example.shaft.softwaredesign.R.id;


public final class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1;
    private final String[] PERMISSIONS = new String[]{android.Manifest.permission.READ_PHONE_STATE};
    private final String projectVersion = BuildConfig.VERSION_NAME;

    // it is not necessary to be permissions granted to call this method
    private void setImei(){
        if (this.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getImei();

            TextView imei_info_tv = (TextView) findViewById(id.imei_info_tv);
            imei_info_tv.setText(getString(R.string.imei_info, imei));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text_version = (TextView) findViewById(R.id.text_version_id);
        text_version.setText(getString(R.string.version_info, projectVersion));

        if (this.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PERMISSIONS, REQUEST_CODE);
        } else {
            setImei();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setImei();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getString(R.string.about_permission))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.clear),
                        (DialogInterface dialogInterface, int emptyArg) -> {requestPermissions(MainActivity.this.PERMISSIONS, MainActivity.this.REQUEST_CODE);}
                        ).create().show();
                }
                break;
            default:
                throw new RuntimeException("No such request code!");
        }
    }
}
