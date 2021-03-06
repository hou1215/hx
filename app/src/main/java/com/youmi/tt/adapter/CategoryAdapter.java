package com.youmi.tt.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youmi.tt.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by hx on 2016/11/24.
 */
public class CategoryAdapter extends BaseAdapter {

    private final List<String> lists;
    private final Context context;
    private final LayoutInflater infater;
    private int index = 0;

    public CategoryAdapter(Context context , List<String> lists){
        this.context = context;
        this.lists = lists;
        infater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelect(int index){
        this.index = index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = infater.inflate(R.layout.item_classify, null);
            holder = new ViewHolder();

            holder.nama = (TextView) view.findViewById(R.id.name);
            holder.tag = view.findViewById(R.id.tag);
            view.setTag(holder);
            AutoUtils.autoSize(view);

        }else {
            holder = (ViewHolder) view.getTag();
        }


        holder.nama.setText(lists.get(i));
        holder.tag.setVisibility(View.GONE);

        if (index == i){
            holder.nama.setTextColor(context.getResources().getColor(R.color.theme));
            holder.tag.setVisibility(View.VISIBLE);
        }else {
            holder.nama.setTextColor(context.getResources().getColor(R.color.color_text));
            holder.tag.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHolder {
        TextView nama;
        View tag;
    }

}
