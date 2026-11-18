import java.util.*;
public class HotelManager {
    private ArrayList<Room> rooms=DataStore.load("rooms.dat");
    private ArrayList<Booking> bookings=DataStore.load("bookings.dat");
    private int nextId=bookings.stream().mapToInt(Booking::getId).max().orElse(1000)+1;
    public HotelManager(){
        if(rooms.isEmpty()){
            rooms.add(new Room(101,"Single",1800)); rooms.add(new Room(102,"Single",1800));
            rooms.add(new Room(201,"Double",2800)); rooms.add(new Room(202,"Double",2800));
            rooms.add(new Room(301,"Deluxe",4500)); rooms.add(new Room(302,"Deluxe",4500));
            saveRooms();
        }
    }
    public ArrayList<Room> getRooms(){return rooms;} public ArrayList<Booking> getBookings(){return bookings;}
    public Room findRoom(int n){return rooms.stream().filter(r->r.getNumber()==n).findFirst().orElse(null);}
    public Booking findBooking(int id){return bookings.stream().filter(b->b.getId()==id).findFirst().orElse(null);}
    public ArrayList<Room> availableRooms(){ArrayList<Room>a=new ArrayList<>();for(Room r:rooms)if(r.getStatus().equals("Available"))a.add(r);return a;}
    public Booking createBooking(Guest g,int room,int nights){
        Room r=findRoom(room); if(r==null||!r.getStatus().equals("Available")||nights<1)return null;
        Booking b=new Booking(nextId++,g,room,nights);bookings.add(b);r.setStatus("Reserved");saveAll();return b;
    }
    public boolean addRoom(int n,String type,double rate){
        if(findRoom(n)!=null||rate<=0)return false;rooms.add(new Room(n,type,rate));saveAll();return true;
    }
    public boolean checkIn(int id){Booking b=findBooking(id);Room r=b==null?null:findRoom(b.getRoomNumber());if(b==null||r==null||!b.getStatus().equals("Reserved"))return false;b.setStatus("Checked In");r.setStatus("Occupied");saveAll();return true;}
    public boolean checkOut(int id){Booking b=findBooking(id);Room r=b==null?null:findRoom(b.getRoomNumber());if(b==null||r==null||!b.getStatus().equals("Checked In"))return false;b.setStatus("Checked Out");r.setStatus("Available");saveAll();return true;}
    public boolean cancel(int id){Booking b=findBooking(id);Room r=b==null?null:findRoom(b.getRoomNumber());if(b==null||r==null||!(b.getStatus().equals("Reserved")||b.getStatus().equals("Checked In")))return false;b.setStatus("Cancelled");r.setStatus("Available");saveAll();return true;}
    public void saveAll(){saveRooms();DataStore.save("bookings.dat",bookings);}
    private void saveRooms(){DataStore.save("rooms.dat",rooms);}
    public double revenue(){return bookings.stream().mapToDouble(Booking::getPaidAmount).sum();}
    public int occupied(){return (int)rooms.stream().filter(r->r.getStatus().equals("Occupied")).count();}
}
