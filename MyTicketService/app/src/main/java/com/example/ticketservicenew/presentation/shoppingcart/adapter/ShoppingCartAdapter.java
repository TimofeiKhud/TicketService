package com.example.ticketservicenew.presentation.shoppingcart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.SeatsHolder> {
List<Seat> seats;

    public ShoppingCartAdapter() {
        seats = new ArrayList<>();
    }

    class SeatsHolder extends RecyclerView.ViewHolder{
        TextView rowTxt;
        TextView seatTxt;

        public SeatsHolder(@NonNull View itemView) {
            super(itemView);
            rowTxt = itemView.findViewById(R.id.row_txt);
            seatTxt = itemView.findViewById(R.id.seat_txt);
        }
    }

    @NonNull
    @Override
    public SeatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_row, parent, false);
        return new SeatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatsHolder holder, int position) {
        holder.seatTxt.setText(seats.get(position).getSeatNum());
        holder.rowTxt.setText(seats.get(position).getRow());
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }


    public void addSeats(List<Seat> seats){
        this.seats.addAll(seats);
        notifyDataSetChanged();
    }

    public List<Seat> getSeats(){
        return seats;
    }

}
