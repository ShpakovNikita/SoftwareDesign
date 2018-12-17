package com.example.shaft.softwaredesign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.shaft.softwaredesign.R;

public class WebActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            url = b.getString(FirstBlankFragment.URL_KEY);
            WebView web_view = (WebView) findViewById(R.id.web_view);
            web_view.getSettings().setJavaScriptEnabled(true);
            web_view.setWebViewClient(new WebViewClient());
            web_view.loadUrl(url);
        }
        else {
            finish();
        }

    }
}
