package com.hotelerp.hotelmaster.constants;

public class ServiceConstants {

    // ── Guest ──────────────────────────────────────────────────────────────
    public static final String GUEST_BASE_URL = "/api/masterService/v1/guests";
    public static final String CREATE_GUEST = "/createGuest";
    public static final String UPDATE_GUEST = "/updateGuest/{id}";
    public static final String GET_GUEST_BY_ID = "/getGuestById/{id}";
    public static final String GET_ALL_GUESTS = "/getAllGuests";
    public static final String DELETE_GUEST = "/deleteGuest/{id}";

    // ── Reservation ────────────────────────────────────────────────────────
    public static final String RESERVATION_BASE_URL = "/api/v1/frontOffice";
    public static final String CREATE_RESERVATION = "/createReservation";
    public static final String GET_RESERVATION_BY_ID = "/getReservationById/{id}";
    public static final String GET_ALL_RESERVATIONS = "/getAllReservations";
    public static final String GET_RESERVATIONS_BY_GUEST = "/getByGuest/{guestId}";
    public static final String CANCEL_RESERVATION = "/cancelReservation/{id}";
    public static final String DELETE_RESERVATION = "/deleteReservation/{id}";
    public static final String GET_ARRIVALS = "/arrivals";
    public static final String GET_CHECKIN_DETAILS = "/checkin-details/{bookingId}";
    public static final String COMPLETE_CHECKIN = "/checkin";
    public static final String GET_FOLIO = "/folio/{bookingId}";
    public static final String COMPLETE_CHECKOUT = "/checkout";

    // ── Hotel ──────────────────────────────────────────────────────────────
    public static final String HOTEL_BASE_URL = "/api/masterService/v1/hotels";
    public static final String CREATE_HOTEL = "/createHotel";
    public static final String UPDATE_HOTEL = "/updateHotel/{id}";
    public static final String GET_HOTEL_BY_ID = "/getHotelById/{id}";
    public static final String GET_ALL_HOTELS = "/getAllHotels";
    public static final String DELETE_HOTEL = "/deleteHotel/{id}";

    // ── Floor ──────────────────────────────────────────────────────────────
    public static final String FLOOR_BASE_URL = "/api/masterService/v1/floors";
    public static final String CREATE_FLOOR = "/createFloor";
    public static final String UPDATE_FLOOR = "/updateFloor/{id}";
    public static final String GET_FLOOR_BY_ID = "/getFloorById/{id}";
    public static final String GET_ALL_FLOORS = "/getAllFloors";
    public static final String DELETE_FLOOR = "/deleteFloor/{id}";

    // ── Room Type ──────────────────────────────────────────────────────────────
    public static final String ROOM_TYPE_BASE_URL = "/api/masterService/v1/roomTypes";
    public static final String CREATE_ROOM_TYPE = "/createRoomType";
    public static final String UPDATE_ROOM_TYPE = "/updateRoomType/{id}";
    public static final String GET_ROOM_TYPE_BY_ID = "/getRoomTypeById/{id}";
    public static final String GET_ALL_ROOM_TYPES = "/getAllRoomTypes";
    public static final String DELETE_ROOM_TYPE = "/deleteRoomType/{id}";

    // ── Room ──────────────────────────────────────────────────────────────
    public static final String ROOM_BASE_URL = "/api/masterService/v1/rooms";
    public static final String GET_AVAILABLE_ROOMS = "/available";
    public static final String CREATE_ROOM = "/createRoom";
    public static final String UPDATE_ROOM = "/updateRoom/{id}";
    public static final String GET_ROOM_BY_ID = "/getRoomById/{id}";
    public static final String GET_ALL_ROOMS = "/getAllRooms";
    public static final String DELETE_ROOM = "/deleteRoom/{id}";

    // ── Rate Plan ──────────────────────────────────────────────────────────────
    public static final String RATE_PLAN_BASE_URL = "/api/masterService/v1/ratePlans";
    public static final String CREATE_RATE_PLAN = "/createRatePlan";
    public static final String UPDATE_RATE_PLAN = "/updateRatePlan/{id}";
    public static final String GET_RATE_PLAN_BY_ID = "/getRatePlanById/{id}";
    public static final String GET_ALL_RATE_PLANS = "/getAllRatePlans";
    public static final String DELETE_RATE_PLAN = "/deleteRatePlan/{id}";

    // Legacy alias
    public static final String BASE_URL = GUEST_BASE_URL;
}
