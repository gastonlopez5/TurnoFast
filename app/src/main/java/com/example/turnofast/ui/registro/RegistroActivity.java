package com.example.turnofast.ui.registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.turnofast.R;
import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.ui.login.LoginActivity;

public class RegistroActivity extends AppCompatActivity {

    private RegistroViewModel vm;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etClave;
    private Button btFoto;
    private Button btRegistro;

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

        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Proximamente...", Toast.LENGTH_LONG).show();
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

                vm.registrarUsuario(u);

                Intent logeo = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(logeo);
            }
        });
    }
}
