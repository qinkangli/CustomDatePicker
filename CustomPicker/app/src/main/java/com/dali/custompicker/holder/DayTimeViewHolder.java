package com.dali.custompicker.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dali.custompicker.R;

/**
 * Created by acer on 2017/11/15.
 */

public class DayTimeViewHolder extends RecyclerView.ViewHolder{

    public TextView select_txt_day;      //日期文本
    public LinearLayout select_ly_day;       //父容器 ， 用于点击日期

    public DayTimeViewHolder(View itemView) {
        super(itemView);
        select_ly_day = (LinearLayout) itemView.findViewById(R.id.select_ly_day);
        select_txt_day = (TextView) itemView.findViewById(R.id.select_txt_day);
    }
}
