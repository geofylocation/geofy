package ca.geofy;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class AccelerometerService extends Service implements SensorEventListener {

    private MediaPlayer dinger;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dinger = MediaPlayer.create(this, R.raw.ding);

        SensorManager sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                float[] mGravity = event.values.clone();

                float x = mGravity[0];
                float y = mGravity[1];
                float z = mGravity[2];

                Log.i("Accel", "TYPE_LINEAR_ACCELERATION: " + y);

                if (y > 3) {
                    playSound();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long lastPlayed;

    private void playSound() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastPlayed;
        if (!dinger.isPlaying() && elapsed > TimeUnit.SECONDS.toMillis(5)) {
            dinger.start(); // no need to call prepare(); create() does that for you
            lastPlayed = now;
        }
    }
}
