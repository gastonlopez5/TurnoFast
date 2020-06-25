package com.example.turnofast.ui.servicio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.example.turnofast.modelos.Prestacion;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacioneTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacioneTurnosFragment extends Fragment implements View.OnClickListener {

    private Button btFechaInicioManiana, btFechaFinMAniana, btHoraInicioManiana, btHoraFinManiana,
    btFechaInicioTarde, btFechaFinTarde, btHoraInicioTarde, btHoraFinTarde, btGuardar;
    private EditText etFechaInicioManiana, etFechaFinManiana, etHoraInicioManiana, etHoraFinManiana,
            etFechaInicioTarde, etFechaFinTarde, etHoraInicioTarde, etHoraFinTarde;
    private CheckBox cbTurnoManiana, cbTurnoTarde;
    private PrestacionTurnosViewModel vm;
    private Calendar calendario;
    private int dia, mes, anio, hora, minutos;
    //private Locale locale = new Locale("es", "AR");
    private SimpleDateFormat diaFormato = new SimpleDateFormat("EEE");
    private SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
    private Prestacion prestacion = new Prestacion();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicio_turnos, container, false);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PrestacionTurnosViewModel.class);

        iniciarVista(view);

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

        btFechaInicioManiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFechaInicioManiana.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.YEAR, year);

                        prestacion.setDiaInicioManiana(diaFormato.format(c.getTime()));
                        prestacion.setFechaInicioManiana(LocalDate.parse(fechaFormato.format(c.getTime())));
                    }
                }
                , dia, mes, anio);
                datePickerDialog.show();
            }
        });

        btFechaInicioTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFechaInicioTarde.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.YEAR, year);

                        prestacion.setGetDiaInicioTarde(diaFormato.format(c.getTime()));
                        prestacion.setFechaInicioTarde(LocalDate.parse(fechaFormato.format(c.getTime())));
                    }
                }
                        , dia, mes, anio);
                datePickerDialog.show();
            }
        });

        btFechaFinMAniana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFechaFinManiana.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.YEAR, year);

                        prestacion.setFechaFinManiana(LocalDate.parse(fechaFormato.format(c.getTime())));
                    }
                }
                        , dia, mes, anio);
                datePickerDialog.show();
            }
        });

        btFechaFinTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFechaFinTarde.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.YEAR, year);

                        prestacion.setFechaFinTarde(LocalDate.parse(fechaFormato.format(c.getTime())));
                    }
                }
                        , dia, mes, anio);
                datePickerDialog.show();
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

                        prestacion.setHoraInicioManiana(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        btHoraInicioTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btHoraFinManiana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btHoraFinTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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


    @Override
    public void onClick(View v) {

    }
}
