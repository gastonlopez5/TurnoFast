package com.tuempresa.turnofast.ui.recordatorio;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ItemRepository {
    private Context context;

    public ItemRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public void addNewItem(DbTable dbTable){
        AsyncTask.execute(() -> DbDataBase.getInstance(context).dbDAO().insertRecordatorio(dbTable));
    }

    public DbTable getItem(String fecha_sistema, String hora_sistema){
        return DbDataBase.getInstance(context).dbDAO().getRecordatorio(fecha_sistema, hora_sistema);
    }

    public List<DbTable> getItems(){
        return DbDataBase.getInstance(context).dbDAO().getRecordatorios();
    }

    public void deleteItem(DbTable dbTable){
        AsyncTask.execute(() -> DbDataBase.getInstance(context).dbDAO().deletRecordatorio(dbTable));
    }

    public void deleteItems(){
        AsyncTask.execute(() -> DbDataBase.getInstance(context).dbDAO().deletRecordatorios());
    }

    public void updateRecordatorio(DbTable dbTable){
        AsyncTask.execute(() -> DbDataBase.getInstance(context).dbDAO().updateRecordatorio(dbTable));
    }
}
