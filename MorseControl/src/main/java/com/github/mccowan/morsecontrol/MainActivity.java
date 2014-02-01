package com.github.mccowan.morsecontrol;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.mccowan.morsecontrol.detection.tap.TapDetector;
import com.github.mccowan.morsecontrol.detection.TapDetectorSensorEventListenerDelegator;

import java.io.IOException;

public class MainActivity extends Activity {
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    // TODO
    final TapDetector tapDetector = new TapDetector(new TapDetector.TapHandler() {
        @Override
        public void onTap() {
            Log.d("TAPPED!", ":)");
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
        //final String filename = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + ".log";
        //final File destination = new File(new File(getFilesDir(), "data"), filename);
        tapDetector.writeAndPurgeData();
        //Toast.makeText(getApplicationContext(), "Wrote to " + destination.getAbsolutePath(), 5).show();
        //FileProvider.getUriForFile(getApplicationContext(), "com.github.mccowan.morsecontrol.fileprovider", destination);
        // todo: what the shebang do i do here
        //final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //sharingIntent.setType("text/rf");
        //sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(destination));
        //startActivity(sharingIntent);
    }
}