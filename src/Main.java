import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int methods = sc.nextInt(); //Number of Test Cases
        String outputs = "";

        //Method Count
        for(int i = 0; i < methods; i++) {

            int instruction = sc.nextInt(); //Number of Processes
            String type = sc.next(); //Algorithm Type

            outputs = outputs + instruction + " " + type + "\n";
            
            int Q=0;
            if(type.equals("RR")) {
                Q = sc.nextInt();
            }

            //Inputs go into these 3 integer arrays
            //at = Arrival Time, bt = Burst Time, nl = Nice Level
            int at[] = new int[instruction];
            int bt[] = new int[instruction];
            int nl[] = new int[instruction];

            //Take in the inputs by placing them into the arrays
            for(int j = 0; i < instruction; i++) {

                at[i] = sc.nextInt();
                bt[i] = sc.nextInt();
                nl[i] = sc.nextInt();

            }

            //Throw into the right method type
            if(type.equals("FCFS")) {

                Fcfs f = new Fcfs(at,bt,nl);
                String result = f.solveAll();
                System.out.println(result);

            } else if(type.equals("SJF")) {

                Sjf sjf = new Sjf(at,bt,nl);
                String result = sjf.solveAll();

            } else if(type.equals("SRTF")) {

                Srtf srtf = new Srtf(at,bt,nl);
                String result = srtf.solveAll();

            } else if(type.equals("P")) {

                PrioritySchedule ps = new PrioritySchedule(at,bt,nl);
                String result = ps.solveAll();

            } else if(type.equals("RR")) {

                RoundRobin rr = new RoundRobin(at,bt,nl,Q);
                String result = rr.solveAll();

            }

        }

    }










}
