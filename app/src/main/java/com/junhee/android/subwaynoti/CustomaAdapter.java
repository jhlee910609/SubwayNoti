package com.junhee.android.subwaynoti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junhee.android.subwaynoti.domain.SubwayTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 7. 6..
 */

public class CustomaAdapter extends RecyclerView.Adapter<CustomaAdapter.Holder> {

    private Context context;
    private List<SubwayTime> datas = new ArrayList<>();

    public CustomaAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SubwayTime> datas){
        this.datas = datas;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SubwayTime subwayTime = datas.get(position);
        holder.time.setText(subwayTime.time);
        holder.time.setText(subwayTime.title);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        int position;

        public Holder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.list_time);
            title = (TextView) itemView.findViewById(R.id.list_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("POSITION", position);
                    context.startActivity(intent);
                }
            });
        }
    }
}
