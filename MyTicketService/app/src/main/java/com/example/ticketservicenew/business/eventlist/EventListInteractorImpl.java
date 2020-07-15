package com.example.ticketservicenew.business.eventlist;

import android.util.Pair;
import com.example.ticketservicenew.data.eventlist.EventListRepository;
import java.util.List;

public class EventListInteractorImpl implements EventListInteractor {
    private EventListRepository repository;

    public EventListInteractorImpl(EventListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onSearchTextChanged(String newText) {
        repository.onSearchTextChanged(newText);
    }

    @Override
    public boolean onFilterSelect(Pair<Long, Long> dateRange, List<Integer> categories) {
        return repository.onFilterSelect(dateRange, categories);
    }
}
