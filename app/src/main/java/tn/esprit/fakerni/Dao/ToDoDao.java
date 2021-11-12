package tn.esprit.fakerni.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tn.esprit.fakerni.Entity.ToDo;

@Dao
public interface ToDoDao {
    @Query("SELECT * FROM todo ORDER BY date DESC")
    List<ToDo> getAll();

    @Query("SELECT * FROM todo WHERE level = 'Urgent' ORDER BY date DESC")
    List<ToDo> getAllUrgent();

    @Query("SELECT * FROM todo WHERE level = 'High' ORDER BY date DESC")
    List<ToDo> getAllHigh();

    @Query("SELECT * FROM todo WHERE level = 'Low' ORDER BY date DESC")
    List<ToDo> getAllLow();


    @Insert
    long insert(ToDo toDo);
}
