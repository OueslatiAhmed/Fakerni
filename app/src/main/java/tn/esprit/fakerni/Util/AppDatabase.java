package tn.esprit.fakerni.Util;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tn.esprit.fakerni.Dao.NotificationDao;
import tn.esprit.fakerni.Entity.Notification;

@Database(entities = {Notification.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotificationDao notificationDao();
}
