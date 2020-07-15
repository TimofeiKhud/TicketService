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
import timber.log.Timber;

public class EventListDataSource extends PositionalDataSource<Event> implements OnFiltersChangedListener {
private EventListRepository repository;
private int sourceIndex;
private CompositeDisposable compositeDisposable;


    public EventListDataSource(EventListRepository repository, CompositeDisposable disposable) {
        this.repository = repository;
        repository.setOnFiltersChangedListener(this);
        this.compositeDisposable = disposable;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Event> callback) {
        repository.onInitialLoading();
        repository.getEvents(sourceIndex++, params.requestedLoadSize)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                })
                .subscribe(events -> {
                            callback.onResult(events, 0);
                        },
                        throwable ->
                                Timber.d(throwable.getMessage())
                );
    }


    @SuppressLint("CheckResult")
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Event> callback) {
        repository.getEvents(sourceIndex++, params.loadSize)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                })
                .subscribe(callback::onResult,
                        throwable ->
                                Timber.d(throwable.getMessage())
                );
    }

    @Override
    public void onFiltersChanged() {
        invalidate();
    }
}
