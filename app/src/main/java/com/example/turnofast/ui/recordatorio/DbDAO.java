package com.example.turnofast.ui.recordatorio;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DbDAO {

    @Insert
    void insertRecordatorio(DbTable...dbTable);

    @Query("SELECT * from DbTable where fecha = :fecha_sistema and hora= :hora_sistema")
    DbTable getRecordatorio(String fecha_sistema, String hora_sistema);

    @Query("SELECT * from DbTable")
    List<DbTable> getRecordatorios();

    @Delete
    void deletRecordatorio(DbTable dbTable);

    @Update
    void updateRecordatorio(DbTable dbTable);

    @Query("DELETE from DbTable")
    void deletRecordatorios();
}
