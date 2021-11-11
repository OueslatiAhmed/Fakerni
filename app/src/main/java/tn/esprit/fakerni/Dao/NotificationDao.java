package tn.esprit.fakerni.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tn.esprit.fakerni.Entity.Notification;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Insert
    long insert(Notification notification);
}
