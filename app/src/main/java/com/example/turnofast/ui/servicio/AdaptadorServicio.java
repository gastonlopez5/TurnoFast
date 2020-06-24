package com.example.turnofast.ui.servicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.turnofast.R;

import java.util.ArrayList;

import static com.example.turnofast.MainActivity.PATH;

public class AdaptadorServicio extends RecyclerView.Adapter<AdaptadorServicio.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Servicio> lista;
    private View.OnClickListener listener;
    private Context context;

    public AdaptadorServicio(ArrayList<Servicio> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorServicio.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, null,false);

        view.setOnClickListener(this);

        return new AdaptadorServicio.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorServicio.ViewHolderDatos holder, int position) {
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
        EditText etDireccion, etNombre;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.ivLogo);
            etDireccion = itemView.findViewById(R.id.etDireccion);
            etNombre = itemView.findViewById(R.id.etNombre);
        }

        public void asignarDatos(Servicio servicio) {
            Glide.with(context)
                    .load(PATH + servicio.getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivLogo);

            etDireccion.setText(servicio.getDireccion());
            etNombre.setText(servicio.getNombre());
        }

    }
}
