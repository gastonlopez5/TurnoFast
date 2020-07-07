package com.example.turnofast.ui.turno;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Categoria;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.ui.rubro.AdaptadorRubro;
import com.example.turnofast.ui.rubro.RubroViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaRubrosTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaRubrosTurnosFragment extends Fragment {

    private RecyclerView rvRubros;
    private RubroViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaRubrosTurnosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaRubrosTurnosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaRubrosTurnosFragment newInstance(String param1, String param2) {
        ListaRubrosTurnosFragment fragment = new ListaRubrosTurnosFragment();
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
        View view = inflater.inflate(R.layout.fragment_lista_rubros_turnos, container, false);
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
                        Categoria categoria = null;

                        ArrayList<Categoria> opciones = rubro.getEspecialidades();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        View mView = getLayoutInflater().inflate(R.layout.categorias_spiner, null);
                        builder.setTitle("Elija una categoria:").setIcon(R.drawable.ico);

                        final Spinner spCategorias = (Spinner) mView.findViewById(R.id.spCategorias);
                        ArrayAdapter<Categoria> adaptador2 = new ArrayAdapter<Categoria>(getContext(),
                                android.R.layout.simple_spinner_item, opciones);
                        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spCategorias.setAdapter(adaptador2);

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), spCategorias.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setView(mView);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        Bundle bundle=new Bundle();
                        bundle.putSerializable("objeto", rubro);
                        //Navigation.findNavController(v).navigate(R.id.nav_servicio, bundle);
                    }
                });

                rvRubros.setAdapter(adaptador);
            }
        });

        vm.cargarDatos();

        return view;
    }
}
