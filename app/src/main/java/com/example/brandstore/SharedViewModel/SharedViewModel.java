package com.example.brandstore.SharedViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<CharSequence> text_name = new MutableLiveData<>();

    public void setTextName(CharSequence input) {
        text_name.setValue(input);
    }
    public LiveData<CharSequence> getTextName() {
        return text_name;
    }

    private MutableLiveData<CharSequence> text_image = new MutableLiveData<>();
    public void setTextImage(CharSequence input) {
        text_image.setValue(input);
    }
    public LiveData<CharSequence> getTextImage() {
        return text_image;
    }
    private MutableLiveData<CharSequence> text_count = new MutableLiveData<>();
    public void setTextCount(CharSequence input) {
        text_count.setValue(input);
    }
    public LiveData<CharSequence> getTextCount() {
        return text_count;
    }
    private MutableLiveData<CharSequence> text_amount = new MutableLiveData<>();
    public void setTextAmount(CharSequence input) {
        text_amount.setValue(input);
    }
    public LiveData<CharSequence> getTextAmount() {
        return text_amount;
    }

}
