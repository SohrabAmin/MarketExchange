public class Transaction {

        private int tradeStatus = 0; // 0- in progress 1- completed 2- cancelled

        private boolean temp; //Set true is this transaction in temporary (must revert in a month) should be able to change easily for phase 2

        private Meeting initialMeeting;

        private Meeting returnMeeting;


        public Transaction(boolean temp){
            this.temp = temp;
            this.initialMeeting = null;
            this.returnMeeting = null;
        }

        public int getTradeStatus(){
            return this.tradeStatus;

        }


        public void setTradeStatus(int tradeStatus){
            this.tradeStatus = tradeStatus;
        }

        public boolean getTemp(){
            return this.temp;
        }

        public void setTemp(boolean temp) {

            this.temp = temp;
        }

        public Meeting getInitialMeeting(){
            return this.initialMeeting;
        }

        public void setInitialMeeting(Meeting meeting){
            this.initialMeeting = meeting;
        }

        public Meeting getReturnMeeting(){
            return this.returnMeeting;
        }

        public void setReturnMeeting(Meeting meeting){
            this.returnMeeting = meeting;
        }
}

