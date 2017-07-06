package com.junhee.android.subwaynoti;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junhee.android.subwaynoti.domain.MySubwayTime;
import com.junhee.android.subwaynoti.domain.MySubwayTimeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunHee on 2017. 7. 6..
 */

public class CustomaAdapter extends RecyclerView.Adapter<CustomaAdapter.Holder> {

    private Context context;
    private List<MySubwayTime> datas = new ArrayList<>();

    public CustomaAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<MySubwayTime> datas) {
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        MySubwayTime subwayTime = MySubwayTimeList.list.get(position);
        holder.time.setText(subwayTime.time);
        holder.title.setText(subwayTime.title);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return MySubwayTimeList.list.size();
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
