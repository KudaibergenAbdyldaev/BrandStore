package com.example.brandstore.FavouriteFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.brandstore.FavouriteRoomData.FavouriteData;
import com.example.brandstore.FavouriteRoomData.FavouriteRepository;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private FavouriteRepository repository;
    private LiveData<List<FavouriteData>> allNotes;
    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavouriteRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void insert(FavouriteData data) {
        repository.insert(data);
    }
    public void update(FavouriteData data) {
        repository.update(data);
    }
    public void delete(FavouriteData data) {
        repository.delete(data);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<FavouriteData>> getAllNotes() {
        return allNotes;
    }
}