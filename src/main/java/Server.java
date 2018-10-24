import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Bank {

    public Server() {}
    /*public String sayHello() {
        return "Hello, world!";
    }*/

    public static void main(String args[]) {

        try {

            LocateRegistry.createRegistry(1099);
            Server obj = new Server();
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            Bank bank = (Bank) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's bank in the registry

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Bank", bank);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public int withdraw(int a, int amount) throws RemoteException {
        amount=amount-a;
        return(amount);
    }

    public int deposit(int b, int amount) throws RemoteException {
        amount=amount+b;
        return(amount);
    }

    public int balance(int amount) throws RemoteException {
        return amount;
    }
}