package com.example.brandstore.BasketRoomData;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class BasketRepository {
    private BasketDao basketDao;
    private LiveData<List<BasketData>> allNotes;
    public BasketRepository(Application application) {
        BasketDatabase database = BasketDatabase.getInstance(application);
        basketDao = database.basketDao();
        allNotes = basketDao.getAllNotes();
    }
    public void insert(BasketData data) {
        new InsertNoteAsyncTask(basketDao).execute(data);
    }

    public void update(BasketData data) {
        new UpdateNoteAsyncTask(basketDao).execute(data);
    }

    public void delete(BasketData data) {
        new DeleteNoteAsyncTask(basketDao).execute(data);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(basketDao).execute();
    }

    public LiveData<List<BasketData>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<BasketData, Void, Void> {
        private BasketDao basketDao;
        private InsertNoteAsyncTask(BasketDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(BasketData... basketData) {
            basketDao.insert(basketData[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<BasketData, Void, Void> {
        private BasketDao basketDao;
        private UpdateNoteAsyncTask(BasketDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(BasketData... basketData) {
            basketDao.update(basketData[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<BasketData, Void, Void> {
        private BasketDao basketDao;
        private DeleteNoteAsyncTask(BasketDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(BasketData... basketData) {
            basketDao.delete(basketData[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private BasketDao basketDao;
        private DeleteAllNotesAsyncTask(BasketDao basketDao) {
            this.basketDao = basketDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            basketDao.deleteAllNotes();
            return null;
        }
    }

}
