package com.example.turnofast.ui.servicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Especialidad;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.modelos.Servicio;

import java.util.ArrayList;

import static com.example.turnofast.MainActivity.PATH;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicioFragment extends Fragment {
    private Spinner spEspecialidades;
    private EditText etDireccion, etNombre, etTelefono, etEmail;
    private CheckBox cbDisponible;
    private Button btTurnos;
    private ServicioViewModel vm;
    private Rubro rubro;
    private Servicio servicio = new Servicio();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServicioFragment newInstance(String param1, String param2) {
        ServicioFragment fragment = new ServicioFragment();
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
        View view = inflater.inflate(R.layout.fragment_servicio, container, false);

        etDireccion = view.findViewById(R.id.etDireccion);
        etEmail = view.findViewById(R.id.etEmail);
        etNombre = view.findViewById(R.id.etNombre);
        etTelefono = view.findViewById(R.id.etTelefono);
        spEspecialidades = view.findViewById(R.id.spEspecialidades);
        cbDisponible = view.findViewById(R.id.cbDisponible);
        btTurnos = view.findViewById(R.id.btTurnos);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ServicioViewModel.class);

        Bundle objetoRubro = getArguments();
        rubro =(Rubro) objetoRubro.getSerializable("objeto");
        ArrayAdapter<Especialidad> adapter = new ArrayAdapter<Especialidad>(getContext(), android.R.layout.simple_spinner_item, rubro.getEspecialidades());
        spEspecialidades.setAdapter(adapter);

        btTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar y continuar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aceptar();
                        Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(v).navigate(R.id.nav_servicioTurnos);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        return view;
    }

    private void aceptar() {
        Especialidad especialidad = (Especialidad) spEspecialidades.getSelectedItem();
        servicio.setDireccion(etDireccion.getText().toString());
        servicio.setEmail(etEmail.getText().toString());
        servicio.setNombre(etNombre.getText().toString());
        servicio.setDisponible(cbDisponible.isChecked());
        servicio.setTelefono(etTelefono.getText().toString());
        servicio.setEspecialidadId(especialidad.getId());

        vm.agregarServicio(servicio);
    }
}
