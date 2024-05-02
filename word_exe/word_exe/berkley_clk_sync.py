import time

# time server node index
TIME_SERVER_NODE = 0

# time server offset in seconds
TIME_SERVER_OFFSET = 0

class Node:
    def __init__(self, node_id, offset):
        self.id = node_id
        self.offset = offset
        self.clock = time.time()

    def adjust_clock(self, offset):
        self.clock += offset

def synchronize_clocks(nodes):
    server_time = nodes[TIME_SERVER_NODE].clock + TIME_SERVER_OFFSET
    for node in nodes:
        if node.id != TIME_SERVER_NODE:
            node_time = node.clock + node.offset
            offset = server_time - node_time
            node.adjust_clock(offset)

if __name__ == "__main__":
    nodes = [
        Node(1, 10),  # node 1 with 10 seconds offset
        Node(2, -5),  # node 2 with -5 seconds offset
        Node(3, 20)   # node 3 with 20 seconds offset
    ]

    print("Before:")
    for node in nodes:
        print(f"Node {node.id} clock: {node.clock}")

    synchronize_clocks(nodes)

    print("After:")
    for node in nodes:
        print(f"Node {node.id} clock: {node.clock}")
