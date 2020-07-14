package com.example.turnofast.ui.registro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;

public class RegistroActivity extends AppCompatActivity {

    private RegistroViewModel vm;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etClave;
    private Button btFoto;
    private Button btRegistro;
    private ImageView ivFoto;
    private Bitmap bitmapFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(RegistroViewModel.class);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etClave = findViewById(R.id.etClave);
        etEmail = findViewById(R.id.etEmail);
        etTelefono = findViewById(R.id.etTelefono);
        btFoto = findViewById(R.id.btFoto);
        btRegistro = findViewById(R.id.btRegistro);
        ivFoto = findViewById(R.id.ivFoto);

        vm.getFoto().observe(this, new Observer<Bitmap>() {
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

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();
                u.setNombre(etNombre.getText().toString());
                u.setApellido(etApellido.getText().toString());
                u.setClave(etClave.getText().toString());
                u.setEmail(etEmail.getText().toString());
                u.setTelefono(etTelefono.getText().toString());
                u.setFotoPerfil(encodeImage(bitmapFoto));

                vm.registrarUsuario(u);

                Intent logeo = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(logeo);
            }
        });
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
