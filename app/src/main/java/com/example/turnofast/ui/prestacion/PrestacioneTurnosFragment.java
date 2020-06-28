package com.example.turnofast.ui.prestacion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Horario;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacioneTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacioneTurnosFragment extends Fragment implements View.OnClickListener {

    private Button btHoraInicioManiana, btHoraFinManiana, btHoraInicioTarde, btHoraFinTarde, btGuardar;
    private EditText etHoraInicioManiana, etHoraFinManiana, etHoraInicioTarde, etHoraFinTarde;
    private CheckBox cbTurnoManiana, cbTurnoTarde;
    private PrestacionTurnosViewModel vm;
    private Calendar calendario;
    private int hora, minutos;
    //private Locale locale = new Locale("es", "AR");
    private SimpleDateFormat diaFormato = new SimpleDateFormat("EEE");
    private SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
    private Horario horario = new Horario();
    private Prestacion prestacionSeleccionada = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrestacioneTurnosFragment() {
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
    public static PrestacioneTurnosFragment newInstance(String param1, String param2) {
        PrestacioneTurnosFragment fragment = new PrestacioneTurnosFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicio_turnos, container, false);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PrestacionTurnosViewModel.class);

        iniciarVista(view);

        Bundle objetoRubro = getArguments();
        prestacionSeleccionada =(Prestacion) objetoRubro.getSerializable("objeto");

        cbTurnoManiana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                habilitaTurnoManiana(isChecked);
            }
        });

        cbTurnoTarde.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                habilitaTurnoTarde(isChecked);
            }
        });


        btHoraInicioManiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHoraInicioManiana.setText(hourOfDay+":"+minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        horario.setHoraDesdeManiana(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        btHoraInicioTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHoraInicioTarde.setText(hourOfDay+":"+minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        horario.setHoraDesdeTarde(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                        ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        btHoraFinManiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHoraFinManiana.setText(hourOfDay+":"+minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        horario.setHoraHastaManiana(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                        ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        btHoraFinTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etHoraFinTarde.setText(hourOfDay+":"+minute);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        horario.setHoraHastaTarde(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                        ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        vm.getErrorCarga().observe(getViewLifecycleOwner(), new Observer<Horario>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Horario p) {
                if (p.getHoraDesdeManiana() == null){etHoraInicioManiana.setText("");}
                else { etHoraInicioManiana.setText(p.getHoraDesdeManiana().getHour()+":"+p.getHoraDesdeManiana().getMinute());}
                if (p.getHoraHastaManiana() == null){etHoraFinManiana.setText("");}
                else {etHoraFinManiana.setText(p.getHoraHastaManiana().getHour()+":"+p.getHoraHastaManiana().getMinute());}
                if (p.getHoraDesdeTarde() == null){etHoraInicioTarde.setText("");}
                else {etHoraInicioTarde.setText(p.getHoraDesdeTarde().getHour()+":"+p.getHoraDesdeTarde().getMinute());}
                if (p.getHoraHastaTarde() == null){etHoraFinTarde.setText("");}
                else {etHoraFinTarde.setText(p.getHoraHastaTarde().getHour()+":"+p.getHoraHastaTarde().getMinute());}

            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar y continuar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //prestacionGuardado = prestacion;
                        horario.setPrestacionId(prestacionSeleccionada.getId());
                        vm.cargarHorario(horario, cbTurnoManiana.isChecked(), cbTurnoTarde.isChecked());
                        //Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                        //Navigation.findNavController(v).navigate(R.id.nav_home);
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

    private void habilitaTurnoTarde(Boolean valor) {
        btHoraInicioTarde.setEnabled(valor);
        btHoraFinTarde.setEnabled(valor);

        etHoraInicioTarde.setEnabled(valor);
        etHoraFinTarde.setEnabled(valor);
    }

    private void habilitaTurnoManiana(Boolean valor) {
        btHoraInicioManiana.setEnabled(valor);
        btHoraFinManiana.setEnabled(valor);

        etHoraInicioManiana.setEnabled(valor);
        etHoraFinManiana.setEnabled(valor);
    }

    private void iniciarVista(View view) {
        btHoraInicioManiana = view.findViewById(R.id.btHoraInicioManiana);
        btHoraFinManiana = view.findViewById(R.id.btHoraFinManiana);
        btHoraInicioTarde = view.findViewById(R.id.btHoraInicioTarde);
        btHoraFinTarde = view.findViewById(R.id.btHoraFinTarde);

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


    @Override
    public void onClick(View v) {

    }
}
