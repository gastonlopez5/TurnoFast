package com.example.turnofast.ui.turno;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Categoria;
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.HorarioFecha;
import com.example.turnofast.modelos.Turno;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaTurnosDisponiblesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaTurnosDisponiblesFragment extends Fragment {

    RecyclerView rvTurnos;
    SolicitarTurnosViewModel vm;
    HorarioFecha horarioFechaEnviado;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaTurnosDisponiblesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaTurnosDisponiblesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaTurnosDisponiblesFragment newInstance(String param1, String param2) {
        ListaTurnosDisponiblesFragment fragment = new ListaTurnosDisponiblesFragment();
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
        final View view = inflater.inflate(R.layout.fragment_lista_turnos_disponibles, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SolicitarTurnosViewModel.class);

        rvTurnos = view.findViewById(R.id.rvTurnos);
        rvTurnos.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.getSinTurnos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.nav_solicitarTurnos);
            }
        });

        vm.getListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(final ArrayList<Turno> turnos) {
                AdaptadorTurnos adaptador = new AdaptadorTurnos(turnos, getContext());

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Turno turno = turnos.get(rvTurnos.getChildAdapterPosition(v));
                        vm.agregarTurno(turno);
                        Navigation.findNavController(view).navigate(R.id.nav_home);
                    }
                });

                rvTurnos.setAdapter(adaptador);
            }
        });

        Bundle objetoHorarioFecha = getArguments();
        horarioFechaEnviado =(HorarioFecha) objetoHorarioFecha.getSerializable("horarioFecha");
        vm.cargarTurnosDisponibles(horarioFechaEnviado.getHorario().getPrestacionId(),
                horarioFechaEnviado.getHorario().getDiaSemana(), horarioFechaEnviado.getFecha());

        return view;
    }
}
