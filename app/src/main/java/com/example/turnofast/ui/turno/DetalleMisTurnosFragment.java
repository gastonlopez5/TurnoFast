package com.example.turnofast.ui.turno;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Turno;

import static com.example.turnofast.MainActivity.PATH;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleMisTurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleMisTurnosFragment extends Fragment {

    private TextView tvDireccion, tvNombre, tvTelefono;
    private ImageView ivLogo;
    private Button btCancelar;
    private MisTurnosViewModel vm;
    private Turno turno;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleMisTurnosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleMisTurnosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleMisTurnosFragment newInstance(String param1, String param2) {
        DetalleMisTurnosFragment fragment = new DetalleMisTurnosFragment();
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
        View view = inflater.inflate(R.layout.fragment_detalle_mis_turnos, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MisTurnosViewModel.class);

        Bundle objetoTurno = getArguments();
        turno =(Turno) objetoTurno.getSerializable("turno");

        configurarVista(view, turno);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(getContext()).setCancelable(false).setMessage("¿Desea cancelar el turno?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vm.cancelarTurno(turno.getId());
                        Navigation.findNavController(v).navigate(R.id.nav_home);
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

    private void configurarVista(View view, Turno turno) {
        ivLogo = view.findViewById(R.id.ivLogo);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvTelefono = view.findViewById(R.id.tvTelefono);
        btCancelar = view.findViewById(R.id.btCancelar);

        Glide.with(getContext())
                .load(PATH + turno.getHorario2().getPrestacion().getLogo())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivLogo);

        tvDireccion.setText(turno.getHorario2().getPrestacion().getDireccion());
        tvNombre.setText(turno.getHorario2().getPrestacion().getNombre());
        tvTelefono.setText(turno.getHorario2().getPrestacion().getTelefono());
    }
}
