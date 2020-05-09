package com.example.ticketservicenew.data.provider.web;

import com.example.ticketservicenew.data.dto.EventBookingDto;
import com.example.ticketservicenew.data.dto.EventDateHallDto;
import com.example.ticketservicenew.data.dto.HallStructureDto;
import com.example.ticketservicenew.data.dto.LoginInDto;
import com.example.ticketservicenew.data.dto.LoginOutDto;
import com.example.ticketservicenew.data.dto.StatusDto;
import com.example.ticketservicenew.data.dto.RegUserDto;
import com.example.ticketservicenew.data.dto.admin.EventOutputDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsDto;
import com.example.ticketservicenew.data.dto.objectsForDto.LockedSeatsRequestDto;
import com.example.ticketservicenew.data.dto.objectsForDto.RestTicketsDto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("/user")
    Single<Response<StatusDto>> registration(@Body RegUserDto body);

    @POST("/login")
    Single<Response<LoginOutDto>> login(@Body LoginInDto body);

    @GET("/events/{page}/{page_size}")
    Single<Response<List<EventOutputDto>>> getCurrentEvents(@Path("page") int page, @Path("page_size") int pageSize);

    @POST("/events/bydate/{page}/{page_size}")
    Single<Response<List<EventOutputDto>>> getByDates(@Path("page") int page, @Path("page_size") int pageSize, @Body EventDateHallDto dateHallDto);

    @GET("/event/{eventId}")
    Single<Response<EventOutputDto>> getEvent(@Path("eventId") String id);

    @GET("/events/rest/{eventId}")
    Single<Response<RestTicketsDto>> getEventInfo(@Path("eventId") String id);

    @GET("/event/{eventId}/{isShort}")
    Single<Response<HallStructureDto>> getHallStructure(@Path("eventId") String id, @Path("isShort") boolean isShort);

    @POST("/event/book")
    Single<Response<Void>> bookSeats(@Body EventBookingDto body);

    //@FormUrlEncoded
    @HTTP(method = "DELETE", path = "/event/book/", hasBody = true)
    Single<Response<Void>> cancelBooking(@Body EventBookingDto body/*@Field("body") EventBookingDto body*/);

    @POST("/user/to_sell")
    Single<Response<StatusDto>> sellTickets(@Body EventBookingDto body);

}


