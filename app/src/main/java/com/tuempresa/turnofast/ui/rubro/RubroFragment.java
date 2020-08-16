package com.tuempresa.turnofast.ui.rubro;

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

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Rubro;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RubroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RubroFragment extends Fragment {
    private RecyclerView rvRubros;
    private RubroViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RubroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RubroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RubroFragment newInstance(String param1, String param2) {
        RubroFragment fragment = new RubroFragment();
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
        View view = inflater.inflate(R.layout.fragment_rubro, container, false);
        rvRubros = view.findViewById(R.id.rvRubro);
        rvRubros.setLayoutManager(new LinearLayoutManager(getContext()));

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RubroViewModel.class);

        vm.getListaRubros().observe(getViewLifecycleOwner(), new Observer<ArrayList<Rubro>>() {
            @Override
            public void onChanged(final ArrayList<Rubro> rubros) {
                AdaptadorRubro adaptador = new AdaptadorRubro(rubros, getContext());

                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Rubro rubro = rubros.get(rvRubros.getChildAdapterPosition(v));
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("objeto", rubro);
                        Navigation.findNavController(v).navigate(R.id.nav_servicio, bundle);
                    }
                });

                rvRubros.setAdapter(adaptador);
            }
        });

        vm.cargarDatos();

        return view;
    }
}
