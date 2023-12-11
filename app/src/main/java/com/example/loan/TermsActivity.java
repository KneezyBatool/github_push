package com.example.loan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;

public class TermsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
SwipeRefreshLayout swipeRefreshLayout;
WebView webView;
String url ="https://www.google.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

    }
    public void loadweb (){
        swipeRefreshLayout=findViewById(R.id.swipelayout);
        webView=findViewById(R.id.webview);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        webView.loadUrl(url);
        webView.canGoBack();
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                    webView.goBack();
                    return (true);
                }
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String urll) {
                swipeRefreshLayout.setRefreshing(false);
                url=urll;
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Snackbar.make(view,"Opps! something wrong.",Snackbar.LENGTH_INDEFINITE)
                        .show();
            }
        });
    }
    protected void onStart(){
        super.onStart();
        loadweb();
    }

    @Override
    public void onRefresh() {
        loadweb();

    }
}