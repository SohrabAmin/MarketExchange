public abstract class Transaction {

        private int tradeStatus = 0; // 0- in progress 1- completed 2- cancelled

        public boolean temp; //Set true is this transaction in temporary (must revert in a month) should be able to change easily for phase 2

        private Meeting initialMeeting;

        private Meeting returnMeeting;


        public int getTradeStatus(){
            return this.tradeStatus;

        }


        public void setTradeStatus(int x){
            this.tradeStatus = x;
        }

        public boolean getTemp(){
            return this.temp;
        }

        public void setTemp(boolean x) {

            this.temp = x;
        }

        public Meeting getInitialMeeting(){
            return this.initialMeeting;
        }

        public void setInitialMeeting(Meeting m){
            this.initialMeeting = m;
        }

        public Meeting getReturnMeeting(){
            return this.returnMeeting;
        }

        public void setReturnMeeting(Meeting m){
            this.returnMeeting = m;
        }
}

