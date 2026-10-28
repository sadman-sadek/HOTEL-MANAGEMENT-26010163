import java.util.ArrayList;
import java.util.Scanner;

public class HotelGrand {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        addRooms();
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    checkIn();
                    break;
                case 3:
                    checkOut();
                    break;
                case 4:
                    viewFeatures();
                    break;
                case 5:
                    running = false;
                    System.out.println("\nThank you for visiting Hotel Grand!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
        input.close();
    }

    private static void addRooms() {
        rooms.add(new Room(101, "Normal", 1500));
        rooms.add(new Room(102, "Normal", 1500));
        rooms.add(new Room(103, "Luxury", 3500));
        rooms.add(new Room(104, "Luxury", 3500));
    }

    private static void showMainMenu() {
        System.out.println("\n================================");
        System.out.println("       WELCOME TO HOTEL GRAND");
        System.out.println("================================");
        System.out.println("1. View Rooms");
        System.out.println("2. Check In / Book a Room");
        System.out.println("3. Check Out / Pay Bill");
        System.out.println("4. View Room Features & Prices");
        System.out.println("5. Exit");
        System.out.println("================================");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static void viewRooms() {
        System.out.println("\\n--- ROOMS ---");
        for (Room room : rooms) {
            room.display();
        }
        returnToMenu();
    }

    private static void viewFeatures() {
        System.out.println("\\n--- ROOM FEATURES & PRICES ---");
        System.out.println("Normal Room | Comfortable room, bed, fan/AC | Tk 1,500 per night");
        System.out.println("Luxury Room | Spacious room, premium bed, AC | Tk 3,500 per night");
        System.out.println("\\nRoom prices are charged per night.");
        returnToMenu();
    }

    private static void checkIn() {
        System.out.println("\\n--- CHECK IN / BOOK A ROOM ---");
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("Sorry, all rooms are currently booked.");
            returnToMenu();
            return;
        }

        for (Room room : availableRooms) {
            room.display();
        }

        int roomNumber = readInt("Enter the room number you want to book: ");
        Room selectedRoom = findRoom(roomNumber);

        if (selectedRoom == null || selectedRoom.isBooked()) {
            System.out.println("That room number is invalid or already booked.");
            returnToMenu();
            return;
        }

        String name = readRequiredText("Guest name: ");
        String email = readRequiredText("Email: ");
        String phone = readRequiredText("Phone number: ");
        int nights;
        do {
            nights = readInt("Number of nights to stay: ");
            if (nights <= 0) {
                System.out.println("Please enter at least 1 night.");
            }
        } while (nights <= 0);

        Guest guest = new Guest(name, email, phone);
        Booking booking = new Booking(guest, selectedRoom, nights);
        bookings.add(booking);
        selectedRoom.setBooked(true);

        System.out.println("\\nBooking successful!");
        System.out.println("Guest: " + guest.getName());
        System.out.println("Room: " + selectedRoom.getRoomNumber() + " (" + selectedRoom.getType() + ")");
        System.out.println("Stay: " + nights + " night(s)");
        System.out.printf("Estimated bill: Tk %.2f%n", booking.getTotalBill());
        returnToMenu();
    }

    private static void checkOut() {
        System.out.println("\\n--- CHECK OUT / PAY BILL ---");
        ArrayList<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoom().isBooked()) {
                activeBookings.add(booking);
            }
        }

        if (activeBookings.isEmpty()) {
            System.out.println("There are no active bookings to check out.");
            returnToMenu();
            return;
        }

        for (Booking booking : activeBookings) {
            System.out.printf("Room %d | Guest: %s | Total: Tk %.2f%n",
                    booking.getRoom().getRoomNumber(),
                    booking.getGuest().getName(),
                    booking.getTotalBill());
        }

        int roomNumber = readInt("Enter the room number to check out: ");
        Booking selectedBooking = findBooking(roomNumber);

        if (selectedBooking == null) {
            System.out.println("No active booking found for that room.");
            returnToMenu();
            return;
        }

        double total = selectedBooking.getTotalBill();
        System.out.printf("Amount to pay: Tk %.2f%n", total);

        double payment;
        do {
            payment = readDouble("Enter payment amount (Tk): ");
            if (payment < total) {
                System.out.printf("Insufficient payment. You still owe Tk %.2f%n", total - payment);
            }
        } while (payment < total);

        if (payment > total) {
            System.out.printf("Change: Tk %.2f%n", payment - total);
        }

        selectedBooking.getRoom().setBooked(false);
        bookings.remove(selectedBooking);
        System.out.println("Payment successful. Check-out complete. Thank you!");
        returnToMenu();
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            try {
                double amount = Double.parseDouble(value);
                if (amount < 0) {
                    System.out.println("Amount cannot be negative.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static Booking findBooking(int roomNumber) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber) {
                return booking;
            }
        }
        return null;
    }

    private static void returnToMenu() {
        System.out.println("\\n0. Return to Main Menu");
        while (true) {
            int choice = readInt("Enter 0 to return: ");
            if (choice == 0) {
                return;
            }
            System.out.println("Please enter 0 to return to the main menu.");
        }
    }
}
