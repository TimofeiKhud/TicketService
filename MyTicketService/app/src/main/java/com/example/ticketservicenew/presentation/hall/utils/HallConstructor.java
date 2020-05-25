package com.example.ticketservicenew.presentation.hall.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.HallStructure;
import com.example.ticketservicenew.business.model.LockedSeats;
import com.example.ticketservicenew.business.model.Price;

import java.util.List;

public class HallConstructor {
    private static final String TAG = "HallConstructor";
    Context context;
    private ColorStateList stateList;
    private int[][] statesOfButton;
    private int[] colorsStroke;


    public HallConstructor(Context context) {
        this.context = context;
        statesOfButton = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked},
                new int[]{-android.R.attr.state_enabled},
        };
        colorsStroke = new int[]{
                context.getResources().getColor(R.color.blue_color),
                context.getResources().getColor(R.color.orange_color),
                context.getResources().getColor(R.color.light_grey_color),
        };
        stateList = new ColorStateList(statesOfButton, colorsStroke);
    }

    public LinearLayout createBigHallView(HallStructure hallInfo, CompoundButton.OnCheckedChangeListener listener, List<String> checked) {
        List<Price> ranges = hallInfo.getPriceList();
        List<LockedSeats> lockedSeats = hallInfo.getLockedSeats();
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        TableLayout right = new TableLayout(context);
        TableLayout center = new TableLayout(context);
        TableLayout left = new TableLayout(context);

        int[][] seatsPerSection = {
                {3, 15, 3},
                {4, 16, 4},
                {5, 17, 5},
                {6, 18, 6},
                {7, 19, 7},
                {8, 20, 8},
                {9, 21, 9},
                {10, 22, 10},
                {10, 23, 10},
                {9, 22, 9},
                {8, 21, 8},
                {7, 20, 7},
                {6, 21, 6},
                {5, 22, 5},
                {0, 22, 0}
        };

        String[][] halls = {
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "19L", "20L", "19R", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "19L", "20L", "21L", "21R", "20R", "19R", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "19L", "20L", "21L", "22L", "21R", "20R", "19R", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "19L", "20L", "20R", "19R", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "18L", "19L", "18R", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "17R", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "17L", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "14L", "15L", "16L", "16R", "15R", "14R", "13R", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"}
        };

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = getSceneTxt();
        center.addView(textView);

        for (int i = 0; i < halls.length; i++) {
            TableRow leftRow = new TableRow(context);
            TableRow centerRow = new TableRow(context);
            TableRow rightRow = new TableRow(context);

            leftRow.setPadding(3, 2, 3, 2);
            centerRow.setPadding(3, 2, 3, 2);
            rightRow.setPadding(3, 2, 3, 2);
            centerRow.setGravity(Gravity.CENTER_HORIZONTAL);
            if (i > 7) {
                rightRow.setGravity(Gravity.START);
                leftRow.setGravity(Gravity.END);
            } else {
                rightRow.setGravity(Gravity.CENTER_HORIZONTAL);
                leftRow.setGravity(Gravity.CENTER_HORIZONTAL);
            }

            Pair<Integer, Double> rowInfo = findColorAndPrice(i + 1, ranges);

            int color = rowInfo.first;
            GradientDrawable btnShape = getBtnShape(color);
            List<String> lockedInRow = getLockedSeatsInRow(lockedSeats, i);
            for (int j = 0; j < halls[i].length; j++) {
                CompoundButton btn = new CompoundButton(context, null, R.style.Widget_AppCompat_Button_Small, R.style.seat_button) {
                };

                if (lockedInRow != null && lockedInRow.contains(halls[i][j])) {
                    btn.setEnabled(false);

                } else {
                    btn.setText(halls[i][j].substring(0, halls[i][j].length() - 1));
                }
                btn.setBackground(btnShape);
                btn.setTextColor(stateList);
                //rowNum-seatNumWithLetter-seatPrice-seatNum
                String tag = (i + 1) + "-" + halls[i][j] + "-" + rowInfo.second + "-" + (j + 1);
                btn.setTag(tag);
                if (checked != null && checked.contains(tag)) {
                    btn.setChecked(true);
                }
                btn.setOnCheckedChangeListener(listener);
                if (j < seatsPerSection[i][0]) {
                    leftRow.addView(btn);
                } else if (j < seatsPerSection[i][0] + seatsPerSection[i][1]) {
                    centerRow.addView(btn);
                } else {
                    rightRow.addView(btn);
                }
            }
            left.addView(leftRow);
            center.addView(centerRow);
            right.addView(rightRow);
        }
        left.setRotation(35);
        right.setRotation(-35);
        container.addView(left);
        container.addView(center);
        container.addView(right);
        return container;
    }
    //arguments: hallInfo(sold seats and price list), listener for click on seat, list of currently booked seats
    public TableLayout createSmallHallView(HallStructure hallInfo, CompoundButton.OnCheckedChangeListener listener, List<String> checked) {
        List<Price> priceList = hallInfo.getPriceList();
        List<LockedSeats> lockedSeats = hallInfo.getLockedSeats();

        String[][] hallScheme = {
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
                {"1L", "2L", "3L", "4L", "5L", "6L", "7L", "8L", "9L", "10L", "11L", "12L", "13L", "12R", "11R", "10R", "9R", "8R", "7R", "6R", "5R", "4R", "3R", "2R", "1R"},
        };
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = getSceneTxt();
        tableLayout.addView(textView);

        for (int i = 0; i < hallScheme.length; i++) {
            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            tableRow.setPadding(3, 2, 3, 2);
            Pair<Integer, Double> rowInfo = findColorAndPrice(i + 1, priceList);

            int color = rowInfo.first;
            GradientDrawable btnShape = getBtnShape(color);
            List<String> lockedInRow = getLockedSeatsInRow(lockedSeats, i);
            for (int j = 0; j < hallScheme[i].length; j++) {
                CompoundButton btn = new CompoundButton(context, null, R.style.Widget_AppCompat_Button_Small, R.style.seat_button) {
                };
                if (lockedInRow != null && lockedInRow.contains(hallScheme[i][j])) {
                    btn.setEnabled(false);
                } else {
                    btn.setText(hallScheme[i][j].substring(0, hallScheme[i][j].length() - 1));
                }
                btn.setTextColor(stateList);
                btn.setBackground(btnShape);

                //rowNum-seatNumWithLetter-seatPrice-seatNum
                String tag = (i + 1) + "-" + hallScheme[i][j] + "-" + rowInfo.second + "-" + (j + 1);
                btn.setTag(tag);
                if (checked != null && checked.contains(tag)) {
                    btn.setChecked(true);
                }
                btn.setOnCheckedChangeListener(listener);
                tableRow.addView(btn);
            }
            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }

    private List<String> getLockedSeatsInRow(List<LockedSeats> lockedSeats, int i) {
        for (LockedSeats s : lockedSeats
        ) {
            if (Integer.parseInt(s.getRow()) == i + 1) {
                return s.getSeats();
            }
        }
        return null;
    }

    private Pair<Integer, Double> findColorAndPrice(int row, List<Price> prices) {
        for (Price priceRange : prices) {
            if (priceRange.getRows().contains(String.valueOf(row))) {
                return new Pair<>(Color.parseColor(priceRange.getColor()), priceRange.getPrice());
            }
        }
        return new Pair<Integer, Double>(Color.GRAY, 0.00);
    }

    private GradientDrawable getBtnShape(int color) {
        GradientDrawable defImg = new GradientDrawable();
        defImg.setShape(GradientDrawable.RECTANGLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defImg.setStroke(2, stateList, 1.0f, 0.0f);
        } else {
            defImg.setStroke(2, colorsStroke[0]);
        }
        defImg.setColor(color);
        return defImg;
    }

    private TextView getSceneTxt() {
        TextView textView = new TextView(context);
        textView.setBackgroundColor(context.getResources().getColor(R.color.blue_color));
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);

        textView.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        //  textView.setPadding(5,3,5,3);
        textView.setText("Scene");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        return textView;
    }
}
