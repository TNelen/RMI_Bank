import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {

    String withdraw(int a,int amount)throws RemoteException;
    String deposit(int b,int amount)throws RemoteException;
    String balance(int amount)throws RemoteException;
    String getOptions()throws RemoteException;
    int login(String name,String password)throws RemoteException;
    int register(String name,String password)throws RemoteException;
}