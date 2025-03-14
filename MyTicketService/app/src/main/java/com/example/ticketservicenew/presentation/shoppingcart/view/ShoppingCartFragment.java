package com.example.ticketservicenew.presentation.shoppingcart.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.presentation.paying.view.PayingFragment;
import com.example.ticketservicenew.presentation.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.ticketservicenew.presentation.shoppingcart.presenter.ShoppingCartPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends MvpAppCompatFragment implements ShoppingCartView{
    public static final String TAG = ShoppingCartFragment.class.getName();
    @InjectPresenter
    ShoppingCartPresenter presenter;
    Unbinder unbinder;
    ShoppingCartAdapter adapter;
    AlertDialog errorDialog;
    AlertDialog confirmDialog;

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.empty_cart_title_txt)
    TextView emptyCartTxt;
    @BindView(R.id.notification_txt)
    TextView notificationTxt;
    @BindView(R.id.total_price_txt)
    TextView totalPriceTxt;
    @BindView(R.id.total_tickets_txt)
    TextView totalTicketsTxt;
    @BindView(R.id.confirm_terms_txt)
    TextView confirmTermsTxt;
    @BindView(R.id.seats_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.delete_selection_btn)
    Button deletSelectionBtn;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this, v);
        adapter = new ShoppingCartAdapter();
        recyclerView.setAdapter(adapter);
        setRetainInstance(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        presenter.onShowBookingInfo();
        return  v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.delete_selection_btn)
    void onDelete(){
        presenter.onDeleteSelectionClicked();
    }

    @OnClick(R.id.pay_btn)
    void onPay(){
        presenter.onPayClicked();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        payBtn.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        payBtn.setEnabled(true);
    }

    @Override
    public void showTitle(String title) {
        titleTxt.setText(title);
    }

    @Override
    public void showNextView(String eventId, int ticketsNum, float totalPrice) {
        Navigation.findNavController(getView())
                .navigate(ShoppingCartFragmentDirections.actionShoppingCartFragmentToPayingFragment(eventId, titleTxt.getText().toString()));
    }

    @Override
    public void showPrewView() {
        Navigation.findNavController(getView())
                .navigate(ShoppingCartFragmentDirections.actionShoppingCartFragmentToEventListFragment());
    }

    @Override
    public void showError(String error) {
        Timber.d("show error");
    }

    @Override
    public void showConfirmationDialog() {
        confirmDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Delete selection")
                .setMessage("Remove selected seats from the cart?")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> presenter.onDialogConfirm())
                .setNegativeButton("Cancel", ((dialog, which) -> {presenter.onDialogCancel();}))
                .create();
        confirmDialog.show();
    }

    @Override
    public void hideConfirmationDialog() {
        confirmDialog.dismiss();
    }

    @Override
    public void showBookingInfo(BookingInfo info) {
        totalPriceTxt.setText("€ " + info.getTotalPrice());
        int totalTickets = info.getNumTicketsBooked();
        totalTicketsTxt.setText(totalTickets + (totalTickets%10 == 1 ? " ticket" : " tickets"));
        List<Seat> seats = new ArrayList<>();
        for(LockedSeats lockedSeats : info.getLockedSeats()){
            for(String seat : lockedSeats.getSeats()){
                seats.add(new Seat(lockedSeats.getRow(), seat));
            }
        }
        adapter.addSeats(seats);
    }

    @Override
    public void showEmptyCart() {
        emptyCartTxt.setVisibility(View.VISIBLE);
        notificationTxt.setVisibility(View.GONE);
        deletSelectionBtn.setVisibility(View.GONE);
        payBtn.setVisibility(View.GONE);
    }

}
