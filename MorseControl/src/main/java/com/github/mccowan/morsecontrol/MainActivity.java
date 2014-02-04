package com.github.mccowan.morsecontrol;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.mccowan.morsecontrol.detection.TapDetectorSensorEventListenerDelegator;
import com.github.mccowan.tap.StreamingTapDetector;
import com.github.mccowan.tap.TapHandler;

import java.io.IOException;

public class MainActivity extends Activity {
    final private String LOG_SLUG = this.getClass().getCanonicalName();
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    final StreamingTapDetector tapDetector = new StreamingTapDetector(new TapHandler() {
        @Override
        public void onTap(final long l) {
            Log.d(LOG_SLUG, "Tap detected!  Time = " + l);
        }
    });
    final TapDetectorSensorEventListenerDelegator tapDetectorAdapter = new TapDetectorSensorEventListenerDelegator(tapDetector);

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void unregisterTapListener() {
        sensorManager.unregisterListener(tapDetectorAdapter);
    }

    private void registerTapListener() {
        sensorManager.registerListener(tapDetectorAdapter, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerTapListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterTapListener();
    }

    public void onSharePress(final View view) throws IOException {
        Log.d(LOG_SLUG, "Share pressed.");
    }
}