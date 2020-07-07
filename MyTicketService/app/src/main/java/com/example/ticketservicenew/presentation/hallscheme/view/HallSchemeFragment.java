package com.example.ticketservicenew.presentation.hallscheme.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ticketservicenew.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HallSchemeFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.big_hall_img)
    ImageView bigHallImg;
    @BindView(R.id.small_hall_img)
    ImageView smallHallImg;

    public HallSchemeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hall_scheme, container, false);
        unbinder = ButterKnife.bind(this, v);
        Picasso.get()
                .load("https://res.cloudinary.com/timofiekhudiakov/image/upload/v1590492027/ticket_project/big_hall_iepaxi.png")
                .fit()
                .into(bigHallImg);
        Picasso.get()
                .load("https://res.cloudinary.com/timofiekhudiakov/image/upload/v1590488462/ticket_project/small_hall_oheaj6.png")
                .fit()
                .into(smallHallImg);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
