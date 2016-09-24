package com.sss.projectx;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sss.projectx.Others.Detail;
import com.sss.projectx.Others.Information;
import com.sss.projectx.Others.UserBase;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    AlertDialog dialog;
    UserBase userBase;
    Information information;

    String act, del,pro;
    boolean light, proxi, power, unlock;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    boolean sound, email, sms, call;
    private static final int FILE_SELECT_CODE = 0;

    EditText et_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        userBase = new UserBase(ActionActivity.this);
        information = userBase.getActionSettings();

        checkBox1 = (CheckBox) findViewById(R.id.checkbox_sound);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_email);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox_sms);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox_call);

        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4.setOnCheckedChangeListener(this);


        Bundle bundle = getIntent().getExtras();
        act = bundle.getString("activation");
        del = bundle.getString("delay");
        pro = bundle.getString("profile");
        light = bundle.getBoolean("light");
        proxi = bundle.getBoolean("proxi");
        power = bundle.getBoolean("power");
        unlock = bundle.getBoolean("unlock");
    }

    private void Upload_data() {
        int counter = userBase.getCounter();
        sound = checkBox1.isChecked();
        email = checkBox2.isChecked();
        sms = checkBox3.isChecked();
        call = checkBox4.isChecked();
        ArrayList<Detail> currentData = new ArrayList<>();
        Detail current = new Detail();
        current.profile = pro;//"profile" + counter;
        current.activation_time = act;
        current.delay_time = del;
        current.light = light;
        current.proximity = proxi;
        current.power = power;
        current.unlock = unlock;
        current.sound = sound;
        current.email = email;
        current.sms = sms;
        current.call = call;
        currentData.add(current);
        MyApplication.getWritableDatabase().insertMyPost(currentData, false);
        counter++;
        userBase.setCounter(counter);
        Toast.makeText(ActionActivity.this, "Profile Created.", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(ActionActivity.this, MainActivity.class));
    }

    private void storeNewDate(String what_to_update, String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8) {
        Information info = userBase.getActionSettings();
        switch (what_to_update) {
            case "sound_path":
                Information inf = new Information(value1, info.email_from, info.email_to, info.email_subject, info.email_message, info.sms_number, info.sms_text, info.call_number);
                userBase.setActionSettings(inf);
                break;
            case "email":
                Information inf2 = new Information(info.sound_path, value2, value3, value4, value5,info.sms_number, info.sms_text, info.call_number);
                userBase.setActionSettings(inf2);
                break;
            case "sms":
                Information inf3 = new Information(info.sound_path,info.email_from, info.email_to, info.email_subject, info.email_message, value6, value7, info.call_number);
                userBase.setActionSettings(inf3);
                break;
            case "call":
                Information inf4 = new Information(info.sound_path, info.email_from, info.email_to, info.email_subject, info.email_message, info.sms_number, info.sms_text, value8);
                userBase.setActionSettings(inf4);
                break;
        }
    }

    public void onClickButton(View view) {
        int id = view.getId();
        if (id == R.id.iv_sound) {
            ShowSound();
        }
        if (id == R.id.iv_email) {
            ShowEmail();
        }
        if (id == R.id.iv_sms) {
            ShowSms();
        }
        if (id == R.id.iv_call) {
            ShowCall();
        }
        if (id == R.id.btn_done) {
            Upload_data();
        }
    }

    private void ShowCall() {
        dialog = new AlertDialog.Builder(ActionActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Call");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_call, (ViewGroup) findViewById(R.id.root), false);
        final EditText et = (EditText) customView.findViewById(R.id.et_call);
        et.setText(information.call_number);

        dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setButton2("Configure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (et.length() == 0) {
                    Toast.makeText(ActionActivity.this, "Please enter a phone number", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    storeNewDate("call", "", "","", "", "", "", "", et.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void ShowSms() {
        dialog = new AlertDialog.Builder(ActionActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Sms");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_sms, (ViewGroup) findViewById(R.id.root), false);
        final EditText et1 = (EditText) customView.findViewById(R.id.et_sms1);
        final EditText et2 = (EditText) customView.findViewById(R.id.et_sms2);
        et1.setText(information.sms_number);
        et2.setText(information.sms_text);

        dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setButton2("Configure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (et1.length() == 0 || et2.length() == 0) {
                    Toast.makeText(ActionActivity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    String e1 = et1.getText().toString();
                    String e2 = et2.getText().toString();
                    storeNewDate("sms", "", "", "", "","", e1, e2, "");
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void ShowEmail() {
        dialog = new AlertDialog.Builder(ActionActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Email");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_email, (ViewGroup) findViewById(R.id.root), false);
        final EditText et1 = (EditText) customView.findViewById(R.id.et_email1);
        final EditText et2 = (EditText) customView.findViewById(R.id.et_email2);
        final EditText et3 = (EditText) customView.findViewById(R.id.et_email3);
        final EditText et4 = (EditText) customView.findViewById(R.id.et_email4);
        et1.setText(information.email_to);
        et2.setText(information.email_subject);
        et3.setText(information.email_message);
        et4.setText(information.email_from);

        dialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setButton2("Configure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (et1.length() == 0 || et2.length() == 0 || et3.length() == 0 || et4.length() == 0) {
                    Toast.makeText(ActionActivity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    String e1 = et1.getText().toString();
                    String e2 = et2.getText().toString();
                    String e3 = et3.getText().toString();
                    String e4 = et3.getText().toString();
                    storeNewDate("email", "", e4,e1, e2, e3, "", "", "");
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void ShowSound() {
        dialog = new AlertDialog.Builder(ActionActivity.this).create();
        dialog.setCancelable(true);
        dialog.setTitle("Configure Sound Path");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.layout_sound, (ViewGroup) findViewById(R.id.root), false);
        et_s = (EditText) customView.findViewById(R.id.et_sound);
        final ImageView iv = (ImageView) customView.findViewById(R.id.imageView2);
        et_s.setText(information.sound_path);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select an audio file to Upload"), FILE_SELECT_CODE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(ActionActivity.this, "Install File Manager", Toast.LENGTH_LONG).show();
                }
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
                if (et_s.length() == 0) {
                    Toast.makeText(ActionActivity.this, "Please enter sound path", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    storeNewDate("sound_path", et_s.getText().toString(),"", "", "", "", "", "", "");
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Uri getUri = data.getData();
            String[] projection = {"_data"};
            Cursor cursor = getContentResolver().query(getUri, projection, null, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String iniPath = cursor.getString(columnIndex);
            String getExtension = iniPath.substring(iniPath.lastIndexOf("."));
            switch (getExtension) {
                case ".mp3":
                    et_s.setText(iniPath);
                    break;
                default:
                    Toast.makeText(ActionActivity.this, "wrong audio format.Try again", Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(ActionActivity.this, "wrong audio format.Try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
