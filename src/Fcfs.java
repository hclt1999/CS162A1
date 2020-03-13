import java.util.Scanner;

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

        //Sort by arrival time

        //Output String
        String outputText = "";

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

        return null;
    }

}
