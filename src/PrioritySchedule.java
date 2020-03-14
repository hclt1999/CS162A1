import java.util.ArrayList;
import java.util.HashMap;

public class PrioritySchedule {

    private int at[];
    private int bt[];
    private int nl[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public PrioritySchedule(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
    }

    public String pFunc(int a[], int b[], int c[]) {

        //Output String
        String outputText = "";
        
        //Output Arrangement
        
        int remainingBT[] = bt; //tracks reduction in BT
        ArrayList<Integer> out = new ArrayList<>(); //stores all done processes
    	int currProcess = -1; //tracks if process will be changed
    	int currProcessRunningTime = 0; //tracks how long a process will run before its terminated
    	int processStarted = 0; //tracks at what second process started
    	boolean processFinished = false; //tracks if the last process was finished
    	
        for (int sec=0; out.size()!=a.length; sec++) { //until all processes are finished
        	HashMap<Integer, Integer> validProcesses = new HashMap<>(); //all unfinished processes that have already entered the system
        	for (int i=a.length-1; i>=0; i--) { //stores in reverse order (in case of tie, it will get the first to appear)
        		if(a[i]<=sec && !out.contains(i)) {
        			validProcesses.put(c[i], i);
        		}
        	}
        	int minIndex = minNL(validProcesses);
        	
        	//if there's no new process with a lower NL
        	if (currProcess==minIndex && !validProcesses.isEmpty()) {
        		//add current process' running time, subtract remaining BT needed to finish
        		currProcessRunningTime++;
        		remainingBT[minIndex]--;
        		if(remainingBT[minIndex]==0) { //if the process finishes
        			out.add(currProcess);
        			int outputIndex = minIndex + 1; 
        			outputText = outputText + processStarted + " " + outputIndex + " " + currProcessRunningTime + "X\n";
        			processFinished=true;
        		}
        	}
        	//process switches
        	else if (currProcess!=minIndex && !validProcesses.isEmpty()){
        		//print current process status
        		if (currProcess!=-1 && !processFinished) {
        			int outputIndex = currProcess + 1; 
        			outputText = outputText + processStarted + " " + outputIndex + " " + currProcessRunningTime + "\n";
        		}
        		//switch current process to new process
        		currProcess = minIndex;
        		currProcessRunningTime=0; //reset running time to 0
        		//run first ns of that process
        		currProcessRunningTime++;
        		remainingBT[minIndex]--;
        		//track when this new process started
        		processStarted = sec;
        		processFinished=false; //reset boolean to false (process just started)
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
    
    public int minNL(HashMap<Integer, Integer> vp) {
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
        String output = pFunc(at,bt,nl);
        return output;
    }

}
