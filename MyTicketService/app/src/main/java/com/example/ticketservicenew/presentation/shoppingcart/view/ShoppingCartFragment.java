package com.example.ticketservicenew.presentation.shoppingcart.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
    @BindView(R.id.total_price_txt)
    TextView totalPriceTxt;
    @BindView(R.id.total_tickets_txt)
    TextView totalTicketsTxt;
    @BindView(R.id.confirm_terms_txt)
    TextView confirmTermsTxt;
    @BindView(R.id.seats_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.delete_selection_btn)
    Button deletSelectionDtn;
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
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new ShoppingCartAdapter();
        recyclerView.setAdapter(adapter);
        setRetainInstance(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Seat> bookedSeats = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            if(getArguments().keySet().contains(Integer.toString(i))){
                for(String seat : getArguments().getStringArrayList(String.valueOf(i))){
                    Log.d(TAG, "seat: " + seat + "row: " + i);
                    bookedSeats.add(new Seat(seat, Integer.toString(i)));
                }
            }
        }
        presenter.onShowBookedSeats(getArguments().getString("Event id"), bookedSeats);

        return  v;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Log.d(TAG, "back pressed");
                showPrewView();
                //getActivity().onBackPressed();
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("SHOPPING CART");
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
        presenter.onPay();
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
    public void setBookedSeats(List<Seat> seats) {
        totalPriceTxt.setText("€" + getArguments().getDouble("Total price"));
        adapter.addSeats(seats);
    }

    @Override
    public void showNextView(String id, List<Seat> seats) {
        Bundle bundle = new Bundle();
        double totalPrice = getArguments().getDouble("Total price");
        bundle.putDouble("Total price", totalPrice);
        bundle.putString("Event id", id);
        for(Seat seat : seats){
            ArrayList<String> seatList = bundle.keySet().contains(seat.getRow()) && bundle.getStringArrayList(seat.getRow()) != null ?
                    bundle.getStringArrayList(seat.getRow()) : new ArrayList<>();
            seatList.add(seat.getSeatNum());
            bundle.putStringArrayList(seat.getRow(), seatList);
        }


        PayingFragment payingFragment = new PayingFragment();
        payingFragment.setArguments(bundle);
//        getParentFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, payingFragment)
//                .addToBackStack(TAG)
//                .commit();
    }

    @Override
    public void showPrewView() {
        getParentFragmentManager().popBackStackImmediate();
    }

    @Override
    public void showError(String error) {
Log.d(TAG, "show error");
    }

    @Override
    public void showConfirmationDialog() {
        confirmDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
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

}
