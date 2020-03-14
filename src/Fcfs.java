
public class Fcfs {

    private int at[];
    private int bt[];
    private int nl[];
    private int indexes[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public Fcfs(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
        indexes = new int[at.length];
        //Make index arrays
        for(int i = 1; i <= at.length; i++) {
            indexes[i-1] = i;
        }
    }

    //METHODS FOR INSTRUCTIONS
    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public String fcfsFunc() {

        //Output Arrangement
        int out[] = new int[at.length];

        //Output Waiting Times
        int outWait[] = new int[at.length];

        //Output Turnaround Times
        int outTurn[] = new int[at.length];

        //Output Response Times
        int outResponse[] = new int[at.length];

        //Sort Arrival Time
        sortOrderByArrival();

        //Output String
        String outputText = "";

        //Order of the processes
        outputText += printOrder(at,bt,nl,indexes);

        //Waiting Time
        outWait[0] = 0;

        for (int i = 1; i < out.length; i++ ) {

            outWait[i] = at[i-1] + bt[i-1] + outWait[i-1];

        }

        //Turn Around Time

        for (int i = 0; i < outTurn.length; i++ ) {

            outTurn[i] = bt[i] + outWait[0];

        }

        //Response Time

        return outputText;
    }

    public String solveAll() {
        String output = fcfsFunc();
        return output;
    }

    //Function to print out the arrival time, burst time, and indexes in order
    public String printOrder(int[] a, int[] b, int[] c, int[] index) {
        String output = "";
        for(int i = 0; i < a.length; i++) {
            output = output + a[i] + " " + index[i] + " " + b[i] + "X";
            output = output + "\n";
        }
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
                    temp = indexes[j];
                    indexes[j] = indexes[j+1];
                    indexes[j+1] = temp;

                }

            }

        }
    }

    public int[] getAt() {
        return at;
    }

    public int[] getBt() {
        return bt;
    }

    public int[] getNl() {
        return nl;
    }

    public int[] getIndexes() {
        return indexes;
    }

}
