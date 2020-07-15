
package com.example.ticketservicenew.presentation.eventlist.utils.pagination;



import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketservicenew.business.model.Event;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PaginationTool {

    private final static String TAG = "Pagination tool";
    private static final int DEFAULT_LIMIT = 50;

    private RecyclerView recyclerView;
    private PagingListener pagingListener;
    private int limit;
    private int loadingPage = 0;

    private PaginationTool() {
    }

    public Observable<List<Event>> getPagingObservable() {
        return getScrollObservable(recyclerView, limit) //on scrolled list
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.onNextPage(offset), offset)
                        .toObservable());
    }

    private Observable<Integer> getScrollObservable(RecyclerView recyclerView, int limit) {

        Observable<Integer> observable = Observable.create(    new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (!emitter.isDisposed()) {
                            int position = getLastVisibleItemPosition(recyclerView);
                            int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (limit / 2); //last visible item index to start loading
                            if (position >= updatePosition) {
                                int offset = loadingPage++;
                                emitter.onNext(offset);
                            }
                        }else{
                            recyclerView.removeOnScrollListener(this);
                        }
                    }
                };
                recyclerView.addOnScrollListener(sl);
                if (recyclerView.getAdapter().getItemCount() == 0) {
                    int offset = loadingPage++;
                    emitter.onNext(offset);
                }
            }
        });

        return observable;
    }

    private int getLastVisibleItemPosition(RecyclerView recyclerView) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            return linearLayoutManager.findLastVisibleItemPosition();
    }

    private Single<List<Event>> getPagingObservable(PagingListener listener, Single<List<Event>> observable, int offset) {
        return observable.onErrorResumeNext(throwable -> {
            // retry to load new data portion if error occurred
            //TODO remove or add count of loading retry
            //return getPagingObservable(listener, listener.onNextPage(offset), offset);
            return Single.fromObservable(Observable.empty());
        });
    }

    public static Builder buildPagingObservable(RecyclerView recyclerView, PagingListener pagingListener) {
        return new Builder(recyclerView, pagingListener);
    }

    public static class Builder{

        private RecyclerView recyclerView;
        private PagingListener pagingListener; //repo loading method
        private int limit = DEFAULT_LIMIT; //items to load

        private Builder(RecyclerView recyclerView, PagingListener pagingListener) {
            if (recyclerView == null) {
                throw new PagingException("null recyclerView");
            }
            if (recyclerView.getAdapter() == null) {
                throw new PagingException("null recyclerView adapter");
            }
            if (pagingListener == null) {
                throw new PagingException("null pagingListener");
            }
            this.recyclerView = recyclerView;
            this.pagingListener = pagingListener;
        }

        public Builder setLimit(int limit) {
            if (limit <= 0) {
                throw new PagingException("limit must be greater then 0");
            }
            this.limit = limit;
            return this;
        }

        public PaginationTool build() {
            PaginationTool paginationTool = new PaginationTool();
            paginationTool.recyclerView = this.recyclerView;
            paginationTool.pagingListener = pagingListener;
            paginationTool.limit = limit;
            return paginationTool;
        }

    }

}
