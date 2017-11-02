package com.group.ibrochure.i_brochure.UI.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group.ibrochure.i_brochure.R;

/**
 * Created by KinKin on 10/29/2017.
 */

public class RegisterForm extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_form, container, false);
        return rootView;
    }
}
