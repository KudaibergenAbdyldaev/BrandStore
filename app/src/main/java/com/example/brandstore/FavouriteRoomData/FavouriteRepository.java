package com.example.brandstore.FavouriteRoomData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteRepository {

    private FavouriteDao basketDao;
    private LiveData<List<FavouriteData>> allNotes;
    public FavouriteRepository(Application application) {
        FavouriteDatabase database = FavouriteDatabase.getInstance(application);
        basketDao = database.basketDao();
        allNotes = basketDao.getAllNotes();
    }
    public void insert(FavouriteData data) {
        new InsertNoteAsyncTask(basketDao).execute(data);
    }

    public void update(FavouriteData data) {
        new UpdateNoteAsyncTask(basketDao).execute(data);
    }

    public void delete(FavouriteData data) {
        new DeleteNoteAsyncTask(basketDao).execute(data);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(basketDao).execute();
    }

    public LiveData<List<FavouriteData>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<FavouriteData, Void, Void> {
        private FavouriteDao basketDao;
        private InsertNoteAsyncTask(FavouriteDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(FavouriteData... basketData) {
            basketDao.insert(basketData[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<FavouriteData, Void, Void> {
        private FavouriteDao basketDao;
        private UpdateNoteAsyncTask(FavouriteDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(FavouriteData... basketData) {
            basketDao.update(basketData[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<FavouriteData, Void, Void> {
        private FavouriteDao basketDao;
        private DeleteNoteAsyncTask(FavouriteDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(FavouriteData... basketData) {
            basketDao.delete(basketData[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavouriteDao basketDao;
        private DeleteAllNotesAsyncTask(FavouriteDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            basketDao.deleteAllNotes();
            return null;
        }
    }

}
