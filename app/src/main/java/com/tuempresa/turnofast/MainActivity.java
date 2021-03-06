package com.tuempresa.turnofast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.ui.prestacion.NotificacionPrestacion;
import com.tuempresa.turnofast.ui.recordatorio.MyTestService;
import com.tuempresa.turnofast.ui.turno.NotificacionTurnos;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    //public static String PATH = "http://172.17.10.5:45455";
    //public static String PATH = "http://192.168.1.4:45455";
    //public static String PATH = "http://192.168.0.100:45455";
    public static String PATH = "http://turnofast.us1.gearhost.cloud";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_rubro, R.id.nav_servicio, R.id.nav_servicioTurnos,
                R.id.nav_listaServicios, R.id.nav_listaPrestacionesBM, R.id.nav_prestacionMB,
                R.id.nav_listaDiasAPrestacionTurnos, R.id.nav_listaRubrosTurnos, R.id.nav_listaPrestacionesDisponibles,
                R.id.nav_listaTurnosDisponibles, R.id.nav_misTurnos, R.id.nav_listaTurnosPorFecha,
                R.id.nav_detalleMisTurnos, R.id.nav_turnosSolicitadosPorMes, R.id.nav_listaTurnosSolicitadosPorDia,
                R.id.nav_detalleTurnoSolicitado, R.id.nav_cerrarsesion, R.id.nav_recordatorio,
                R.id.nav_misRecordatorios, R.id.nav_actualizarRecordatorio)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        servicios();
    }

    private void servicios() {
        Intent i = new Intent(this, MyTestService.class);
        Intent i2 = new Intent(this, NotificacionPrestacion.class);
        Intent i3 = new Intent(this, NotificacionTurnos.class);
        startService(i);
        startService(i2);
        startService(i3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
