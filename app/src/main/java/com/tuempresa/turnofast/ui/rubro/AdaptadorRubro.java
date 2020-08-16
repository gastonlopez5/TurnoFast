package com.tuempresa.turnofast.ui.rubro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tuempresa.turnofast.R;
import com.tuempresa.turnofast.modelos.Rubro;
import com.tuempresa.turnofast.MainActivity;

import java.util.ArrayList;

public class AdaptadorRubro extends RecyclerView.Adapter<AdaptadorRubro.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Rubro> lista;
    private View.OnClickListener listener;
    private Context context;

    public AdaptadorRubro(ArrayList<Rubro> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rubro, null,false);

        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
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
        ImageView ivRubro;
        TextView tvRubro;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            ivRubro = itemView.findViewById(R.id.ivRubro);
            tvRubro = itemView.findViewById(R.id.tvRubro);
        }

        public void asignarDatos(Rubro rubro) {
            Glide.with(context)
                    .load(MainActivity.PATH + rubro.getRutaFoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivRubro);

            tvRubro.setText(rubro.getTipo());
        }

    }
}

