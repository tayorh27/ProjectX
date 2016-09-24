package com.sss.projectx.Others;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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

        ImageButton ib;
        TextView tv;

        public MAdapter(View itemView) {
            super(itemView);
            ib = (ImageButton) itemView.findViewById(R.id.ib);
            tv = (TextView) itemView.findViewById(R.id.tv);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onClickListener(view,getPosition());
                    }
                }
            });
        }
    }
}
