package skypay.test.Entity;

import skypay.test.exception.RoomAvailabilityException;

import java.util.ArrayList;
import java.util.Date;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int pricePerNight;
    private ArrayList<Booking> bookings;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.bookings = new ArrayList<>();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public boolean isAvailable(Date checkIn, Date checkOut) throws RoomAvailabilityException {
        for (Booking booking : bookings) {
            if (booking.isConflicting(checkIn, checkOut)) {
                throw new RoomAvailabilityException("Room with number " + roomNumber + " is not available between " + checkIn + " and " + checkOut);
            }
        }
        return true;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
