package team10.sumato;


import android.os.Bundle;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import javax.microedition.khronos.egl.EGLConfig;


public class TrackingActivity extends CardboardActivity implements CardboardView.StereoRenderer{

    float[] movementMatrix = new float[16];
    MotorMovement motorMovement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

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
