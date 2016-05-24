package team10.sumato;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


/**
 * Created by Ioannis on 5/22/2016.
 */
public class JSInterface {
    Context mContext;
    ConnectActivity activity;

    /** Instantiate the interface and set the context */
    JSInterface(Context c,ConnectActivity ca) {
        mContext = c;
        activity = ca;
    }

    @JavascriptInterface
    public void ConnectSumato() {
        activity.ConnectSumato();
    }
    @JavascriptInterface
    public void LoadPage(String page) {
        activity.LoadPage(page);
    }
    @JavascriptInterface
    public void ContinueVR() {
        activity.VRmode();
    }
    @JavascriptInterface
    public void ContinueStandard() {
        activity.StandardMode();
    }
}
