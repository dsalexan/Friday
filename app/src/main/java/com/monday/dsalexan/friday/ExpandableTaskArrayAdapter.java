package com.monday.dsalexan.friday;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danilo on 10/07/2017.
 */

public class ExpandableTaskArrayAdapter extends BaseExpandableListAdapter {

    private Context context;
    private int rootResource;
    private int childResource;
    private int reminderDateResourceId;
    private int taskTitleResourceId;
    private ArrayList<Task> tasks;

    public ExpandableTaskArrayAdapter(Context context,
                                      @LayoutRes int rootResource,
                                      @LayoutRes int childResource,
                                      @IdRes int taskTitleResourceId,
                                      @IdRes int reminderDateResourceId,
                                      ArrayList<Task> tasks) {
        this.context = context;
        this.rootResource = rootResource;
        this.childResource = childResource;
        this.reminderDateResourceId = reminderDateResourceId;
        this.taskTitleResourceId = taskTitleResourceId;

        //this.expandableListTitle = expandableListTitle;
        //this.expandableListDetail = expandableListDetail;
        this.tasks = tasks;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        //return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
        return this.tasks.get(listPosition).getReminders().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Reminder r = (Reminder) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(childResource, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(reminderDateResourceId);
        expandedListTextView.setText(r.getDate());

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        //return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        int t = this.tasks.get(listPosition).getReminders().size();
        return this.tasks.get(listPosition).getReminders().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        //return this.expandableListTitle.get(listPosition);
        return tasks.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        //return this.expandableListTitle.size();
        return tasks.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Task t = ((Task)getGroup(listPosition));

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(rootResource, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(taskTitleResourceId);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(t.getTitle());
        listTitleTextView.setTag(t.getId());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void clear(){
        this.tasks = new ArrayList<>();
    }

    public void addAll(ArrayList<Task> tasks){
        this.tasks = tasks;
    }
}