package com.example.turnofast.ui.recordatorio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RecordatorioViewModel extends AndroidViewModel {
    private Context context;
    private ItemRepository itemRepository;
    private MutableLiveData<DbTable> error;

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
}
