public class Bill {
    private Booking booking;
    private Room room;
    public Bill(Booking booking, Room room){this.booking=booking;this.room=room;}
    public double getRoomSubtotal(){return booking.getNights()*room.getRate();}
    public double getServices(){return booking.getServiceCharges();}
    public double getTotal(){return getRoomSubtotal()+getServices();}
    public String createReceipt(){
        return "========= GRANDSTAY HOTEL =========\n"+
        "Receipt / Booking #"+booking.getId()+"\nGuest: "+booking.getGuest().getName()+"\nPhone: "+booking.getGuest().getPhone()+
        "\nRoom: "+room.getNumber()+" ("+room.getType()+")\nNights: "+booking.getNights()+
        "\nRate per night: ৳"+room.getRate()+"\nRoom subtotal: ৳"+getRoomSubtotal()+
        "\nAdditional services: ৳"+getServices()+"\nTOTAL: ৳"+getTotal()+
        "\nPayment: "+booking.getPaymentMethod()+"\nPaid (simulation): ৳"+booking.getPaidAmount()+
        "\n==================================\nThank you for staying with us!\n";
    }
}
