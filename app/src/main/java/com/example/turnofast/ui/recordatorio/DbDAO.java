package com.example.turnofast.ui.recordatorio;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DbDAO {

    @Insert
    void insertRecordatorio(DbTable...dbTable);

    @Query("SELECT * from DbTable where fecha = :fecha_sistema and hora= :hora_sistema")
    DbTable getRecordatorio(String fecha_sistema, String hora_sistema);

    @Delete
    void deletRecordatorio(DbTable dbTable);
}
