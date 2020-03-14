import java.util.ArrayList;
import java.util.HashMap;

public class RoundRobin {

    private int at[];
    private int bt[];
    private int nl[];
    private int Q;
    private int indexes[];
    private Fcfs fcfs;

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public RoundRobin(int a[], int b[], int c[], int q) {
        at = a;
        bt = b;
        nl = c;
        Q = q;
        indexes = new int[at.length];
        //Make index arrays
        for(int i = 1; i <= at.length; i++) {
            indexes[i-1] = i;
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
        fcfs.sortOrderByArrival();
        at = fcfs.getAt();
        bt = fcfs.getBt();
        nl = fcfs.getNl();
        indexes = fcfs.getIndexes();
        int currentSecondsCount = 0;
        int i = 0;
        int currentProcessIndex = 0;
        boolean hasStartedProcess = false;
        boolean isCurrentProcessDone = false;

        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<Integer> doneList = new ArrayList<>();
        HashMap<Integer, Integer> doneProcess = new HashMap<>(); //all unfinished processes that have already entered the system

        for(int seconds = 0; !(doneList.size()==bt.length); seconds++) {

            if(at[i] >= seconds || (currentSecondsCount == Q || bt[i] == 0) || !hasStartedProcess) {

                if(!hasStartedProcess) { //Hasn't started the process

                    outputText = outputText + seconds + " ";
                    if(at[i] >= seconds && i<bt.length) {
                        currentProcessIndex = i;
                    } else if(!indexList.isEmpty()){
                        currentProcessIndex = indexList.get(0);
                    }
                    hasStartedProcess = true;

                }

                if(currentSecondsCount == Q || bt[currentProcessIndex] == 0) { //IF REACH THE QUANTUM TIME OR NO MORE BURST TIME LEFT

                    if(bt[currentProcessIndex] == 0) {

                        outputText = outputText + indexes[currentProcessIndex] + " " + currentSecondsCount + "X\n";

                        currentSecondsCount = 0;

                        doneList.add(currentProcessIndex);
                        hasStartedProcess = false;
                        i++;

                    } else if(currentSecondsCount == Q && !(bt[currentProcessIndex]==0)){

                        outputText = outputText + indexes[currentProcessIndex] + " " + currentSecondsCount + "\n";

                        currentSecondsCount = 0;

                        hasStartedProcess = false;
                        indexList.add(currentProcessIndex);

                    }

                }

            } else {

                if(hasStartedProcess) {

                    currentSecondsCount++;
                    bt[currentProcessIndex]--;

                }

            }

        }

        for(int j = 0; j<bt.length;j++) {
            System.out.println(bt[j]);
        }

        return outputText;
    }

    public String solveAll() {
        String output = rrFunc();
        return output;
    }

}
