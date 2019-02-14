// Reference: https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html
// https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html
// http://www.cs.columbia.edu/~hgs/teaching/ais/1998/projects/telepath/report.html#server

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ElectionInterface extends Remote{
    public String generateVotersNum() throws RemoteException;
    public Boolean vote( String name, String voter) throws RemoteException;
    public String result(String name) throws RemoteException;
}