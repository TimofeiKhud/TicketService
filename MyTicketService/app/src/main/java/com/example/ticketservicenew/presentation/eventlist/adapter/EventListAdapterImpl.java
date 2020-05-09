package com.example.ticketservicenew.presentation.eventlist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketservicenew.R;
import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.presentation.eventlist.OnEventListAdapterListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventListAdapterImpl extends PagedListAdapter<Event, EventListAdapterImpl.EventHolder> /*implements Filterable*/ {
    public static final String TAG = "Event list adapter";
    private OnEventListAdapterListener listener;

//    private Filter filterByArtist;
    public EventListAdapterImpl(@NonNull DiffUtil.ItemCallback<Event> diffCallback, OnEventListAdapterListener listener) {
        super(diffCallback);
        this.listener = listener;
//        filterByArtist = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//                if(constraint == null || constraint.length() == 0){
//                    results.values = getCurrentList();
//                    return results;
//                }else{
//                    List<Event> filteredList = new ArrayList<>();
//                    String filterPattern = constraint.toString().toLowerCase().trim();
//                    for(Event event : getCurrentList()){
//                        if(event.getArtist() != null && event.getArtist().toLowerCase().startsWith(filterPattern)){
//                            filteredList.add(event);
//                        }
//                    }
//
//                    results.values = filteredList;
//                    return results;
//                }
//            }

//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if(results == null){
//                    return;
//                }
//                submitList((PagedList)results.values);
//                Log.d(TAG, "Filter(publish results)");
//                //notifyDataSetChanged();
//            }
//        };
    }

//    @Override
//    public Filter getFilter() {
//        return filterByArtist;
//    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView eventTitleTxt;
        private TextView eventNameTxt;
        private TextView eventDateTxt;
        private ImageView eventImg;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventImg = itemView.findViewById(R.id.event_img);
            eventTitleTxt = itemView.findViewById(R.id.event_title_txt);
            eventNameTxt = itemView.findViewById(R.id.event_name_txt);
            eventDateTxt = itemView.findViewById(R.id.event_date_txt);
        }
    }

    @Override
    @NonNull
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        //Event current = allEvents.get(position);
        //Log.d(TAG, "on bind view holder, position: " + position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Event current = getItem(position);
        holder.eventTitleTxt.setText(current.getArtist());
        holder.eventNameTxt.setText(current.getEventName());
        holder.eventDateTxt.setText(dateFormat.format(current.getEventStart()));
        int images = current.getImages().size();
        if (images > 0) {
            Picasso.get().load(current.getImages().get(0)).fit().into(holder.eventImg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showEvent(current);
            }
        });
    }

//    @Override
//    public Filter getFilter() {
//        return artistFilter;
//    }
//
//    private Filter artistFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Event> filteredList = new ArrayList<>();
//            FilterResults results = new FilterResults();
//
//            if(getCurrentList() == null){
//                results.values = filteredList;
//                return results;
//            }
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(getCurrentList());
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (Event event : getCurrentList()) {
//                    if (event.getArtist() != null && event.getArtist().toLowerCase().startsWith(filterPattern)) {
//                        filteredList.add(event);
//                    }
//                }
//            }
//            return results;
//        }

//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//        }
//    };
}
















//
//
//
//
//private List<Event> allEvents;
//private List<Event> events;
//private boolean eventListLoadingComplete;
//private OnEventListAdapterListener listener;
//
//    public EventListAdapterImpl(OnEventListAdapterListener listener) {
//        allEvents = new ArrayList<>();
//        events = new ArrayList<>();
//        this.listener = listener;
//    }
//
//    class EventHolder extends RecyclerView.ViewHolder{
//    private TextView eventTitleTxt;
//    private TextView eventNameTxt;
//    private TextView eventDateTxt;
//    private ImageView eventImg;
//
//    public EventHolder(@NonNull View itemView) {
//        super(itemView);
//        eventImg = itemView.findViewById(R.id.event_img);
//        eventTitleTxt = itemView.findViewById(R.id.event_title_txt);
//        eventNameTxt = itemView.findViewById(R.id.event_name_txt);
//        eventDateTxt = itemView.findViewById(R.id.event_date_txt);
//    }
//}
//
//    @Override
//    @NonNull
//    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.event_row, parent, false);
//        return new EventHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(EventHolder holder, int position) {
//        //Event current = allEvents.get(position);
//        //Log.d(TAG, "on bind view holder, position: " + position);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
//        Event current = events.get(position);
//        holder.eventTitleTxt.setText(current.getArtist());
//        holder.eventNameTxt.setText(current.getEventName());
//        holder.eventDateTxt.setText(dateFormat.format(current.getEventStart()));
//        int images = current.getImages().size();
//        if(images > 0) {
//            Picasso.get().load(current.getImages().get(0)).fit().into(holder.eventImg);
//            //(int)(Math.random() * (images - 1))
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.showEvent(current);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (events != null)
//            return events.size();
//        else return 0;
//    }
//
//    public boolean isEventListLoadingComplete() {
//        return eventListLoadingComplete;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return artistFilter;
//    }
//
//    private Filter artistFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Event> filteredList = new ArrayList<>();
//            if(constraint == null || constraint.length() == 0){
//                filteredList.addAll(allEvents);
//            }else{
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for(Event event : allEvents){
//                    if(event.getArtist() != null && event.getArtist().toLowerCase().startsWith(filterPattern)){
//                        filteredList.add(event);
//                    }
//                }
//            }
////            for (int i = 0; i < filteredList.size(); i++) {
////                Log.d(TAG, "perform filtering(filtered list: " + filteredList.get(i));
////            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            events.clear();
////            Log.d(TAG, "publich results(filtered list): " + results.values);
////            List<Event> list = (List)results.values;
////            Log.d(TAG, "filtered list: " + list.toString());
//            events.addAll((List)results.values);
//            Log.d(TAG, "Filter(publish results)");
//            notifyDataSetChanged();
//        }
//    };
//
//    public void showEvents(List<Event> events){
//        this.events = events;
//        notifyDataSetChanged();
//    }
//
//    public void setEvents(List<Event> events){
//        this.events.clear();
//        this.events.addAll(events);
//        allEvents.clear();
//        allEvents.addAll(events);
//        //notifyDataSetChanged();
//    }
//
//    public List<Event> getAllEvents() {
//        return allEvents;
//    }
////
////    public void setAndShowAllEvents(List<Event> allEvents) {
////        this.allEvents = allEvents;
////        events = new ArrayList<>(allEvents);
////        notifyDataSetChanged();
////    }
//
//    public void addEvents(List<Event> events) {
//
//        if (events.size() == 0) {
//            eventListLoadingComplete = true;
//            return;
//        }
//        allEvents.addAll(events);
//        this.events.addAll(events);
//        //notifyDataSetChanged();
//        int i = 0;
//        for(Event event : events){
//            Log.d(TAG, "add events: event no. " + i++ + ": " + event.toString());
//        }
//        int j = 0;
//        for(Event event : this.events){
//            Log.d(TAG, "cuur events: event no. " + j++ + ": " + event.toString());
//        }
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public Event getEvent(int position) {
//        //return allEvents.get(position);
//        return events.get(position);
//    }

