package com.example.turnofast.ui.prestacion;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
 * Use the {@link PrestacioneTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacioneTurnosFragment extends Fragment implements View.OnClickListener {

    private Button btHoraInicioManiana, btHoraFinManiana, btHoraInicioTarde, btHoraFinTarde, btGuardar,
            btEliminar, btActualizar;
    private EditText etHoraInicioManiana, etHoraFinManiana, etHoraInicioTarde, etHoraFinTarde;
    private TextView tvDiasSeleccionados;
    private CheckBox cbTurnoManiana, cbTurnoTarde;
    private Spinner spFrecuencia;
    private PrestacionTurnosViewModel vm;
    private Calendar calendario;
    private int hora, minutos;
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
    private Horario2 horario2 = new Horario2();
    private Horario2 horarioSeleccionado = null;
    private Integer [] opciones = null;
    private boolean bandera;

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

        Bundle objetoHorario = getArguments();
        horario2 =(Horario2) objetoHorario.getSerializable("horario");

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
                        if (cbTurnoManiana.isChecked()){
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                        } else {
                            c.set(Calendar.HOUR_OF_DAY, 0);
                            c.set(Calendar.MINUTE, 0);
                        }

                        horario2.setHoraDesdeManiana(LocalTime.parse(horaFormato.format(c.getTime())));
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
                        if (cbTurnoTarde.isChecked()){
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                        } else {
                            c.set(Calendar.HOUR_OF_DAY, 0);
                            c.set(Calendar.MINUTE, 0);
                        }

                        horario2.setHoraDesdeTarde(LocalTime.parse(horaFormato.format(c.getTime())));
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
                        if (cbTurnoManiana.isChecked()){
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                        } else {
                            c.set(Calendar.HOUR_OF_DAY, 0);
                            c.set(Calendar.MINUTE, 0);
                        }

                        horario2.setHoraHastaManiana(LocalTime.parse(horaFormato.format(c.getTime())));
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
                        if (cbTurnoTarde.isChecked()){
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                        } else {
                            c.set(Calendar.HOUR_OF_DAY, 0);
                            c.set(Calendar.MINUTE, 0);
                        }

                        horario2.setHoraHastaTarde(LocalTime.parse(horaFormato.format(c.getTime())));
                    }
                }
                        ,hora, minutos, false);
                timePickerDialog.show();
            }
        });

        vm.getBotones().observe(getViewLifecycleOwner(), new Observer<Horario2>() {
            @Override
            public void onChanged(Horario2 horario2) {
                btEliminar.setEnabled(true);
                btActualizar.setEnabled(true);
                btGuardar.setEnabled(false);
            }
        });

        vm.getErrorCarga().observe(getViewLifecycleOwner(), new Observer<Horario2>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Horario2 p) {
                horarioSeleccionado = new Horario2();
                horarioSeleccionado = p;

                if (p.getHoraDesdeManiana() == null || p.getHoraDesdeManiana().getHour() == 0){etHoraInicioManiana.setText("");}
                else {
                    cbTurnoManiana.setChecked(true);
                    etHoraInicioManiana.setText(p.getHoraDesdeManiana().getHour()+":"+p.getHoraDesdeManiana().getMinute());
                }
                if (p.getHoraHastaManiana() == null || p.getHoraHastaManiana().getHour() == 0){etHoraFinManiana.setText("");}
                else {etHoraFinManiana.setText(p.getHoraHastaManiana().getHour()+":"+p.getHoraHastaManiana().getMinute());}
                if (p.getHoraDesdeTarde() == null || p.getHoraDesdeTarde().getHour() == 0){etHoraInicioTarde.setText("");}
                else {
                    cbTurnoTarde.setChecked(true);
                    etHoraInicioTarde.setText(p.getHoraDesdeTarde().getHour()+":"+p.getHoraDesdeTarde().getMinute());
                }
                if (p.getHoraHastaTarde() == null || p.getHoraHastaTarde().getHour() == 0){etHoraFinTarde.setText("");}
                else {etHoraFinTarde.setText(p.getHoraHastaTarde().getHour()+":"+p.getHoraHastaTarde().getMinute());}

                for (int i=0; i<opciones.length; i++){
                    if (opciones[i] == p.getFrecuencia()){
                        spFrecuencia.setSelection(i);
                    }
                }

                horarioSeleccionado.setId(p.getId());

                bandera = false;
            }
        });

        btActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                bandera = true;
                actualizar();
                if (bandera){
                    Toast.makeText(getContext(), "Horario actualizado correctamente!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(v).navigate(R.id.nav_home);
                }
            }
        });

        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.borrarHorario(horario2.getPrestacionId(), horario2.getDiaSemana());
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                bandera = true;
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar y continuar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aceptar();
                        if (bandera){Navigation.findNavController(v).navigate(R.id.nav_home);}
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        vm.recuperarHorarios(horario2.getPrestacionId(), horario2.getDiaSemana());

        /*
        btDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //Arreglo de Strings para los días que se muestran en el dialogo
                final String[] dias = new String[]{"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo"};

                //Arreglo de booleanos para los días que se muestran seleccionados
                final boolean[] diasChequeados = new boolean[]{true, true, true, true, true, false, false};

                //Conversion del arreglo de días a lista
                final List<String> diasLista = Arrays.asList(dias);

                //Título del AlertDialog
                builder.setTitle("Deleccionar días:");

                //Icono
                builder.setIcon(R.drawable.ico);

                //Configuro los elementos a seleccionar
                builder.setMultiChoiceItems(dias, diasChequeados, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //Actualizo los elementos que ya estan seleccionados
                        diasChequeados[which] = isChecked;

                        //Obtengo el elemento de foco
                        String itemActual = diasLista.get(which);

                        //Notifico el elemento actual
                        Toast.makeText(getContext(), itemActual+" "+isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                //Configuro el boton OK
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDiasSeleccionados.setText("Usted seleccionó los días: ");
                        for (int i=0; i<diasChequeados.length; i++){
                            boolean seleccionado = diasChequeados[i];
                            if (seleccionado){
                                tvDiasSeleccionados.setText(tvDiasSeleccionados.getText()+", "+diasLista.get(i));
                                diasSeleccionados.add(diasLista.get(i));
                            }
                        }
                    }
                });

                //Configuro el boton cancelar
                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

         */

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void aceptar() {
        horario2.setFrecuencia((Integer) spFrecuencia.getSelectedItem());
        vm.cargarHorario(horario2, cbTurnoManiana.isChecked(), cbTurnoTarde.isChecked());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actualizar(){
        if (horario2.getHoraDesdeManiana() != null){horarioSeleccionado.setHoraDesdeManiana(horario2.getHoraDesdeManiana());}
        if (!cbTurnoManiana.isChecked()){horarioSeleccionado.setHoraDesdeManiana(null);}
        if (horario2.getHoraHastaManiana() != null){horarioSeleccionado.setHoraHastaManiana(horario2.getHoraHastaManiana());}
        if (!cbTurnoManiana.isChecked()){horarioSeleccionado.setHoraHastaManiana(null);}
        if (horario2.getHoraDesdeTarde() != null){horarioSeleccionado.setHoraDesdeTarde(horario2.getHoraDesdeTarde());}
        if (!cbTurnoTarde.isChecked()){horarioSeleccionado.setHoraDesdeTarde(null);}
        if (horario2.getHoraHastaTarde() != null){horarioSeleccionado.setHoraHastaTarde(horario2.getHoraHastaTarde());}
        if (!cbTurnoTarde.isChecked()){horarioSeleccionado.setHoraHastaTarde(null);}
        horarioSeleccionado.setFrecuencia((Integer) spFrecuencia.getSelectedItem());
        vm.actualizar(horarioSeleccionado, cbTurnoManiana.isChecked(), cbTurnoTarde.isChecked());
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
        btEliminar = view.findViewById(R.id.btEliminar);
        btActualizar = view.findViewById(R.id.btActualizar);
        cbTurnoManiana = view.findViewById(R.id.cbTurnoManiana);
        cbTurnoTarde = view.findViewById(R.id.cbTurnoTarde);

        spFrecuencia = view.findViewById(R.id.spFrecuencia);
        opciones = new Integer[]{15, 20, 30, 45, 60};
        ArrayAdapter<Integer> adaptador = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, opciones);
        spFrecuencia.setAdapter(adaptador);

        habilitaTurnoManiana(false);
        habilitaTurnoTarde(false);
    }

    @Override
    public void onClick(View v) {

    }
}
