import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminInputGetter {

    public User authenticator(AdminManager allUsers) {

        //we fill out allUsers with whatever is in ser file
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserIterator prompts = new UserIterator();
        int curr = 0;

        ArrayList<String> temp = new ArrayList<>();

        System.out.println("Hello Admin. Please enter 'login' to log in or 'exit' to exit at anytime.");
        try {
            String input = br.readLine();

           if (input.equals("login")) {
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

                Admin tempAdmin = new Admin(temp.get(0), temp.get(1));
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
}
