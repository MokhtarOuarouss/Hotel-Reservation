package skypay.test.service;

import skypay.test.Entity.Booking;
import skypay.test.Entity.Room;
import skypay.test.Entity.RoomType;
import skypay.test.Entity.User;
import skypay.test.exception.InsufficientBalanceException;
import skypay.test.exception.UserExistenceException;
import skypay.test.exception.RoomAvailabilityException;

import java.util.ArrayList;
import java.util.Date;

public class Service implements ServiceImpl{
    private ArrayList<Room> rooms;
    private ArrayList<User> users;

    public Service() {
        rooms = new ArrayList<>();
        users = new ArrayList<>();
    }

    @Override
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try{
            if (roomTypePriceExists(roomType, roomPricePerNight)) {
                throw new RoomAvailabilityException("Error: a room of type "
                        + roomType + " at price " + roomPricePerNight
                        + " already exists.");
            }
            else if(roomExists(roomNumber)) {
                Room room = getRoom(roomNumber);
                room.setRoomType(roomType);
                room.setPricePerNight(roomPricePerNight);
            }
            else rooms.add(new Room(roomNumber, roomType, roomPricePerNight));

        }catch (RoomAvailabilityException e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void setUser(int userId, int balance) {
        try{
            if (userExists(userId)) {
                throw new UserExistenceException("User already exists");
            }
            users.add(new User(userId, balance));
        }catch (UserExistenceException e){
            System.out.println("Error: " + e.getMessage());
        }


    }

    @Override
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        try {
            User user = getUser(userId);
            Room room = getRoom(roomNumber);

            if (checkIn == null || checkOut == null) {
                throw new IllegalArgumentException("Dates must not be null");
            }
            if (!checkIn.before(checkOut)) {
                throw new IllegalArgumentException("Check-in date must be before check-out date");
            }

            if (room.isAvailable(checkIn, checkOut)) {
                Booking booking = new Booking(user, room, checkIn, checkOut);
                int bookingPrice = booking.getBookingPrice();
                user.deductBalance(bookingPrice);
                room.addBooking(booking);
            }

        } catch (UserExistenceException | RoomAvailabilityException | InsufficientBalanceException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void printAll() {
        for (Room room : rooms) {
            System.out.println("Room Name: " + room.getRoomNumber() + ", Type: " + room.getRoomType() + ", Price per Night: " + room.getPricePerNight());
            for (Booking booking : room.getBookings()) {
                System.out.println("\t"+booking);
            }
        }
    }

    @Override
    public void printAllUsers() {
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", Balance: " + user.getBalance());
        }
    }

    private boolean roomTypePriceExists(RoomType type, int price) {
        return rooms.stream().anyMatch(r -> r.getRoomType() == type && r.getPricePerNight() == price);
    }

    private boolean roomExists(int roomNumber) {
        return rooms.stream().anyMatch(room -> room.getRoomNumber() == roomNumber);
    }

    private boolean userExists(int userId) {
        return users.stream().anyMatch(user -> user.getUserId() == userId);
    }

    private Room getRoom(int roomNumber) throws RoomAvailabilityException {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new RoomAvailabilityException("Room with ID " + roomNumber + " does not exist."));
    }

    private User getUser(int userId) throws UserExistenceException {
        return users.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserExistenceException("User with ID " + userId + " does not exist."));
    }

}
