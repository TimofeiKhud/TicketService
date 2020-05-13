package com.example.ticketservicenew.presentation.auth.login.view;


import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.ticketservicenew.R;
import com.example.ticketservicenew.presentation.auth.AuthView;
import com.example.ticketservicenew.presentation.auth.login.presenter.LoginPresenter;
import com.example.ticketservicenew.presentation.auth.registration.view.RegFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends MvpAppCompatFragment implements AuthView{
    private static final String TAG = LoginFragment.class.getName();
    private Unbinder unbinder;
    @BindView(R.id.email_edit)
    EditText emailEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.reg_btn)
    Button regBtn;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.show_pass_check)
    CheckBox showPasswordCheck;
    @BindView(R.id.login_txt)
    TextView loginTxt;
    @BindView(R.id.logout_btn)
    Button logoutBtn;

    @BindViews({R.id.title_txt, R.id.email_edit, R.id.password_edit, R.id.password_options_layout, R.id.login_btn})
    List<View> setVisibilityList;

    private AlertDialog errorDialog;

    @InjectPresenter
    LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
        presenter.onSetLoginState();

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

    @OnClick(R.id.login_btn)
    void onLoginClick(){
        presenter.onLogin(emailEdit.getText().toString(), passwordEdit.getText().toString());
    }

    @OnClick(R.id.logout_btn)
    void onClick(){
        Log.d(TAG, "logout clicked");
        presenter.onLogout();
    }

    @OnCheckedChanged(R.id.show_pass_check)
    void onClick(boolean isChecked){
        if(isChecked){
            passwordEdit.setTransformationMethod(null);
        }else {
            passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    @OnClick(R.id.reg_btn)
    void onRegClick(){
        navigateToRegFragment();
    }

    @OnClick(R.id.inv_code_btn)
    void  onInvCodeClick(){
        //TODO
    }

    private void navigateToRegFragment(){
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container,new RegFragment())
//                .commit();
    }

    @Override
    public void showProgress() {
        loginBtn.setEnabled(false);
        regBtn.setEnabled(false);
        emailEdit.setEnabled(false);
        passwordEdit.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginBtn.setEnabled(true);
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
    //    setRegisteredUserState(true);
//        Objects.requireNonNull(getFragmentManager()).beginTransaction()
//        .replace(R.id.root, new EventListFragment())
//                .commit();
    }

    @Override
    public void showRegisteredUserState(String userName){
        for(View view : setVisibilityList){
            view.setVisibility(View.GONE);
        }
            loginTxt.setText(userName);
            loginTxt.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotRegisteredUserState() {
        for(View view : setVisibilityList){
            view.setVisibility(View.VISIBLE);
        }
        loginTxt.setVisibility(View.GONE);
        logoutBtn.setVisibility(View.GONE);
    }

}
