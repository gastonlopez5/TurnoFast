package com.tuempresa.turnofast.ui.turno;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Turno;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AdaptadorTurnos extends RecyclerView.Adapter<AdaptadorTurnos.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Turno> lista;
    private View.OnClickListener listener;
    private Context context;
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm");

    public AdaptadorTurnos(ArrayList<Turno> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorTurnos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno, null,false);

        view.setOnClickListener(this);

        return new AdaptadorTurnos.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTurnos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvHora;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHora);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(Turno turno) {
            tvHora.setText(turno.getHora().getHour()+":"+turno.getHora().getMinute());
        }

    }
}
