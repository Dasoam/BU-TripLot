package com.dadash.butriplot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class googleformwebview extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleformwebview);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("about:blank");

        ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Please Wait",true);
        progressDialog.setCancelable(false);
        // add your link here
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSekSw0C25Ko2Z3tb2OYIQDV_pfs2zSUZ5Yjro7a6vLs-ElMrw/viewform?usp=sf_link");
    }
}