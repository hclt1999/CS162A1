import java.util.ArrayList;
import java.util.HashMap;

public class RoundRobin {

    private int at[];
    private int bt[];
    private int nl[];
    private int Q;
    private int indices[];
    private Fcfs fcfs;

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
        fcfs = new Fcfs(at,bt,nl);
    }

    public String rrFunc() {

        //Output String
        String outputText = "";

        //Output Arrangement
        int out[] = new int[at.length];

        //Output Waiting Times
        int outWait[] = new int[at.length];

        //Output Turnaround Times
        int outTurn[] = new int[at.length];

        //Output Response Times
        int outResponse[] = new int[at.length];

        //Sort by arrival time
        sortOrderByArrival();
        int currentSecondsCount = 0;
        int i = 0;
        int currentProcessIndex = 0;
        boolean hasProcessStarted = false;
        boolean isCurrentProcessDone = true;
        boolean isOldProcess = false;

        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<Integer> doneList = new ArrayList<>();
        HashMap<Integer, Integer> doneProcess = new HashMap<>(); //all unfinished processes that have already entered the system

        for(int seconds = 0; !(doneList.size()==bt.length); seconds++) {

            //If the current arrival time has not been reached

            if(hasProcessStarted && currentProcessIndex<at.length){

                //CHECK IF PROCESS IS DONE
                if(bt[currentProcessIndex] == 0) {

                    outputText = outputText + " " + indices[currentProcessIndex] + " " + currentSecondsCount + "X\n";

                    currentSecondsCount = 0;
                    isCurrentProcessDone = true;
                    doneList.add(currentProcessIndex);
                    if(!isOldProcess) {
                        i++;
                        currentProcessIndex = i;
                    }

                } else if(currentSecondsCount == Q && !(bt[currentProcessIndex] == 0)) { //PROCESS IS NOT YET DONE BUT REACHED QUANTUM TIME

                    outputText = outputText + " " + indices[i] + " " + currentSecondsCount + "\n";

                    currentSecondsCount = 0;
                    indexList.add(currentProcessIndex);
                    isCurrentProcessDone = true;
                    if(!isOldProcess) {
                        i++;
                        currentProcessIndex = i;
                    }

                }

                if(!isCurrentProcessDone) {
                    bt[currentProcessIndex]--;
                    currentSecondsCount++;
                }

            }

            if(isCurrentProcessDone && i<at.length && currentProcessIndex<at.length) {

                outputText = outputText + seconds;

                if(seconds>=at[i]) {

                    hasProcessStarted = true;
                    currentProcessIndex = i;
                    isOldProcess = false;

                } else if(!indexList.isEmpty()){
                    currentProcessIndex = indexList.get(0);
                    indexList.remove(0);
                    hasProcessStarted = true;
                    isOldProcess = true;
                }

                isCurrentProcessDone = false;
                seconds--;
            }


        }

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

}
