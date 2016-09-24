package com.sss.projectx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sss.projectx.Others.ClickListener;
import com.sss.projectx.Others.Detail;
import com.sss.projectx.Others.MainAdapter;
import com.sss.projectx.Others.MyService;
import com.sss.projectx.Others.NotificationBar;
import com.sss.projectx.Others.UnitTime;
import com.sss.projectx.Others.UserBase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickListener {

    UserBase userBase;
    RecyclerView recyclerView;
    MainAdapter adapter;
    ArrayList<Detail> customData = new ArrayList<>();
    TextView tv;
    MediaPlayer mediaPlayer;


    float max, current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userBase = new UserBase(MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv = (TextView) findViewById(R.id.tv_);
        adapter = new MainAdapter(MainActivity.this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setAdapter(adapter);
        customData.clear();
        customData = MyApplication.getWritableDatabase().getAllMyPosts();
        if (!customData.isEmpty()) {
            adapter.LoadAdapter(customData);
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LoadDB();
                startActivity(new Intent(MainActivity.this, GeneralActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void playSound(String path) {
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound2);//Uri.parse("file://" + path)
        mediaPlayer.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        if (id == R.id.action_remove) {
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setCancelable(true);
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Are you sure you want to delete all profiles?");
            alertDialog.setButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.setButton2("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MyApplication.getWritableDatabase().deleteAll();
                    userBase.setCounter(0);
                    customData.clear();
                    adapter.LoadAdapter(customData);
                    if (!customData.isEmpty()) {
                        tv.setVisibility(View.GONE);
                    } else {
                        tv.setVisibility(View.VISIBLE);
                    }
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want to exit?");
        alertDialog.setButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton2("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userBase.setLogged(false);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userBase.setLogged(false);
    }

    private void LoadDB() {
        ArrayList<Detail> currentData = new ArrayList<>();
        Detail current = new Detail();
        current.id = 0;
        current.profile = "none";
        current.activation_time = "none";
        current.delay_time = "none";
        current.light = false;
        current.proximity = false;
        current.power = false;
        current.unlock = false;
        current.sound = false;
        current.email = false;
        current.sms = false;
        current.call = false;
        currentData.add(current);
        MyApplication.getWritableDatabase().insertMyPost(currentData, false);
        int counter = userBase.getCounter();
        counter++;
        userBase.setCounter(counter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userBase.getLogged()) {

        } else {
            if (userBase.getActivated()) {
                Intent intent1 = new Intent(MainActivity.this, ActivateActivity.class);
                intent1.putExtra("from", "MainActivity");
                finish();
                startActivity(intent1);
            }
        }
    }

    @Override
    public void onClickListener(View view, int position) {

        //Set of Actions
        Detail current = customData.get(position);
        long future = UnitTime.getTimeMilli(current.activation_time);
        String actions = "";
        String sensors = "";
        if (current.sound) {
            actions += "sound";
        }
        if (current.email) {
            actions += "email";
        }
        if (current.sms) {
            actions += "sms";
        }
        if (current.call) {
            actions += "call";
        }
        if (current.light) {
            sensors += "light";
        }
        if (current.proximity) {
            sensors += "proximity";
        }
        if (current.power) {
            sensors += "power";
        }
        if (current.unlock) {
            sensors += "unlock";
        }
        Intent intent = new Intent(MainActivity.this, MyService.class);
        intent.putExtra("actions", actions);
        intent.putExtra("sensors", sensors);
        intent.putExtra("delay_time", current.delay_time);
        intent.putExtra("future", future);
        startService(intent);
        //CountDownClass countDownClass = new CountDownClass(MainActivity.this, actions, sensors, current.delay_time, future, 1000);
        //countDownClass.start();
        String text = current.profile + " has been activated";
        NotificationBar notificationBar = new NotificationBar(MainActivity.this);
        notificationBar.ShowNotification(text);
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
