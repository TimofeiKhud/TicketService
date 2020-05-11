package com.example.ticketservicenew.presentation.eventlist.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.presentation.eventlist.presenter.BottomSheetPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.timessquare.CalendarPickerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BottomSheetFragment extends BottomSheetDialogFragment implements BottomSheetView {
    static final String EXTRA_TYPE_FILTER = "type";
    static final String EXTRA_DATE_FILTER_START = "first";
    static final String EXTRA_DATE_FILTER_END = "last";

    BottomSheetPresenter presenter;
    private Unbinder unbinder;
    @BindView(R.id.calendar)
    CalendarPickerView datePicker;
    @BindView(R.id.concerts_check)
    CheckBox concertsCheck;
    @BindView(R.id.exhibitions_check)
    CheckBox exhibitionsCheck;
    @BindView(R.id.shows_check)
    CheckBox showsCheck;
    @BindView(R.id.collapse_calendar_btn)
    Button collapseCalendarBtn;
    @BindView(R.id.collapse_categories_btn)
    Button collapseCatBtn;
    @BindView(R.id.types_layout)
    LinearLayout types;
    @BindView(R.id.reset_filters)
    ImageButton resetFiltersBtn;
    @BindView(R.id.close_btn)
    ImageButton closeFilterBtn;

    private boolean isCalendarShown = false;
    private boolean isTypeSelectShown = false;

    private List<Date> selectedDates;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        presenter = new BottomSheetPresenter();
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 2);

        if (savedInstanceState != null) {
            selectedDates.clear();
            Date fromDate = (Date) savedInstanceState.getSerializable(EXTRA_DATE_FILTER_START);
            Date toDate = (Date) savedInstanceState.getSerializable(EXTRA_DATE_FILTER_END);
            if (fromDate != null) {
                selectedDates.add(fromDate);
            }
            if (toDate != null) {
                selectedDates.add(toDate);
            }
        }

        if (selectedDates.isEmpty()) {
            datePicker.init(today, nextYear.getTime())
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withHighlightedDate(today);
        } else {
            datePicker.init(today, nextYear.getTime())
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withHighlightedDate(today)
                    .withSelectedDates(selectedDates);
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (selectedDates == null) {
            selectedDates = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedDates.size() > 0) {
            outState.putSerializable(EXTRA_DATE_FILTER_START, selectedDates.get(0));
            outState.putSerializable(EXTRA_DATE_FILTER_END, selectedDates.get(selectedDates.size() - 1));
        }

    }

    @OnClick(R.id.collapse_calendar_btn)
    void onCollapseCalendarClicked() {
        showCalendar();
    }

    @OnClick(R.id.collapse_categories_btn)
    void onCollapseCatClicked() {
        showTypes();
    }

    @OnClick(R.id.reset_filters)
    void onResetClicked() {
        concertsCheck.setChecked(false);
        showsCheck.setChecked(false);
        exhibitionsCheck.setChecked(false);
        selectedDates.clear();
        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 2);
        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withHighlightedDate(today);
    }

    @OnClick(R.id.close_btn)
    void onClose() {
        this.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog d = super.onCreateDialog(savedInstanceState);
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) d;

        d.setOnShowListener(dialogInterface -> {
            try {
                Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
                behaviorField.setAccessible(true);
                final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
                //behavior.setPeekHeight(requireView().getMeasuredHeight());
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }

                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return d;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        selectedDates.clear(); //clear previous filter
        List<Date> dates = datePicker.getSelectedDates();
        List<Integer> selectedTypes = new ArrayList<>();
        if (concertsCheck.isChecked()) {
            selectedTypes.add(1);
        }
        if (exhibitionsCheck.isChecked()) {
            selectedTypes.add(2);
        }
        if (showsCheck.isChecked()) {
            selectedTypes.add(3);
        }

        sendResult(Activity.RESULT_OK, selectedTypes, dates);
        unbinder.unbind();
    }

    private void sendResult(int resultCode, List<Integer> selectedTypes, List<Date> dates) {
        Intent intent = new Intent();
        if (!selectedTypes.isEmpty()) {
            intent.putIntegerArrayListExtra(EXTRA_TYPE_FILTER, new ArrayList<>(selectedTypes));
        }

        if (!dates.isEmpty()) {
            if (dates.size() > 1) {
                selectedDates.add(dates.get(0));
                selectedDates.add(dates.get(dates.size() - 1));
                intent.putExtra(EXTRA_DATE_FILTER_START, dates.get(0).getTime());
                intent.putExtra(EXTRA_DATE_FILTER_END, dates.get(dates.size() - 1).getTime());
            } else {
                selectedDates.add(dates.get(0));
                intent.putExtra(EXTRA_DATE_FILTER_START, dates.get(0).getTime());
            }
        }
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public void showCalendar() {
        int orientation = getTargetFragment().getActivity().getResources().getConfiguration().orientation;
        isCalendarShown = !isCalendarShown;
        collapseCalendarBtn.setBackground(getResources().getDrawable(isCalendarShown ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp));
        datePicker.setVisibility(isCalendarShown ? View.VISIBLE : View.GONE);
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && isCalendarShown && isTypeSelectShown) {
            isTypeSelectShown = false;
            collapseCatBtn.setBackground(getResources().getDrawable(isTypeSelectShown ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp));
            types.setVisibility(isTypeSelectShown ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showTypes() {
        int orientation = getTargetFragment().getActivity().getResources().getConfiguration().orientation;
        isTypeSelectShown = !isTypeSelectShown;
        collapseCatBtn.setBackground(getResources().getDrawable(isTypeSelectShown ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp));
        types.setVisibility(isTypeSelectShown ? View.VISIBLE : View.GONE);
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && isCalendarShown && isTypeSelectShown) {
            isCalendarShown = false;
            collapseCalendarBtn.setBackground(getResources().getDrawable(isCalendarShown ? R.drawable.ic_keyboard_arrow_up_black_24dp : R.drawable.ic_keyboard_arrow_down_black_24dp));
            datePicker.setVisibility(isCalendarShown ? View.VISIBLE : View.GONE);
        }
    }
}
