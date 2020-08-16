package com.tuempresa.turnofast.ui.prestacion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Prestacion;
import com.tuempresa.turnofast.MainActivity;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacionesBMFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacionesBMFragment extends Fragment {

    private ImageView ivLogo;
    private EditText etDireccion, etNombre, etTelefono;
    private Button btActualizar, btEliminar, btLogo;
    private TextView tvCategoria;
    private CheckBox cbDisponible;
    private PrestacionViewModel vm;
    private Prestacion prestacionSeleccionada;
    private Prestacion prestacionEditada = new Prestacion();
    private Bitmap bitmapFoto = null;

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

        vm.getFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                bitmapFoto = bitmap;
                ivLogo.setImageBitmap(bitmap);
            }
        });

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
            public void onClick(final View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea eliminar la prestación?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vm.eliminarPrestacion(prestacionSeleccionada.getId());
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

        btLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent, 11);
            }
        });

        vm.recuperarPrestación(prestacionSeleccionada.getId());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.cargarImagen(requestCode,resultCode,data);
    }

    private void iniciarVista(View view) {
        etDireccion = view.findViewById(R.id.etDireccion);
        etNombre = view.findViewById(R.id.etNombre);
        etTelefono = view.findViewById(R.id.etTelefono);
        tvCategoria = view.findViewById(R.id.tvCategoria);
        cbDisponible = view.findViewById(R.id.cbDisponible);
        btActualizar = view.findViewById(R.id.btActualizar);
        btEliminar = view.findViewById(R.id.btEliminar);
        ivLogo = view.findViewById(R.id.ivLogo);
        btLogo = view.findViewById(R.id.btLogo);
    }

    private void cargarDatos(Prestacion p){
        int numero = (int) (Math.random() * 10) + 1;
        if (p.getLogo() != null){
            Glide.with(getContext())
                    .load(MainActivity.PATH + p.getLogo() + "?temp=" + numero)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivLogo);
        }

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
        btLogo.setEnabled(true);
    }

    private void aceptar(){
        prestacionEditada.setId(prestacionSeleccionada.getId());
        prestacionEditada.setDireccion(etDireccion.getText().toString());
        prestacionEditada.setNombre(etNombre.getText().toString());
        prestacionEditada.setTelefono(etTelefono.getText().toString());
        prestacionEditada.setDisponible(cbDisponible.isChecked());
        prestacionEditada.setCategoriaId(prestacionSeleccionada.getCategoriaId());
        prestacionEditada.setProfesionalId(prestacionSeleccionada.getProfesionalId());
        if (bitmapFoto != null){prestacionEditada.setLogo(encodeImage(bitmapFoto));}

        vm.actualizarPrestacion(prestacionEditada);
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

}
