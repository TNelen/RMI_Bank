public class User {
    String name;
    int balance = 0;
    String password;
    public User(String name,String password){
        this.name = name;
        this.password = password;
    }

    // get Balance of this users account
    public int getBalance(){
        return balance;
    }

    // function for depositing a positive amount
    public int deposit(int amount){
        if(amount>0) {
            balance += amount;
        }else{
            return -1; //error code for negative deposit amount;
        }
        return 1;//success
    }

    public int withdrawal(int amount){
        if(amount<= balance){
            balance-=amount;
        }else{
            return -1 ;//error code insufficient balance
        }
        return 1; //success
    }

    // get password of this users account
    public String getPassword(){
        return password;
    }
}
