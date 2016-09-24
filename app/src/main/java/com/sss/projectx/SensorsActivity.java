package com.sss.projectx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sss.projectx.Others.SensorsInfo;
import com.sss.projectx.Others.UserBase;

public class SensorsActivity extends AppCompatActivity {

    Button btn;
    String act, del,pro;
    boolean light, proxi, power, unlock;

    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    AlertDialog dialog;
    UserBase userBase;
    SensorsInfo sensorInfo;
    ProgressBar pb;
    SeekBar sb;
    TextView tv1, tv2;
    float max, current;
    float pmax, pcurrent;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        userBase = new UserBase(SensorsActivity.this);
        sensorInfo = userBase.getSensorsSettings();

        Bundle bundle = getIntent().getExtras();
        act = bundle.getString("activation");
        del = bundle.getString("delay");
        pro = bundle.getString("profile");

        btn = (Button) findViewById(R.id.btn);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox_light);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_proximity);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox_power);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox_unlock);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light = checkBox1.isChecked();
                proxi = checkBox2.isChecked();
                power = checkBox3.isChecked();
                unlock = checkBox4.isChecked();
                Intent intent = new Intent(SensorsActivity.this, ActionActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("activation", act);
                bundle1.putString("delay", del);
                bundle1.putString("profile", pro);
                bundle1.putBoolean("light", light);
                bundle1.putBoolean("proxi", proxi);
                bundle1.putBoolean("power", power);
                bundle1.putBoolean("unlock", unlock);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (lightSensor == null || proximitySensor == null) {
            Toast.makeText(SensorsActivity.this, "No sensor detected on device", Toast.LENGTH_SHORT).show();
        } else {
            max = lightSensor.getMaximumRange();
            pmax = proximitySensor.getMaximumRange();

            sensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);

            sensorManager.registerListener(proximitySensorEventListener,
                    proximitySensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                current = sensorEvent.values[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                pcurrent = sensorEvent.values[0];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public void onClickButton(View view) {
        int id = view.getId();
        if (id == R.id.iv_light) {
            ShowLight();
        }
        if (id == R.id.iv_proximity) {
            ShowProximity();
        }
    }

    private void ShowProximity() {
        dialog = new AlertDialog.Builder(SensorsActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Proximity Sensor");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_proximity, (ViewGroup) findViewById(R.id.root), false);
        sb = (SeekBar) customView.findViewById(R.id.proximitymeter);
        final TextView tv3 = (TextView) customView.findViewById(R.id.tv_current);
        TextView tv4 = (TextView) customView.findViewById(R.id.tv_max);
        sb.setMax((int) pmax);
        tv4.setText("Max Reading: " + String.valueOf(pmax));
        sb.setProgress((int) pcurrent);
        tv3.setText("Current Reading: " + String.valueOf(pcurrent));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv3.setText("Current Reading: " + String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setButton2("Configure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int current = sb.getProgress();
                SensorsInfo si = new SensorsInfo(sensorInfo.light_intensity, current);
                userBase.setSensorsSettings(si);
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void ShowLight() {
        dialog = new AlertDialog.Builder(SensorsActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Light Sensor");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_light, (ViewGroup) findViewById(R.id.root), false);
        pb = (ProgressBar) customView.findViewById(R.id.lightmeter);
        tv1 = (TextView) customView.findViewById(R.id.tv_current);
        tv2 = (TextView) customView.findViewById(R.id.tv_max);
        pb.setMax((int) max);
        tv2.setText("Max Reading: " + String.valueOf(max));
        pb.setProgress((int) current);
        tv1.setText("Current Reading: " + String.valueOf(current));


        dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setButton2("Configure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int current = pb.getProgress();
                SensorsInfo si = new SensorsInfo(current, sensorInfo.proximity_intensity);
                userBase.setSensorsSettings(si);
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }
}
