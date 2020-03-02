import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int methods = sc.nextInt();

        //Method Count
        for(int i = 0; i < methods; i++) {

            int instruction = sc.nextInt();
            String type = sc.next();

            for(int j = 0; i < instruction; i++) {

                if(type.equals("FCFS")) {



                } else if(type.equals("SJF")) {



                } else if(type.equals("SRTF")) {



                } else if(type.equals("P")) {



                } else if(type.equals("RR")) {



                }

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
