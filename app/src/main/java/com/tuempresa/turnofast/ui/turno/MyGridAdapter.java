package com.tuempresa.turnofast.ui.turno;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Feriado;
import com.tuempresa.turnofast.modelos.Turno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Turno> turnos;
    List<Feriado> feriados;
    LayoutInflater layoutInflater;


    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Turno> turnos, List<Feriado> feriados) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = currentDate;
        this.turnos = turnos;
        this.feriados = feriados;
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

        for (int i=0; i<feriados.size(); i++) {
            int dia = feriados.get(i).getDia();
            int mes = feriados.get(i).getMes();
            if ( mes == currentMonth && dia == DayNro){
                view.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
            }
        }

        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView cantidadTurnos = view.findViewById(R.id.evants_id);
        Day_Number.setText(String.valueOf(DayNro));

        Calendar turnoCalendario = Calendar.getInstance();
        ArrayList<Turno> listaTurnos = new ArrayList<Turno>();
        for (int i=0; i<turnos.size(); i++){
            turnoCalendario.setTime(convertirStringToDate(turnos.get(i).getFecha()));
            if (monthDate.getDate() == turnoCalendario.get(Calendar.DAY_OF_MONTH) && currentMonth == turnoCalendario.get(Calendar.MONTH)+1
                && currentYear == turnoCalendario.get(Calendar.YEAR)){
                listaTurnos.add(turnos.get(i));
                cantidadTurnos.setText(listaTurnos.size()+"turnos");
            }
        }

        return view;
    }

    private Date convertirStringToDate(String fecha){
        SimpleDateFormat diaFormato = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = diaFormato.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
