package com.sss.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sss.projectx.Others.UserBase;

public class ActivateActivity extends AppCompatActivity {

    Button button;
    EditText et;
    UserBase userBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);

        button = (Button) findViewById(R.id.btn_activate);
        et = (EditText) findViewById(R.id.et);
        userBase = new UserBase(ActivateActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTag = button.getTag().toString();
                if (getTag.contentEquals("First_Time")) {
                    FirstTime();
                } else {
                    Login();
                }
            }
        });
    }

    private boolean checkEditText() {
        int getLength = et.length();
        if (getLength == 0) {
            Toast.makeText(ActivateActivity.this, "Pin Code should be more than 4.", Toast.LENGTH_LONG).show();
            return false;
        } else if (getLength < 4) {
            Toast.makeText(ActivateActivity.this, "Pin Code should be more than 4.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void Login() {
        if (checkEditText()) {
            int code1 = Integer.parseInt(et.getText().toString());
            int code2 = userBase.getCode();
            if(code1 == code2) {
                userBase.setLogged(true);
                Intent intent = new Intent(ActivateActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }else {
                Toast.makeText(ActivateActivity.this,"Pin Code Error.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void FirstTime() {
        if (checkEditText()) {
            int code = Integer.parseInt(et.getText().toString());
            userBase.setActivation(code);
            userBase.setActivated(true);
            userBase.setLogged(true);
            Toast.makeText(ActivateActivity.this, "Pin Code has been set.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ActivateActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            String getExtra = intent.getStringExtra("from");
            if (getExtra != null && getExtra.contentEquals("MainActivity")) {
                button.setTag("Time_Not");
                button.setText("Enter");
            }
        }
    }
}
