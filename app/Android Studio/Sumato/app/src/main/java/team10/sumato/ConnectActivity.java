package team10.sumato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ConnectActivity extends AppCompatActivity {

    private WebView webGUI;

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
        webGUI.addJavascriptInterface(new JSInterface(this,this), "Interface");
    }


    public void ConnectSumato(){
        initSingleton();
        //TCPSingleton.getInstance().getClient().start();
        //TCPSingleton.getInstance().getClient().send("PAIR_PS3");

        //TODO: check if the pairing was successful and move to the next page

        // if (checkSingleton()) {
            LoadPage("choice.html");
        // }
        //else {
        //    Log.d("Connection was success","false");
        //    Toast toast = Toast.makeText(this, "Ooops try again!", Toast.LENGTH_LONG);
        //    toast.setGravity(Gravity.CENTER_VERTICAL, 80, 0);
        //    toast.show();
        //}

    }

    public void LoadPage(String page){
        final String Page = page;
        webGUI.post(new Runnable() {   //force the task (url loading) to run on the main thread because of WebView restrictions
            @Override
            public void run() {
                webGUI.loadUrl("file:///android_asset/" + Page);
            }
        });
    }

    public void VRmode() {
        Intent VRActivity = new Intent(this, VRActivity.class);
        startActivity(VRActivity);
    }

    public void StandardMode(){
        Intent StandardActivity = new Intent(ConnectActivity.this, StandardActivity.class);
        startActivity(StandardActivity);
    }

    protected void initSingleton(){
        SharedPreferences preferences = getSharedPreferences("team10.sumato_preferences", MODE_PRIVATE);
       // TCPSingleton.initSingleton(preferences.getString("IP_key", "127.0.0.1"), preferences.getString("port_key", "9999"));
        TCPSingleton.initSingleton("192.168.42.1", "9999");
    }

    public boolean checkSingleton() {
        //// TODO: 5/23/2016 check connection and if everything is in order(probably with a handshake).return the status.
        return true;
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
