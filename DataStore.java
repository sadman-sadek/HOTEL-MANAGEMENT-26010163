import java.io.*;
import java.nio.file.*;
import java.util.*;
public class DataStore {
    private static final String DIR="data";
    public static <T> ArrayList<T> load(String file){
        try(ObjectInputStream in=new ObjectInputStream(new FileInputStream(DIR+"/"+file))){
            return (ArrayList<T>)in.readObject();
        }catch(Exception e){return new ArrayList<>();}
    }
    public static void save(String file,Object data){
        try{
            Files.createDirectories(Paths.get(DIR));
            try(ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(DIR+"/"+file))){out.writeObject(data);}
        }catch(IOException e){System.err.println("Save error: "+e.getMessage());}
    }
    public static void saveReceipt(String text,int bookingId){
        try{
            Files.createDirectories(Paths.get(DIR+"/receipts"));
            Files.writeString(Paths.get(DIR+"/receipts/receipt_"+bookingId+".txt"),text);
        }catch(IOException e){System.err.println("Receipt error: "+e.getMessage());}
    }
}
