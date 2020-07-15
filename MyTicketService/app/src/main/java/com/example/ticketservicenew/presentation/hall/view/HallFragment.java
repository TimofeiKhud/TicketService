package com.example.ticketservicenew.presentation.hall.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.presentation.hall.adapter.SelectedSeatsAdapter;
import com.example.ticketservicenew.presentation.hall.presenter.HallPresenter;
import com.example.ticketservicenew.presentation.hall.utils.HallConstructor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class HallFragment extends MvpAppCompatFragment implements HallView{
    public static final String TAG = HallFragment.class.getName();
    @InjectPresenter
    HallPresenter presenter;
    Unbinder unbinder;
    SelectedSeatsAdapter adapter;


    @BindView(R.id.event_title_txt)
    TextView eventTitleTxt;
    @BindView(R.id.high_price_txt)
    TextView highPriceTxt;
    @BindView(R.id.middle_price_txt)
    TextView middlePriceTxt;
    @BindView(R.id.low_price_txt)
    TextView lowPriceTxt;
    @BindView(R.id.price_total_txt)
    TextView totalPriceTxt;
    @BindView(R.id.tickets_total_txt)
    TextView totalTicketsTxt;
    @BindView(R.id.selected_places_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.to_the_cart_btn)
    Button toCartBtn;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.hall_scroll)
    HorizontalScrollView hallScroll;

//    @BindView(R.id.row_edit)
//    EditText rowEdit;
//    @BindView(R.id.seat_edit)
//    EditText seatEdit;

    public HallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.hall_fragment, container, false);

        unbinder = ButterKnife.bind(this, v);
        if(adapter == null) {
            adapter = new SelectedSeatsAdapter();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        HallFragmentArgs args = HallFragmentArgs.fromBundle(requireArguments());
        eventTitleTxt.setText(args.getTitle());
        presenter.onShowHallStructure(args.getEventId(), args.getHallId());
        return  v;
    }

    @Override
    public void showHallStructure(HallStructure hallStructure, int hallId) {
        Timber.d("Show hall structure");
        List<Price> priceList = hallStructure.getPriceList();
        HallConstructor constructor = new HallConstructor(requireContext());
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            Timber.d("button check changed");
            if (isChecked){
                boolean seatSelected = presenter.onSeatClicked(true, buttonView.getTag().toString(), adapter.getItemCount());
                if(!seatSelected){
                    buttonView.setChecked(false);
                }
            }else{
                presenter.onSeatClicked(false, buttonView.getTag().toString(), adapter.getItemCount());
            }
        };

            if (hallId == 1) {
                hallScroll.addView(constructor.createSmallHallView(hallStructure, listener, null));
            } else if (hallId == 0) {
                hallScroll.addView(constructor.createBigHallView(hallStructure, listener, null));
            }
    }

    @OnClick(R.id.to_the_cart_btn)
    void onBookTickets(){
        Timber.d("on Pay");
        presenter.onBookTicketsClicked(adapter.getSeats());
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "on Destroy view");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        toCartBtn.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        toCartBtn.setEnabled(true);
    }

    @Override
    public void showNextView() {
        Navigation.findNavController(getView()).navigate(HallFragmentDirections.actionHallFragmentToShoppingCartFragment());
    }

    @Override
    public void showError(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPriceList(List<Price> priceList) {
        if(priceList.size() > 0){
            highPriceTxt.setText(String.valueOf(priceList.get(0).getPrice()));
            highPriceTxt.setBackgroundColor(Color.parseColor(priceList.get(0).getColor()));
        }
        if (priceList.size() > 1){
            middlePriceTxt.setText(String.valueOf(priceList.get(1).getPrice()));
            middlePriceTxt.setBackgroundColor(Color.parseColor(priceList.get(1).getColor()));
        }
        if (priceList.size() > 2){
            lowPriceTxt.setText(String.valueOf(priceList.get(2).getPrice()));
            lowPriceTxt.setBackgroundColor(Color.parseColor(priceList.get(2).getColor()));
        }
    }

    @Override
    public void showSelectedSeatsInfo(boolean isSelected, Seat seat, double totalPrice, int totalTickets) {
        totalPriceTxt.setText("€ " + totalPrice);
        totalTicketsTxt.setText(totalTickets + (totalTickets%10 == 1 ? " ticket" : " tickets"));
        if (isSelected) {
            adapter.addSeat(seat);
        } else {
            adapter.removeSeat(seat);
        }
    }
}
