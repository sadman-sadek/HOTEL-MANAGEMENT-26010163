import java.io.Serializable;
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id, roomNumber, nights;
    private Guest guest;
    private String status; // Reserved, Checked In, Checked Out, Cancelled
    private double serviceCharges, paidAmount;
    private String paymentMethod="Not paid";

    public Booking(int id, Guest guest, int roomNumber, int nights){
        this.id=id; this.guest=guest; this.roomNumber=roomNumber; this.nights=nights; this.status="Reserved";
    }
    public int getId(){return id;} public int getRoomNumber(){return roomNumber;}
    public int getNights(){return nights;} public Guest getGuest(){return guest;}
    public String getStatus(){return status;} public void setStatus(String s){status=s;}
    public double getServiceCharges(){return serviceCharges;} public void setServiceCharges(double v){serviceCharges=v;}
    public double getPaidAmount(){return paidAmount;} public void setPaidAmount(double v){paidAmount=v;}
    public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String s){paymentMethod=s;}
    public double roomSubtotal(double rate){return nights*rate;}
    @Override public String toString(){return "#"+id+" | "+guest.getName()+" | Room "+roomNumber+" | "+nights+" night(s) | "+status;}
}
