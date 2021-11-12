package tn.esprit.fakerni.Util;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tn.esprit.fakerni.Dao.NotificationDao;
import tn.esprit.fakerni.Dao.ToDoDao;
import tn.esprit.fakerni.Entity.Notification;
import tn.esprit.fakerni.Entity.ToDo;

@Database(entities = {Notification.class, ToDo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotificationDao notificationDao();
    public abstract ToDoDao toDoDao();
}
