package com.example.ticketservicenew.data.eventlist.datasource;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.eventlist.EventListRepository;
import com.example.ticketservicenew.data.eventlist.OnFiltersChangedListener;

import java.util.Observable;
import java.util.Observer;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class EventListDataSource extends PositionalDataSource<Event> implements OnFiltersChangedListener {
    private static final String TAG = EventListDataSource.class.getName();
private EventListRepository repository;
private int sourceIndex;
//private MutableLiveData<String> progressLiveStatus;
private CompositeDisposable compositeDisposable;


    public EventListDataSource(EventListRepository repository, CompositeDisposable disposable) {
        this.repository = repository;
        repository.setOnFiltersChangedListener(this);
        this.compositeDisposable = disposable;
        //repository.setDataSource(this);
        //progressLiveStatus = new MutableLiveData<>();
    }

//    public MutableLiveData<String> getProgressLiveStatus() {
//        return progressLiveStatus;
//    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Event> callback) {
        Log.d(TAG, "loadInitial: start: " + params.requestedStartPosition +  "page number: " + sourceIndex + " size: " + params.requestedLoadSize);
        repository.onInitialLoading();
        repository.getEvents(sourceIndex++, params.requestedLoadSize)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    //progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(events -> {
                            //progressLiveStatus.postValue(Constant.LOADED);
                    for(Event event : events){
                        Log.d(TAG, "load initial: " + event.getArtist());
                    }
                            callback.onResult(events, 0);
                        },
                        throwable ->
                                Log.d(TAG, throwable.getMessage())
                );
    }


    @SuppressLint("CheckResult")
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Event> callback) {
        Log.d(TAG, "loadRange: start: " + params.startPosition + "page number: " + sourceIndex + " size: " + params.loadSize);
        repository.getEvents(sourceIndex++, params.loadSize)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    //progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(events -> {
                            for(Event event : events){
                                Log.d(TAG, "load range: " + event.getArtist());
                            }
                            //progressLiveStatus.postValue(Constant.LOADED);
                            callback.onResult(events);
                        },
                        throwable ->
                                Log.d(TAG, throwable.getMessage())
                );
    }

    @Override
    public void onFiltersChanged() {
        invalidate();
    }
}
