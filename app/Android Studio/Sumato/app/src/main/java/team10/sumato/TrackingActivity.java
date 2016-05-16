package team10.sumato;


import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import javax.microedition.khronos.egl.EGLConfig;


public class TrackingActivity extends CardboardActivity implements CardboardView.StereoRenderer{

    float[] movementMatrix = new float[16];
    MotorMovement motorMovement;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        TCPSingleton.getInstance().getClient().start();

        CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        cardboardView.setRenderer(this);
        cardboardView.setTransitionViewEnabled(true);
        cardboardView.setOnCardboardBackButtonListener(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
        setCardboardView(cardboardView);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("file:///android_asset/vr.html");



        motorMovement= new MotorMovement();
    }


    @Override
    public void onNewFrame(HeadTransform headTransform) {
        movementMatrix = headTransform.getHeadView();
        motorMovement.addValues(movementMatrix);

    }

    @Override
    public void onDrawEye(Eye eye) {


    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

    }

    @Override
    public void onRendererShutdown() {

    }

}
