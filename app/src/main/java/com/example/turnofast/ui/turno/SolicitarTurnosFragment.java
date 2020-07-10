package com.example.turnofast.ui.turno;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Categoria;
import com.example.turnofast.modelos.Evento;
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.HorarioFecha;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Turno;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitarTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitarTurnosFragment extends Fragment {

    ImageButton btSiguiente, btAtras;
    TextView fechaActual;
    GridView gvCalendario;
    Locale locale = new Locale("es", "AR");
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendario = Calendar.getInstance();
    SimpleDateFormat diaFormato = new SimpleDateFormat("MMMM yyyy", locale);
    SimpleDateFormat anioFormato = new SimpleDateFormat("yyyy", locale);
    SimpleDateFormat eventoFechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    List<Date> dates = new ArrayList<>();
    List<Turno> listaTurnos = new ArrayList<>();
    MyGridAdapter myGridAdapter;
    Prestacion prestacionSeleccionada;
    Horario2 horario;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitarTurnosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolicitarTurnos.
     */
    // TODO: Rename and change types and number of parameters
    public static SolicitarTurnosFragment newInstance(String param1, String param2) {
        SolicitarTurnosFragment fragment = new SolicitarTurnosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitar_turnos, container, false);

        btAtras = view.findViewById(R.id.btAtras);
        btSiguiente = view.findViewById(R.id.btSiguiente);
        fechaActual = view.findViewById(R.id.tvFechaActual);
        gvCalendario = view.findViewById(R.id.gvCalendario);

        configurarCalendario();

        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.add(Calendar.MONTH, -1);
                configurarCalendario();
            }
        });

        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.add(Calendar.MONTH, +1);
                configurarCalendario();
            }
        });

        gvCalendario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,position+1);
                Date d = c.getTime();
                int nrodia = d.getDay();
                String fecha = eventoFechaFormato.format(c.getTime());

                horario = new Horario2();
                horario.setPrestacionId(prestacionSeleccionada.getId());
                horario.setDiaSemana(nrodia);

                HorarioFecha horarioFecha = new HorarioFecha();
                horarioFecha.setHorario(horario);
                horarioFecha.setFecha(fecha);

                Bundle bundle = new Bundle();
                bundle.putSerializable("horarioFecha", horarioFecha);
                Navigation.findNavController(view).navigate(R.id.nav_listaTurnosDisponibles, bundle);
            }
        });

        Bundle objetoPrestacion = getArguments();
        prestacionSeleccionada =(Prestacion) objetoPrestacion.getSerializable("prestacion");

        return view;
    }

    private void configurarCalendario() {
        String fecha = diaFormato.format(calendario.getTime());
        fechaActual.setText(fecha);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendario.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        while (dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }

        myGridAdapter = new MyGridAdapter(getContext(), dates, calendario, listaTurnos);
        gvCalendario.setAdapter(myGridAdapter);
    }
}
