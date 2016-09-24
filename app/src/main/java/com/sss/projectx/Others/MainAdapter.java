package com.sss.projectx.Others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sss.projectx.MyApplication;
import com.sss.projectx.R;

import java.util.ArrayList;

/**
 * Created by Control & Inst. LAB on 23-Sep-16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MAdapter> {

    ArrayList<Detail> info = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    ClickListener clickListener;

    public MainAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
    }

    public void LoadAdapter(ArrayList<Detail> info) {
        this.info = info;
        notifyDataSetChanged();
    }

    @Override
    public MAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom, parent, false);
        MAdapter bp = new MAdapter(view);
        return bp;
    }

    @Override
    public void onBindViewHolder(MAdapter holder, int position) {
        Detail current = info.get(position);
        String any = current.profile;
        //if(!any.contentEquals("none")) {
        holder.tv.setText(current.profile);
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    class MAdapter extends RecyclerView.ViewHolder {

        ImageButton ib, rem, set;
        TextView tv;

        public MAdapter(View itemView) {
            super(itemView);
            ib = (ImageButton) itemView.findViewById(R.id.ib);
            rem = (ImageButton) itemView.findViewById(R.id.ib_remove);
            set = (ImageButton) itemView.findViewById(R.id.ib_view);
            tv = (TextView) itemView.findViewById(R.id.tv);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onClickListener(view, getPosition());
                    }
                }
            });

            rem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Detail current = info.get(getPosition());
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setCancelable(true);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Are you sure you want to delete profile " + current.profile + "?");
                    alertDialog.setButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setButton2("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyApplication.getWritableDatabase().deleteDatabase(current.id);
                            LoadAdapter(MyApplication.getWritableDatabase().getAllMyPosts());
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });

            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Detail current = info.get(getPosition());
                    ArrayList<String> settings = new ArrayList<String>();
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setCancelable(true);
                    alertDialog.setTitle("Set of Actions and Sensors used");
                    ListView listView = new ListView(context);
                    if (current.light) {
                        settings.add("Sensor - light");
                    }
                    if (current.proximity) {
                        settings.add("Sensor - proximity");
                    }
                    if (current.power) {
                        settings.add("Sensor - power");
                    }
                    if (current.unlock) {
                        settings.add("Sensor - unlock");
                    }
                    if (current.sound) {
                        settings.add("Action - sound");
                    }
                    if (current.email) {
                        settings.add("Action - email");
                    }
                    if (current.sms) {
                        settings.add("Action - sms");
                    }
                    if (current.call) {
                        settings.add("Action - call");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, settings);
                    listView.setAdapter(adapter);
                    alertDialog.setView(listView);
                    alertDialog.show();
                }
            });
        }
    }
}
