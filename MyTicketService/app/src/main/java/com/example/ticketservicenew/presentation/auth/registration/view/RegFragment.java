package com.example.ticketservicenew.presentation.auth.registration.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.presentation.auth.AuthView;
import com.example.ticketservicenew.presentation.auth.registration.presenter.RegPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RegFragment extends MvpAppCompatFragment implements AuthView{
    private Unbinder unbinder;

    @BindView(R.id.email_edit)
    EditText emailEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.reg_btn)
    Button regBtn;
    @BindView(R.id.back_btn)
    Button backBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.agreement_txt)
    TextView agreementTxt;
    @BindView(R.id.registerView)
    ScrollView regView;
    @BindView(R.id.termsView)
    ScrollView termsView;
    @BindView(R.id.terms_web_view)
    WebView termsVebView;
    @BindView(R.id.male_check)
    CheckBox maleCheck;
    @BindView(R.id.female_check)
    CheckBox femaleCheck;

        private AlertDialog errorDialog;

        @InjectPresenter
        RegPresenter presenter;

        public RegFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.registration_fragment, container, false);
            unbinder = ButterKnife.bind(this, v);
            agreementTxt.setText(showAgreement());
            agreementTxt.setMovementMethod(LinkMovementMethod.getInstance());
            return v;
        }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("REGISTRATION");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.reg_btn)
    void onRegClick(){
        presenter.onRegistration(emailEdit.getText().toString(), passwordEdit.getText().toString());
    }

    @OnCheckedChanged(R.id.male_check)
    void onMaleGenderSelected(boolean checked){
            femaleCheck.setEnabled(!checked);
    }

    @OnCheckedChanged(R.id.female_check)
    void onFemaleGenderSelected(boolean checked){
            maleCheck.setEnabled(!checked);
    }

        @Override
        public void showProgress() {
            backBtn.setEnabled(false);
            regBtn.setEnabled(false);
            emailEdit.setEnabled(false);
            passwordEdit.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideProgress() {
            backBtn.setEnabled(true);
            regBtn.setEnabled(true);
            emailEdit.setEnabled(true);
            passwordEdit.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void showError(String error) {
            errorDialog = new AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(error)
                    .setPositiveButton("OK", (dialog, which) -> presenter.onDialogClicked())
                    .setCancelable(false)
                    .create();
            errorDialog.show();
        }

        @Override
        public void hideError() {
            if(errorDialog != null && errorDialog.isShowing()){
                errorDialog.dismiss();
            }
        }

        @Override
        public void showNextView() {
            getParentFragmentManager().popBackStack();
        }

    @Override
    public void showRegisteredUserState(String userName) {

    }

    @Override
    public void showNotRegisteredUserState() {

    }


    public SpannableString showAgreement(){
            SpannableString ss=new SpannableString(requireContext().getString(R.string.agreements));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    regView.setVisibility(View.GONE);
                    termsView.setVisibility(View.VISIBLE);
                    regBtn.setVisibility(View.GONE);
                    backBtn.setVisibility(View.VISIBLE);
                    termsVebView.loadUrl("https://www.webid-solutions.de/en/standard-terms-of-business/");
                }
            };
            ss.setSpan(clickableSpan,36,62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }
    }

