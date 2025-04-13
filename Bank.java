import java.sql.*;
import java.util.*;

class Bank {
  static final String DB_URL = "jdbc:mysql://localhost:3306/mcadb12";
  static final String USER = "mcauser12";
  static final String PASS = "Abcd@123#";

  Connection conn;
  Statement stmt;
  ResultSet rs;

  void connectToDB() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
    } catch (SQLException | ClassNotFoundException e) {
       System.out.println("error connecting database"+e.getMessage());
    }
  }

  void createUserTable() {
    try {
      stmt.execute("CREATE TABLE IF NOT EXISTS users (account_number INT PRIMARY KEY, pin INT, balance DECIMAL(10, 2))");
    } catch (SQLException e) {
      System.out.println("Error creating users table: " + e.getMessage());
    }
    catch(NullPointerException e){}
  }

  void createTransactionTable() {
    try {
      stmt.execute("CREATE TABLE IF NOT EXISTS transactions (transaction_id INT PRIMARY KEY AUTO_INCREMENT, account_number INT, transaction_type VARCHAR(20), amount DECIMAL(10, 2), timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    } catch (SQLException e) {
      System.out.println("Error creating transactions table: " + e.getMessage());
    }
    catch(NullPointerException e){}
  }

  boolean authenticateUser(int accountNumber, int pin) {
    try {
      rs = stmt.executeQuery("SELECT * FROM users WHERE account_number = " + accountNumber + " AND pin = " + pin);
      return rs.next();
    } catch (SQLException e) {
      System.out.println("Error authenticating user: " + e.getMessage());
      return false;
    }
    catch(NullPointerException e){ return true;}
  }

  void deposit(int accountNumber, double amount) {
    try {
      stmt.executeUpdate("UPDATE users SET balance = balance + " + amount + " WHERE account_number = " + accountNumber);
      stmt.executeUpdate("INSERT INTO transactions (account_number, transaction_type, amount) VALUES (" + accountNumber + ", 'deposit', " + amount + ")");
      System.out.println("Deposit successful!");
    } catch (SQLException e) {
      System.out.println("Error depositing: " + e.getMessage());
    }
    catch(NullPointerException e)
    {}
  }

  void withdraw(int accountNumber, double amount) {
    try {
      rs = stmt.executeQuery("SELECT balance FROM users WHERE account_number = " + accountNumber);
      if (rs.next() && rs.getDouble("balance") >= amount) {
        stmt.executeUpdate("UPDATE users SET balance = balance - " + amount + " WHERE account_number = " + accountNumber);
        stmt.executeUpdate("INSERT INTO transactions (account_number, transaction_type, amount) VALUES (" + accountNumber + ", 'withdrawal', " + amount + ")");
        System.out.println("Withdrawal successful!");
      } else {
        System.out.println("Insufficient balance!");
      }
    } catch (SQLException e) {
      System.out.println("Error withdrawing: " + e.getMessage());
    }
    catch(NullPointerException e){}
  }

  void displayBalance(int accountNumber) {
    try {
      rs = stmt.executeQuery("SELECT balance FROM users WHERE account_number = " + accountNumber);
      if (rs.next()) {
        System.out.println("Account balance: " + rs.getDouble("balance"));
      }
    } catch (SQLException e) {
      System.out.println("Error displaying balance: " + e.getMessage());
    }
    catch(NullPointerException e){}
  }

  public static void main(String args[]) {
    Bank bank = new Bank();
    bank.connectToDB();
    bank.createUserTable();
    bank.createTransactionTable();

    Scanner sc = new Scanner(System.in);
    int accountNumber, pin;
    double amount;

    System.out.println("Enter account number:");
    accountNumber = sc.nextInt();
    System.out.println("Enter PIN:");
    pin = sc.nextInt();

    if (bank.authenticateUser(accountNumber, pin)) {
      while (true) {
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. Exit");
        int choice = sc.nextInt();

        switch (choice) {
          case 1:
            System.out.println("Enter deposit amount:");
            amount = sc.nextDouble();
            bank.deposit(accountNumber, amount);
            break;
          case 2:
            System.out.println("Enter withdrawal amount:");
            amount = sc.nextDouble();
            bank.withdraw(accountNumber, amount);
            break;
          case 3:
            bank.displayBalance(accountNumber);
            break;
         case 4:
        System.out.println("Exiting...");
         return;
         default:
        System.out.println("Invalid choice. Please try again.");
        }
      }
    }
   else {
     System.out.println("Invalid account number or PIN. Please try again.");
    }
  }
}
