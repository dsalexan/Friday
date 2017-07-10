package com.monday.dsalexan.friday;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Danilo on 09/07/2017.
 */

class TaskArrayAdapter extends ArrayAdapter {

    public ArrayAdapter<String> reminderAdapter;

    private int layoutResource;
    private int textViewResourceId;
    private int listViewResourceId;
    private int textViewResource;

    TaskArrayAdapter(@NonNull Context context,
                     @LayoutRes int resource, // layout do item task (c textView e listView e buttons)
                     @IdRes int textViewResourceId, // control do titulo da task
                     @IdRes int listViewResourceId, // control do listView dos reminders da task
                     @LayoutRes int textViewResource, // layout do textView que vai entrar no listView dos reminders da task
                     @NonNull ArrayList<Task> objects) {
        super(context, 0, objects);
        layoutResource = resource;
        this.textViewResourceId = textViewResourceId;
        this.listViewResourceId= listViewResourceId;
        this.textViewResource = textViewResource;
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

        ListView taskReminders = (ListView)convertView.findViewById(listViewResourceId);
        if(reminderAdapter == null) {
            reminderAdapter = new ArrayAdapter<String>(getContext(), textViewResource, t.getFlatReminders());
            taskReminders.setAdapter(reminderAdapter);
        }else{
            reminderAdapter.clear();
            reminderAdapter.addAll(t.getFlatReminders());
            reminderAdapter.notifyDataSetChanged();
        }
        setListViewHeightBasedOnChildren(taskReminders);

        return convertView;
    }

    // todo Verificar pq só funciona no segundo listView, n no pai
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
