package com.example.turnofast.ui.recordatorio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Turno;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActualizarRecordatorioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActualizarRecordatorioFragment extends Fragment {

    private TextView tvNombre, tvFechaTurno, tvHoraTurno;
    private EditText etFecha, etHora;
    private Button btFecha, btHora, btActualizar;
    private DbTable dbTable;
    private Calendar calendario;
    private int hora, minutos, dia, mes, anio;
    private RecordatorioViewModel vm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActualizarRecordatorioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActualizarRecordatorioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActualizarRecordatorioFragment newInstance(String param1, String param2) {
        ActualizarRecordatorioFragment fragment = new ActualizarRecordatorioFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actualizar_recordatorio, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RecordatorioViewModel.class);

        Bundle objetoRecordatorio = getArguments();
        dbTable =(DbTable) objetoRecordatorio.getSerializable("recordatorio");

        iniciarVista(view, dbTable);

        btHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHora.setText(hourOfDay+":"+minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                    }
                }
                        ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, dia, mes, anio);
                datePickerDialog.show();
            }
        });

        vm.getError().observe(getViewLifecycleOwner(), new Observer<DbTable>() {
            @Override
            public void onChanged(DbTable dbTable) {
                tvFechaTurno.setText(dbTable.getFechaTurno());
                tvHoraTurno.setText(dbTable.getHoraTurno());
                etFecha.setText(dbTable.getFecha());
                etHora.setText(dbTable.getHora());

                Toast.makeText(getContext(), "Debe seleccionar fecha y hora", Toast.LENGTH_LONG).show();
            }
        });

        btActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbTable dbTableUpdate = new DbTable();
                dbTableUpdate.setId(dbTable.getId());
                dbTableUpdate.setEncabezado("Turno: "+ tvNombre.getText());
                dbTableUpdate.setFechaTurno(tvFechaTurno.getText().toString());
                dbTableUpdate.setHoraTurno(tvHoraTurno.getText().toString());
                dbTableUpdate.setFecha(etFecha.getText().toString());
                dbTableUpdate.setHora(etHora.getText().toString());

                vm.actualizarRecordatorio(dbTableUpdate);

                Toast.makeText(getContext(), "Recordatorio actualizado!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void iniciarVista(View view, DbTable dbTable) {
        tvFechaTurno = view.findViewById(R.id.tvFecha);
        tvHoraTurno = view.findViewById(R.id.tvHora);
        tvNombre = view.findViewById(R.id.tvNombre);
        etFecha = view.findViewById(R.id.etFecha);
        etHora = view.findViewById(R.id.etHora);
        btFecha = view.findViewById(R.id.btFecha);
        btHora = view.findViewById(R.id.btHora);
        btActualizar = view.findViewById(R.id.btActualizar);

        String[] a = dbTable.getEncabezado().split(":");
        tvFechaTurno.setText(dbTable.getFechaTurno());
        tvHoraTurno.setText(dbTable.getHoraTurno());
        tvNombre.setText(a[1]);
        etFecha.setText(dbTable.getFecha());
        etHora.setText(dbTable.getHora());
    }

}
