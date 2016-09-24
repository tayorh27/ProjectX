package com.sss.projectx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class GeneralActivity extends AppCompatActivity {

    Button btn;
    Spinner item1, item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        btn = (Button) findViewById(R.id.btn);
        item1 = (Spinner) findViewById(R.id.spinner1);
        item2 = (Spinner) findViewById(R.id.spinner2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralActivity.this, SensorsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("activation",item1.getSelectedItem().toString());
                bundle.putString("delay",item2.getSelectedItem().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
