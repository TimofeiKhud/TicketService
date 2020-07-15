package com.example.ticketservicenew.presentation.paymentsuccess.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.presentation.eventlist.view.EventListFragment;
import com.example.ticketservicenew.presentation.paymentsuccess.presenter.PaymentSuccessPresenter;
import com.paypal.android.sdk.payments.PayPalService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PaymentSuccessFragment extends MvpAppCompatFragment implements PaymentSuccessView{

    @InjectPresenter
    PaymentSuccessPresenter presenter;
    Unbinder unbinder;

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.total_price_txt)
    TextView totalPriceTxt;
    @BindView(R.id.total_tickets_txt)
    TextView totalTicketsTxt;
    @BindView(R.id.navigate_to_event_list_txt)
    TextView navToList;
    @BindView(R.id.download_btn)
    Button downloadBtn;

    public PaymentSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment_success, container, false);
        unbinder = ButterKnife.bind(this, v);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        PaymentSuccessFragmentArgs args = PaymentSuccessFragmentArgs.fromBundle(requireArguments());
        presenter.onShowPaymentInfo(args.getEventId(), args.getTitle());
        navToList.setText(toMainMenuString());
        navToList.setMovementMethod(LinkMovementMethod.getInstance());
        return  v;
    }

    private SpannableString toMainMenuString() {
        SpannableString ss=new SpannableString("Return to the main page");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showNextView();
            }
        };
        ss.setSpan(clickableSpan,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }



    @OnClick(R.id.download_btn)
    void onDownload(){
        presenter.onDownloadClicked();
    }

    @Override
    public void onDestroy() {
        requireActivity().stopService(new Intent(requireContext(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showNextView() {
        //getParentFragmentManager().popBackStack(EventListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Navigation.findNavController(getView())
                .navigate(PaymentSuccessFragmentDirections.actionPaymentSuccessFragmentToEventListFragment());
    }

    @Override
    public void showPaymentInfo(String title, BookingInfo info) {
        titleTxt.setText(title);
        totalPriceTxt.setText("€" + info.getTotalPrice());
        int totalTickets = info.getNumTicketsBooked();
        totalTicketsTxt.setText(totalTickets + (totalTickets%10 == 1 ? " ticket" : " tickets"));
    }
}
