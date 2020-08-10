package system_menus.user_main_menus.options;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import transactions.*;
import meetings.*;
import items.*;
import accounts.users.*;
import accounts.admins.*;
import requests.*;
import currency.*;
import system_menus.admin_main_menus.options.*;
import system_menus.user_main_menus.options.*;

public class ConfirmMeetings implements userMainMenuOptions {

    /**
     * Displays the pending transactions that the accounts.users.User user has. It will allow accounts.users to approve or edit the transactions
     * they currently have pending.
     *  @param user            the accounts.users.User that wants to view their pending transactions
     * @param allItems        items.ItemManager that stores the system's inventory
     * @param allUsers        accounts.users.UserManager that stores all the Users in the system
     * @param allMeetings     meetings.MeetingManager that deals with the creation of meetings
     * @param allTransactions transactions.TransactionManager that stores all the information of all system transactions
     * @param currencyManager
     * @return null if the current menu is to be reprinted; accounts.users.User user if the user is to be redirected to the main menu;
     * String "exit" if the user is to be logged out.
     */
    public Object execute(User user, ItemManager allItems, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminManager allAdmins, Logger undoLogger, UserMessageManager allUserMessages, CurrencyManager currencyManager) {
        System.out.print("Please select 1 for all your initial pending meetings+virtual meetings and 2 for all return meetings\n");
        Scanner sc4 = new Scanner(System.in);    //System.in is a standard input stream
        String selection = sc4.nextLine();

        if (selection.equals("1")) {
            List<Transaction> userTransactions = new ArrayList<>();
            userTransactions = user.getAgreedUponMeeting();
            if (userTransactions.size() == 0) {
                System.out.print("No initial pending trade for you to confirm!\n");
                return user;
            }
            System.out.print("Here are your pending meetings ready to be confirmed!\n");
            //prints the pending meetings
            for (int i = 0; i < userTransactions.size(); i++) {

                if (userTransactions.get(i).getVirtual()){
                    //if virtual, do these
                    OneWayMonetized t = (OneWayMonetized) userTransactions.get(i);
                    String otherSide = t.getFirstTrader().getName();
                    //finding the other side of this transaction

                    if (otherSide.equals(user.getName())){
                        otherSide = t.getSecondTrader().getName();

                    }


                    System.out.print((i + 1) + " . " + "Virtual trade with: " + otherSide + " - email: (" + t.getEmail() + ")" +  "\n");

                }
                else {


                String otherSide = "";
                Integer confirmed = userTransactions.get(i).getInitialMeeting().userconfirmed(user.getName());
                String status = "";
                if (confirmed == 1) {
                    status = " [CONFIRMED BY YOU] ";
                }
                //SAMPLE : 1 . Sat, Aug 8, 2020 11:00AM at 1 With: Mo






                System.out.print((i + 1) + " . " + userTransactions.get(i).getInitialMeeting() + " With: " + userTransactions.get(i).getInitialMeeting().getOtherSide(user.getName()) + status + "\n");
            }
            }

            System.out.print("Please enter the ID of the transaction you would like to confirm.\n");
            Scanner sc11 = new Scanner(System.in);
            int meetingIndex = (Integer.parseInt(sc11.nextLine())) - 1;
            Transaction selectedTransaction = userTransactions.get(meetingIndex);

            //VIRTUAL
            if (selectedTransaction.getVirtual()){
                OneWayMonetized t = (OneWayMonetized) selectedTransaction;
                System.out.print("You have selected:\n");

                System.out.print("Virtual trade - sending file to email: " +  t.getEmail()  +  "\n");
                System.out.print("Press 1 to confirm that email has been sent from you! Press 2 to cancel\n");

                String action = sc11.nextLine();
                if (action.equals("1")) {

                    String otherSide = t.getFirstTrader().getName();
                    //finding the other side of this transaction

                    if (otherSide.equals(user.getName())){
                        otherSide = t.getSecondTrader().getName();

                    }

                    if (!t.getPerson1Confirmed() && !t.getPerson2Confirmed()){ //if they both have not confirmed
                        t.Person1Confirmed();
                        System.out.print("Confirmed by you! Waiting on " + otherSide + " to confirm!\n");
                        return user;

                    }

                    else if (t.getPerson1Confirmed()){
                        //if one person has confirmed
                        t.Person2Confirmed();
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                        //i probably should deal with money here or moe/aidan should idk
                        System.out.print("Confirmed by you! Looks like both sides have confirmed!\n");
                        return user;

                    }


                }

                else if (action.equals("2")){
                    System.out.print("\u2639 Cancelling this transaction! We are sorry to hear that! Better luck next time!\n");
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 4, currencyManager);




                }

            }










            System.out.print("You have selected:\n");
            Integer confirmed = selectedTransaction.getInitialMeeting().userconfirmed(user.getName());

            if ( confirmed == 1) {
                System.out.print("You have already confirmed this meeting!\n");
                return user;
            }
            System.out.print(selectedTransaction.getInitialMeeting() + " With: " + selectedTransaction.getInitialMeeting().getOtherSide(user.getName()) + "\n");
            System.out.print("Press 1 to confirm that the meeting is done. Press 2 to cancel the meeting and press 3 if you got stood up\n");


            String action = sc11.nextLine();
            if (action.equals("1")) {
                //confirm meeting by the user



                selectedTransaction.getInitialMeeting().meetingConfirmed(user.getName());
                System.out.print("Confirmed that the meeting occurred on " + selectedTransaction.getInitialMeeting() + "\n");

                //lets check if both people have confirmed meeting
                if (selectedTransaction.getInitialMeeting().confirmedByBothSides()) {
                    //looks like the meeting was confirmed by both parties!



                    //now i have to check if it was 2 way or 3 way
                    if (selectedTransaction instanceof OneWay){

                        System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");

                        if (!selectedTransaction.getTemp()){
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                        return user;}
                        else if (selectedTransaction.getTemp()){
                            //if it was a temporary meeting, then I need to set up a second meeting
                            allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 2, currencyManager);
                            //by now, the second agreed upon meeting is set for both accounts.users
                            Calendar date = selectedTransaction.getInitialMeeting().getDate();
                            date.add(Calendar.MONTH, 1);
                            Meeting returnMeeting = new Meeting(date, selectedTransaction.getInitialMeeting().getPlace());
                            returnMeeting.initialconfirm(user.getName(), selectedTransaction.getInitialMeeting().getOtherSide(user.getName()));
                            System.out.print("REMINDER: You need to return the borrowed item(s) back by " + returnMeeting.toString() + "\n");
                            //need to add return meeting to transactions
                            allTransactions.setFinalMeeting(selectedTransaction, returnMeeting);

                        }

                    }

                    else if (selectedTransaction instanceof OneWayMonetized){


                    }
                    else if (selectedTransaction instanceof TwoWay){
                        System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");
                    if (!selectedTransaction.getTemp()) { //if it was a permenant transaction
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);

                    } else if (selectedTransaction.getTemp()) {
                        //if it was a temporary meeting, then I need to set up a second meeting
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 2, currencyManager);
                        //by now, the second agreed upon meeting is set for both accounts.users
                        Calendar date = selectedTransaction.getInitialMeeting().getDate();
                        date.add(Calendar.MONTH, 1);
                        Meeting returnMeeting = new Meeting(date, selectedTransaction.getInitialMeeting().getPlace());
                        returnMeeting.initialconfirm(user.getName(), selectedTransaction.getInitialMeeting().getOtherSide(user.getName()));
                        System.out.print("REMINDER: You need to return the borrowed item(s) back by " + returnMeeting.toString() + "\n");
                        //need to add return meeting to transactions
                        allTransactions.setFinalMeeting(selectedTransaction, returnMeeting);

                    }
                }
                else if (selectedTransaction instanceof ThreeWay){
                        selectedTransaction.getInitialMeeting().meetingConfirmed(user.getName());
                     //   System.out.print("Confirmed that the meeting occurred on " + selectedTransaction.getInitialMeeting() + "\n");
                        //if its confirmed by all 3 side
                        if (selectedTransaction.getInitialMeeting().confirmByThreeSides()){
                            if (!selectedTransaction.getTemp()) { //if it was a permenant transaction
                                allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                                System.out.print("Guess it is confirmed by all 3 people?!\n");
                            }else if (selectedTransaction.getTemp()) { //if it was temporary
                                //if it was a temporary meeting, then I need to set up a second meeting
                                allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 2, currencyManager);
                                //by now, the second agreed upon meeting is set for both accounts.users
                                Calendar date = selectedTransaction.getInitialMeeting().getDate();
                                date.add(Calendar.MONTH, 1);
                                Meeting returnMeeting = new Meeting(date, selectedTransaction.getInitialMeeting().getPlace());
                                returnMeeting.initial3confirm(((ThreeWay) selectedTransaction).getFirstTrader().getName(), ((ThreeWay) selectedTransaction).getSecondTrader().getName(), ((ThreeWay) selectedTransaction).getThirdTrader().getName());

                                System.out.print("REMINDER: You need to return the borrowed item(s) back by " + returnMeeting.toString() + "\n");
                                //need to add return meeting to transactions
                                allTransactions.setFinalMeeting(selectedTransaction, returnMeeting);

                            }





                        }






                    }

                }
            } else if (selection.equals("2") || selection.equals("3")) { //cancelling
                System.out.print("\u2639 We are sorry to hear that! Better luck next time!\n");
                allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 4, currencyManager);
            }
        }
        else if (selection.equals("2")) {
            System.out.print("Here are your meetings to return items:\n");
            List<Transaction> userTransactions = new ArrayList<>();
            userTransactions = user.getSecondAgreedUponMeeting();

            if (userTransactions.size() == 0) {
                System.out.print("No pending trade for you to confirm!\n");
                return user;
            }
            System.out.print("Here are your return pending meetings ready to be confirmed!\n");
            //prints the pending meetings
            for (int i = 0; i < userTransactions.size(); i++) {
                String otherSide = "";
                Integer confirmed = userTransactions.get(i).getReturnMeeting().userconfirmed(user.getName());
                String status = "";
                if (confirmed == 1) {
                    status = " [CONFIRMED BY YOU] ";
                }
                System.out.print(Integer.toString(i + 1) + " . " + userTransactions.get(i).getInitialMeeting() + " With: " + userTransactions.get(i).getInitialMeeting().getOtherSide(user.getName()) + status + "\n");
            }
            System.out.print("Please enter the ID of the meeting you would like to confirm.\n");
            Scanner sc11 = new Scanner(System.in);
            int meetingIndex = (Integer.parseInt(sc11.nextLine())) - 1;
            Transaction selectedTransaction = userTransactions.get(meetingIndex);
            System.out.print("You have selected:\n");
            Integer confirmed = selectedTransaction.getInitialMeeting().userconfirmed(user.getName());
//printing the confirmed transaction and its meeting and stuff
            System.out.print(selectedTransaction.getInitialMeeting() + " With: " + selectedTransaction.getInitialMeeting().getOtherSide(user.getName()) + "\n");
            System.out.print("Press 1 to confirm that the meeting is done!\n");
            String action = sc11.nextLine();
            if (action.equals("1")) {
                //confirm meeting by the user
                selectedTransaction.getReturnMeeting().meetingConfirmed(user.getName());

                System.out.print("Confirmed that the meeting occurred on " + selectedTransaction.getReturnMeeting() + "\n");

                //lets check if both people have confirmed meeting
                if (selectedTransaction.getReturnMeeting().confirmedByBothSides()) {
                    //looks like the meeting was confirmed by both parties!

                    if (selectedTransaction instanceof OneWay){
                        System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");
                        allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                    }


                    if (selectedTransaction instanceof TwoWay){
                    System.out.print("\uD83E\uDD29 Looks like the meeting was confirmed by both sides!\n ");
                    allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                    }
                    else if (selectedTransaction instanceof ThreeWay){
                        selectedTransaction.getInitialMeeting().meetingConfirmed(user.getName());
                        System.out.print("Confirmed!\n");
                        if (selectedTransaction.getReturnMeeting().confirmByThreeSides()){
                            //if everyone confirmed
                            System.out.print("Looks like everyone confirmed!\n");
                            allTransactions.updateTransactionStatus(allItems, allUsers, allAdmins, selectedTransaction, 3, currencyManager);
                        }





                    }
                }
            }
        }
        return user;
    }

}
