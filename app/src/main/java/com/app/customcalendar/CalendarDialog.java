package com.app.customcalendar;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarDialog extends BottomSheetDialogFragment implements DayViewDecorator {
    final static String TAG = CalendarDialog.class.getSimpleName();

    List<CalendarDay> rangeDate = new ArrayList<>();
    private CalendarInterface calendarInterface;
    private MaterialCalendarView mcv;
    private RelativeLayout rlBtnShow;




    public CalendarDialog() {
    }

    public void setCalendarInterface(CalendarInterface calendarInterface) {
        this.calendarInterface = calendarInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar, container, false);


        // Get Min & Max Date
        String[] today  = getToday();
        String[] last6month = getLast6month();

        mcv = view.findViewById(R.id.calendar_view);
        rlBtnShow = view.findViewById(R.id.rl_btn_show);

        mcv.state().edit()
                .setMinimumDate(CalendarDay.from(Integer.parseInt(last6month[0]), Integer.parseInt(last6month[1]), Integer.parseInt(last6month[2])))
                .setMaximumDate(CalendarDay.from(Integer.parseInt(today[0]), Integer.parseInt(today[1]), Integer.parseInt(today[2])))
                .commit();

        mcv.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                rangeDate = dates;
            }
        });


        rlBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rangeDate.size() > 1) {
                    calendarInterface.onDateRange(rangeDate);
                    dismiss();
                }
            }
        });


        return view;
    }


    private String[] getToday() {
        String[] result = new String[3];

        Date today = new Date();
        result[0] = (String) DateFormat.format("yyyy", today);
        result[1] = (String) DateFormat.format("MM", today);
        result[2] = (String) DateFormat.format("dd", today);
        Log.d(TAG, "getToday: " + result[0] + result[1] + result[2]);

        return result;
    }

    private String[] getLast6month() {
        String[] result = new String[3];

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date last6month = calendar.getTime();

        result[0] = (String) DateFormat.format("yyyy", last6month);
        result[1] = (String) DateFormat.format("MM", last6month);
        result[2] = (String) DateFormat.format("dd", last6month);
        Log.d(TAG, "getLast6month: " + result[0] + result[1] + result[2]);

        return result;
    }



    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {

    }


    public interface CalendarInterface {
        void onDateRange(List<CalendarDay> dates);
    }
}
