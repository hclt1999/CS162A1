import java.util.ArrayList;

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
        int currentProcessIndex = i;
        boolean hasStartedProcess = false;
        boolean isCurrentProcessDone = false;

        ArrayList<Integer> indexList = new ArrayList<>();

        for(int seconds = 0; seconds<10000000; seconds++) {

            if(seconds == at[i] || !hasStartedProcess || currentSecondsCount == Q || isCurrentProcessDone) {

                if(currentSecondsCount == Q && !isCurrentProcessDone) {
                    indexList.add(i);
                    i++;
                    currentSecondsCount = 0;
                } else if(isCurrentProcessDone && !hasStartedProcess) {

                    i++;
                    currentSecondsCount = 0;

                } else if(!isCurrentProcessDone && !hasStartedProcess) {

                    hasStartedProcess = true;

                }
            }

            if(hasStartedProcess && currentSecondsCount != Q) {



            } else {

                currentSecondsCount++;

            }

            //WAIT TIME COUNTING
            if(!indexList.isEmpty()) {
                for (int j = 0; j < indexList.size(); j++) {



                }
            }

        }

        //Output String
        String outputText = "";

        return outputText;
    }

    public String solveAll() {
        String output = rrFunc();
        return output;
    }

}
