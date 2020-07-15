package com.example.ticketservicenew.data.event;

import android.util.Log;

import com.example.ticketservicenew.business.model.Event;
import com.example.ticketservicenew.business.model.EventInfo;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.dto.objectsForDto.RestTicketsDto;
import com.example.ticketservicenew.data.provider.web.Api;

import java.io.IOException;

import io.reactivex.Single;
import retrofit2.Response;

public class EventRepositoryImpl implements EventRepository{
    private Api api;
    private String eventId;
    private int hallId;

    public EventRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public Single<Event> getEvent(String id) {
        return api.getEvent(id).flatMap(this::onGetEventSuccess).map(this::mapEventDtoToModel);
    }

    @Override
    public Single<EventInfo> getEventInfo() {
        return api.getEventInfo(eventId).flatMap(this::onGetEventInfoSuccess).map(this::mapEventInfoDtoToModel);
    }

    @Override
    public void saveEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public void saveHallId(int hall) {
        this.hallId = hall;
    }

    @Override
    public int getHallId() {
        return hallId;
    }

    private Single<EventOutputDto> onGetEventSuccess(Response<EventOutputDto> response) throws IOException {
            if (response.isSuccessful()){
                EventOutputDto event = response.body();
                return Single.just(event);
            }else {
                throw new RuntimeException("Server error! Call to support");
            }
    }

    private Event mapEventDtoToModel(EventOutputDto dto){
        return new Event(dto.getEventId(),
                dto.getEventStatus(),
                dto.getEventName(),
                dto.getArtist(),
                dto.getEventStart(),
                dto.getEventDurationHours(),
                dto.getHall(),
                dto.getEventType(),
                dto.getDescription(),
                dto.getImages(),
                dto.getPriceRanges().toString(),
                dto.getManagers().toString());
    }

    private Single<RestTicketsDto> onGetEventInfoSuccess(Response<RestTicketsDto> response) throws IOException {
        if (response.isSuccessful()){
            RestTicketsDto eventInfo = response.body();
            return Single.just(eventInfo);
        }else {
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private EventInfo mapEventInfoDtoToModel(RestTicketsDto dto){
        return new EventInfo(dto.eventId, dto.maxPrice, dto.minPrice, dto.restTick);
    }
}
