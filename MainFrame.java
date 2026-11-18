import javax.swing.*;import javax.swing.table.*;import java.awt.*;import java.util.*;
public class MainFrame extends JFrame {
    private HotelManager hm=new HotelManager(); private JTextArea output=new JTextArea(18,65);
    public MainFrame(){
        setTitle("GrandStay Hotel Management");setSize(900,650);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);
        output.setEditable(false);output.setFont(new Font("Monospaced",Font.PLAIN,13));
        JPanel buttons=new JPanel(new GridLayout(3,3,8,8));
        addButton(buttons,"Dashboard",this::dashboard);addButton(buttons,"Rooms",this::rooms);
        addButton(buttons,"Add Room",this::addRoom);addButton(buttons,"New Booking",this::booking);
        addButton(buttons,"Bookings / Search",this::bookings);addButton(buttons,"Check In",()->statusAction(true));
        addButton(buttons,"Check Out",()->statusAction(false));addButton(buttons,"Cancel Booking",this::cancel);
        addButton(buttons,"Billing / Payment",this::billing);
        JPanel top=new JPanel();top.add(new JLabel("GrandStay Hotel — Reception Console"));
        JPanel main=new JPanel(new BorderLayout(10,10));main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));main.add(top,BorderLayout.NORTH);
        main.add(buttons,BorderLayout.CENTER);main.add(new JScrollPane(output),BorderLayout.SOUTH);add(main);dashboard();
    }
    private void addButton(JPanel p,String name,Runnable r){JButton b=new JButton(name);b.addActionListener(e->r.run());p.add(b);}
    private void show(String s){output.setText(s);}
    private void dashboard(){show("DASHBOARD\n================================\nRooms total: "+hm.getRooms().size()+"\nOccupied rooms: "+hm.occupied()+"\nAvailable rooms: "+hm.availableRooms().size()+"\nBookings total: "+hm.getBookings().size()+"\nSimulated revenue: ৳"+hm.revenue()+"\n\nChoose an operation above.");}
    private void rooms(){StringBuilder s=new StringBuilder("ROOM LIST\n================================\n");for(Room r:hm.getRooms())s.append(r).append("\n");show(s.toString());}
    private void addRoom(){try{int n=Integer.parseInt(JOptionPane.showInputDialog(this,"Room number:"));String[] types={"Single","Double","Deluxe","Suite"};String t=(String)JOptionPane.showInputDialog(this,"Room type:", "Room type",JOptionPane.QUESTION_MESSAGE,null,types,types[0]);if(t==null)return;double rate=Double.parseDouble(JOptionPane.showInputDialog(this,"Rate per night (৳):"));if(hm.addRoom(n,t,rate)){JOptionPane.showMessageDialog(this,"Room added.");rooms();}else JOptionPane.showMessageDialog(this,"Room number already exists or rate invalid.");}catch(Exception ex){JOptionPane.showMessageDialog(this,"Invalid input.");}}
    private void booking(){
        try {
            String name=JOptionPane.showInputDialog(this,"Guest name:");
            if(name==null||name.isBlank())return;

            String[] countries={"Bangladesh (+880) - 10 digits","USA (+1) - 10 digits","India (+91) - 10 digits","UK (+44) - 10 digits","Saudi Arabia (+966) - 9 digits","UAE (+971) - 9 digits"};
            String selected=(String)JOptionPane.showInputDialog(this,"Select country for phone number:","Country code",JOptionPane.QUESTION_MESSAGE,null,countries,countries[0]);
            if(selected==null)return;
            String code, digits;
            if(selected.startsWith("Bangladesh")){code="+880";digits="10";}
            else if(selected.startsWith("USA")){code="+1";digits="10";}
            else if(selected.startsWith("India")){code="+91";digits="10";}
            else if(selected.startsWith("UK")){code="+44";digits="10";}
            else if(selected.startsWith("Saudi")){code="+966";digits="9";}
            else {code="+971";digits="9";}

            String phone;
            while(true){
                phone=JOptionPane.showInputDialog(this,"Enter "+digits+" digits (without country code "+code+"):");
                if(phone==null)return;
                phone=phone.trim();
                if(phone.matches("\\d{"+digits+"}"))break;
                JOptionPane.showMessageDialog(this,"Invalid phone number. "+selected+" requires exactly "+digits+" digits.");
            }
            String fullPhone=code+" "+phone;

            String email;
            while(true){
                email=JOptionPane.showInputDialog(this,"Email address (must be a valid @gmail.com address):");
                if(email==null)return;
                email=email.trim();
                if(email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$"))break;
                JOptionPane.showMessageDialog(this,"Invalid email. Please enter an address ending in @gmail.com.");
            }

            ArrayList<Room> av=hm.availableRooms();
            if(av.isEmpty()){JOptionPane.showMessageDialog(this,"No rooms currently available.");return;}
            String[] choices=av.stream().map(Room::toString).toArray(String[]::new);
            String choice=(String)JOptionPane.showInputDialog(this,"Choose room:","Available rooms",JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
            if(choice==null)return;
            int room=Integer.parseInt(choice.substring(5,choice.indexOf(" |")));
            int nights=Integer.parseInt(JOptionPane.showInputDialog(this,"Number of nights:"));
            Booking b=hm.createBooking(new Guest(name,fullPhone,email),room,nights);
            if(b!=null){JOptionPane.showMessageDialog(this,"Reservation created. Booking ID: "+b.getId());bookings();}
            else JOptionPane.showMessageDialog(this,"Could not create booking.");
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"Invalid booking input.");}
    }
    private void bookings(){
        String query=JOptionPane.showInputDialog(this,"Enter booking ID or guest name (leave blank to show all):");
        if(query==null)return; StringBuilder s=new StringBuilder("BOOKINGS / SEARCH\n================================\n");
        for(Booking b:hm.getBookings())if(query.isBlank()||String.valueOf(b.getId()).contains(query.trim())||b.getGuest().getName().toLowerCase().contains(query.trim().toLowerCase()))s.append(b).append("\n");
        show(s.toString());
    }
    private void statusAction(boolean in){try{int id=Integer.parseInt(JOptionPane.showInputDialog(this,"Booking ID:"));boolean ok=in?hm.checkIn(id):hm.checkOut(id);JOptionPane.showMessageDialog(this,ok?(in?"Check-in complete.":"Check-out complete."):"Action failed. Check booking ID and status.");dashboard();}catch(Exception ex){JOptionPane.showMessageDialog(this,"Invalid ID.");}}
    private void cancel(){try{int id=Integer.parseInt(JOptionPane.showInputDialog(this,"Booking ID to cancel:"));if(hm.cancel(id))JOptionPane.showMessageDialog(this,"Booking cancelled.");else JOptionPane.showMessageDialog(this,"Cancellation not possible.");dashboard();}catch(Exception ex){JOptionPane.showMessageDialog(this,"Invalid ID.");}}
    private void billing(){
        try{int id=Integer.parseInt(JOptionPane.showInputDialog(this,"Booking ID:"));Booking b=hm.findBooking(id);if(b==null){JOptionPane.showMessageDialog(this,"Booking not found.");return;}Room r=hm.findRoom(b.getRoomNumber());if(r==null)return;
            double extras=Double.parseDouble(JOptionPane.showInputDialog(this,"Additional services amount (৳), e.g. food/laundry:", "0"));b.setServiceCharges(Math.max(0,extras));Bill bill=new Bill(b,r);
            String receipt=bill.createReceipt();int choice=JOptionPane.showConfirmDialog(this,receipt+"\nSimulate payment of ৳"+bill.getTotal()+"?", "Bill",JOptionPane.YES_NO_OPTION);
            if(choice==JOptionPane.YES_OPTION){String[] methods={"Cash","Card (simulation)","Mobile banking (simulation)"};String method=(String)JOptionPane.showInputDialog(this,"Payment method:", "Payment",JOptionPane.QUESTION_MESSAGE,null,methods,methods[0]);if(method!=null){b.setPaymentMethod(method);b.setPaidAmount(bill.getTotal());String finalReceipt=new Bill(b,r).createReceipt();DataStore.saveReceipt(finalReceipt,id);hm.saveAll();show(finalReceipt+"\nReceipt saved to data/receipts/receipt_"+id+".txt");}}
        }catch(Exception ex){JOptionPane.showMessageDialog(this,"Invalid bill input.");}
    }
}
