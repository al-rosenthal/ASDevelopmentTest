import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started");

        /// File reading class

        try {
            Graph g = readFile("src/clothing.txt");
            StringBuilder builder = new StringBuilder();
            System.out.println(g.usage.size());
            for(String key: g.usage.keySet()) {
                builder.append(key +": ");
                builder.append(g.usage.get(key) +", ");
                builder.append("\n");
            }
            System.out.println("Usage: \n" + builder.toString());
//            Graph.depthSort(g);
            Graph.kahnSort(g);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

    }

    public static Graph readFile(String fileName) throws FileNotFoundException {
        Graph graph = new Graph();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pairs = line.split("->");

            // assume all lines in file will have only 2 items
            if (pairs.length == 2) {
                graph.addNode(pairs[0]);
                graph.addNode(pairs[1]);
                graph.addDependency(pairs[0], pairs[1]);
            }
        }
        scanner.close();
        System.out.println(graph.toString());

        return graph;
    }
}

class Graph {
    Map<String, List<String>> list = new HashMap<>();
    Map<String, Integer> usage = new HashMap<>();
    public void addNode(String n) {
        list.putIfAbsent(n, new ArrayList<>());
        usage.putIfAbsent(n, 0);
    }

    public void addDependency(String s, String e) {
        list.get(s).add(e);
        Integer count = usage.get(e);
        count++;
        usage.replace(e, count);
    }

    public List<String> getDependencies(String n) {
        return list.get(n);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(String key: list.keySet()) {
            builder.append(key +": ");
            for(String item: list.get(key)) {
                builder.append(item +", ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void kahnSort(Graph g) {
        List<String> order = new ArrayList<>();
        List<String> group = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
//        int level = 0;

        // get starting nodes
        List<String> sorted = new ArrayList<>(g.usage.keySet());
        for (String key: sorted) {
            if (g.usage.get(key) == 0) {
                queue.add(key);
            }
        }

        System.out.println("STARTING STACK: " + queue.toString());
        int level = 0;
        while(!queue.isEmpty()) {
            String key = queue.remove();

            order.add(key);
            System.out.println("Key: " + key + " Level: " + level);
            for(String nextItem: g.list.get(key)) {
                Integer count = g.usage.get(nextItem);
                count--;
                g.usage.replace(nextItem, count);

                if (g.usage.get(nextItem) == 0) {
                    System.out.println("Adding: " + nextItem + " to queue.");
                    level++;
                    queue.add(nextItem);
                }
            }
        }

        for(String key: g.usage.keySet()) {
            if (g.usage.get(key) != 0) {
                System.out.println("THERE IS SOMETHING BAD IN THE DATA");
            }
        }
        System.out.println(order.toString());
    }

    // depth first sort
    public static void depthSort(Graph g) {
        Stack<String> stack = new Stack<>();
        int level = 0;
        Map<Integer, List<String>> order = new HashMap<>();
        Map<String, Boolean> checked = new HashMap<>();
        for(String key: g.list.keySet()) {
            checked.put(key, false);
        }

        for(String key: g.list.keySet()) {
            System.out.println("First level: " + key);
            if (!checked.get(key)) {
                check(g, key, checked, stack, order, level);
            }
        }

        System.out.println(order);

        while(!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

    public static void check(Graph g, String key, Map<String, Boolean> checked, Stack<String> stack, Map<Integer, List<String>>order, int level) {
        checked.replace(key, true);
//        order.putIfAbsent(level, new ArrayList<>());

        for (String value: g.list.get(key)) {
            if (!checked.get(value)) {
                level++;
                check(g, value, checked, stack, order, level);
            }
        }

//        System.out.println(localLevel);
//        order.get(localLevel).add(key);

        stack.add(key);
    }
}

/*

    Steps
    1. Read a file
    2. Parse data into useful format
    3. Place data into structure (Directed Graph)
    4. Print out correct order

    // Bonus prompt user for a file?

 */