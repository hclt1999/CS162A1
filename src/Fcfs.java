
public class Fcfs {

    private int at[];
    private int bt[];
    private int nl[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public Fcfs(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
    }

    //METHODS FOR INSTRUCTIONS
    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public String fcfsFunc(int a[], int b[], int c[]) {

        //Output Arrangement
        int out[] = new int[a.length];

        //Output Waiting Times
        int outWait[] = new int[a.length];

        //Output Turnaround Times
        int outTurn[] = new int[a.length];

        //Output Response Times
        int outResponse[] = new int[a.length];

        //Make index arrays
        int indexes[] = new int[a.length];
        for(int i = 1; i <= a.length; i++) {

            indexes[i-1] = i;

        }

        //Sort by arrival time
        for(int i = 0; i < a.length - 1; i ++) {

            for(int j = 0; j < a.length-i-1; j++) {

                if(a[j] > a[j+1]) {

                   //Sorts arrival time
                   int temp = a[j];
                   a[j] = a[j+1];
                   a[j+1] = temp;

                   //Sorts burst time
                   temp = b[j];
                   b[j] = b[j+1];
                   b[j+1] = temp;

                   //Sorts nice level
                   temp = c[j];
                   c[j] = c[j+1];
                   c[j+1] = temp;

                   //Sorts the indexes
                   temp = indexes[j];
                   indexes[j] = indexes[j+1];
                   indexes[j+1] = temp;

                }

            }

        }

        //Output String
        String outputText = "";

        //Order of the processes
        outputText += printOrder(a,b,c,indexes);

        //Waiting Time
        outWait[0] = 0;

        for (int i = 1; i < out.length; i++ ) {

            outWait[i] = a[i-1] + b[i-1] + outWait[i-1];

        }

        //Turn Around Time

        for (int i = 0; i < outTurn.length; i++ ) {

            outTurn[i] = b[i] + outWait[0];

        }

        //Response Time

        return outputText;
    }

    public String solveAll() {
        String output = fcfsFunc(at,bt,nl);
        return output;
    }

    //Function to print out the arrival time, burst time, and indexes in order
    public String printOrder(int[] a, int[] b, int[] c, int[] index) {
        String output = "";
        for(int i = 0; i < a.length; i++) {
            output = output + a[i] + " " + index[i] + " " + b[i] + "X";
            if(i < a.length-1) {
                output = output + "\n";
            }
        }
        return output;
    }

}
