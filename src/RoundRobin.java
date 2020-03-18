import java.util.ArrayList;
import java.util.HashMap;

public class RoundRobin {

    private int at[];
    private int bt[];
    private int nl[];
    private int outWait[];
    private int outResponse[];
    private int outTurn[];
    private int Q;
    private int indices[];
    private int[] dupBt;

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public RoundRobin(int a[], int b[], int c[], int q) {
        at = a;
        bt = b;
        nl = c;
        Q = q;
        indices = new int[at.length];
        //Make index arrays
        for(int i = 1; i <= at.length; i++) {
            indices[i-1] = i;
        }
        outWait = new int[at.length];
        outTurn = new int[at.length];
        outResponse = new int[at.length];

        for(int j = 0; j < outResponse.length; j++) {
            outResponse[j] = -1;
        }

        dupBt = new int[bt.length];
        for (int j=0; j<dupBt.length; j++) { //initialize all wait times to 0
            dupBt[j]=bt[j];
        }

    }

    public String rrFunc() {

        //Output String
        String outputText = "";

        //Sort by arrival time
        sortOrderByArrival();

        //For Round Robin
        int currentSecondsCount = 0;
        int i = 0;
        int currentProcessIndex = 0;
        boolean hasProcessStarted = false;
        boolean isCurrentProcessDone = true;
        boolean isOldProcess = false;
        boolean wasChecked = false;
        ArrayList<Integer> indexList = new ArrayList<>(); //Unfinished Processes
        ArrayList<Integer> waitList = new ArrayList<>(); //Waiting Processes
        ArrayList<Integer> doneList = new ArrayList<>(); //Finished Processes

        double totalTimeElapsed = 0;
        double totalBurstTime = 0;

        for(int j = 0; j < bt.length; j++) {
            totalBurstTime += bt[j];
        }


        boolean isProcessCalled[] = new boolean[at.length];
        for(int j = 0; j < at.length; j++) {
            isProcessCalled[j] = false;
        }

        boolean hasProcessArrived[] = new boolean[at.length];
        for(int j = 0; j < at.length; j++) {
            hasProcessArrived[j] = false;
        }


        for(int seconds = 0; !(doneList.size()==bt.length); seconds++) {

            for(int j = 0; j<at.length; j++) {
                if(seconds == at[j]) {
                    hasProcessArrived[j] = true;
                }
                if(hasProcessArrived[j] && !isProcessCalled[j]) {
                    //outResponse[j]++;
                    if(!waitList.contains(j)) {
                        waitList.add(j);
                    }
                }
            }

            //If the current arrival time has not been reached
            if(hasProcessStarted && currentProcessIndex<at.length){

                //CHECK IF PROCESS IS DONE
                if(bt[currentProcessIndex] == 0) {
                    outputText = outputText + " " + indices[currentProcessIndex] + " " + currentSecondsCount + "X\n";

                    totalTimeElapsed = (double) seconds;
                    currentSecondsCount = 0;
                    isCurrentProcessDone = true;
                    hasProcessStarted = false;
                    doneList.add(currentProcessIndex); //Index of the process is added into a list of finished processes

                } else if(currentSecondsCount == Q && !(bt[currentProcessIndex] == 0)) { //PROCESS IS NOT YET DONE BUT REACHED QUANTUM TIME

                    outputText = outputText + " " + indices[currentProcessIndex] + " " + currentSecondsCount + "\n";

                    hasProcessStarted = false;
                    currentSecondsCount = 0; //Reset count to 0
                    isCurrentProcessDone = true;
                    indexList.add(currentProcessIndex); //Index of the process is added into a list of unfinished processes

                }

                if(!isCurrentProcessDone) {
                    bt[currentProcessIndex]--;
                    currentSecondsCount++;

                    //Check for waiting
                    if(!indexList.isEmpty()) {
                        for(int j = 0; j <indexList.size(); j++) {
                            outWait[indexList.get(j)]++;
                        }
                    }
                    if(!waitList.isEmpty()) {
                        for(int j = 0; j <waitList.size(); j++) {
                            outWait[waitList.get(j)]++;
                        }
                    }
                    //Check for response
                }

                if(isCurrentProcessDone) {

                    if(!isOldProcess) {
                        i++;
                        wasChecked = false;
                    }

                }

            }

            if(isCurrentProcessDone) { //If the process is done, reroute the index to the next process needed to be done
                if(i<at.length) {
                    if (seconds >= at[i]) { //Regular arrival time checking compared to seconds
                        outputText = outputText + seconds;
                        for (int j = 0; j < waitList.size(); j++) {
                            if (waitList.get(j) == i) {
                                waitList.remove(j);
                            }
                        }
                        outResponse[i] = seconds - at[i];
                        isProcessCalled[i] = true;
                        hasProcessStarted = true;
                        currentProcessIndex = i;
                        isOldProcess = false;
                        isCurrentProcessDone = false;
                        seconds--;
                        wasChecked = true;
                    }
                }
                if(!indexList.isEmpty() && !wasChecked){ //If no process arrival time has met the current count, check the old processes
                    outputText = outputText + seconds;
                    for(int j = 0; j<waitList.size(); j++) {
                        if(waitList.get(j) == i) {
                            waitList.remove(j);
                        }
                    }
                    currentProcessIndex = indexList.get(0);
                    indexList.remove(0);
                    hasProcessStarted = true;
                    isOldProcess = true;
                    isCurrentProcessDone = false;
                    seconds--;
                    wasChecked = false;
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

        //Out Turnaround time
        for(int j = 0; j<dupBt.length; j++) {
            outTurn[j] = dupBt[j] + outWait[j];
        }

        //Wait Time
        double aveWaitTime = 0;
        outputText = outputText + "Waiting times:\n";
        for(int j = 1; j <= outWait.length; j++) {
            outputText = outputText + " Process " + j + ": " + outWait[j-1] + "ns\n";
            aveWaitTime += outWait[j-1];
        }
        outputText = outputText + "Average waiting time: " + (aveWaitTime/outWait.length) + " ns\n";

        //Turnaround Time
        double aveTurnTime = 0;
        outputText = outputText + "Turnaround times:\n";
        for(int j = 1; j <= outTurn.length; j++) {
            outputText = outputText + " Process " + j + ": " + outTurn[j-1] + "ns\n";
            aveTurnTime += outTurn[j-1];
        }
        outputText = outputText + "Average turnaround time: " + (aveTurnTime/outTurn.length) + " ns\n";

        //Response Time
        double aveResTime = 0;
        outputText = outputText + "Response times:\n";
        for(int j = 1; j <= outResponse.length; j++) {
            outputText = outputText + " Process " + j + ": " + outResponse[j-1] + "ns\n";
            aveResTime += outResponse[j-1];
        }
        outputText = outputText + "Average response time: " + (aveResTime/outResponse.length) + " ns\n";

        return outputText;

    }

    public String solveAll() {
        String output = rrFunc();
        return output;
    }

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
