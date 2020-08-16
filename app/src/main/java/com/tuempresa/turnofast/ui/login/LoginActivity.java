package com.tuempresa.turnofast.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tuempresa.turnofast.MainActivity;
import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.ui.registro.RegistroActivity;

public class LoginActivity extends AppCompatActivity {
    private Button logeo;
    private Button registro;
    private EditText email;
    private EditText password;
    private TextView tvError;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        logeo = (Button)findViewById(R.id.button);
        registro = findViewById(R.id.btRegistro);
        email = findViewById(R.id.editText1);
        password = findViewById(R.id.editText2);
        tvError = findViewById(R.id.tvError);

        vm.getMsg1().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent logeo = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(logeo);
            }
        });

        vm.getMsg2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(registro);
            }
        });

        logeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.validar(email.getText().toString(), password.getText().toString());
            }
        });
    }



}
