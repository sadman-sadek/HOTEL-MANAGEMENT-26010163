public class Booking {
    private Guest guest;
    private Room room;
    private int nights;

    public Booking(Guest guest, Room room, int nights) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalBill() {
        return room.getPricePerNight() * nights;
    }
}
