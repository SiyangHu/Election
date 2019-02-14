// run client with -> java -classpath . Client

import java.rmi.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.rmi.registry.*;

public class Client{
    ElectionInterface ei;

    private Client(){}

    // public String generateVotersNum(){
    //     try{
    //         return ei.generateVotersNum();
    //     }
    //     catch (RemoteException e){
    //             System.err.println("Server exception: " + e.toString()); 
    //             e.printStackTrace(); 
    //     }
    //     return null;
    // }

    // public Boolean vote(string name, int voter){
    //     try{
    //         return ei.vote(name, voter);
    //     }
    //     catch (RemoteException e){
    //         System.err.println("Server exception: " + e.toString()); 
    //         e.printStackTrace(); 
    //     }
    //     return null;
    // }
    // public int result(string name){
    //     try{
    //         return ei.result(name);
    //     }
    //     catch (RemoteException e){
    //         System.err.println("Server exception: " + e.toString()); 
    //         e.printStackTrace(); 
    //     }
    //     return null;
    // }

    public static void main(String args[]){
        String host = (args.length < 1) ? null : args[0];
        try {
            //Set up connection with the Server
            Registry registry = LocateRegistry.getRegistry(host);
            ElectionInterface stub = (ElectionInterface) registry.lookup("Hello");
            // Start the program
            System.out.println();
            System.out.println("Welcome to the voting program!");
            System.out.println();
            System.out.println("----- Candidate List -----");
            System.out.println("Clarence_Thomas");
            System.out.println("Ruth_Bader_Ginsburg");
            System.out.println("Stephen_Breyer");
            System.out.println("Samuel_Alito");
            System.out.println("Sonia_Sotomayor");
            System.out.println("Elena_Kagan");
            System.out.println("Neil_Gorsuch");
            System.out.println("Brett_Kavanaugh");
            System.out.println("-------------------------");
            System.out.println();
            System.out.println("------ Action -----------");
            System.out.println("Get Voter's number");
            System.out.println("Vote");
            System.out.println("See result");
            System.out.println("Exit");
            System.out.println("-------------------------");
            System.out.println("-- Choose 'get', 'vote', 'result', or 'exit' --");
            Scanner sc = new Scanner(System.in);
            String todo = sc.next();
            String name;
            String voter;
            Boolean hasVoted;
            while(!todo.equals("exit")){
                if (todo.equals("vote")){
                    System.out.println("Please enter your voter's number: ");
                    voter = sc.next();
                    System.out.println("Please enter the name of the ideal candidate: ");
                    name = sc.next();
                    hasVoted = stub.vote(name, voter);
                    if (hasVoted){
                        System.out.println("You have voted before. Please view results instead");
                    }
                    else{
                        System.out.println("You successfully voted for " + name + "!");
                    }
                }
                else if(todo.equals("get")){
                    System.out.println("Your voter's number is: " + stub.generateVotersNum());
                    // System.out.println("Please enter 'get', 'vote', 'result', or 'exit':");
                    // todo = sc.next();
                }
                else if(todo.equals("result")){
                    System.out.println("Who's result would you like to view? ");
                    name = sc.next();
                    System.out.println(name + " has " + stub.result(name) + " votes.");
                    // System.out.println("Please enter 'get', 'vote', 'result', or 'exit':");
                    // todo = sc.next();
                }
                System.out.println("Please enter 'get', 'vote', 'result', or 'exit':");
                todo = sc.next();
            }
            sc.close();
        }
        catch (RemoteException e){
            System.out.println("Server exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
       
        catch (NotBoundException ew) {
            System.out.println("Registry was not found.");
        }
        
        
    }

}