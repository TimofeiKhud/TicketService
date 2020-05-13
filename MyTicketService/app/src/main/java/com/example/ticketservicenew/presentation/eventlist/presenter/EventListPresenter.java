package com.example.ticketservicenew.presentation.eventlist.presenter;

import android.util.Log;
import android.util.Pair;

import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.ticketservicenew.App;
import com.example.ticketservicenew.business.eventlist.EventListInteractor;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.eventlist.datasource.EventListDataSourceFactory;
import com.example.ticketservicenew.di.eventlist.EventListModule;
import com.example.ticketservicenew.presentation.eventlist.view.EventListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventListPresenter extends MvpPresenter<EventListView> {
    private final static String TAG = "EventListPresenter";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    Observable<PagedList<Event>> pagedListObservable;
    PagedList.Config pagedListConfig;

    @Inject
    EventListDataSourceFactory factory;
    @Inject
    EventListInteractor interactor;


    public EventListPresenter() {
        App.get().plusList(new EventListModule()).inject(this);
        initializePaging();
    }

    private void initializePaging() {
        Log.d(TAG, "initialize paging");
    }

    public void onShowEventList() {
        pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(6)
                        .setPageSize(6)
                        .build();

        pagedListObservable = new RxPagedListBuilder<>(factory, pagedListConfig).buildObservable();

        Disposable disposable = pagedListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((PagedList<Event> events) -> {
                    getViewState().onFiltersSet(events);
                }, (error) -> {
                });
        compositeDisposable.add(disposable);
    }

    public void onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories) {
        interactor.onFilterSelect(dateRange, categories);
    }

    public void onSearchTextChanged(String newText) {
        interactor.onSearchTextChanged(newText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.get().clearEventListComponent();
        compositeDisposable.clear();
    }
}
