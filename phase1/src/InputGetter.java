import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class InputGetter {

    public User authenticator(UserManager allUsers) {


        //we fill out allUsers with whatever is in csv file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;


        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Type 'signup' to create an account or 'login' to access your account or 'exit' to exit at anytime.");
        try {
            String input = br.readLine();

            boolean usergot = false;
            if (input.equals("signup")) {

                while (!input.equals("exit") && !prompts.usergot/*&& curr < 2*/) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());

                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                        if (curr == 2)
                            prompts.usergot = true;


                    }

                }
                allUsers.createUser(temp.get(0), temp.get(1));
                return (allUsers.getAllUsers().get(allUsers.getAllUsers().size() - 1));


            } else if (input.equals("login")) {
                while (!input.equals("exit") && curr < 2) {
                    if (prompts.hasNext()) {
                        System.out.println(prompts.next());
                    }
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        temp.add(input);
                        curr++;
                    }
                }

                User TempUser = new User(temp.get(0), temp.get(1));
                boolean iExist = false;
                //check if temp exists in allusers
                for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
                    if (allUsers.getAllUsers().get(i).getName().equals(temp.get(0)))
                        if (allUsers.getAllUsers().get(i).getPassword().equals(temp.get(1))) {
                            iExist = true;
                            System.out.println("Successful Login");
                            return (allUsers.getAllUsers().get(i));
                        }

                }
                //if user doesnt exist
                System.out.println("Wrong username or password. Please try Again");
                //curr = 0;
                return authenticator(allUsers);

            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
        }


//it will only get here if the person pressed exit
        return null;
    }

    public void mainMenu(User user) {
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Please select from the following: \n 'View Wishlist' \n 'View Inventory' \n 'Log out' \n");
        String a= sc.nextLine();

        if (a.equals("View Wishlist")){
            List<Item> wishlist = user.getWishlist();
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println(wishlist.get(i).getName());
            }
        }
        else if (a.equals("View Inventory")){
            List<Item> in = user.getInventory();
            for (int i = 0; i < in.size(); i++) {
                System.out.println(in.get(i).getName());
            }
        }
        else if (a.equals("Log out")){
            return;
        }
        System.out.print("your command was " + a);
    }
}
