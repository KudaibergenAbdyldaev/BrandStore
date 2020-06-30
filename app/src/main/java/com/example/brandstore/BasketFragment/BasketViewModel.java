package com.example.brandstore.BasketFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.brandstore.BasketRoomData.BasketData;
import com.example.brandstore.BasketRoomData.BasketRepository;

import java.util.List;

public class BasketViewModel extends AndroidViewModel {
    private BasketRepository repository;
    private LiveData<List<BasketData>> allNotes;
    public BasketViewModel(@NonNull Application application) {
        super(application);
        repository = new BasketRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void insert(BasketData data) {
        repository.insert(data);
    }
    public void update(BasketData data) {
        repository.update(data);
    }
    public void delete(BasketData data) {
        repository.delete(data);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<BasketData>> getAllNotes() {
        return allNotes;
    }
}
