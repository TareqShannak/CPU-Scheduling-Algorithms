package application;

import java.util.Comparator;
/*
|------------------------------------------------------- |
| This Process class has all the required attributes that|
| each scheduling algorithms needs.                      |
|------------------------------------------------------- |
*/
public class Process {
	private int ID;
	private int starttime;
	private int finishtime;
	private int repeat = 1;
	private int burst;
	private int remainingBurst;
	private int runningBurst = 0;
	private int arrival;
	private boolean flag = false; // flag if first time on cpu
	private boolean finishflag = false;
	private int priority;
	private int priority2;
	private int turnaround;
	private int waitingtime;
	private double WTA;


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getStarttime() {
		return starttime;
	}

	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}

	public int getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(int finishtime) {
		this.finishtime = finishtime;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getBurst() {
		return burst;
	}

	public void setBurst(int burst) {
		this.burst = burst;
	}

	public int getRemainingBurst() {
		return remainingBurst;
	}

	public void setRemainingBurst(int remainingBurst) {
		this.remainingBurst = remainingBurst;
	}

	public int getRunningBurst() {
		return runningBurst;
	}

	public void setRunningBurst(int runningBurst) {
		this.runningBurst = runningBurst;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isFinishflag() {
		return finishflag;
	}

	public void setFinishflag(boolean finishflag) {
		this.finishflag = finishflag;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority2() {
		return priority2;
	}

	public void setPriority2(int priority2) {
		this.priority2 = priority2;
	}

	public int getTurnaround() {
		return turnaround;
	}

	public void setTurnaround(int turnaround) {
		this.turnaround = turnaround;
	}

	public int getWaitingtime() {
		return waitingtime;
	}

	public void setWaitingtime(int waitingtime) {
		this.waitingtime = waitingtime;
	}

	public double getWTA() {
		return WTA;
	}

	public void setWTA(double wTA) {
		WTA = wTA;
	}

	public Process(int iD, int arrival, int burst, int priority) {
		this.burst = burst;
		this.arrival = arrival;
		this.ID = iD;
		this.priority = priority;
		this.remainingBurst = burst;
		this.priority2 = 0;
	}

	@Override
	public String toString() {
		return "Process [starttime=" + starttime + ", finishtime=" + finishtime + ", turnaround=" + turnaround
				+ ", waitingtime=" + waitingtime + ", burst=" + burst + ", arrival=" + arrival + ", ID=" + ID + "]";
	}
}
/*
|-------------------------------------------------------|
| method for sorting processes based on ID which        |
| essentially implements the idea of breaking a tie     |
| based on PID                                          |
|-------------------------------------------------------|
*/
class sortByID implements Comparator<Process> {
	public int compare(Process a, Process b) {
		return a.getID() - b.getID();
	}
}
/*
|-------------------------------------------------------|
| method for sorting processes based on their burst     |
| for the scheduling algorithms SJF and SRTF.           |
|-------------------------------------------------------|
*/
class sortByBurst implements Comparator<Process> {
	public int compare(Process a, Process b) {
		return a.getRemainingBurst() - b.getRemainingBurst();
	}
}
/*
|-------------------------------------------------------|
| method for sorting processes based on their priority  |
| for the scheduling algorithm priority.                |
|-------------------------------------------------------|
*/
class sortByPriority implements Comparator<Process> {
	public int compare(Process a, Process b) {
		return a.getPriority() - b.getPriority();
	}
}
/*
|-------------------------------------------------------|
| method for sorting processes based on their virtual   |
|   priority for any scheduling algorithm has aging.    |
|-------------------------------------------------------|
*/
class sortByPriority2 implements Comparator<Process> {
	public int compare(Process a, Process b) {
		return a.getPriority2() - b.getPriority2();
	}
}
/*
|-------------------------------------------------------|
| This method is to sort processes based on their       |
| arrival.                                              |
|-------------------------------------------------------|
*/
class sortByArrival implements Comparator<Process> {
	public int compare(Process a, Process b) {
		return a.getArrival() - b.getArrival();
	}
}