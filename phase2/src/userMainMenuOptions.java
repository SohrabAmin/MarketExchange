public interface userMainMenuOptions {
    public Object execute(User user, ItemManager allItems, InputGetter system1, TradeRequestManager allTradeRequests,
                          UserManager allUsers, MeetingManager allMeetings, TransactionManager allTransactions,
                          AdminInputGetter admininputgetter, AdminManager allAdmins);
}
