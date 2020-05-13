package com.example.ticketservicenew.presentation.event.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;
import com.example.ticketservicenew.presentation.event.presenter.EventPresenter;
import com.example.ticketservicenew.presentation.hall.view.HallFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends MvpAppCompatFragment implements EventView {
    public static final String TAG = EventFragment.class.getName();
        Unbinder unbinder;
        Disposable disposable;

        @BindView(R.id.event_title_txt)
        TextView eventTitle;
        @BindView(R.id.event_name_txt)
        TextView eventName;
        @BindView(R.id.event_date_txt)
        TextView eventDate;
        @BindView(R.id.event_img)
        ImageView eventImg;
        @BindView(R.id.description_txt)
        TextView description;
        @BindView(R.id.date_txt)
        TextView date;
        @BindView(R.id.start_time_txt)
        TextView time;
        @BindView(R.id.tickets_available_txt)
        TextView ticketsAvailable;
        @BindView(R.id.price_range_txt)
        TextView priceRange;
        @BindView(R.id.buy_tickets_btn)
        Button buyTicketsBtn;

    @InjectPresenter
    EventPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View v = inflater.inflate(R.layout.event_details, container, false);

        unbinder = ButterKnife.bind(this, v);

        EventFragmentArgs args = EventFragmentArgs.fromBundle(requireArguments());
        presenter.onShowEvent(args.getEventId());
        eventTitle.setText(event.getArtist());
        eventName.setText(event.getEventName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
        eventDate.setText(dateFormat.format(event.getEventStart()));
        if(event.getImages().size() > 0) {
            Picasso.get().load(event.getImages().get(0)).fit().into(eventImg);
        }
        description.setText(event.getDescription());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        date.setText(String.format("Date: %s", dateFormat.format(event.getEventStart())));
        time.setText(timeFormat.format(event.getEventStart()));

        Single<EventInfo> single = presenter.getEventInfo(event.getEventId());
        disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventInfo -> {
                    ticketsAvailable.setText(String.format("%s%d", getString(R.string.tickets_available), eventInfo.getRestTick()));
                    priceRange.setText(String.format("Price range: %s € - %s €", eventInfo.getMinPrice(), eventInfo.getMaxPrice()));
                });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("EVENT");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Log.d(TAG, "back pressed");
                getParentFragmentManager().popBackStackImmediate();
                //getActivity().onBackPressed();
                return true;
            }
            default:
                return false;
        }
    }

    @OnClick(R.id.buy_tickets_btn)
    void onBuyTicketsClick(){
//        HallFragment hallFragment = new HallFragment();
//        Bundle bundle = new Bundle();
//        hallFragment.setArguments(bundle);
//        bundle.putString("Event id", event.getEventId());
//            getParentFragmentManager().beginTransaction()
//               .replace(R.id.fragment_container, hallFragment)
//               .addToBackStack(TAG)
//               .commit();
        Navigation.findNavController(getView()).navigate(EventFragmentDirections.actionEventFragmentToHallFragment(event.getEventId()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        disposable.dispose();
    }
}
