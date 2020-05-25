package com.example.ticketservicenew.presentation.hall.adapter;

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

public class SelectedSeatsAdapter extends RecyclerView.Adapter<SelectedSeatsAdapter.SeatsHolder> {
List<Seat> seats;

    public SelectedSeatsAdapter() {
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


    public void addSeat(Seat seat){
        seats.add(seat);
        notifyDataSetChanged();
    }

    public void removeSeat(Seat seat) {
        int position = 0;
        for (Seat s : seats){
            if(s.getRow().equals(seat.getRow()) && s.getSeatNum().equals(seat.getSeatNum())){
                seats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, seats.size());
                break;
            }
            position++;
        }
        notifyDataSetChanged();
    }

    public List<Seat> getSeats(){
        return seats;
    }

}
