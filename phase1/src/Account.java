import java.io.Serializable;

public abstract class Account implements Serializable {
     protected String name;
     protected String password;

     /**
      * getter for an account password
      * @return this account password
      */
     public String getPassword() {
          return password;
     }

     public String getName(){
          return name;
     }
}

