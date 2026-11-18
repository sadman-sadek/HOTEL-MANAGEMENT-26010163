import java.io.Serializable;
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name, phone, email;
    public Guest(String name,String phone,String email){this.name=name;this.phone=phone;this.email=email;}
    public String getName(){return name;} public String getPhone(){return phone;} public String getEmail(){return email;}
    @Override public String toString(){return name+" ("+phone+")";}
}
