import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WishlistManager implements userMainMenuOptions {

    // placeholder for Tina Tsan
    public Object execute(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminInputGetter admininputgetter, AdminManager allAdmins, Logger undoLogger) {
        return null;
    }

    /**
     * Prints out the wishlist of the User user showing the name and description of the items. Also in charge of
     * removing items from wishlist as per User input.
     *
     * @param user     the User that is requesting to see/edit their wishlist.
     * @param allUsers UserManager which stores the User user.
     * @return depending on what the User inputs it will return different objects
     * returns null to tell mainmenu() to call wishlist() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object wishlist(User user, UserManager allUsers) {
        List<Item> wishlist = user.getWishlist();
        //if wishlist is empty, returns "back" which will bring them back to main menu
        if (wishlist.size() == 0) {
            System.out.print("Your wishlist is empty!\n");
            return "back";
        } else { //if wishlist is not empty, prints out all the items in the wishlist and the description of the item
            System.out.print("\uD83C\uDF20Your wishlist: \n");
            for (int i = 0; i < wishlist.size(); i++) {
                System.out.println((i + 1) + ". " + wishlist.get(i).getName() + " : " + wishlist.get(i).getDescription());
            }
        }
        if (wishlist.size() == 0)
            return "back";
        Scanner sc = new Scanner(System.in);
        //asks if the User wants to remove an item from their wishlist
        System.out.print("If you would like to remove an item, please enter the ID of the item you would like to remove " +
                "or type 'back'\n");
        Object input = sc.nextLine();
        //returns them to the main menu if they wish to go "back"
        if (input.equals("back")) {
            return "back";
        }
        try {
            //will try to turn the input into an integer
            //if input is not an integer, returns null and recalls wishlist()
            input = Integer.parseInt((String) input);
        } catch (NumberFormatException e) {
            return null;
        }
        //remove the item they requested from wishlist
        allUsers.removeFromWishlist(allUsers.getUser(user), wishlist.get((Integer) input - 1));
        System.out.println("Item has been removed successfully!");
        return null;
    }

    /**
     * Deals with adding a new item to the User's wishlist. Prompts user for details of the item
     * and sends a request to the Admin for approval.
     *
     * @param user     the User requesting to add a new item to their wishlist
     * @param allUsers UserManager which stores all Users
     * @return depending on what the User inputs it will return different objects:
     * returns null to tell mainmenu() to call addtoWishlist() again
     * returns String "back" to tell mainmenu() to prompt main menu again so User can choose another
     * main menu option
     */
    public Object addToWishlist(User user, UserManager allUsers) {
        System.out.print("Please enter the name of item you would like to add to your wishlist " +
                "or 'back' to go back to the main menu.\n");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        if (name.equals("back")) {
            return "back";
        }
        System.out.print("Please enter the description of item or 'back' to go back to the main menu.\n");
        String description = sc.nextLine();
        if (name.equals("back")) {
            return "back";
        }
        System.out.print("Please choose the category of item by typing the number or 'back' to go back to the main menu.\n");
        System.out.print("1.Electronics\n2.Automotive and car accessories\n3.Baby\n4.Beauty, Health and Personal Care\n5.Books\n6.Home and Kitchen Supplies\n" +
                "7.Clothing\n8.Movies, music and TV\n9.Office Supplies\n10.Gaming\n");

        String ID = sc.nextLine();

        String category = "Undefined";
        while (category.equals("Undefined")) {
            if (name.equals("back")) {
                return "back";
            } else if (ID.equals("1")) {
                category = "Electronics";
            } else if (ID.equals("2")) {
                category = "Automotive and car accessories";
            } else if (ID.equals("3")) {
                category = "Baby";
            } else if (ID.equals("4")) {
                category = "Beauty, Health and Personal Care";
            } else if (ID.equals("5")) {
                category = "Books";
            } else if (ID.equals("6")) {
                category = "Home and Kitchen supplies";

            } else if (ID.equals("7")) {
                category = "Clothing";

            } else if (ID.equals("8")) {
                category = "Movies, music and TV";

            } else if (ID.equals("9")) {
                category = "Office Supplies";

            } else if (ID.equals("10")) {
                category = "Gaming";
            } else {
                System.out.print("Please enter a category for the product!\n");
            }
        }


        Item wishlistItem = new Item(name, null, description, category);
        System.out.println("The item you wish to add to your wishlist is the following: ");
        System.out.println("Item name: " + wishlistItem.getName() + "\n" + "Item description: " + wishlistItem.getDescription() + "\nItem category: " + wishlistItem.getCategory());
        System.out.println("\nIf this is correct, please enter '1'. If you would like to change the item, " +
                "please enter '2'.");

        String confirmation = sc.nextLine();
        if (!confirmation.equals("1")) {
            if (!confirmation.equals("2")) {
                System.out.println("Invalid input. Please try adding the item again.");
                return null;
            }
            return null;
        }


        //adding item to the person's
        allUsers.addToWishlist(user, wishlistItem);

        undoLogger.log(Level.INFO, user.getName() + " added " + wishlistItem.getName() + " to their wishlist.");

        if (ID.equals("1")) {
            allUsers.addToFC("Electronics", user);
        } else if (ID.equals("2")) {
            allUsers.addToFC("Automotive and car accessories", user);
        } else if (ID.equals("3")) {
            allUsers.addToFC("Baby", user);
        } else if (ID.equals("4")) {
            allUsers.addToFC("Beauty, Health and Personal Care", user);
        } else if (ID.equals("5")) {
            allUsers.addToFC("Books", user);
        } else if (ID.equals("6")) {
            allUsers.addToFC("Home and Kitchen Supplies", user);
        } else if (ID.equals("7")) {
            allUsers.addToFC("Clothing", user);
        } else if (ID.equals("8")) {
            allUsers.addToFC("Movies, music and TV", user);
        } else if (ID.equals("9")) {
            allUsers.addToFC("Office Supplies", user);
        } else if (ID.equals("10")) {
            allUsers.addToFC("Gaming", user);
        }
        System.out.print("Item has been added to your wishlist \uD83C\uDF20\n");

        return "back";
    }

}
