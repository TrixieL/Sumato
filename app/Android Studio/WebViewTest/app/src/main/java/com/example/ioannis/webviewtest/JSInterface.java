package com.example.ioannis.webviewtest;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Ioannis on 5/20/2016.
 */
public class JSInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    JSInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void connect() {
        //call stuff that need to happen when connected


    }
}
