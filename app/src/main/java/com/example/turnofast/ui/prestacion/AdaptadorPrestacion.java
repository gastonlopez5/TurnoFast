package com.example.turnofast.ui.prestacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Prestacion;

import java.util.ArrayList;

import static com.example.turnofast.MainActivity.PATH;

public class AdaptadorPrestacion extends RecyclerView.Adapter<AdaptadorPrestacion.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Prestacion> lista;
    private View.OnClickListener listener;
    private Context context;

    public AdaptadorPrestacion(ArrayList<Prestacion> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorPrestacion.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, null,false);

        view.setOnClickListener(this);

        return new AdaptadorPrestacion.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPrestacion.ViewHolderDatos holder, int position) {
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
        TextView tvDireccion, tvNombre;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.ivLogo);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }

        public void asignarDatos(Prestacion prestacion) {
            int numero = (int) (Math.random() * 10) + 1;
            Glide.with(context)
                    .load(PATH + prestacion.getLogo() + "?temp=" + numero)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivLogo);
            tvDireccion.setText(prestacion.getDireccion());
            tvNombre.setText(prestacion.getNombre());
        }

    }
}
