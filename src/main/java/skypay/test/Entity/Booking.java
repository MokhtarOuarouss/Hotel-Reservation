package skypay.test.Entity;

import java.util.Date;

public class Booking {
    private User user;
    private Room room;
    private Date checkIn;
    private Date checkOut;

    public Booking(User user, Room room, Date checkIn, Date checkOut) {
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public boolean isConflicting(Date checkIn, Date checkOut) {
        return !(this.checkOut.before(checkIn) || this.checkIn.after(checkOut));
    }

    public int getBookingPrice() {
        long differenceInMilliseconds = checkOut.getTime() - checkIn.getTime();
        int days = (int) differenceInMilliseconds / (24 * 60 * 60 * 1000);

        return days * room.getPricePerNight();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "user=" + user.getUserId() +
                ", roomName=" + room.getRoomNumber() +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", price=" + getBookingPrice() +
                '}';
    }
}
