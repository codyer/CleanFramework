package com.cody.app.business.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.cody.app.R;
import com.cody.app.framework.hybrid.JsBridge;
import com.cody.app.framework.hybrid.handler.JsHandlerCommonImpl;
import com.cody.app.framework.hybrid.handler.JsHttpHandler;
import com.cody.repository.framework.Repository;
import com.cody.repository.framework.local.BaseLocalKey;

public class DemoHtmlActivity extends AppCompatActivity {

    private WebView mWebView;
    private RelativeLayout webViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid_html);
        webViewContainer = (RelativeLayout) findViewById(R.id.webViewContainer);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(this);
        mWebView.setLayoutParams(params);
        webViewContainer.addView(mWebView);

        String url = "file:///android_asset/hybrid_demo.html";
        JsBridge.getInstance()
                .addJsHandler("JsHandlerCommonImpl", JsHandlerCommonImpl.class)
                .addJsHandler("JsHandlerImpl", JsHandlerImpl.class)
                .addJsHandler("JsHttpHandler", JsHttpHandler.class)
                .syncCookie(this, url, Repository.getLocalMap(BaseLocalKey.HEADERS))
                .build(mWebView, null);

        if (null != savedInstanceState) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        JsHttpHandler.onDestroy();
        JsBridge.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JsBridge.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
