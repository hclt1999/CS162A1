import java.util.ArrayList;

public class Fcfs {

    private int at[];
    private int bt[];
    private int nl[];
    private int indices[];
    private int outWait[];
    private int outResponse[];
    private int outTurn[];
    private int dupBt[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public Fcfs(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
        indices = new int[at.length];
        //Make index arrays
        for(int i = 1; i <= at.length; i++) {
            indices[i-1] = i;
        }

        outWait = new int[at.length];
        outTurn = new int[at.length];
        outResponse = new int[at.length];

        for (int j=0; j<outWait.length; j++) { //initialize all wait times to 0
            outResponse[j]=-1;
        }

        outTurn = new int[at.length];
        for (int j=0; j<outWait.length; j++) { //initialize all wait times to 0
            outTurn[j]=0;
        }

        outWait = new int[at.length];
        for (int j=0; j<outWait.length; j++) { //initialize all wait times to 0
            outWait[j]=0;
        }

        dupBt = new int[bt.length];
        for (int j=0; j<dupBt.length; j++) { //initialize all wait times to 0
            dupBt[j]=bt[j];
        }
    }

    //METHODS FOR INSTRUCTIONS
    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public String fcfsFunc() {

        //Sort Arrival Time
        sortOrderByArrival();

        //Output String
        String outputText = "";


        //COUNTS

        int currentSecondsCount = 0;
        int i = 0;
        int currentProcessIndex = 0;
        boolean hasProcessStarted = false;
        boolean isCurrentProcessDone = true;
        ArrayList<Integer> doneList = new ArrayList<>(); //Finished Processes

        double totalTimeElapsed = 0;
        double totalBurstTime = 0;

        for (int value : bt) {
            totalBurstTime += value;
        }

        boolean isProcessCalled[] = new boolean[at.length];
        for(int j = 0; j < at.length; j++) {
            isProcessCalled[j] = false;
        }

        boolean hasProcessArrived[] = new boolean[at.length];
        for(int j = 0; j < at.length; j++) {
            hasProcessArrived[j] = false;
        }

        for(int seconds = 0; !(doneList.size()==at.length); seconds++) {

            //If the current arrival time has not been reached

            if(hasProcessStarted && i<at.length){

                //CHECK IF PROCESS IS DONE
                if(bt[i] == 0) {
                    outputText = outputText + " " + indices[i] + " " + currentSecondsCount + "X\n";
                    totalTimeElapsed = (double) seconds;

                    currentSecondsCount = 0;
                    isCurrentProcessDone = true;
                    doneList.add(i); //Index of the process is added into a list of finished processes
                    i++;

                }

                if(!isCurrentProcessDone) {
                    bt[i]--;
                    currentSecondsCount++;
                }

            }

            if(isCurrentProcessDone && i<at.length) { //If the process is done, reroute the index to the next process needed to be done
                if(seconds>=at[i]) { //Regular arrival time checking compared to seconds
                    outputText = outputText + seconds;
                    isProcessCalled[i] = true;
                    hasProcessStarted = true;
                    isCurrentProcessDone = false;
                    outResponse[i] = seconds - at[i];
                    seconds--;
                }
            }

        }

        double cpuUtilization =  (totalBurstTime / totalTimeElapsed) * 100;
        double throughputCount = at.length / totalTimeElapsed;

        outputText = outputText + "Total time elapsed: " + totalTimeElapsed + "ns\n";
        outputText = outputText + "Total CPU burst time: " + totalBurstTime + "ns\n";
        outputText = outputText + "CPU Utilization: " + cpuUtilization + "%\n";
        outputText = outputText + "Throughput: " + throughputCount + " processes/ns\n";

        sortOrderByIndices();

        for(int j = 0; j<outResponse.length; j++) {
            if(outResponse[j] == -1) {
                outResponse[j]++;
            }
        }

        outWait = outResponse;
        //Out Turnaround time
        for(int j = 0; j<dupBt.length; j++) {
            outTurn[j] = dupBt[j] + outResponse[j];
        }

        //Wait Time
        double aveWaitTime = 0;
        outputText = outputText + "Waiting times: \n";
        for(int j = 1; j <= outWait.length; j++) {
            outputText = outputText + " Process " + j + ": " + outWait[j-1] + "ns\n";
            aveWaitTime += outWait[j-1];
        }
        outputText = outputText + "Average waiting time: " + (aveWaitTime/outWait.length) + " ns\n";

        //Turnaround Time
        double aveTurnTime = 0;
        outputText = outputText + "Turnaround times: \n";
        for(int j = 1; j <= outTurn.length; j++) {
            outputText = outputText + " Process " + j + ": " + outTurn[j-1] + "ns\n";
            aveTurnTime += outTurn[j-1];
        }
        outputText = outputText + "Average turnaround time: " + (aveTurnTime/outTurn.length) + " ns\n";

        //Response Time
        double aveResTime = 0;
        outputText = outputText + "Response times: \n";
        for(int j = 1; j <= outResponse.length; j++) {
            outputText = outputText + " Process " + j + ": " + outResponse[j-1] + "ns\n";
            aveResTime += outResponse[j-1];
        }
        outputText = outputText + "Average response time: " + (aveResTime/outResponse.length) + " ns\n";

        return outputText;
    }

    public String solveAll() {
        String output = fcfsFunc();
        return output;
    }

    //Function to sort the order of the arrays by arrival time
    public void sortOrderByArrival() {
        //Sort by arrival time
        for(int i = 0; i < at.length - 1; i ++) {

            for(int j = 0; j < at.length-i-1; j++) {

                if(at[j] > at[j+1]) {

                    //Sorts arrival time
                    int temp = at[j];
                    at[j] = at[j+1];
                    at[j+1] = temp;

                    //Sorts burst time
                    temp = bt[j];
                    bt[j] = bt[j+1];
                    bt[j+1] = temp;

                    //Sorts nice level
                    temp = nl[j];
                    nl[j] = nl[j+1];
                    nl[j+1] = temp;

                    //Sorts the indexes
                    temp = indices[j];
                    indices[j] = indices[j+1];
                    indices[j+1] = temp;

                }

            }

        }
    }

    public void sortOrderByIndices() {
        //Sort by indices time
        for(int i = 0; i < at.length - 1; i ++) {

            for(int j = 0; j < at.length-i-1; j++) {

                if(indices[j] > indices[j+1]) {

                    //Sorts arrival time
                    int temp = at[j];
                    at[j] = at[j+1];
                    at[j+1] = temp;

                    //Sorts burst time
                    temp = bt[j];
                    bt[j] = bt[j+1];
                    bt[j+1] = temp;

                    //Sorts nice level
                    temp = nl[j];
                    nl[j] = nl[j+1];
                    nl[j+1] = temp;

                    //Sorts the indexes
                    temp = indices[j];
                    indices[j] = indices[j+1];
                    indices[j+1] = temp;

                    temp = outWait[j];
                    outWait[j] = outWait[j+1];
                    outWait[j+1] = temp;

                    temp = outResponse[j];
                    outResponse[j] = outResponse[j+1];
                    outResponse[j+1] = temp;

                    temp = outTurn[j];
                    outTurn[j] = outTurn[j+1];
                    outTurn[j+1] = temp;

                }

            }

        }
    }

}
