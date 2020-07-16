package com.example.turnofast.ui.perfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;

import static com.example.turnofast.MainActivity.PATH;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private Usuario usuarioVisto = null;
    private Usuario usuario = new Usuario();
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etClave;
    private Button btEditar;
    private Button btFoto;
    private ImageView ivFoto;
    private Bitmap bitmapFoto = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PerfilViewModel.class);

        etNombre = view.findViewById(R.id.etNombre);
        etApellido = view.findViewById(R.id.etApellido);
        etClave = view.findViewById(R.id.etClave);
        etEmail = view.findViewById(R.id.etEmail);
        etTelefono = view.findViewById(R.id.etTelefono);
        btEditar = view.findViewById(R.id.btEditar);
        btFoto = view.findViewById(R.id.btFoto);
        ivFoto = view.findViewById(R.id.ivFoto);

        vm.getFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                bitmapFoto = bitmap;
                ivFoto.setImageBitmap(bitmap);
            }
        });

        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent, 10);
            }
        });

        vm.getUsuarioMLD().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario p) {
                usuarioVisto = p;
                fijarDatos(p);
            }
        });

        vm.getMsgLD().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                //usuarioVisto = new Usuario();
            }
        });

        vm.cargarUsuario();

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                etNombre.setEnabled(true);
                etApellido.setEnabled(true);
                etClave.setEnabled(true);
                etEmail.setEnabled(true);
                etTelefono.setEnabled(true);
                btFoto.setEnabled(true);

                if (btEditar.getText() == "Guardar"){

                    new AlertDialog.Builder(getContext()).setTitle("").setMessage("Desea guardar los datos?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aceptar();
                            Toast.makeText(getContext(), "Datos guardados correctamente!. Ingrese nuevamente.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getContext(), LoginActivity.class);
                            getContext().startActivity(i);
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fijarDatos(usuarioVisto);
                        }
                    }).show();



                }
                btEditar.setText("Guardar");

            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.cargarImagen(requestCode,resultCode,data);
    }

    private void fijarDatos(Usuario p) {

        if(p.getFotoPerfil() != null){
            int numero = (int) (Math.random() * 10) + 1;
            Glide.with(getContext())
                    .load(PATH + p.getFotoPerfil() + "?temp=" + numero)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivFoto);
        }

        etApellido.setText(p.getApellido());
        etNombre.setText(p.getNombre());
        etEmail.setText(p.getEmail());
        etTelefono.setText(p.getTelefono());

        etNombre.setEnabled(false);
        etApellido.setEnabled(false);
        etClave.setEnabled(false);
        etEmail.setEnabled(false);
        etTelefono.setEnabled(false);
        btFoto.setEnabled(false);

        btEditar.setText("Actualizar");
    }

    private void aceptar() {
        usuario.setApellido(etApellido.getText().toString());
        usuario.setNombre(etNombre.getText().toString());
        usuario.setTelefono(etTelefono.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setClave(etClave.getText().toString());
        if (bitmapFoto != null){usuario.setFotoPerfil(encodeImage(bitmapFoto));}
        usuario.setEstado(true);

        vm.actualizarUsuario(usuario);
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
