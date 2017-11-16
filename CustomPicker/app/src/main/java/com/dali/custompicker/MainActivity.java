package com.dali.custompicker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dali.custompicker.adapter.MonthTimeAdapter;
import com.dali.custompicker.bean.DayTimeEntity;
import com.dali.custompicker.bean.MonthTimeEntity;
import com.dali.custompicker.bean.UpdataCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity {

    private ImageButton back;
    private TextView startTime;          //开始时间
    private TextView stopTime;           //结束时间
    private TextView plan_time_txt_month;
    private RecyclerView reycycler;
    private MonthTimeAdapter adapter;
    private ArrayList<MonthTimeEntity> datas;

    public static int today = 0;

    public static DayTimeEntity startDay;
    public static DayTimeEntity stopDay;

    private int mSuspensionHeight;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        initView();
        initData();

        EventBus.getDefault().register(this);


    }

    private void initData() {
        startDay = new DayTimeEntity(0,0,0,0);
        stopDay = new DayTimeEntity(-1,-1,-1,-1);
        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();

        today = c.get(Calendar.DAY_OF_MONTH);

        c.add(Calendar.MONTH,1);
        int nextYear = c.get(Calendar.YEAR);
        int nextMonth = c.get(Calendar.MONTH);

        for (int i = 0; i < 5; i++) {
            datas.add(new MonthTimeEntity(nextYear,nextMonth,nextYear + "--"+ nextMonth));
            if (nextMonth == 12){
                nextMonth = 0;
                nextYear = nextYear + 1;
            }
            nextMonth = nextMonth + 1;
        }

        adapter = new MonthTimeAdapter(datas, MainActivity.this);
        reycycler.setAdapter(adapter);

    }

    private void initView() {
        startTime = (TextView) findViewById(R.id.plan_time_txt_start);
        stopTime = (TextView) findViewById(R.id.plan_time_txt_stop);
        plan_time_txt_month = (TextView) findViewById(R.id.plan_time_txt_month);
        reycycler = (RecyclerView) findViewById(R.id.plan_time_calender);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,   // 上下文
                        LinearLayout.VERTICAL,  //垂直布局,
                        false);

        reycycler.setLayoutManager(layoutManager);

        reycycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = plan_time_txt_month.getHeight();
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = layoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mSuspensionHeight) {
                        plan_time_txt_month.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        plan_time_txt_month.setY(0);
                    }
                }

                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    plan_time_txt_month.setY(0);
                    plan_time_txt_month.setText(datas.get(mCurrentPosition).getSticky());
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdataCalendar event) {
        adapter.notifyDataSetChanged();
        startTime.setText("入住:" + startDay.getMonth()+"月"+startDay.getDay()+"日"+"\n");
        if (stopDay.getDay() == -1) {
            stopTime.setText("结束"+"\n"+"时间");
        }else{
            stopTime.setText("离店:" + stopDay.getMonth() + "月" + stopDay.getDay() + "日" + "\n");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
