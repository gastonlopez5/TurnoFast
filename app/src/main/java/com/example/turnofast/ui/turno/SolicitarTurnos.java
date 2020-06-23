package com.example.turnofast.ui.turno;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Evento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitarTurnos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitarTurnos extends Fragment {

    ImageButton btSiguiente, btAtras;
    TextView fechaActual;
    GridView gvCalendario;
    Locale locale = new Locale("es", "AR");
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendario = Calendar.getInstance();
    SimpleDateFormat diaFormato = new SimpleDateFormat("MMMM yyyy", locale);
    SimpleDateFormat mesFormato = new SimpleDateFormat("MMMM", locale);
    SimpleDateFormat anioFormato = new SimpleDateFormat("yyyy", locale);
    SimpleDateFormat eventoFechaFormato = new SimpleDateFormat("dd-MM-yyyy", locale);
    List<Date> dates = new ArrayList<>();
    List<Evento> listaEventos = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitarTurnos() {
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
    public static SolicitarTurnos newInstance(String param1, String param2) {
        SolicitarTurnos fragment = new SolicitarTurnos();
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

        return view;
    }

    private void configurarCalendario() {
        String fecha = diaFormato.format(calendario.getTime());
        fechaActual.setText(fecha);
    }
}
