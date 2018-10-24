import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {

    int withdraw(int a,int amount)throws RemoteException;
    int deposit(int b,int amount)throws RemoteException;
    int balance(int amount)throws RemoteException;
}