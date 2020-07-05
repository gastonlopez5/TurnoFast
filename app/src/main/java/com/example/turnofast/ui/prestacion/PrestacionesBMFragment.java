package com.example.turnofast.ui.prestacion;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Prestacion;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacionesBMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacionesBMFragment extends Fragment {

    EditText etDireccion, etNombre, etTelefono;
    Button btActualizar, btEliminar;
    TextView tvCategoria;
    CheckBox cbDisponible;
    PrestacionViewModel vm;
    Prestacion prestacionSeleccionada;
    Prestacion prestacionEditada = new Prestacion();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrestacionesBMFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrestacionesTurnosBMFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrestacionesBMFragment newInstance(String param1, String param2) {
        PrestacionesBMFragment fragment = new PrestacionesBMFragment();
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
        View view = inflater.inflate(R.layout.fragment_prestaciones__bm, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PrestacionViewModel.class);

        Bundle objetoPrestacion = getArguments();
        prestacionSeleccionada =(Prestacion) objetoPrestacion.getSerializable("prestacion");

        iniciarVista(view);

        vm.getPrestacionMLD().observe(getViewLifecycleOwner(), new Observer<Prestacion>() {
            @Override
            public void onChanged(Prestacion prestacion) {
                cargarDatos(prestacion);
            }
        });

        btActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (btActualizar.getText() == "Guardar"){
                    new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar los datos?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aceptar();
                            Navigation.findNavController(v).navigate(R.id.nav_home);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                habilitarModificacion(v);
                btActualizar.setText("Guardar");
            }
        });

        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.eliminarPrestacion(prestacionSeleccionada.getId());
            }
        });

        vm.recuperarPrestación(prestacionSeleccionada.getId());

        return view;
    }

    private void iniciarVista(View view) {
        etDireccion = view.findViewById(R.id.etDireccion);
        etNombre = view.findViewById(R.id.etNombre);
        etTelefono = view.findViewById(R.id.etTelefono);
        tvCategoria = view.findViewById(R.id.tvCategoria);
        cbDisponible = view.findViewById(R.id.cbDisponible);
        btActualizar = view.findViewById(R.id.btActualizar);
        btEliminar = view.findViewById(R.id.btEliminar);
    }

    private void cargarDatos(Prestacion p){
        etDireccion.setText(p.getDireccion());
        etTelefono.setText(p.getTelefono());
        etNombre.setText(p.getNombre());
        tvCategoria.setText(p.getCategoria().toString());
        cbDisponible.setChecked(p.getDisponible());
    }

    private void habilitarModificacion(View v){
        etDireccion.setEnabled(true);
        etNombre.setEnabled(true);
        etTelefono.setEnabled(true);
        cbDisponible.setEnabled(true);
        btEliminar.setEnabled(false);
    }

    private void aceptar(){
        prestacionEditada.setId(prestacionSeleccionada.getId());
        prestacionEditada.setDireccion(etDireccion.getText().toString());
        prestacionEditada.setNombre(etNombre.getText().toString());
        prestacionEditada.setTelefono(etTelefono.getText().toString());
        prestacionEditada.setDisponible(cbDisponible.isChecked());
        prestacionEditada.setCategoriaId(prestacionSeleccionada.getCategoriaId());
        prestacionEditada.setProfesionalId(prestacionSeleccionada.getProfesionalId());
        vm.actualizarPrestacion(prestacionEditada);
    }
}
