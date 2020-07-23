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
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Turno;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordatorioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordatorioFragment extends Fragment {

    private TextView tvNombre, tvFechaTurno, tvHoraTurno;
    private EditText etFecha, etHora;
    private Button btFecha, btHora, btGuardar;
    private Turno turno;
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
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

    public RecordatorioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordatorioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordatorioFragment newInstance(String param1, String param2) {
        RecordatorioFragment fragment = new RecordatorioFragment();
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
        View view = inflater.inflate(R.layout.fragment_recordatorio, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RecordatorioViewModel.class);

        Bundle objetoTurno = getArguments();
        turno =(Turno) objetoTurno.getSerializable("turno");

        iniciarVista(view, turno);

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

                        //horario2.setHoraDesdeManiana(LocalTime.parse(horaFormato.format(c.getTime())));
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

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbTable dbTable = new DbTable();
                dbTable.setEncabezado("Turno: "+ tvNombre.getText());
                dbTable.setFechaTurno(tvFechaTurno.getText().toString());
                dbTable.setHoraTurno(tvHoraTurno.getText().toString());
                dbTable.setFecha(etFecha.getText().toString());
                dbTable.setHora(etHora.getText().toString());

                vm.insertNewItem(dbTable);

                Toast.makeText(getContext(), "Recordatorio agendado", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void iniciarVista(View view, Turno turno) {
        tvFechaTurno = view.findViewById(R.id.tvFecha);
        tvHoraTurno = view.findViewById(R.id.tvHora);
        tvNombre = view.findViewById(R.id.tvNombre);
        etFecha = view.findViewById(R.id.etFecha);
        etHora = view.findViewById(R.id.etHora);
        btFecha = view.findViewById(R.id.btFecha);
        btHora = view.findViewById(R.id.btHora);
        btGuardar = view.findViewById(R.id.btGuardar);

        String[] a = turno.getFecha().split("-");
        tvFechaTurno.setText(a[2]+"/"+a[1]+"/"+a[0]);
        tvHoraTurno.setText(turno.getHora().getHour()+":"+turno.getHora().getMinute());
        tvNombre.setText(turno.getHorario2().getPrestacion().getNombre());
    }
}
