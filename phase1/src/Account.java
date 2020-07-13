import java.io.Serializable;
/**
 * Represent an account with name and corresponding password
 */
public abstract class Account implements Serializable {
     protected String name;
     protected String password;

     /**
      * getter for an account password
      * @return this account's password
      */
     public String getPassword() {
          return password;
     }

     /**
      * getter for an account name
      * @return this account's name
      */
     public String getName(){
          return name;
     }
}

