import java.util.ArrayList;
import java.util.HashMap;

public class Srtf {

    private int at[];
    private int bt[];
    private int nl[];

    //a = Arrival Time , b = Burst Time , c = Nice Level , length of any of the arrays is the # of processors
    public Srtf(int a[], int b[], int c[]) {
        at = a;
        bt = b;
        nl = c;
    }

    public String srtfFunc(int a[], int b[], int c[]) {

        //Output String
        String outputText = "";
        
        //Output Arrangement
        int timeElapsed=0;
        int CPUbt=0;
        int outWait[] = new int[a.length]; //increments when process is valid but is not the currProcess
        for (int i=0; i<outWait.length; i++) { //initialize all wait times to 0
        	outWait[i]=0;
        }
        int outTurn[] = new int[a.length]; //stores when the process ends
        int outResponse[] = new int[a.length]; //stores when it first runs
        for (int i=0; i<outResponse.length; i++) { //initialize all response times to -1
        	outResponse[i]=-1;
        }
        
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
        			validProcesses.put(remainingBT[i], i);
        		}
        	}

        	int minIndex = minBT(validProcesses);
        	
        	//increment wait times
        	for (int i=0; i<outWait.length; i++) {
        		if (validProcesses.containsValue(i) && i!=minIndex) {
        			outWait[i]++;
        		}
        	}
        	
        	//if there's no new process with a shorter BT
        	if (currProcess==minIndex && !validProcesses.isEmpty()) {
        		CPUbt++;
        		//add current process' running time, subtract remaining BT needed to finish
        		currProcessRunningTime++;
        		remainingBT[minIndex]--;
        		if(remainingBT[minIndex]==0) { //if the process finishes
        			outTurn[minIndex]=sec+1; //plus 1 because it counts the last 1ns the last process ran
        			out.add(currProcess);
        			int outputIndex = minIndex + 1; 
        			outputText = outputText + processStarted + " " + outputIndex + " " + currProcessRunningTime + "X\n";
        			processFinished=true;
        		}
        	}
        	//process switches
        	else if (currProcess!=minIndex && !validProcesses.isEmpty()){
        		CPUbt++;
        		//print current process status
        		if (currProcess!=-1 && !processFinished) {
        			int outputIndex = currProcess + 1; 
        			outputText = outputText + processStarted + " " + outputIndex + " " + currProcessRunningTime + "\n";
        		}
        		//adds response time
        		if (outResponse[minIndex]==-1) {
        			outResponse[minIndex] = sec;
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

        	if (out.size()==a.length) {
        		timeElapsed=sec+1; //plus 1 because it counts the last 1ns the last process ran
        	}
        }

        //Output Total Time Elapsed
        outputText = outputText + "Total time elapsed: " + timeElapsed + "ns\n";
        
        //Output Total CPU Burst Time
        outputText = outputText + "Total CPU burst time: " + CPUbt + "ns\n";
        
        //Output CPU Utilization
        int u = (CPUbt/timeElapsed)*100;
        outputText = outputText + "CPU Utilization: " + u + "%\n";
        
        //Output Waiting Times
        outputText = outputText + "Waiting Times: \n";
        double sumWait = 0;
        for (int i=0; i<outWait.length; i++) {
        	int waitTime = outWait[i];
        	sumWait = sumWait + waitTime;
        	outputText = outputText + " Process " + i + ": " + waitTime +  "ns\n";
        }
        outputText = outputText + "Average waiting time: " + sumWait/outWait.length +  "ns\n";

        //Output Turnaround Times
        outputText = outputText + "Turnaround Times: \n";
        double sumTurn = 0;
        for (int i=0; i<outTurn.length; i++) {
        	int turnTime = outTurn[i]-a[i];
        	sumTurn = sumTurn + turnTime;
        	outputText = outputText + " Process " + i + ": " + turnTime +  "ns\n";
        }
        outputText = outputText + "Average turnaround time: " + sumTurn/outTurn.length +  "ns\n";

        //Output Response Times
        outputText = outputText + "Response Times: \n";
        double sumRes = 0;
        for (int i=0; i<outResponse.length; i++) {
        	int resTime = outResponse[i]-a[i];
        	sumRes = sumRes + resTime;
        	outputText = outputText + " Process " + i + ": " + resTime +  "ns\n";
        }
        outputText = outputText + "Average response time: " + sumRes/outResponse.length +  "ns\n";

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
        String output = srtfFunc(at,bt,nl);
        return output;
    }

}
