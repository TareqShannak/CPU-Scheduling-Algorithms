package application;

import java.util.ArrayList;
import java.util.Collections;
/*
|-------------------------------------------------------|
| This class contains methods for a                     |
| all scheduling algorithm implemented in this project  |
| when the user pick an algorithm from the list view    |
| the selected method is called.                        |
|-------------------------------------------------------|
*/
public class Scheduler {
	public static ArrayList<Process> CPU = new ArrayList<Process>();
	public static ArrayList<Process> readyQueue = new ArrayList<Process>();
	static MyThread t = new MyThread();
	public static double waittimeavg;
	public static double taavg;
	public static double wtaavg;
	public static int currenttime;
	public static boolean done = false;
	public static int time;

	/*
	 -------------------------------------------------------------
	 first the method checks arrived process and then add them
	 to the ready QUEUE if there is any then it sort the all
	 processes in the ready QUEUE by their burst time then assign
	 the process with the lowest burst to the CPU and this method
	 keeps track on time by a variable called currenttime.This
	 algorithm has a priority with AGING which increase the priority
	 (priority--) in some idea. If the burst of the method is
	 finished then the process is removed from the ready QUEUE.
	 If the ready QUEUE is empty and all processes have finished
	 calculateAverages and displayProcesses are called.
	 ************SJF depends on PRIORITY and burst time***********
	 --------------------------------------------------------------
	*/
	public static ArrayList<Process> SJF(ArrayList<Process> P) {
		clearOldData();
		currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
		time = currenttime;
		while (!done) {
			// readyQueue = considerAging1(readyQueue, time);
			//readyQueue = considerAging2(readyQueue, time);
			if (readyQueue.size() > 1) {
				Collections.sort(readyQueue, new sortByBurst());
				Collections.sort(readyQueue, new sortByPriority2());

			}
			if (!readyQueue.isEmpty()) {
				if (!readyQueue.get(0).isFlag()) {
					P.get(readyQueue.get(0).getID()).setStarttime(currenttime);
					P.get(readyQueue.get(0).getID()).setFlag(true);
				}
				CPU = runningState(CPU, readyQueue.get(0), readyQueue.get(0).getBurst());
				P.get(readyQueue.get(0).getID()).setFinishflag(true);
				P.get(readyQueue.get(0).getID()).setFinishtime(currenttime + readyQueue.get(0).getBurst());
				currenttime = currenttime + readyQueue.get(0).getBurst();
				readyQueue.remove(0);
				for (int i = time + 1; i <= currenttime; i++)
					readyQueue.addAll(checkArrival(P, i));
				time = currenttime;
			}
			done = areAllProcessesFinished(P);
			if (readyQueue.isEmpty() && !done) {
				currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
				time = currenttime;
			}
		}
		calculateAverages(P);
		displayProcesses(CPU);
		return CPU;
	}
	/*
	 ---------------------------------------------------------------------
	 first the method checks arrived process and then add them
	 to the ready QUEUE assign it keeps track on time by a
	 variable called currenttime. It checks if there are any processes
	 on the ready queue and if there are, then it assigns the CPU for
	 a specific time quantum given by the user
	 the burst of the method is finished then the process is removed
	 from the ready QUEUE. If the ready QUEUE is empty and all
	 processes have finished calculateAverages and displayProcesses
	 are called.
	 ----------------------------------------------------------------------
	*/
	public static ArrayList<Process> RR(ArrayList<Process> P, int Q) {
		clearOldData();
		currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
		time = currenttime;
		while (!done) {
			if (!readyQueue.isEmpty()) {
				if (!P.get(readyQueue.get(0).getID()).isFlag()) {
					P.get(readyQueue.get(0).getID()).setFlag(true);
					P.get(readyQueue.get(0).getID()).setStarttime(currenttime);
				}
				if (readyQueue.get(0).getRemainingBurst() > Q) {
					P.get(readyQueue.get(0).getID())
							.setRemainingBurst(P.get(readyQueue.get(0).getID()).getRemainingBurst() - Q);
					currenttime = currenttime + Q;
					for (int i = time + 1; i <= currenttime; i++)
						readyQueue.addAll(checkArrival(P, i));
					time = currenttime;
					CPU = runningState(CPU, readyQueue.get(0), Q);
					readyQueue.add(readyQueue.get(0));
					readyQueue.remove(0);
				} else {
					if (P.get(readyQueue.get(0).getID()).getRemainingBurst() == Q) {
						currenttime = currenttime + Q;
						P.get(readyQueue.get(0).getID()).setFinishtime(currenttime);
						P.get(readyQueue.get(0).getID()).setFinishflag(true);
						CPU = runningState(CPU, readyQueue.get(0), Q);
						readyQueue.remove(0);
						for (int kk = time + 1; kk <= currenttime; kk++)
							readyQueue.addAll(checkArrival(P, kk));
						time = currenttime;
					} else if (P.get(readyQueue.get(0).getID()).getRemainingBurst() < Q) {
						currenttime = currenttime + (P.get(readyQueue.get(0).getID()).getRemainingBurst());
						P.get(readyQueue.get(0).getID()).setFinishflag(true);
						P.get(readyQueue.get(0).getID()).setFinishtime(currenttime);
						CPU = runningState(CPU, readyQueue.get(0),
								P.get(readyQueue.get(0).getID()).getRemainingBurst());
						readyQueue.remove(0);
						for (int kk = time + 1; kk <= currenttime; kk++)
							readyQueue.addAll(checkArrival(P, kk));
						time = currenttime;
					}
				}
			}

			done = areAllProcessesFinished(P);
			if (readyQueue.isEmpty() && !done) {
				currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
				time = currenttime;
			}
		}
		calculateAverages(P);
		displayProcesses(CPU);
		return CPU;
	}
	/*
	 -------------------------------------------------------------
	 first the method checks arrived process and then add them
	 to the ready QUEUE if there is any then it sort the all
	 processes in the ready QUEUE by their burst time every time
	 unit, then assign the process with the lowest burst or lowest
	 remaining burst to the CPU and this method keeps track on time
	 by a variable called currenttime. If the burst of the method
	 is finished then the process is removed from the ready QUEUE.
	 If the ready QUEUE is empty and all processes have finished
	 calculateAverages and displayProcesses are called.
	 --------------------------------------------------------------
	*/
	public static ArrayList<Process> SRTF(ArrayList<Process> P) {
		clearOldData();
		currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
		time = currenttime;
		while (!done) {
			if (readyQueue.size() > 1)
				Collections.sort(readyQueue, new sortByBurst());
			if (!readyQueue.isEmpty()) {
				if (!readyQueue.get(0).isFlag()) {
					P.get(readyQueue.get(0).getID()).setStarttime(currenttime);
					P.get(readyQueue.get(0).getID()).setFlag(true);
				}
				P.get(readyQueue.get(0).getID())
						.setRemainingBurst(P.get(readyQueue.get(0).getID()).getRemainingBurst() - 1);
				CPU = runningState(CPU, readyQueue.get(0), 1);
				if (P.get(readyQueue.get(0).getID()).getRemainingBurst() == 0) {
					P.get(readyQueue.get(0).getID()).setFinishflag(true);
					P.get(readyQueue.get(0).getID()).setFinishtime(currenttime + 1);
					readyQueue.remove(0);
				}
				currenttime++;
				for (int i = time + 1; i <= currenttime; i++)
					readyQueue.addAll(checkArrival(P, i));
				time = currenttime;
			}
			done = areAllProcessesFinished(P);
			if (readyQueue.isEmpty() && !done) {
				currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
				time = currenttime;
			}
		}
		calculateAverages(P);
		displayProcesses(CPU);
		return CPU;
	}
	/*
	 -------------------------------------------------------------
	 first the method checks arrived process and then add them
	 to the ready QUEUE if there is any then it sort the all
	 processes in the ready QUEUE by their priority then assign
	 the process with the lowest priority to the CPU and this method
	 keeps track on time by a variable called currenttime. If the
	 burst of the method is finished then the process is removed
	 from the ready QUEUE.This algorithm has a priority with AGING
	 which increase the priority (priority--) in some idea. If the ready
	 QUEUE is empty and all processes have finished calculateAverages
	 and displayProcesses are called.
	 -------------------------------------------------------------
	*/
	public static ArrayList<Process> EPwithoutPreemption(ArrayList<Process> P) {
		clearOldData();
		currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
		time = currenttime;
		while (!done) {
			readyQueue = considerAging2(readyQueue, time);
			if (readyQueue.size() > 1) {
				Collections.sort(readyQueue, new sortByPriority());
				Collections.sort(readyQueue, new sortByPriority2());
			}
			if (!readyQueue.isEmpty()) {
				P.get(readyQueue.get(0).getID()).setStarttime(currenttime);
				P.get(readyQueue.get(0).getID()).setFlag(true);
				P.get(readyQueue.get(0).getID()).setFinishflag(true);
				currenttime = currenttime + readyQueue.get(0).getBurst();
				P.get(readyQueue.get(0).getID()).setFinishtime(currenttime);
				CPU = runningState(CPU, readyQueue.get(0), readyQueue.get(0).getBurst());
				readyQueue.remove(0);
				for (int i = time + 1; i <= currenttime; i++)
					readyQueue.addAll(checkArrival(P, i));
				time = currenttime;
			}
			done = areAllProcessesFinished(P);
			if (readyQueue.isEmpty() && !done) {
				currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
				time = currenttime;
			}
		}
		calculateAverages(P);
		displayProcesses(CPU);
		return CPU;
	}
	/*
	 -------------------------------------------------------------
	 first the method checks arrived process and then add them
	 to the ready QUEUE if there is any then it sort the all
	 processes in the ready QUEUE by their arrive time then assign
	 the first arrived process to the CPU and this method
	 keeps track on time by a variable called currenttime. If
	 the burst of the method is finished then the process is removed
	 from the ready QUEUE. If the ready QUEUE is empty and all
	 processes have finished calculateAverages and displayProcesses
	 are called.
	 --------------------------------------------------------------
	*/
	public static ArrayList<Process> FCFS(ArrayList<Process> P) {
		clearOldData();
		currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
		time = currenttime;
		while (!done) {

			if (readyQueue.size() > 1){

				Collections.sort(readyQueue, new sortByArrival());
				}

			if (!readyQueue.isEmpty()) {
				if (!readyQueue.get(0).isFlag()) {
					P.get(readyQueue.get(0).getID()).setStarttime(currenttime);
					P.get(readyQueue.get(0).getID()).setFlag(true);
				}
				CPU = runningState(CPU, readyQueue.get(0), readyQueue.get(0).getBurst());
				P.get(readyQueue.get(0).getID()).setFinishflag(true);
				P.get(readyQueue.get(0).getID()).setFinishtime(currenttime + readyQueue.get(0).getBurst());
				int x = currenttime + readyQueue.get(0).getBurst();
				for (int i = currenttime + 1; i <= x; i++) {
					readyQueue.addAll(checkArrival(P, i)); // Check if new
															// arrival of
															// process
					currenttime++;
				}
				readyQueue.remove(0);
			}
			done = areAllProcessesFinished(P);
			if (readyQueue.isEmpty() && !done) {
				currenttime = getNextProcessArrivalTime(P, readyQueue, currenttime);
				time = currenttime;
			}
		}
		calculateAverages(P);
		displayProcesses(CPU);
		return CPU;
	}

	/*
	 * return an array list that has all processes which arrived in specified time
	 * 			and they will be sorted by ID and by Priority
	 *  */
	public static ArrayList<Process> checkArrival(ArrayList<Process> P, int time) {
		ArrayList<Process> justArrived = new ArrayList<Process>();
		int count = 0;
		for (int i = 0; i < P.size(); i++)
			if (P.get(i).getArrival() == time) {
				justArrived.add(P.get(i));
				count++;
			}
		if (count > 1) {
			Collections.sort(justArrived, new sortByID());
		}
		return justArrived;
	}
	/*
	 * this method will calculate how much the Cpu work on the process in the current time
	 * 					before the cpu works on another process
	 *  */
	public static ArrayList<Process> runningState(ArrayList<Process> CPU, Process ready, int duration) {
		if (CPU.size() != 0 && CPU.get(CPU.size() - 1).getID() == ready.getID())
			CPU.get(CPU.size() - 1).setRunningBurst(CPU.get(CPU.size() - 1).getRunningBurst() + duration);
		else {
			Process p = new Process(ready.getID(), ready.getArrival(), ready.getBurst(), ready.getPriority());
			p.setRunningBurst(duration);
			CPU.add(p);
		}
		return CPU;
	}
	/*
	 * this method returns the earliest arrival time of a process
	 * */
	public static int getNextProcessArrivalTime(ArrayList<Process> P, ArrayList<Process> readyQueue, int currenttime) {
		while (readyQueue.isEmpty()) {
			readyQueue.addAll(checkArrival(P, currenttime));
			currenttime++;
		}
		currenttime--;
		return currenttime;
	}

	/*
	 * this method checks the arrival of process every time unit
	 */
	public static boolean areAllProcessesFinished(ArrayList<Process> P) {
		for (int j = 0; j < P.size(); j++)
			if (!P.get(j).isFinishflag())
				return false;
		return true;
	}
	/*
	 * this method display some information on console
	 *  */
	public static void displayProcesses(ArrayList<Process> CPU) {
		for (int i = 0; i < CPU.size() - 1; i++)
			System.out.print("P" + CPU.get(i).getID() + " , ");
		System.out.print("P" + CPU.get(CPU.size() - 1).getID() + "\n");
		System.out.println("\nWait time Avg = " + waittimeavg);
		System.out.println("ta Avg = " + taavg);
		System.out.println("wta Avg = " + wtaavg);
	}
	/*
	 *  to calculate the averages using MyThread class
	 *   */
	public static void calculateAverages(ArrayList<Process> P) {
		taavg = t.ta(P);
		waittimeavg = t.wt(P);
		wtaavg = t.wta(P);
	}
	/*
	 * Clear the data from the variables to be used in another new algorithm
	 *  */
	public static void clearOldData() {
		CPU.clear();
		readyQueue.clear();
		currenttime = 0;
		done = false;
		time = 0;
	}

	/*
	 * this method returns the process that has been waiting more than twice
	 * time of its remaining burst time
	 */
	public static ArrayList<Process> considerAging1(ArrayList<Process> P, int time) {
		for (int i = 0; i < P.size(); i++)
			if (!P.get(i).isFlag())
				P.get(i).setPriority2(
						P.get(i).getPriority() - (time - P.get(i).getArrival()) / (2 * P.get(i).getBurst()));
		return P;
	}

	/*
	 * waiting more than difference between the maximum and the minimum waiting
	 * time of all processes in the ready queue
	 */
	public static ArrayList<Process> considerAging2(ArrayList<Process> P, int time) {
		int x = getMax(readyQueue, time) - getMin(readyQueue, time);
		if (x == 0)
			return P;
		for (int i = 0; i < P.size(); i++)
			if (!P.get(i).isFlag())
				P.get(i).setPriority2(P.get(i).getPriority() - (time - P.get(i).getArrival()) / x);
		return P;
	}

	/*
	 * this method returns the process that had waited in the ready QUEUE the
	 * longest
	 */
	static int getMax(ArrayList<Process> p, int time) {
		int max = -1;
		for (int i = 0; i < p.size(); i++)
			if (time - p.get(i).getArrival() > max)
				max = time - p.get(i).getArrival();
		return max;
	}

	/*
	 * this method returns the process that had waited in the ready QUEUE the
	 * shortest
	 */
	static int getMin(ArrayList<Process> p, int time) {
		int min = time - p.get(0).getArrival();
		for (int i = 1; i < p.size(); i++)
			if (time - p.get(i).getArrival() < min)
				min = time - p.get(i).getArrival();
		return min;
	}
}