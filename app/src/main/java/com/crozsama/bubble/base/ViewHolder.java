package com.crozsama.bubble.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 93201 on 2017/11/8.
 */

public class ViewHolder {
    private SparseArray<View> views;
    private int position;
    private View convertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position)
    {
        this.position = position;
        this.views = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context,View convertView, ViewGroup parent,int layoutId,int position)
    {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder(context, parent, layoutId, position);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            holder.position = position;
        }
        return holder;
    }


    public <T extends View> T getView(int viewId)
    {
        View view = views.get(viewId);
        if(view == null)
        {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return convertView;
    }
    public int getPosition()
    {
        return position;
    }

    public ViewHolder setText(String text,int id)
    {
        TextView tv = (TextView) convertView.findViewById(id);
        tv.setText(text);
        return this;
    }
}
