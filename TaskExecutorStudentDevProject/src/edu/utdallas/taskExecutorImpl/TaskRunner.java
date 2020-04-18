package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFifoQueue;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {

	BlockingFifoQueue fifo_object;

	public TaskRunner(BlockingFifoQueue obj) {
		fifo_object = obj;
	}

	@Override
	public void run() {
		while (true) {

			Task execute_task = fifo_object.dequeue();

			try {

				execute_task.execute();
			} catch (Exception e) {

				System.out.println("Ignoring error in task: " + e.getMessage());
			}

		}

	}

}
