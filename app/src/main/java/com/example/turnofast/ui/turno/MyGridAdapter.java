package com.example.turnofast.ui.turno;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Turno;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Turno> turnos;
    LayoutInflater layoutInflater;

    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Turno> turnos) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = currentDate;
        this.turnos = turnos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCAlendar = Calendar.getInstance();
        dateCAlendar.setTime(monthDate);
        int DayNro = dateCAlendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCAlendar.get(Calendar.MONTH)+1;
        int displayYear = dateCAlendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view == null){
            view = layoutInflater.inflate(R.layout.single_cell_layout, parent,false);
        }

        if (displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
        }else {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Number = view.findViewById(R.id.calendar_day);
        Day_Number.setText(String.valueOf(DayNro));

        return view;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
