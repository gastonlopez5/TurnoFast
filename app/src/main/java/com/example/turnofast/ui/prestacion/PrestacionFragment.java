package com.example.turnofast.ui.prestacion;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Categoria;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrestacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrestacionFragment extends Fragment {

    private ImageView ivLogo;
    private Spinner spCategorias;
    private EditText etDireccion, etNombre, etTelefono, etEmail;
    private CheckBox cbDisponible;
    private Button btGuardar, btLogo;
    private PrestacionViewModel vm;
    private Rubro rubro;
    private Prestacion prestacion = new Prestacion();
    private Bitmap bitmapFoto = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrestacionFragment() {
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
    public static PrestacionFragment newInstance(String param1, String param2) {
        PrestacionFragment fragment = new PrestacionFragment();
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
        View view = inflater.inflate(R.layout.fragment_prestacion, container, false);

        etDireccion = view.findViewById(R.id.etDireccion);
        etEmail = view.findViewById(R.id.etEmail);
        etNombre = view.findViewById(R.id.etNombre);
        etTelefono = view.findViewById(R.id.etTelefono);
        spCategorias = view.findViewById(R.id.spCategorias);
        cbDisponible = view.findViewById(R.id.cbDisponible);
        btGuardar = view.findViewById(R.id.btGuardar);
        btLogo = view.findViewById(R.id.btLogo);
        ivLogo = view.findViewById(R.id.ivLogo);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PrestacionViewModel.class);

        Bundle objetoRubro = getArguments();
        rubro =(Rubro) objetoRubro.getSerializable("objeto");
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getContext(), android.R.layout.simple_spinner_item, rubro.getEspecialidades());
        spCategorias.setAdapter(adapter);

        vm.getFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                bitmapFoto = bitmap;
                ivLogo.setImageBitmap(bitmap);
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

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar y continuar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aceptar();
                        Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_LONG).show();
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

    private void aceptar() {
        Categoria categoria = (Categoria) spCategorias.getSelectedItem();

        prestacion.setDireccion(etDireccion.getText().toString());
        prestacion.setNombre(etNombre.getText().toString());
        prestacion.setDisponible(cbDisponible.isChecked());
        prestacion.setTelefono(etTelefono.getText().toString());
        prestacion.setCategoriaId(categoria.getId());
        if(bitmapFoto != null){prestacion.setLogo(encodeImage(bitmapFoto));}

        vm.agregarPrestacion(prestacion);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.cargarImagen(requestCode,resultCode,data);
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
