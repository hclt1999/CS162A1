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
        double timeElapsed=0;
        double CPUbt=0;
        int outWait[] = new int[a.length]; //stores when the process starts to run
        int outTurn[] = new int[a.length]; //stores when the process ends
        int outResponse[] = new int[a.length]; //stores when it first runs
        
        ArrayList<Integer> out = new ArrayList<>(); //stores all done processes
        for (int sec=0; out.size()!=a.length; sec++) { //until all processes are finished
        	HashMap<Integer, Integer> validProcesses = new HashMap<>(); //all unfinished processes that have already entered the system
        	for (int i=a.length-1; i>=0; i--) { //stores in reverse order (in case of tie, it will get the first to appear)
        		if(a[i]<=sec && !out.contains(i)) {
        			validProcesses.put(i,b[i]);
        		}
        	}

        	if (!validProcesses.isEmpty()) {
	        	int minIndex = minBT(validProcesses); //gets the index of the minimum BT inside validProcesses
	        	outWait[minIndex] = sec;
	        	outTurn[minIndex] = sec + b[minIndex];
	        	outResponse[minIndex] = sec;
	        	out.add(minIndex); //adds it to done processes
	        	int outputIndex = minIndex + 1; 
	        	outputText = outputText + sec + " " + outputIndex + " " + b[minIndex] + "X\n";
	        	sec = sec + b[minIndex] - 1; //changes seconds
	        	CPUbt = CPUbt + b[minIndex];
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
        double u = (CPUbt/timeElapsed)*100;
        outputText = outputText + "CPU Utilization: " + u + "%\n";
        
        //Output Throughput
        double numProcess = a.length;
        double tp = numProcess/timeElapsed;
        outputText = outputText + "Throughput: " + tp + " processes/ns\n";
        
        //Output Waiting Times
        outputText = outputText + "Waiting times:\n";
        double sumWait = 0;
        for (int i=0; i<outWait.length; i++) {
        	int waitTime = outWait[i]-a[i];
        	sumWait = sumWait + waitTime;
        	int processNum = i+1;
        	outputText = outputText + " Process " + processNum + ": " + waitTime +  "ns\n";
        }
        outputText = outputText + "Average waiting time: " + sumWait/outWait.length +  "ns\n";

        //Output Turnaround Times
        outputText = outputText + "Turnaround times:\n";
        double sumTurn = 0;
        for (int i=0; i<outTurn.length; i++) {
        	int turnTime = outTurn[i]-a[i];
        	sumTurn = sumTurn + turnTime;
        	int processNum = i+1;
        	outputText = outputText + " Process " + processNum + ": " + turnTime +  "ns\n";
        }
        outputText = outputText + "Average turnaround time: " + sumTurn/outTurn.length +  "ns\n";

        //Output Response Times
        outputText = outputText + "Response times:\n";
        double sumRes = 0;
        for (int i=0; i<outResponse.length; i++) {
        	int resTime = outResponse[i]-a[i];
        	sumRes = sumRes + resTime;
        	int processNum = i+1;
        	outputText = outputText + " Process " + processNum + ": " + resTime +  "ns\n";
        }
        outputText = outputText + "Average response time: " + sumRes/outResponse.length +  "ns\n";

        return outputText;
    }
    
    public int minBT(HashMap<Integer, Integer> vp) {
    	if (!vp.isEmpty()) {
    		int index = (int) vp.keySet().toArray()[vp.size()-1];
    		int min = vp.get(index);
    		for (int i=vp.size()-1; i>=0;i--) {
    			int tempIndex = (int) vp.keySet().toArray()[i];
    			if (vp.get(tempIndex)<=min) {
    				min=vp.get(tempIndex);
    				index=tempIndex;
    			}
    		}
    		return index;
    	}
    	return -1;
    }

    public String solveAll() {
        String output = sjfFunc(at,bt,nl);
        return output;
    }

}
