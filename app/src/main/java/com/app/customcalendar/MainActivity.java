package com.app.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import org.threeten.bp.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView startDate, endDate;
    Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDate = findViewById(R.id.tv_start_date);
        endDate = findViewById(R.id.tv_end_date);
        btnShow = findViewById(R.id.btn_show);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });
    }

    private void showCalendar() {
        CalendarDialog calendar = new CalendarDialog();
        calendar.show(getSupportFragmentManager(), calendar.getTag());


        calendar.setCalendarInterface(new CalendarDialog.CalendarInterface() {
            @Override
            public void onDateRange(List<CalendarDay> dates) {
                LocalDate dateRange1 = dates.get(0).getDate();
                LocalDate dateRange2 = dates.get(dates.size() -1).getDate();
                String sDate1 = dateRange1.toString();
                String sDate2 = dateRange2.toString();

                startDate.setText(sDate1);
                endDate.setText(sDate2);
            }
        });
    }
}
