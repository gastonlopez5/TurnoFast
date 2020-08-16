package com.tuempresa.turnofast.ui.prestacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Horario2;
import com.tuempresa.turnofast.modelos.Prestacion;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaDiasAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaDiasAFragment extends Fragment {

    ImageButton ibLunes, ibMartes, ibMiercoles, ibJueves, ibViernes, ibSabado, ibDomingo;
    Button btFinalizar;
    Prestacion prestacionSeleccionada = null;
    Horario2 horario2 = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaDiasAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaDiasAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaDiasAFragment newInstance(String param1, String param2) {
        ListaDiasAFragment fragment = new ListaDiasAFragment();
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
        View view = inflater.inflate(R.layout.fragment_lista_dias_a, container, false);

        iniciarVista(view);

        ibLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(1, v);
            }
        });

        ibMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(2, v);
            }
        });

        ibMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(3, v);
            }
        });

        ibJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(4, v);
            }
        });

        ibViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(5, v);
            }
        });

        ibSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(6, v);
            }
        });

        ibDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarHorario(7, v);
            }
        });

        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        return view;
    }

    private void iniciarVista(View view) {
        ibLunes = view.findViewById(R.id.ibLunes);
        ibMartes = view.findViewById(R.id.ibMartes);
        ibMiercoles = view.findViewById(R.id.ibMiercoles);
        ibJueves = view.findViewById(R.id.ibJueves);
        ibViernes = view.findViewById(R.id.ibViernes);
        ibSabado = view.findViewById(R.id.ibSabado);
        ibDomingo = view.findViewById(R.id.ibDomingo);
        btFinalizar = view.findViewById(R.id.btFinalizar);

        Bundle objetoRubro = getArguments();
        prestacionSeleccionada = (Prestacion) objetoRubro.getSerializable("objeto");
    }

    private void enviarHorario(int nroDia, View v){
        horario2 = new Horario2();
        horario2.setDiaSemana(nroDia);
        horario2.setPrestacionId(prestacionSeleccionada.getId());
        Bundle bundle=new Bundle();
        bundle.putSerializable("horario", horario2);
        Navigation.findNavController(v).navigate(R.id.nav_servicioTurnos, bundle);
    }
}
