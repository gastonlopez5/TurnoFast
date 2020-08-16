package com.tuempresa.turnofast.ui.recordatorio;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = DbTable.class, version = 1)
public abstract class DbDataBase extends RoomDatabase {
    private static DbDataBase dbDataBaseInstnce;

    public static synchronized DbDataBase getInstance(Context context){
        if (dbDataBaseInstnce==null){
            dbDataBaseInstnce = Room.databaseBuilder(context.getApplicationContext(), DbDataBase.class, "cybraryShopDb")
                    .allowMainThreadQueries().build();
        }
        return dbDataBaseInstnce;
    }

    public abstract DbDAO dbDAO();
}
