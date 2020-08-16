package com.tuempresa.turnofast.ui.recordatorio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.tuempresa.turnofast.R;

import java.util.List;

public class AdaptadorRecordatorios extends RecyclerView.Adapter<AdaptadorRecordatorios.ViewHolderDatos> implements View.OnClickListener {
    private List<DbTable> lista;
    private View.OnClickListener listener;
    private Context context;

    public AdaptadorRecordatorios(List<DbTable> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaptadorRecordatorios.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordatorio, null,false);

        view.setOnClickListener(this);

        return new AdaptadorRecordatorios.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecordatorios.ViewHolderDatos holder, int position) {
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

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivBorrar;
        TextView tvDireccion, tvFechaHora;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            ivBorrar = itemView.findViewById(R.id.ivBorrar);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);

            ivBorrar.setOnClickListener(this::onClick);
        }

        public void asignarDatos(DbTable dbTable) {
            String[] a = dbTable.getEncabezado().split(":");
            tvDireccion.setText(a[1]);
            tvFechaHora.setText(dbTable.getFecha()+" "+dbTable.getHora());
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            DbTable dbTable = lista.get(posicion);
            ItemRepository itemRepository = new ItemRepository(v.getContext());
            itemRepository.deleteItem(dbTable);
            Toast.makeText(v.getContext(), "Recordatorio eliminado", Toast.LENGTH_LONG).show();

            Navigation.findNavController(v).navigate(R.id.nav_home);
        }
    }
}
