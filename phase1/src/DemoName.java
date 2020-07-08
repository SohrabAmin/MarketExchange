import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DemoName {
    public static void main(String[] args) {
        InputGetter system1 = new InputGetter();
        UserManager allUsers = new UserManager();
        allUsers.createUser("Tina", "123");
        //User hello = null;
        //while (hello == null){
        User hello = system1.authenticator(allUsers);//}

        System.out.println(hello.name);



    }

}
