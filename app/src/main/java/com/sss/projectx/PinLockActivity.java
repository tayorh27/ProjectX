package com.sss.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sss.projectx.Others.UserBase;

public class PinLockActivity extends AppCompatActivity {

    LinearLayout layout;
    UserBase userBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lock);

        userBase = new UserBase(PinLockActivity.this);
        layout = (LinearLayout)findViewById(R.id.lock);
        if(!userBase.getActivated()){
            layout.setEnabled(false);
        }else {
            layout.setEnabled(true);
        }
    }

    public void ActivateClick(View view){
        startActivity(new Intent(PinLockActivity.this, ActivateActivity.class));
    }

    public void LockClick(View view){
        userBase.clear();
        Toast.makeText(PinLockActivity.this,"Pin Lock deleted.",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(PinLockActivity.this, ActivateActivity.class));
    }
}
