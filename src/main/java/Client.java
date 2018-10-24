import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        int ch;
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
            Bank bank = (Bank) registry.lookup("Bank");
           // String response = stub.sayHello();
           // System.out.println("response: " + response);
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\nEnter the UserName:");
            String s=br.readLine();
            System.out.println("\nEnter the Account Number:");
            int ac=Integer.parseInt(br.readLine());
            System.out.println("\nEnter the Initial Amount:");
            int amount=Integer.parseInt(br.readLine());
            do
            {
                System.out.println("\n\t1.Withdraw\n\t2.Deposit\n\t3.Balance\n\t4.Exit");
                System.out.println("\nEnter your choice:");
                ch=Integer.parseInt(br.readLine());
                switch(ch)
                {
                    case 1:
                        System.out.println("\nEnter amount of Withdraw:");
                        int wd=Integer.parseInt(br.readLine());
                        System.out.println("\nUserName:"+s);
                        System.out.println("Account Number:"+ac);
                        if(wd>amount)
                            System.out.println("Balance less unable to proceed withdraw");
                        else
                        {
                            amount=bank.withdraw(wd,amount);
                            System.out.println("Balance:"+amount);
                        }
                        break;
                    case 2:
                        System.out.println("\nEnter amount of deposit:");
                        int dp=Integer.parseInt(br.readLine());
                        System.out.println("\nUserName:"+s);
                        System.out.println("Account Number:"+ac);
                        amount=bank.deposit(dp,amount);
                        System.out.println("Balance:"+amount);
                        break;
                    case 3:
                        System.out.println("\nUserName:"+s);
                        System.out.println("Account Number:"+ac);
                        amount=bank.balance(amount);
                        System.out.println("Balance:"+amount);
                        break;
                }
            }while(ch<4);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());

            e.printStackTrace();
        }
    }
}