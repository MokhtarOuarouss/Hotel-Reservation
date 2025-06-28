package skypay.test;

import skypay.test.Entity.RoomType;
import skypay.test.service.Service;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {

        Service service = new Service();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.MASTER, 3000);

        service.setUser(1, 5000);
        service.setUser(2, 10000);

        try {
            System.out.println("\nTest 1: User 1 books Room 2 (7 nights) have not enough balance");
            service.bookRoom(1, 2, sdf.parse("30/06/2026"), sdf.parse("07/07/2026"));
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            System.out.println("\nTest 2: User 1 tries invalid dates");
            service.bookRoom(1, 2, sdf.parse("07/07/2026"), sdf.parse("30/06/2026"));
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            System.out.println("\nTest 3: User 1 books Room 1 (1 night)");
            service.bookRoom(1, 1, sdf.parse("07/07/2026"), sdf.parse("08/07/2026"));
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            System.out.println("\nTest 4: User 2 books Room 1 (2 nights) room does not available");
            service.bookRoom(2, 1, sdf.parse("07/07/2026"), sdf.parse("09/07/2026"));
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            System.out.println("\nTest 5: User 2 books Room 3 (1 night)");
            service.bookRoom(2, 3, sdf.parse("07/07/2026"), sdf.parse("10/07/2026"));
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        service.setRoom(1, RoomType.MASTER, 10000);

        System.out.println("\n=== Final State ===");
        service.printAll();
        System.out.println();
        service.printAllUsers();

    }
}