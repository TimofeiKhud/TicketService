package com.example.ticketservicenew.presentation.eventlist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.data.eventlist.utils.Constant;
import com.example.ticketservicenew.presentation.event.view.EventFragment;
import com.example.ticketservicenew.presentation.eventlist.OnEventListAdapterListener;
import com.example.ticketservicenew.presentation.eventlist.adapter.EventListAdapterImpl;
import com.example.ticketservicenew.presentation.eventlist.presenter.EventListPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EventListFragment extends MvpAppCompatFragment implements EventListView, OnEventListAdapterListener {
    public static final String TAG = EventListFragment.class.getName();
    private static final String BOTTOM_SHEET_FILTER = "bottomSheetFilter";
    private static final String SEARCH_KEY = "searchQuiery";
    private static final int REQUEST_FILTER = 0;
    private final static int LIMIT = 5;

    private BottomSheetFragment bottomSheetFragment;

    @InjectPresenter
    EventListPresenter presenter;

    private EventListAdapterImpl adapter;

    private String searchString;

    Unbinder unbinder;

    @BindView(R.id.event_list_recycler)
    RecyclerView eventListRecycler;
    @BindView(R.id.filter_btn)
    Button filterBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    SearchView searchView;
    MenuItem searchItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.event_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        init(savedInstanceState);
        presenter.onShowEventList();
//                presenter.getPagedListLiveData().observe(getViewLifecycleOwner(), events -> adapter.submitList(events));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            searchString = savedInstanceState.getString(SEARCH_KEY);
            // saved instance state, fragment may exist
            // look up the instance that already exists by tag
            bottomSheetFragment = (BottomSheetFragment)
                    getActivity().getSupportFragmentManager().findFragmentByTag(BOTTOM_SHEET_FILTER);
        } else if (bottomSheetFragment == null) {
            // only create fragment if they haven't been instantiated already
            bottomSheetFragment = new BottomSheetFragment();
        }
    }

    private void init(Bundle savedInstanceState) {
        DiffUtil.ItemCallback<Event> callback = new DiffUtil.ItemCallback<Event>() {
            @Override
            public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.getArtist().equalsIgnoreCase(newItem.getArtist())
                        && (oldItem.getEventStart() == newItem.getEventStart());
            }
        };

        if (!Constant.checkInternetConnection(requireContext())) {
            Snackbar.make(requireView().findViewById(android.R.id.content), Constant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }

        // init adapter for the first time
        if (savedInstanceState == null) {
            //TODO inject adapter
            adapter = new EventListAdapterImpl(callback, this);
            //adapter.setHasStableIds(true);
        }

        eventListRecycler.setSaveEnabled(true);
        eventListRecycler.setAdapter(adapter);
        eventListRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        eventListRecycler.setOnTouchListener((v, event) -> {
            if (searchString == null || searchString.isEmpty()) {
                searchItem.collapseActionView();
                return false;
            } else {
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchString = searchView.getQuery().toString();
        outState.putString(SEARCH_KEY, searchString);
    }

    @Override
    public void onDestroyView() {
        if (eventListRecycler != null) {
            eventListRecycler.setAdapter(null);
        }
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.filter_btn)
    void onFilterClick() {
        showFilters();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_FILTER) {
            if (data != null) {
                long fromDate = data.getLongExtra(BottomSheetFragment.EXTRA_DATE_FILTER_START, -1);
                long toDate = data.getLongExtra(BottomSheetFragment.EXTRA_DATE_FILTER_END, -1);
                List<Integer> types = new ArrayList<>();
                if (data.hasExtra(BottomSheetFragment.EXTRA_TYPE_FILTER)) {
                    types = data.getIntegerArrayListExtra(BottomSheetFragment.EXTRA_TYPE_FILTER);
                }
                presenter.onFilterSelect(new Pair<>(fromDate, toDate), types);
            }

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.event_list_menu, menu);
        searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        if (searchString != null && !searchString.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(searchString, true);
            //searchView.clearFocus();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onSearchTextChanged(newText);
                return true;
            }
        });
        //when recycler touched and searchItem collapsed
        searchView.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                searchItem.collapseActionView();
                InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void showFilters() {
        bottomSheetFragment.setTargetFragment(this, REQUEST_FILTER);
        bottomSheetFragment.show(getParentFragmentManager(), BOTTOM_SHEET_FILTER);
    }

    @Override
    public void showEvents(PagedList<Event> filteredEvents) {
        for (Event event : filteredEvents) {
            Log.d(TAG, "submit list: " + event.getArtist());
        }
        adapter.submitList(filteredEvents);
    }

    @Override
    public void showProgress() {
        eventListRecycler.setEnabled(false);
        filterBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        eventListRecycler.setEnabled(true);
        filterBtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEventClicked() {

    }

    @Override
    public void showEvent(String eventId) {
        Navigation.findNavController(getView())
                .navigate(EventListFragmentDirections.actionEventListFragmentToEventFragment(eventId));
    }
}
