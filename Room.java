public class Room {
    private int roomNumber;
    private String type;
    private double pricePerNight;
    private boolean booked;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public void display() {
        System.out.printf("Room %d | %-12s | Tk %.2f per night | %s%n",
                roomNumber, type, pricePerNight, booked ? "Booked" : "Available");
    }
}
