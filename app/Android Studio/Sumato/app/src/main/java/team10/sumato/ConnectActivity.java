package team10.sumato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ConnectActivity extends AppCompatActivity {

    Button btnConnect;

    protected void initSingleton(){
        SharedPreferences preferences = getSharedPreferences("team10.sumato_preferences", MODE_PRIVATE);
        TCPSingleton.initSingleton(preferences.getString("IP_key", "127.0.0.1"), preferences.getString("port_key", "9999"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        btnConnect = (Button) findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectSumato();
            

            }
       });
    }

    public void connectSumato(){
        initSingleton();
        //TCPSingleton.getInstance().getClient().start();
       // TCPSingleton.getInstance().getClient().send("PAIR_PS3");

        //TODO: check if the pairing was successful and move to the next activity

    //    final Intent gyroscopeReadings = new Intent(getApplicationContext(), GyroscopeReadings.class);
    //    startActivity(gyroscopeReadings);
        Intent trackerActivity = new Intent(this, TrackingActivity.class);
        startActivity(trackerActivity);
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /*
     * Open the settings menu.
     */
    public void onGroupItemClick(MenuItem item){
        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
    }




}
