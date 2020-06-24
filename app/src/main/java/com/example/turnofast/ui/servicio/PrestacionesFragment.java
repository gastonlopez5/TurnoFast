package com.example.turnofast.ui.servicio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.turnofast.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacionesFragment extends Fragment {

    private Button btFechaInicioManiana, btFechaFinMAniana, btHoraInicioManiana, btHoraFinManiana,
    btFechaInicioTarde, btFechaFinTarde, btHoraInicioTarde, btHoraFinTarde, btGuardar;
    private EditText etFechaInicioManiana, etFechaFinManiana, etHoraInicioManiana, etHoraFinManiana,
            etFechaInicioTarde, etFechaFinTarde, etHoraInicioTarde, etHoraFinTarde;
    private CheckBox cbTurnoManiana, cbTurnoTarde;
    private PrestacionesViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrestacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicioTurnosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrestacionesFragment newInstance(String param1, String param2) {
        PrestacionesFragment fragment = new PrestacionesFragment();
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
        View view = inflater.inflate(R.layout.fragment_servicio_turnos, container, false);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PrestacionesViewModel.class);

        iniciarVista(view);

        vm.getTurnoManianaMLD().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    habilitaTurnoManiana(aBoolean);
                } else {
                    habilitaTurnoManiana(aBoolean);
                }
            }
        });

        vm.getTurnoTardeMLD().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    habilitaTurnoTarde(aBoolean);
                } else {
                    habilitaTurnoTarde(aBoolean);
                }
            }
        });

        vm.setTurnoManianaMLD(cbTurnoManiana.isChecked());
        vm.setTurnoTardeMLD(cbTurnoTarde.isChecked());

        return view;
    }

    private void habilitaTurnoTarde(Boolean valor) {
        btHoraInicioTarde.setEnabled(valor);
        btHoraFinTarde.setEnabled(valor);
        btFechaInicioTarde.setEnabled(valor);
        btFechaFinTarde.setEnabled(valor);

        etFechaInicioTarde.setEnabled(valor);
        etFechaFinTarde.setEnabled(valor);
        etHoraInicioTarde.setEnabled(valor);
        etHoraFinTarde.setEnabled(valor);
    }

    private void habilitaTurnoManiana(Boolean valor) {
        btHoraInicioManiana.setEnabled(valor);
        btHoraFinManiana.setEnabled(valor);
        btFechaInicioManiana.setEnabled(valor);
        btFechaFinMAniana.setEnabled(valor);

        etFechaInicioManiana.setEnabled(valor);
        etFechaFinManiana.setEnabled(valor);
        etHoraInicioManiana.setEnabled(valor);
        etHoraFinManiana.setEnabled(valor);
    }

    private void iniciarVista(View view) {
        btHoraInicioManiana = view.findViewById(R.id.btHoraInicioManiana);
        btHoraFinManiana = view.findViewById(R.id.btHoraFinManiana);
        btFechaInicioManiana = view.findViewById(R.id.btFechaInicioManiana);
        btFechaFinMAniana = view.findViewById(R.id.btFechaFinManiana);
        btHoraInicioTarde = view.findViewById(R.id.btHoraInicioTarde);
        btHoraFinTarde = view.findViewById(R.id.btHoraFinTarde);
        btFechaInicioTarde = view.findViewById(R.id.btFechaInicioTarde);
        btFechaFinTarde = view.findViewById(R.id.btFechaFinTarde);

        etFechaInicioManiana = view.findViewById(R.id.etFechaInicioManiana);
        etFechaInicioTarde = view.findViewById(R.id.etFechaInicioTarde);
        etFechaFinManiana = view.findViewById(R.id.etFechaFinManiana);
        etFechaFinTarde = view.findViewById(R.id.etFechaFinTade);
        etHoraInicioManiana = view.findViewById(R.id.etHoraInicioManiana);
        etHoraInicioTarde = view.findViewById(R.id.etHoraInicioTarde);
        etHoraFinManiana = view.findViewById(R.id.etHoraFinManiana);
        etHoraFinTarde = view.findViewById(R.id.etHoraFinTarde);

        btGuardar = view.findViewById(R.id.btGuardar);
        cbTurnoManiana = view.findViewById(R.id.cbTurnoManiana);
        cbTurnoTarde = view.findViewById(R.id.cbTurnoTarde);

        habilitaTurnoManiana(false);
        habilitaTurnoTarde(false);
    }


}
