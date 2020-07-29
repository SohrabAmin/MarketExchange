public class UserChosenOption {
    public userMainMenuOptions chosenOption;

    public void setChosenOption(userMainMenuOptions option){
        this.chosenOption = option;
    }

    public Object executeOption(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                                UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                                AdminInputGetter admininputgetter, AdminManager allAdmins) {
        return chosenOption.execute(user, allItems, system1, allTradeRequests, allUsers, allMeetings, allTransactions,
                admininputgetter, allAdmins);
    }
}
