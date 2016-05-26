package team10.sumato;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ConnectActivity extends AppCompatActivity {

    private WebView webGUI;
    private final String wifiSSID = "Sumato";
    private final String wifiPass = "sumato3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_connect);

        webGUI = new WebView(this);
        webGUI.setWebViewClient(new WebViewClient());
        webGUI.loadUrl("file:///android_asset/index.html");
        setContentView(webGUI);
        webGUI.getSettings().setJavaScriptEnabled(true);
        webGUI.getSettings().setDomStorageEnabled(true);
        webGUI.addJavascriptInterface(new JSInterface(this, this), "Interface");

        connectWifi(wifiSSID, wifiPass);

    }


    public void ConnectSumato() {

        if (isWifiConnected(wifiSSID)) {
            TCPManager.getInstance().getClient().send("PAIR_PS3");
            if (TCPManager.getInstance().getClient().isConnected()) {

                    LoadPage("choice.html");
            }
            else{
                LoadPage("index.html#wifiError");
            }
        } else {
            LoadPage("index.html#wifiError");

        }

        //TCPManager.getInstance().getClient().start();
        //TCPManager.getInstance().getClient().send("PAIR_PS3");


        //else {
        //    Log.d("Connection was success","false");
        //    Toast toast = Toast.makeText(this, "Ooops try again!", Toast.LENGTH_LONG);
        //    toast.setGravity(Gravity.CENTER_VERTICAL, 80, 0);
        //    toast.show();
        //}

    }

    boolean isWifiConnected(String SSID) {
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                if (info != null) {
                    String currentSSID = info.getSSID();
                    //check if SSID has quotations around it and remove them
                    if (currentSSID.startsWith("\"") && currentSSID.endsWith("\"")) {
                        currentSSID = currentSSID.substring(1, currentSSID.length() - 1);
                    }
                    return currentSSID.equals(SSID);
                }
            }
        }
        return false;
    }


    void connectWifi(String SSID, String pass) {
        if (!isWifiConnected(SSID)) {
            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = "\"" + SSID + "\"";
            conf.preSharedKey = "\"" + pass + "\"";
            WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
            manager.setWifiEnabled(true);

            int id = manager.addNetwork(conf);

            manager.disconnect();
            manager.enableNetwork(id, true);
            manager.reconnect();
        }
    }

    public void LoadPage(String page) {
        final String Page = page;
        webGUI.post(new Runnable() {   //force the task (url loading) to run on the main thread because of WebView restrictions
            @Override
            public void run() {
                webGUI.loadUrl("file:///android_asset/" + Page);
            }
        });
    }

    public void VRmode() {
        Intent vrActivity = new Intent(this, VRActivity.class);
        startActivity(vrActivity);
    }

    public void StandardMode() {
        Intent standardActivity = new Intent(ConnectActivity.this, StreamActivity.class);
        startActivity(standardActivity);
    }

    @Override
    public void onBackPressed() {
        if (webGUI.canGoBack()) {
            webGUI.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
