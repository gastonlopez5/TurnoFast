package com.tuempresa.turnofast.ui.turno;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Feriado;
import com.tuempresa.turnofast.modelos.Turno;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TurnosSolicitadosPorMesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TurnosSolicitadosPorMesFragment extends Fragment {

    private ImageButton btSiguiente, btAtras;
    private TextView fechaActual;
    private GridView gvCalendario;
    private Locale locale = new Locale("es", "AR");
    private static final int MAX_CALENDAR_DAYS = 42;
    private Calendar calendario = Calendar.getInstance();
    private SimpleDateFormat diaFormato = new SimpleDateFormat("MMMM yyyy", locale);
    private SimpleDateFormat anioFormato = new SimpleDateFormat("yyyy");
    private SimpleDateFormat mesFormato = new SimpleDateFormat("MM");
    private SimpleDateFormat eventoFechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    private List<Date> dates = new ArrayList<>();
    private List<Turno> listaTurnos = new ArrayList<>();
    private MyGridAdapter myGridAdapter;
    private MisTurnosViewModel vm;
    private List<Feriado> listaFeriados;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TurnosSolicitadosPorMesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TurnosSolicitadosPorMesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TurnosSolicitadosPorMesFragment newInstance(String param1, String param2) {
        TurnosSolicitadosPorMesFragment fragment = new TurnosSolicitadosPorMesFragment();
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
        final View view = inflater.inflate(R.layout.fragment_turnos_solicitados_por_mes, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MisTurnosViewModel.class);

        btAtras = view.findViewById(R.id.btAtras);
        btSiguiente = view.findViewById(R.id.btSiguiente);
        fechaActual = view.findViewById(R.id.tvFechaActual);
        gvCalendario = view.findViewById(R.id.gvCalendario);

        vm.getFeriados().observe(getViewLifecycleOwner(), new Observer<List<Feriado>>() {
            @Override
            public void onChanged(List<Feriado> feriados) {
                listaFeriados = feriados;
                configurarCalendario(vm, view, feriados);
            }
        });

        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.add(Calendar.MONTH, -1);
                configurarCalendario(vm, view, listaFeriados);
            }
        });

        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.add(Calendar.MONTH, +1);
                configurarCalendario(vm, view, listaFeriados);
            }
        });

        gvCalendario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String fecha = eventoFechaFormato.format(dates.get(position));

                Bundle bundle = new Bundle();
                bundle.putString("fecha", fecha);
                Navigation.findNavController(v).navigate(R.id.nav_listaTurnosSolicitadosPorDia, bundle);
            }
        });

        vm.obtenerFeriados();

        return view;
    }

    private void configurarCalendario(MisTurnosViewModel vm, final View view, List<Feriado> dias) {
        String fecha = diaFormato.format(calendario.getTime());
        fechaActual.setText(fecha);
        dates.clear();
        final Calendar monthCalendar = (Calendar) calendario.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        vm.getListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(ArrayList<Turno> turnos) {
                listaTurnos = turnos;
                while (dates.size() < MAX_CALENDAR_DAYS){
                    dates.add(monthCalendar.getTime());
                    monthCalendar.add(Calendar.DAY_OF_MONTH,1);
                }

                myGridAdapter = new MyGridAdapter(getContext(), dates, calendario, listaTurnos, dias);
                gvCalendario.setAdapter(myGridAdapter);
            }
        });

        vm.getSinTurnos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });
        vm.turnoSolicitadosPorMes(mesFormato.format(calendario.getTime()), anioFormato.format(calendario.getTime()));
    }
}
