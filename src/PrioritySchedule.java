public class PrioritySchedule {

    private static int at[];
    private static int bt[];
    private static int nl[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public PrioritySchedule(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
    }

    public static String pFunc(int a[], int b[], int c[]) {

        //Output Arrangement
        int out[] = new int[a.length];

        //Output Waiting Times
        int outWait[] = new int[a.length];

        //Output Turnaround Times
        int outTurn[] = new int[a.length];

        //Output Response Times
        int outResponse[] = new int[a.length];

        //Output String
        String outputText = "";

        return outputText;
    }

    public static String solveAll() {
        String output = pFunc(at,bt,nl);
        return null;
    }

}
