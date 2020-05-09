
package com.example.ticketservicenew.presentation.eventlist.utils.pagination;


import com.example.ticketservicenew.business.model.Event;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PagingListener {
    Single<List<Event>> onNextPage(int offset);
}
