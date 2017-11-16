package com.dali.custompicker.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dali.custompicker.R;

/**
 * Created by acer on 2017/11/15.
 */

public class MonthTimeViewHolder extends RecyclerView.ViewHolder{

    public TextView plan_time_txt_month;                         //文本 2018-1
    public RecyclerView plan_time_recycler_content ;            //月份里面详细日期的列表
    public Context context;                                        //上下文

    public MonthTimeViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        plan_time_recycler_content = (RecyclerView) itemView.findViewById(R.id.plan_time_recycler_content);
        plan_time_txt_month = (TextView) itemView.findViewById(R.id.plan_time_txt_month);

        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(context,
                        7,  // 每行显示item项数目
                        GridLayoutManager.VERTICAL, //水平排列
                        false
                );

        plan_time_recycler_content.setLayoutManager(layoutManager);
    }
}