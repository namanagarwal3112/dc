import multiprocessing

def worker_function(worker_id, shared_data):
    print("Worker {} received: {}".format(worker_id, shared_data.value))


if __name__ == "__main__":
    # Shared data among processes
    shared_data = multiprocessing.Value('i', 10)

    # Creating multiple processes
    num_workers = 3
    processes = []
    for i in range(num_workers):
        process = multiprocessing.Process(target=worker_function, args=(i, shared_data))
        processes.append(process)
        process.start()

    # Waiting for all processes to finish
    for process in processes:
        process.join()

    # Broadcasting data to all processes after they start
    shared_data.value = 42
