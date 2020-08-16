package com.tuempresa.turnofast.ui.turno;

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

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Turno;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaTurnosPorFechaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaTurnosPorFechaFragment extends Fragment {

    private RecyclerView rvTurnos;
    private MisTurnosViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaTurnosPorFechaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaTurnosPorFechaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaTurnosPorFechaFragment newInstance(String param1, String param2) {
        ListaTurnosPorFechaFragment fragment = new ListaTurnosPorFechaFragment();
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
        final View view = inflater.inflate(R.layout.fragment_lista_turnos_por_fecha, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MisTurnosViewModel.class);

        rvTurnos = view.findViewById(R.id.rvTurnos);
        rvTurnos.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.getSinTurnos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.nav_misTurnos);
            }
        });

        vm.getListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(final ArrayList<Turno> turnos) {
                AdaptadorTurnosPorFecha adaptador = new AdaptadorTurnosPorFecha(turnos, getContext());

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Turno turno = turnos.get(rvTurnos.getChildAdapterPosition(v));
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("turno", turno);
                        Navigation.findNavController(v).navigate(R.id.nav_detalleMisTurnos, bundle);
                    }
                });

                rvTurnos.setAdapter(adaptador);
            }
        });

        Bundle objetoHorarioFecha = getArguments();
        vm.turnosPorDia(objetoHorarioFecha.getString("fecha"));

        return view;
    }
}
