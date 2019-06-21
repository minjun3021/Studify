package com.kmj.studify;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kmj.studify.activity.MainActivity;
import com.kmj.studify.data.UserModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>  {

    ArrayList<UserModel> mList;
    MainActivity mainActivity;

    public FriendsAdapter(ArrayList<UserModel> mList, MainActivity mainActivity) {
        this.mList = mList;
        this.mainActivity = mainActivity;
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView circleImageView;
        protected TextView fname;
        protected TextView time;
        protected CircleImageView doing;

        public FriendsViewHolder(@NonNull View v) {
            super(v);
            this.circleImageView=(CircleImageView) v.findViewById(R.id.friend_pic);
            this.fname=(TextView) v.findViewById(R.id.friends_name);
            this.time=(TextView)v.findViewById(R.id.friends_time);
            this.doing=(CircleImageView) v.findViewById(R.id.friendDoing);

        }
    }
    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_item, viewGroup, false);

        FriendsAdapter.FriendsViewHolder viewHolder = new FriendsAdapter.FriendsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder friendsViewHolder, int i) {
        friendsViewHolder.fname.setText(mList.get(i).getName());
        Glide.with(mainActivity)
                .load(mList.get(i).getProfileURL())
                .fitCenter()
                .into(friendsViewHolder.circleImageView);
        if(mList.get(i).getStart_time()==-1 && mList.get(i).getEnd_time()==-1){
            friendsViewHolder.time.setText("");
            friendsViewHolder.doing.setImageResource(R.drawable.offline);
        }
        else if(mList.get(i).getStart_time()==-1){
            friendsViewHolder.doing.setImageResource(R.drawable.offline);
            friendsViewHolder.time.setText("");
        }
        else if(mList.get(i).getEnd_time()==-1){
            friendsViewHolder.doing.setImageResource(R.drawable.online);
            long time;
            time=System.currentTimeMillis()-mList.get(i).getStart_time();
            Log.e("currentitme",String.valueOf(System.currentTimeMillis())+" "+String.valueOf(mList.get(i).getStart_time()));
            Log.e("minus",String.valueOf(time));
            time/=1000;
            int hour, min, sec;
            hour = (int) (time / 3600);
            min = (int) (time% 3600 / 60);
            sec = (int) (time% 3600 % 60);
            String max = mList.get(i).getCurrent()+" | "+String.valueOf(hour) + " : " + String.valueOf(min) + " : " + String.valueOf(sec);

            friendsViewHolder.time.setText(max);

        }
    }

    @Override
    public int getItemCount() {


        return (null != mList ? mList.size() : 0);
    }


}
