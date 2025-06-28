package skypay.test.service;

import skypay.test.Entity.RoomType;

import java.util.Date;

public interface ServiceImpl {

    void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight);
    void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut);
    void printAll();
    void setUser(int userId, int balance);
    void printAllUsers();
}
