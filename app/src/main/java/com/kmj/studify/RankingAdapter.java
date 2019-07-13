package com.kmj.studify;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.UserModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private ArrayList<UserModel> mList;
    private MainActivity mainActivity;

    public RankingAdapter(ArrayList<UserModel> mList, MainActivity mainActivity) {
        this.mList = mList;
        this.mainActivity = mainActivity;
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView circleImageView;
        protected TextView fname;
        protected TextView avg;
        protected TextView max;

        public RankingViewHolder(@NonNull View v) {
            super(v);
            this.circleImageView = (CircleImageView) v.findViewById(R.id.ranking_pic);
            this.avg=(TextView)v.findViewById(R.id.ranking_avg);
            this.fname = (TextView) v.findViewById(R.id.ranking_name);
            this.max = (TextView) v.findViewById(R.id.ranking_max);

        }
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ranking_item, viewGroup, false);

        RankingViewHolder viewHolder = new RankingViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder rankingViewHolder, int i) {
        rankingViewHolder.fname.setText(mList.get(i).getName());
        Glide.with(mainActivity)
                .load(mList.get(i).getProfileURL())
                .fitCenter()
                .into(rankingViewHolder.circleImageView);
        int hour, min, sec;
        hour = (int) (mList.get(i).getMax_time() / 3600);
        min = (int) (mList.get(i).getMax_time() % 3600 / 60);
        sec = (int) (mList.get(i).getMax_time() % 3600 % 60);
        String max = String.valueOf(hour) + " : " + String.valueOf(min) + " : " + String.valueOf(sec);
        rankingViewHolder.max.setText(max);

        hour = (int) (mList.get(i).getAverage_time() / 3600);
        min = (int) (mList.get(i).getAverage_time() % 3600 / 60);
        sec = (int) (mList.get(i).getAverage_time() % 3600 % 60);
        String avg = String.valueOf(hour) + " : " + String.valueOf(min) + " : " + String.valueOf(sec);

        rankingViewHolder.avg.setText(avg);

    }

    @Override
    public int getItemCount() {

        return (null != mList ? mList.size() : 0);
    }


}
