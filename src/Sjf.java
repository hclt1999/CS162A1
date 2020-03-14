import java.util.ArrayList;
import java.util.HashMap;

public class Sjf {

    private int at[];
    private int bt[];
    private int nl[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public Sjf(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
    }

    public String sjfFunc(int a[], int b[], int c[]) {

        //Output String
        String outputText = "";
        
        //Output Arrangement
        ArrayList<Integer> out = new ArrayList<>(); //stores all done processes
        for (int sec=0; out.size()!=a.length; sec++) { //until all processes are finished
        	HashMap<Integer, Integer> validProcesses = new HashMap<>(); //all unfinished processes that have already entered the system
        	for (int i=a.length-1; i>=0; i--) { //stores in reverse order (in case of tie, it will get the first to appear)
        		if(a[i]<=sec && !out.contains(i)) {
        			validProcesses.put(b[i], i);
        		}
        	}

        	if (!validProcesses.isEmpty()) {
	        	int minIndex = minBT(validProcesses); //gets the index of the minimum BT inside validProcesses
	        	out.add(minIndex); //adds it to done processes
	        	int outputIndex = minIndex + 1; 
	        	outputText = outputText + sec + " " + outputIndex + " " + b[minIndex] + "X\n";
	        	sec = sec + b[minIndex] - 1; //changes seconds
        	}
        }

        //Output Waiting Times
        int outWait[] = new int[a.length];

        //Output Turnaround Times
        int outTurn[] = new int[a.length];

        //Output Response Times
        int outResponse[] = new int[a.length];

        return outputText;
    }
    
    public int minBT(HashMap<Integer, Integer> vp) {
    	if (!vp.isEmpty()) {
	    	int min = (int) vp.keySet().toArray()[vp.size()-1];
	    	int index=vp.size()-1;
	    	if (vp.get(min) != null) {
	    		index = vp.get(min);
	    	}
	    	for (int bt : vp.keySet()) {
	    		if (bt<min) {
	    			min=bt;
	    			index=vp.get(bt);
	    		}
	    	}
	    	return index;
    	}
    	return 0;
    }

    public String solveAll() {
        String output = sjfFunc(at,bt,nl);
        return output;
    }

}
