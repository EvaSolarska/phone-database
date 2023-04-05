package com.example.phonedb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract PhoneDao phoneDao();

    private static volatile PhoneRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static PhoneRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class) {
                if (INSTANCE == null) {
                    // tworzenie bazy danych
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhoneRoomDatabase.class, "phone_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        // metoda wywoływana przy tworzeniu bazy danych
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.phoneDao();
                Phone[] phones = {
                        new Phone("Samsung", "Galaxy S21", "Android 11", "www.samsung.com"),
                        new Phone("Apple", "iPhone 13", "iOS 15", "www.apple.com")
                };
                dao.insertAll(phones);
            });
        }
    };
}