import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server implements Bank {
    List<User> users = new ArrayList<User>();
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

    @Override
    public String withdraw(int accountNumber, int amount) throws RemoteException {
        int code = users.get(accountNumber).withdrawal(amount);
        switch (code){
            case 1:  System.out.println("Withdrawal: "+amount+", from user: "+users.get(accountNumber));
                return "\nwithdrawal of "+amount+" was a success"; //success;
            case -1: System.out.println("Withdrawal failed for user: "+users.get(accountNumber));
                return "\ninsufficient balance, please try again"; // insufficient balance
        }
        return "\nan unknown error has occurred please try again"; //unknown error
    }

    @Override
    public String deposit(int accountNumber, int amount) throws RemoteException {
        int code = users.get(accountNumber).deposit(amount);
        switch (code){
            case 1:  System.out.println("Deposit: "+amount+", from user: "+users.get(accountNumber));
                return "\ndeposit was a success"; //success;
            case -1: System.out.println("Deposit failed for user: "+users.get(accountNumber));
                return "\nnegative deposit amount, please try again"; // negative deposit amoung
        }
        return "\nan unknown error has occurred please try again"; //unknown error
    }

    @Override
    public String balance(int accountNumber) throws RemoteException {
        System.out.println("Balance: "+users.get(accountNumber).getBalance());
        return "\nbalance: "+users.get(accountNumber).getBalance();
    }

    @Override
    public String getOptions(){
        String options = "\n1.Withdraw" +
                "\n2.Deposit" +
                "\n3.Balance" +
                "\n4.Logout" +
                "\n5.Exit";
        return options;
    }

    @Override
    public int login(String name, String password) throws RemoteException {
        if(users.size()==0){
            return -1; //error code no such login;
        }else{
            for(int current = 0;current < users.size();current++){
                if(users.get(current).name.equalsIgnoreCase(name) && users.get(current).password.equalsIgnoreCase(password)){
                    return current; //users account number equals users array current place (always bigger or equal then 0)
                }
            }
        }
        return -2; //unknown error
    }

    @Override
    public int register(String name, String password) throws RemoteException {
        for(int current = 0;current < users.size();current++){
            if(users.get(current).name.equalsIgnoreCase(name) && users.get(current).password.equalsIgnoreCase(password)){
                return -1; //combination of username and password already exists
            }
        }
        users.add(new User(name,password));
        return 1; //registration successful
    }
}