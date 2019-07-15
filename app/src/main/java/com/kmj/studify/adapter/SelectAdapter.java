package com.kmj.studify.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kmj.studify.R;
import com.kmj.studify.data.Subject;

import java.util.ArrayList;

public class SelectAdapter extends BaseAdapter {
    ArrayList<Subject> data;
    Context context;
    int layout;
    OnItemClickListener listener;

    public SelectAdapter(Context context,ArrayList<Subject> data,  int layout) {
        this.data = data;
        this.context = context;
        this.layout = layout;
        listener= (OnItemClickListener) context;
    }

    public interface OnItemClickListener {
        void itemDelete(int position);
        void toNoBookMark(int position);
        void toBookMark(int position);

    }

    @Override
    public int getCount() {
        return (null != data ? data.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView name=convertView.findViewById(R.id.select_item_name);
        name.setText(data.get(position).getName());
        ImageView bookmark=convertView.findViewById(R.id.select_item_isbookmark);
        TextView delete=convertView.findViewById(R.id.select_item_delete);
        if(data.get(position).isBookmarked()){
            bookmark.setImageResource(R.drawable.bookmark);
        }else{
            bookmark.setImageResource(R.drawable.nobookmark);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemDelete(position);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).isBookmarked()){
                    listener.toNoBookMark(position);
                }
                else{
                    listener.toBookMark(position);
                }
            }
        });


        return convertView;
    }
}
