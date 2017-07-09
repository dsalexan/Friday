package com.monday.dsalexan.friday;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Danilo on 09/07/2017.
 */

class TaskArrayAdapter extends ArrayAdapter {

    private int layoutResource;
    private int textViewResourceId;

    TaskArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull ArrayList<Task> objects) {
        super(context, 0, objects);
        layoutResource = resource;
        this.textViewResourceId = textViewResourceId;
    }

    void addAll(@NonNull ArrayList<Task> objects) {
        super.addAll(objects);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        Task t = (Task)getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        TextView taskTitle = (TextView)convertView.findViewById(textViewResourceId);
        taskTitle.setText(t.getTitle());
        taskTitle.setTag(t.getId());

        return convertView;
    }
}
