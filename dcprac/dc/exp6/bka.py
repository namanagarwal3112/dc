import random

class Machine:
    def __init__(self, id):
        self.id = id
        self.clock = random.randint(0, 100)

    def get_id(self):
        return self.id

    def get_clock(self):
        return self.clock

    def set_clock(self, clock):
        self.clock = clock

class BerkeleyAlgorithm:
    def __init__(self, num_machines):
        self.machines = [Machine(i) for i in range(num_machines)]

    def synchronize_clocks(self):
        sum_clocks = sum(machine.get_clock() for machine in self.machines)
        average_clock = sum_clocks // len(self.machines)
        for machine in self.machines:
            machine.set_clock(average_clock)

    def print_clocks(self):
        for machine in self.machines:
            print("Machine {}: Clock = {}".format(machine.get_id(), machine.get_clock()))

if __name__ == "__main__":
    algorithm = BerkeleyAlgorithm(5)  # Number of machines
    print("Before synchronization:")
    algorithm.print_clocks()
    algorithm.synchronize_clocks()
    print("\nAfter synchronization:")
    algorithm.print_clocks()
