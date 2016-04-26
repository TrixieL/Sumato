package team10.sumato;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.*;

public class GyroscopeReadings extends AppCompatActivity {

    private boolean gyroscopeAvailable()
    {
        return getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_GYROSCOPE);
    }

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_readings);

        TextView available = (TextView) findViewById(R.id.available);
        final Button btnStartReadings = (Button) findViewById(R.id.btnStartReadings);

        if (gyroscopeAvailable()) {
            available.setText("Your device supports Gyroscope!Start readings?");
        }
        btnStartReadings.setText("Start");
        btnStartReadings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( started ) {
                    started = false;
                    btnStartReadings.setText("Start");
                }
                else {
                    started = true;
                    btnStartReadings.setText("Stop");
                }

            }
        });





    }
}
