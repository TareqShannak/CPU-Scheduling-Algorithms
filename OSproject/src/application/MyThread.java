package application;

import java.util.ArrayList;
/*
|-------------------------------------------------------|
|The class myThread which extends the Thread class      |
|is called by each scheduling algorithm to print on the |
|console the CPU QUEUE which represents the Gantt chart.|
|-------------------------------------------------------|
*/

public class MyThread extends Thread {
/*
 * There are 3 methods to calculate the average of :
 *  turnaround, wait time and wta
 */
	public double ta(ArrayList<Process> p) {
		double x2 = 0;
		for (int i = 0; i < p.size(); i++) {
			p.get(i).setTurnaround(p.get(i).getFinishtime() - p.get(i).getArrival());
			x2 = x2 + p.get(i).getTurnaround();
		}
		x2 = x2 / p.size();
		return Math.round(x2*1000.0)/1000.0;
	}

	public double wt(ArrayList<Process> p) {
		double x1 = 0;
		for (int i = 0; i < p.size(); i++) {
			p.get(i).setWaitingtime(p.get(i).getTurnaround() - p.get(i).getBurst());
			x1 = x1 + p.get(i).getWaitingtime();
		}
		x1 = x1 / p.size();
		return Math.round(x1*1000.0)/1000.0;
	}

	public double wta(ArrayList<Process> p) {
		double x3 = 0;
		for (int i = 0; i < p.size(); i++) {
			p.get(i).setWTA(Math.round( 1000 * p.get(i).getTurnaround()/p.get(i).getBurst()) / 1000.0);
			x3 += p.get(i).getWTA();
		}
		x3 = x3 / p.size();
		return Math.round(x3*1000.0)/1000.0;
	}
}