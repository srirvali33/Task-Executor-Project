package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFifoQueue;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor {

	BlockingFifoQueue blocking_object;

	public TaskExecutorImpl(int total_no_threads) {

		blocking_object = new BlockingFifoQueue(100);
		TaskRunner taskrunner = new TaskRunner(blocking_object);
		total_no_threads = 100;
		for (int i = 0; i < total_no_threads; i++) {

			Thread newthread = new Thread(taskrunner);
			newthread.start();

		}
	}

	@Override
	public void addTask(Task task) {

		blocking_object.enqueue(task);
	}

}
