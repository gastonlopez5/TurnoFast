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
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.ui.prestacion.AdaptadorPrestacion;
import com.example.turnofast.ui.prestacion.ListaPrestacionesViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPrestacionesDisponiblesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPrestacionesDisponiblesFragment extends Fragment {

    private RecyclerView rvPrestaciones;
    private ListaPrestacionesViewModel vm;
    private Categoria categoriaSeleccionada = null;
    private boolean bandera = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPrestacionesDisponiblesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPrestacionesDisponiblesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPrestacionesDisponiblesFragment newInstance(String param1, String param2) {
        ListaPrestacionesDisponiblesFragment fragment = new ListaPrestacionesDisponiblesFragment();
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
        final View view = inflater.inflate(R.layout.fragment_lista_prestaciones_disponibles, container, false);

        rvPrestaciones = view.findViewById(R.id.rvPrestacion);
        rvPrestaciones.setLayoutManager(new LinearLayoutManager(getContext()));

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ListaPrestacionesViewModel.class);

        vm.getListaPrestaciones().observe(getViewLifecycleOwner(), new Observer<ArrayList<Prestacion>>() {
            @Override
            public void onChanged(final ArrayList<Prestacion> prestacions) {
                AdaptadorPrestacion adaptador = new AdaptadorPrestacion(prestacions, getContext());

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Prestacion prestacion = prestacions.get(rvPrestaciones.getChildAdapterPosition(v));
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("prestacion", prestacion);
                        Toast.makeText(getContext(), prestacion.getNombre(), Toast.LENGTH_SHORT).show();
                        //Navigation.findNavController(v).navigate(R.id.nav_listaDiasAPrestacionTurnos, bundle);
                    }
                });

                rvPrestaciones.setAdapter(adaptador);
            }
        });

        vm.getSinPrestaciones().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });

        Bundle objetoCategoria = getArguments();
        categoriaSeleccionada =(Categoria) objetoCategoria.getSerializable("categoria");
        vm.cargarPrestacionesDisponibles(categoriaSeleccionada.getId());

        return view;
    }
}
