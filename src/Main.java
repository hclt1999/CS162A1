import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int methods = sc.nextInt();

        //Method Count
        for(int i = 0; i < methods; i++) {

            int instruction = sc.nextInt();
            String type = sc.next();

            //Inputs go into these 3 integer arrays
            int startTime[] = new int[instruction];
            int duration[] = new int[instruction];
            int priority[] = new int[instruction];

            //Take in the inputs by placing them into the arrays
            for(int j = 0; i < instruction; i++) {

                startTime[i] = sc.nextInt();
                duration[i] = sc.nextInt();
                priority[i] = sc.nextInt();

            }

            //Throw into the right method type
            if(type.equals("FCFS")) {



            } else if(type.equals("SJF")) {



            } else if(type.equals("SRTF")) {



            } else if(type.equals("P")) {



            } else if(type.equals("RR")) {



            }

        }

    }

    public static String fcfsFunc() {

        return null;
    }

    public static String sjfFunc() {

        return null;
    }

    public static String srtfFunc() {

        return null;
    }

    public static String pFunc() {

        return null;
    }

    public static String rrFunc() {

        return null;
    }

}
