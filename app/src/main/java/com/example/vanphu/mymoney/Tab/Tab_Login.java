package com.example.vanphu.mymoney.Tab;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.vanphu.mymoney.Controller.LoginController;
import com.example.vanphu.mymoney.R;

public class Tab_Login extends Fragment {
    @SuppressLint("StaticFieldLeak")
    public static TextInputEditText sEdit_User;
    @SuppressLint("StaticFieldLeak")
    public static TextInputEditText sEdit_Password;
    @SuppressLint("StaticFieldLeak")
    public static CheckBox sCb_Remember;
    @SuppressLint("StaticFieldLeak")
    public static Button sBtn_Login;
    private LoginController mLoginController;
    private String mURL = "https://vanphudhsp2015.000webhostapp.com/user_control.php";

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_login, container, false);
//        call Init
        init(rootView);
//        call class login
        mLoginController = new LoginController(inflater.getContext());
        mLoginController.login(mURL);
        return rootView;
    }

    public void init(View rootView) {
        sEdit_User = rootView.findViewById(R.id.edit_User);
        sEdit_Password = rootView.findViewById(R.id.edit_Password);
        sBtn_Login = rootView.findViewById(R.id.btn_Login);
        sCb_Remember = rootView.findViewById(R.id.cb_Remember);
    }
}
