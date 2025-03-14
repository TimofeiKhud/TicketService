package com.example.ticketservicenew.presentation.paying.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.BookingInfo;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.Seat;
import com.example.ticketservicenew.presentation.hall.adapter.SelectedSeatsAdapter;
import com.example.ticketservicenew.presentation.hall.presenter.HallPresenter;
import com.example.ticketservicenew.presentation.hall.view.HallFragment;
import com.example.ticketservicenew.presentation.paying.config.Config;
import com.example.ticketservicenew.presentation.paying.presenter.PayingPresenter;
import com.example.ticketservicenew.presentation.paymentsuccess.view.PaymentSuccessFragment;
import com.example.ticketservicenew.presentation.shoppingcart.view.ShoppingCartFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class PayingFragment extends MvpAppCompatFragment implements PayingView {
    public static final String TAG = PayingFragment.class.getName();
    private static final int PAYPAL_REQUEST_CODE = 1;
    @InjectPresenter
    PayingPresenter presenter;
    Unbinder unbinder;

    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.total_price_txt)
    TextView totalPriceTxt;
    @BindView(R.id.total_tickets_txt)
    TextView totalTicketsTxt;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public PayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_paying, container, false);
        unbinder = ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        PayingFragmentArgs args = PayingFragmentArgs.fromBundle(requireArguments());
        presenter.onShowBookingInfo(args.getEventId(), args.getTitle());
        //Start PayPal Service
        Intent intent = new Intent(requireContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        requireActivity().startService(intent);
        return  v;
    }

    @Override
    public void onDestroy() {
        requireActivity().stopService(new Intent(requireContext(), PayPalService.class));
        super.onDestroy();
    }

    @OnClick(R.id.pay_btn)
    void onPay(){
        processPayment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void showNextView(String eventId) {
        Navigation.findNavController(getView())
                .navigate(PayingFragmentDirections.actionPayingFragmentToPaymentSuccessFragment(eventId, titleTxt.getText().toString()));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBookingInfo(String title, BookingInfo info) {
        titleTxt.setText(title);
        int totalTickets = info.getNumTicketsBooked();
        totalTicketsTxt.setText(totalTickets + (totalTickets%10 == 1 ? " ticket" : " tickets"));
        totalPriceTxt.setText("€ " + info.getTotalPrice());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void processPayment(){
        PayingFragmentArgs args = PayingFragmentArgs.fromBundle(requireArguments());
        double amount = presenter.getTotalPrice();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),
                "EUR",
                "Buy tickets",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(requireContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == PaymentActivity.RESULT_OK){
            if(requestCode == PAYPAL_REQUEST_CODE){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        presenter.onPaymentSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if(resultCode == PaymentActivity.RESULT_CANCELED){
            Toast.makeText(requireContext(), "Payment cancelled", Toast.LENGTH_SHORT).show();
        }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(requireContext(), "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
