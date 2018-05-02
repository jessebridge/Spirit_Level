package com.example.jesse.spirit_level;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private float x, y, z;
    private double xposition;
    private TextView xpos;
    private TextView ypos;
    private double finalValue;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        xpos = (TextView) findViewById(R.id.xpos);
        imageView = (ImageView)findViewById(R.id.spirit);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            xposition = x * 18.3486238532 /2;
            DecimalFormat df = new DecimalFormat("0.00");
            String formate = df.format(xposition);
            finalValue = Double.parseDouble(formate);
            if (finalValue <= 55 && finalValue >= -55 ){
                imageView.setRotation((float) finalValue);
            }
            if (finalValue == 0){
                xpos.setText(R.string.level);
            }
            else if(finalValue > 0){
                xpos.setText(new StringBuilder().append("Object is leaning to the right by ").append(String.valueOf(finalValue)).append(" degrees").toString());

            }
            else{
                xpos.setText(new StringBuilder().append("Object is leaning to the left by ").append(String.valueOf(finalValue)).append(" degrees").toString());


            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
