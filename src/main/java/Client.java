import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    static int accountNumber = -1; //not logged in yet
    static String username = "";
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
            do{
            //login/registration loop
            while(accountNumber==-1){
                System.out.println("\nConnected to bank ready to login or register");
                System.out.println("\n1.Login\n2.Register");
                int answer = Integer.parseInt(br.readLine());
                System.out.println("\nEnter the UserName:");
                username = br.readLine();
                System.out.println("\nEnter the Password:");
                String password = br.readLine();
                int errorcode = 0;
                switch (answer){
                    case 1: System.out.println("\nTrying to login:");
                        errorcode = bank.login(username, password);
                        if (errorcode >= 0) {                //success
                            accountNumber = errorcode;
                            System.out.println("\nlogin successful");
                            System.out.println("\ncurrent balance: " + bank.balance(accountNumber));
                        }else if(errorcode == -1){
                            System.out.println("\n no such combination of username and password found");
                        }else{
                            System.out.println("\n an unknown error occurred please try again");
                        }
                        break;
                    case 2: System.out.println("\nTrying to register:");
                        errorcode = bank.register(username, password);
                        if (errorcode == 1) {                //success
                            System.out.println("\nregistration successful");
                        }else if(errorcode == -1){
                            System.out.println("\n username already exists");
                        }else{
                            System.out.println("\n an unknown error occurred please try again");
                        }
                }
            }

                    System.out.println(bank.getOptions());
                    System.out.println("\nEnter your choice:");
                    ch = Integer.parseInt(br.readLine());
                    int amount = 0;
                    switch (ch) {
                        case 1:
                            System.out.println("\nEnter amount of Withdraw:");
                            amount = Integer.parseInt(br.readLine());
                            System.out.println(bank.withdraw(accountNumber,amount));
                            System.out.println("\nUserName:" + username);
                            System.out.println("Balance:" + bank.balance(accountNumber));
                            break;
                        case 2:
                            System.out.println("\nEnter amount of deposit:");
                            amount = Integer.parseInt(br.readLine());
                            System.out.println(bank.deposit(accountNumber,amount));
                            System.out.println("\nUserName:" + username);
                            System.out.println("Balance:" + bank.balance(accountNumber));
                            break;
                        case 3:
                            System.out.println(bank.balance(accountNumber));
                            System.out.println("\nUserName:" + username);
                            System.out.println("Balance:" + bank.balance(accountNumber));
                            break;
                        case 4:
                            System.out.println("\nLogged out");
                            accountNumber = -1;
                            break;
                    }

            }while (ch < 5) ;

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());

            e.printStackTrace();
        }
    }
}