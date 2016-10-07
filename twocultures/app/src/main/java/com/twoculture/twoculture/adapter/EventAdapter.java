package com.twoculture.twoculture.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.twoculture.twoculture.R;
import com.twoculture.twoculture.models.EventItem;
import com.twoculture.twoculture.models.TopicItem;
import com.twoculture.twoculture.ui.EventDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songxingchao on 3/10/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventItem> mEventsList = new ArrayList<>();
    public EventAdapter(Context context){
        this.mContext = context;
    }
    public void addData(List<EventItem> events){
        mEventsList.addAll(events);
        this.notifyDataSetChanged();
    }

    public void resetData(){
        mEventsList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(mContext)
                .inflate(R.layout.item_main_event,parent,false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final EventItem eventItem = mEventsList.get(position);
        holder.tv_title.setText(eventItem.title);
        holder.tv_activity_time.setText("Event Time: "+eventItem.activity_time);
        holder.tv_registration_deadline.setText("Deadline: "+eventItem.registration_deadline);
        holder.tv_joined_number.setText("Joined number: "+eventItem.join_num);
        Picasso.with(mContext).load(eventItem.event_bg).placeholder(R.drawable.default_image).into(holder.iv_event_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                Gson gson = new Gson();
                String eventString = gson.toJson(eventItem);
                intent.putExtra(EventDetailActivity.EVENT_DATA,eventString);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_title;
        ImageView iv_event_bg;
        TextView tv_activity_time;
        TextView tv_registration_deadline;
        TextView tv_joined_number;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_event_bg = (ImageView) itemView.findViewById(R.id.iv_event_bg);
            tv_activity_time = (TextView) itemView.findViewById(R.id.tv_activity_time);
            tv_registration_deadline = (TextView) itemView.findViewById(R.id.tv_registration_deadline);
            tv_joined_number = (TextView) itemView.findViewById(R.id.tv_joined_number);
        }
    }
}
