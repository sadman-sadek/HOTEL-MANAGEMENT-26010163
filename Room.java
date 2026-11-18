import java.io.Serializable;
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    private int number;
    private String type;
    private double rate;
    private String status; // Available, Occupied, Reserved

    public Room(int number, String type, double rate) {
        this.number=number; this.type=type; this.rate=rate; this.status="Available";
    }
    public int getNumber(){return number;}
    public String getType(){return type;}
    public double getRate(){return rate;}
    public String getStatus(){return status;}
    public void setStatus(String s){status=s;}
    public void setType(String t){type=t;}
    public void setRate(double r){rate=r;}
    @Override public String toString(){return "Room "+number+" | "+type+" | ৳"+rate+"/night | "+status;}
}
