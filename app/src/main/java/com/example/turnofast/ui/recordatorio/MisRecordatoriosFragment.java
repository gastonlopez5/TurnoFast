package com.example.turnofast.ui.recordatorio;

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
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.ui.prestacion.AdaptadorPrestacion;
import com.example.turnofast.ui.prestacion.ListaPrestacionesViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MisRecordatoriosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisRecordatoriosFragment extends Fragment {

    private RecyclerView rvRecordatorios;
    private RecordatorioViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MisRecordatoriosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisRecordatoriosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisRecordatoriosFragment newInstance(String param1, String param2) {
        MisRecordatoriosFragment fragment = new MisRecordatoriosFragment();
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
        View view = inflater.inflate(R.layout.fragment_mis_recordatorios, container, false);

        rvRecordatorios = view.findViewById(R.id.rvRecordatorios);
        rvRecordatorios.setLayoutManager(new LinearLayoutManager(getContext()));

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RecordatorioViewModel.class);

        vm.getError2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });

        vm.getListaRecordatorios().observe(getViewLifecycleOwner(), new Observer<List<DbTable>>() {
            @Override
            public void onChanged(List<DbTable> dbTables) {
                AdaptadorRecordatorios adaptador = new AdaptadorRecordatorios(dbTables, getContext());

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbTable dbTable = dbTables.get(rvRecordatorios.getChildAdapterPosition(v));
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("recordatorio", dbTable);
                        Navigation.findNavController(v).navigate(R.id.nav_actualizarRecordatorio, bundle);
                    }
                });

                rvRecordatorios.setAdapter(adaptador);
            }
        });

        vm.recuperarRecordatorios();

        return view;
    }
}
