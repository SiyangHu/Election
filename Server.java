// cd Desktop/2016-2017/2019Spring/Distributed\ Systems/Election/
// Compile with -> javac -d . *.java
// Start registry -> rmiregistry &
// Start Server with -> java -classpath . -Djava.rmi.server.codebase=file:./ Server


import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.net.*;
import java.beans.*;
import java.io.*;
import java.lang.*;

public class Server implements ElectionInterface{
    
    
    
    // Initialize Server's attributes
    public Server() throws RemoteException{
        super();
        // Initialize the list
        result_table.put("Clarence_Thomas", 0);
        result_table.put("Ruth_Bader_Ginsburg", 0);
        result_table.put("Stephen_Breyer", 0);
        result_table.put("Samuel_Alito", 0);
        result_table.put("Sonia_Sotomayor", 0);
        result_table.put("Elena_Kagan", 0);
        result_table.put("Neil_Gorsuch", 0);
        result_table.put("Brett_Kavanaugh", 0);
    }

    //For each name of the candidate (string), the number of votes (integer) is associated.
    public static ArrayList<String> voter_list = new ArrayList<String>();
    public static Hashtable<String, Integer> result_table = new Hashtable<String, Integer>();
        

    @Override
    public String generateVotersNum() throws RemoteException{
        return UUID.randomUUID().toString();
    }

    // Making the voting results synchronized with remote
    @Override
    public synchronized Boolean vote( String name, String voter) throws RemoteException{
        try{
            // Check is a voter has voted
            boolean voted = false;
            BufferedReader input = new BufferedReader(new FileReader("voter_list.txt"));
            String line = input.readLine();
            FileWriter fw_voter;
            FileWriter fw_result;
            while(line != null){
                if (line.equals(voter)){
                    voted = true;
                }
                line = input.readLine();
            }
            input.close();

            // if the voter has not voted
            if(!voted){
                // Increment the vote
                result_table.put(name, (int)result_table.get(name) + 1);
           
                // write result_table to local
                Set<String> keys = result_table.keySet();
                try{
                    fw_result = new FileWriter("result.txt");
                    for(String key : keys){
                        fw_result.write(key+"\n"+result_table.get(key).toString()+ "\n");
                        fw_result.write(1);
                        fw_result.write(result_table.get(key));
                    }
                    fw_result.close();
                }
                catch (Exception e) { 
                    System.out.println(e); 
                    System.out.println("Main exception");
                } 

                // Write to voter_list to local
                fw_voter = new FileWriter("voter_list.txt", true);
                fw_voter.write(voter + "\n");
                fw_voter.close();
                
                voted = false;
            }
            return voted;
        }
        catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException: " + ex.toString());
            return false;
        }
        catch (IOException ex) {
            System.out.println("Server : vote : IOException");
            return false;
        }

    }
        

    
    @Override
    public String result(String name) throws RemoteException{
        try{ 
            String vote_count = "";
            BufferedReader output = new BufferedReader(new FileReader("result.txt"));
            String line = output.readLine();
            while(line != null){
                if (line.toLowerCase().contains(name.toLowerCase()) || name.toLowerCase().contains(line.toLowerCase())){
                    line = output.readLine();
                    vote_count = line;
                }
                line = output.readLine();
            }
            output.close();

        return vote_count;
        }
        catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
            return "Failed to see result";
        }
        catch (IOException ex) {
            System.out.println("Server : Result : IOException");
            return "Failed to see result";
        }
        // catch (ClassNotFoundException ex) {
        //     System.out.println("ClassNotFoundException");
        //     return "Failed to see result";
        // }

        

    }

    public static void main(String args[]){
        try{
            System.out.println("Creating an RMI server...");
            // Create a vote remote object of the interface
            Server vote_obj = new Server();
            // Use default TCP port number, which is 1099
            ElectionInterface stub = (ElectionInterface) UnicastRemoteObject.exportObject(vote_obj,0);
            // Create a registry
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");

        }
        catch(RemoteException e){
            System.err.println("Server exception: " + e.toString()); 
            e.printStackTrace(); 
        }
        catch(AlreadyBoundException e){
            System.err.println("Server exception: " + e.toString()); 
            e.printStackTrace(); 
        }


        

        

    }
}