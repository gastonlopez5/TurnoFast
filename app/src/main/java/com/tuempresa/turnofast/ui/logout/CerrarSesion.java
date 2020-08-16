package com.tuempresa.turnofast.ui.logout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.tuempresa.turnofast.R;


public class CerrarSesion extends Fragment {
    View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // sendViewModel =
        //         ViewModelProviders.of(this).get(SendViewModel.class);
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        v = view;
        notificacion();
        return view;
    }

    private void notificacion() {
        new AlertDialog.Builder(getContext()).setCancelable(false).setMessage("¿Desea salir de la aplicación?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Navigation.findNavController(v).navigate(R.id.nav_home, null);
            }
        }).show();
    }

}
