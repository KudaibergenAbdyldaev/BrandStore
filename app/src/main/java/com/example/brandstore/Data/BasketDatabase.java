package com.example.brandstore.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {BasketData.class}, version = 2)
public abstract class BasketDatabase extends RoomDatabase {

    private static BasketDatabase instance;
    public abstract BasketDao basketDao();
    public static synchronized BasketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BasketDatabase.class, "basket_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BasketDao basketDao;
        private PopulateDbAsyncTask(BasketDatabase db) {
            basketDao = db.basketDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            basketDao.insert(new BasketData("Title 1", "Description 1", "100","2"));
//            basketDao.insert(new BasketData("Title 2", "Description 2", "100","3"));
//            basketDao.insert(new BasketData("Title 3", "Description 3", "100","4"));
            return null;
        }
    }
}
