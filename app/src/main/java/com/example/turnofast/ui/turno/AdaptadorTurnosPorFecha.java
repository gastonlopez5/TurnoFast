package com.example.turnofast.ui.turno;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Turno;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.turnofast.MainActivity.PATH;

public class AdaptadorTurnosPorFecha extends RecyclerView.Adapter<AdaptadorTurnosPorFecha.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Turno> lista;
    private View.OnClickListener listener;
    private Context context;
    private SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm");

    public AdaptadorTurnosPorFecha(ArrayList<Turno> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorTurnosPorFecha.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_turno_por_fecha, null,false);

        view.setOnClickListener(this);

        return new AdaptadorTurnosPorFecha.ViewHolderDatos(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AdaptadorTurnosPorFecha.ViewHolderDatos holder, int position) {
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
        ImageView ivLogo;
        TextView tvFechaHora, tvPrestacion;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.ivLogo);
            tvPrestacion = itemView.findViewById(R.id.tvDireccion);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void asignarDatos(Turno turno) {
            Glide.with(context)
                    .load(PATH + turno.getHorario2().getPrestacion().getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivLogo);
            tvPrestacion.setText(turno.getHorario2().getPrestacion().getNombre());
            tvFechaHora.setText(turno.getFecha()+"   "+ turno.getHora().getHour()+":"+turno.getHora().getMinute());
        }

    }
}
