import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Hashtable;
import java.util.Set;

public class testio{
    public static void main(String args[]){

        Hashtable<String, Integer> result_table = new Hashtable<String, Integer>();
        result_table.put("Clarence_Thomas", 0);
        result_table.put("Ruth_Bader_Ginsburg", 5);
        result_table.put("Stephen_Breyer", 0);
        result_table.put("Samuel_Alito", 0);
        Set<String> keys = result_table.keySet();


        try{
            // FileOutputStream fos = new FileOutputStream("resulttest.xml");
            // // ObjectOutputStream out = new ObjectOutputStream(fos);
            // // out.writeObject(result_table);
            // // out.close();
            // for(String key : keys){
            //     fos.write(key.getBytes());
            //     fos.write(1);
            //     fos.write(result_table.get(key));
            // }
            // // fos.write(result_table.get("Clarence Thomas"));
            // fos.close();


            FileWriter fw = new FileWriter("resulttest.txt");
                for(String key : keys){
                    fw.write(key+"\n"+result_table.get(key).toString()+ "\n");
                    fw.write(1);
                    fw.write(result_table.get(key));
                }
                fw.close();


         }
        catch (Exception e) { 
            System.out.println(e); 
            System.out.println("Main exception");
        } 
    }
}
