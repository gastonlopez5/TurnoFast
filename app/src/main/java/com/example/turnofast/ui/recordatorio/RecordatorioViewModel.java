package com.example.turnofast.ui.recordatorio;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class RecordatorioViewModel extends AndroidViewModel {
    private Context context;
    private ItemRepository itemRepository;
    private MutableLiveData<DbTable> error;
    private MutableLiveData<String> error2;
    private MutableLiveData<List<DbTable>> listaRecordatorios;
    private String msjError = "No hay recordatorios cargados";

    public RecordatorioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        itemRepository = new ItemRepository(context);
    }

    public LiveData<DbTable> getError(){
        if (error==null){
            error = new MutableLiveData<>();
        }
        return error;
    }

    public LiveData<String> getError2(){
        if (error2==null){
            error2 = new MutableLiveData<>();
        }
        return error2;
    }

    public LiveData<List<DbTable>> getListaRecordatorios(){
        if (listaRecordatorios==null){
            listaRecordatorios = new MutableLiveData<>();
        }
        return listaRecordatorios;
    }

    public void insertNewItem(DbTable dbTable){
        if (dbTable.getFecha().isEmpty() || dbTable.getHora().isEmpty()){
            error.setValue(dbTable);
        }
        else{
            itemRepository.addNewItem(dbTable);
        }
    }

    public void deleteItem(DbTable dbTable){
            itemRepository.deleteItem(dbTable);
    }

    public DbTable getItem(String fecha_sistema, String hora_sistema){
        return itemRepository.getItem(fecha_sistema, hora_sistema);
    }

    public void recuperarRecordatorios(){
        //itemRepository.deleteItems();
        List<DbTable> lista = itemRepository.getItems();
        if (lista.size() != 0){
            listaRecordatorios.setValue(itemRepository.getItems());
        }
        else {
            error2.setValue(msjError);
        }
    }

    public void actualizarRecordatorio(DbTable dbTable){
        if (dbTable.getFecha().isEmpty() || dbTable.getHora().isEmpty()){
            error.setValue(dbTable);
        }
        else{
            itemRepository.updateRecordatorio(dbTable);
        }
    }
}
