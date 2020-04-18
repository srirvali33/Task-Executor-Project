package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFifoQueue {

	int size;
	public Task BlockBuffer[];
	int taskCount;
	int EnqueTask;
	int DequeTask;
	Object QueueNotFull;
	Object QueueNotEmpty;

	public BlockingFifoQueue(int BufferSize) {
		size = BufferSize;
		BlockBuffer = new Task[size];
		taskCount = 0;
		EnqueTask = 0;
		DequeTask = 0;
		QueueNotFull = new Object();
		QueueNotEmpty = new Object();

	}

	public int enqueue(Task task) {
		try {
			synchronized (QueueNotFull) {
				if (taskCount >= size) {
					QueueNotFull.wait();
				}

			}
			synchronized (this) {
				BlockBuffer[EnqueTask] = task;
				taskCount += 1;
				EnqueTask += 1;
				EnqueTask = (EnqueTask) % size;// this so that we can maintain an index
												// with the limit of buffer size
				synchronized (QueueNotEmpty) {
					try {
						QueueNotEmpty.notify();
					} catch (Exception e) {
						System.err.println(e.toString());
					}

				}
			}

			return 1;
		} catch (Exception e) {
			System.err.println(e.toString());
			return -1;
		}
	}

	public Task dequeue() {
		Task task = null;
		try {
			while (true) { // It will remain in this and
							// It will keep on executing
				synchronized (QueueNotEmpty) {
					while (taskCount <= 0) {
						QueueNotEmpty.wait();
					}
				}
				synchronized (this) {
					if (taskCount > 0) {
						task = BlockBuffer[DequeTask];
						DequeTask += 1;
						DequeTask = DequeTask % size;
						taskCount -= 1;
						synchronized (QueueNotFull) {
							QueueNotFull.notify();
							return task;
						}

					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return task;

	}

}
