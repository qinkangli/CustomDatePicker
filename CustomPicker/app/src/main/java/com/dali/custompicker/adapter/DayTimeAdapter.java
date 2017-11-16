package com.dali.custompicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dali.custompicker.MainActivity;
import com.dali.custompicker.R;
import com.dali.custompicker.bean.DayTimeEntity;
import com.dali.custompicker.bean.UpdataCalendar;
import com.dali.custompicker.holder.DayTimeViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by xqx on 2017/1/17.
 */
public class DayTimeAdapter extends RecyclerView.Adapter<DayTimeViewHolder>{

    private ArrayList<DayTimeEntity> days;
    private Context context;

    public DayTimeAdapter(ArrayList<DayTimeEntity> days, Context context) {
        this.days = days;
        this.context = context;

    }

    @Override
    public DayTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DayTimeViewHolder ret = null;
        // 不需要检查是否复用，因为只要进入此方法，必然没有复用
        // 因为RecyclerView 通过Holder检查复用
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_selectday, parent, false);
        ret = new DayTimeViewHolder(v);

        return ret;
    }

    @Override
    public void onBindViewHolder(final DayTimeViewHolder holder, final int position) {
        final DayTimeEntity dayTimeEntity = days.get(position);
        //显示日期
        if (dayTimeEntity.getDay()!=0) {
            if (dayTimeEntity.getStatus() == 100){
                holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
                holder.select_ly_day.setEnabled(false);
                holder.select_txt_day.setTextColor(Color.parseColor("#FFFFFF"));
            }else if (dayTimeEntity.getStatus() == 101){
                holder.select_txt_day.setText("今天");
                holder.select_ly_day.setEnabled(true);
            }else {
                holder.select_txt_day.setText(dayTimeEntity.getDay() + "");
                holder.select_ly_day.setEnabled(true);
            }
        }else{
            holder.select_ly_day.setEnabled(false);
        }
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.startDay.getYear() == 0 ){          // 第一次点击开始的位置，因为开始默认参数是 0,0,0,0
                    MainActivity.startDay.setDay(dayTimeEntity.getDay());           // 该item 天数的 年月日等信息  赋给  开始日期
                    MainActivity.startDay.setMonth(dayTimeEntity.getMonth());
                    MainActivity.startDay.setYear(dayTimeEntity.getYear());
                    MainActivity.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                    MainActivity.startDay.setDayPosition(position);
                }else if(MainActivity.startDay.getYear()>0 && MainActivity.stopDay.getYear() ==-1){      //已经点击了开始 ，点击结束位置，（默认结束位置-1,-1,-1,-1 说明还没有点击结束位置）
                    if (dayTimeEntity.getYear()> MainActivity.startDay.getYear()) {
                        //如果选中的年份大于开始的年份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                        MainActivity.stopDay.setDay(dayTimeEntity.getDay());
                        MainActivity.stopDay.setMonth(dayTimeEntity.getMonth());
                        MainActivity.stopDay.setYear(dayTimeEntity.getYear());
                        MainActivity.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        MainActivity.stopDay.setDayPosition(position);
                    }else if (dayTimeEntity.getYear() == MainActivity.startDay.getYear()){
                        //如果选中的年份 等于 选中的年份
                        if (dayTimeEntity.getMonth()> MainActivity.startDay.getMonth()){
                            //如果改item的天数的月份大于开始日期的月份，说明结束日期肯定大于开始日期 ，合法的 ，将该item的天数的 信息  赋给 结束日期
                            MainActivity.stopDay.setDay(dayTimeEntity.getDay());
                            MainActivity.stopDay.setMonth(dayTimeEntity.getMonth());
                            MainActivity.stopDay.setYear(dayTimeEntity.getYear());
                            MainActivity.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            MainActivity.stopDay.setDayPosition(position);
                        }else if(dayTimeEntity.getMonth() == MainActivity.startDay.getMonth()){
                            //年份月份 都相等
                            if (dayTimeEntity.getDay() >= MainActivity.startDay.getDay()){
                                //判断天数 ，如果 该item的天数的 日子大于等于 开始日期的 日子 ，说明结束日期合法的 ，将该item的天数的 信息  赋给 结束日期
                                MainActivity.stopDay.setDay(dayTimeEntity.getDay());
                                MainActivity.stopDay.setMonth(dayTimeEntity.getMonth());
                                MainActivity.stopDay.setYear(dayTimeEntity.getYear());
                                MainActivity.stopDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                MainActivity.stopDay.setDayPosition(position);
                            }else{
                                //天数小与初始  从新选择开始  ，结束日期重置，开始日期为当前的位置的天数的信息
                                MainActivity.startDay.setDay(dayTimeEntity.getDay());
                                MainActivity.startDay.setMonth(dayTimeEntity.getMonth());
                                MainActivity.startDay.setYear(dayTimeEntity.getYear());
                                MainActivity.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                                MainActivity.startDay.setDayPosition(position);
                                MainActivity.stopDay.setDay(-1);
                                MainActivity.stopDay.setMonth(-1);
                                MainActivity.stopDay.setYear(-1);
                                MainActivity.stopDay.setMonthPosition(-1);
                                MainActivity.stopDay.setDayPosition(-1);
                            }
                        }else {
                            //选中的月份 比开始日期的月份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                            MainActivity.startDay.setDay(dayTimeEntity.getDay());
                            MainActivity.startDay.setMonth(dayTimeEntity.getMonth());
                            MainActivity.startDay.setYear(dayTimeEntity.getYear());
                            MainActivity.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                            MainActivity.startDay.setDayPosition(position);
                            MainActivity.stopDay.setDay(-1);
                            MainActivity.stopDay.setMonth(-1);
                            MainActivity.stopDay.setYear(-1);
                            MainActivity.stopDay.setMonthPosition(-1);
                            MainActivity.stopDay.setDayPosition(-1);
                        }

                    }else{
                        //选中的年份 比开始日期的年份还小，说明 结束位置不合法，结束日期重置，开始日期为当前的位置的天数的信息
                        MainActivity.startDay.setDay(dayTimeEntity.getDay());
                        MainActivity.startDay.setMonth(dayTimeEntity.getMonth());
                        MainActivity.startDay.setYear(dayTimeEntity.getYear());
                        MainActivity.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                        MainActivity.startDay.setDayPosition(position);
                        MainActivity.stopDay.setDay(-1);
                        MainActivity.stopDay.setMonth(-1);
                        MainActivity.stopDay.setYear(-1);
                        MainActivity.stopDay.setMonthPosition(-1);
                        MainActivity.stopDay.setDayPosition(-1);
                    }
                }else if(MainActivity.startDay.getYear()>0 && MainActivity.startDay.getYear()>1){      //已经点击开始和结束   第三次点击 ，重新点击开始
                    MainActivity.startDay.setDay(dayTimeEntity.getDay());
                    MainActivity.startDay.setMonth(dayTimeEntity.getMonth());
                    MainActivity.startDay.setYear(dayTimeEntity.getYear());
                    MainActivity.startDay.setMonthPosition(dayTimeEntity.getMonthPosition());
                    MainActivity.startDay.setDayPosition(position);
                    MainActivity.stopDay.setDay(-1);
                    MainActivity.stopDay.setMonth(-1);
                    MainActivity.stopDay.setYear(-1);
                    MainActivity.stopDay.setMonthPosition(-1);
                    MainActivity.stopDay.setDayPosition(-1);
                }
                EventBus.getDefault().post(new UpdataCalendar()); // 发消息刷新适配器，目的为了显示日历上各个日期的背景颜色
            }
        });


        if (MainActivity.startDay.getYear()== dayTimeEntity.getYear() && MainActivity.startDay.getMonth() == dayTimeEntity.getMonth() && MainActivity.startDay.getDay() == dayTimeEntity.getDay()
                && MainActivity.stopDay.getYear()== dayTimeEntity.getYear() && MainActivity.stopDay.getMonth() == dayTimeEntity.getMonth() && MainActivity.stopDay.getDay() == dayTimeEntity.getDay() ){
            //开始和结束同一天
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_startstop);

        }
        else if (MainActivity.startDay.getYear()== dayTimeEntity.getYear() && MainActivity.startDay.getMonth() == dayTimeEntity.getMonth() && MainActivity.startDay.getDay() == dayTimeEntity.getDay()){
            //该item是 开始日期
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start);
        }else if(MainActivity.stopDay.getYear()== dayTimeEntity.getYear() && MainActivity.stopDay.getMonth() == dayTimeEntity.getMonth() && MainActivity.stopDay.getDay() == dayTimeEntity.getDay()){
            //该item是 结束日期
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_stop);
        }else if(dayTimeEntity.getMonthPosition()>= MainActivity.startDay.getMonthPosition() && dayTimeEntity.getMonthPosition()<= MainActivity.stopDay.getMonthPosition()){
            //处于开始和结束之间的点
            if (dayTimeEntity.getMonthPosition()== MainActivity.startDay.getMonthPosition()&& dayTimeEntity.getMonthPosition()== MainActivity.stopDay.getMonthPosition()){
                //开始和结束是一个月份
                if (dayTimeEntity.getDay()> MainActivity.startDay.getDay() && dayTimeEntity.getDay() < MainActivity.stopDay.getDay()) {
                    holder.select_ly_day.setBackgroundResource(R.color.blue);
                }else{
                    if (dayTimeEntity.getStatus() == 100){
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_gray);
                    }else {
                        holder.select_ly_day.setBackgroundResource(R.color.white);
                    }
                }
            }else if(MainActivity.startDay.getMonthPosition() != MainActivity.stopDay.getMonthPosition()){
                // 日期和 开始 不是一个月份
                if (dayTimeEntity.getMonthPosition()== MainActivity.startDay.getMonthPosition() && dayTimeEntity.getDay()> MainActivity.startDay.getDay()){
                    //和初始相同月  天数往后
                    holder.select_ly_day.setBackgroundResource(R.color.blue);
                }else if(dayTimeEntity.getMonthPosition()== MainActivity.stopDay.getMonthPosition() && dayTimeEntity.getDay()< MainActivity.stopDay.getDay()){
                    //和结束相同月   天数往前
                    holder.select_ly_day.setBackgroundResource(R.color.blue);
                }else if(dayTimeEntity.getMonthPosition()!= MainActivity.startDay.getMonthPosition() && dayTimeEntity.getMonthPosition()!= MainActivity.stopDay.getMonthPosition()){
                    //和 开始结束都不是同一个月
                    holder.select_ly_day.setBackgroundResource(R.color.blue);
                }else{
                    if (dayTimeEntity.getStatus() == 100){
                        holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_gray);
                    }else {
                        holder.select_ly_day.setBackgroundResource(R.color.white);
                    }
                }
            }

        }else {
            if (dayTimeEntity.getStatus() == 100){
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_gray);
            }else {
                holder.select_ly_day.setBackgroundResource(R.color.white);
            }
        }

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (days!=null){
            ret = days.size();
        }
        return ret;
    }
}
