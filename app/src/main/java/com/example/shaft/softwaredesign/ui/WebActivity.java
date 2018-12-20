package com.example.shaft.softwaredesign.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaft.softwaredesign.R;

public class WebActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            url = b.getString(RssViewFragment.URL_KEY);
            WebView web_view = (WebView) findViewById(R.id.web_view);
            web_view.getSettings().setJavaScriptEnabled(true);
            web_view.setWebViewClient(new WebViewClient());

            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
            TextView tv = (TextView) findViewById(R.id.loading);
            CardView card = (CardView) findViewById(R.id.card_view);

            web_view.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    bar.setProgress(progress);
                    if(progress == 100) {
                        bar.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        card.setVisibility(View.GONE);
                    }
                }
            });
            web_view.loadUrl(url);
        }
        else {
            finish();
        }

    }
}
