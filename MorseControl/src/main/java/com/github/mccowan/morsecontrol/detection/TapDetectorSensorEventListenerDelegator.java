package com.github.mccowan.morsecontrol.detection;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.github.mccowan.morsecontrol.detection.tap.TapDetector;

import java.util.Date;

/**
* Delegates {@link android.hardware.SensorEventListener} events to a {@link com.github.mccowan.morsecontrol.detection.tap.TapDetector}.
*/
public class TapDetectorSensorEventListenerDelegator implements SensorEventListener {
    private final TapDetector tapDetector;
    public TapDetectorSensorEventListenerDelegator(final TapDetector tapDetector) {
        this.tapDetector = tapDetector;
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        tapDetector.acceptAccelerometerData(sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
    }

    @Override
    public void onAccuracyChanged(final Sensor sensor, final int i) {
        tapDetector.acceptAccelerometerAccuracyChange(new Date(), i);
    }
}
