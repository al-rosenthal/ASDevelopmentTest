import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;


/*
      tie pants belt are different
 */
public class Main {
    public static void main(String[] args) {
        try {
            Graph g = readFile("src/clothing.txt");
            StringBuilder builder = new StringBuilder();
            Graph.kahnSort(g);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
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

    public static void kahnSort(Graph g) throws Exception {
//        List<String> basic = new ArrayList<>();
        List<String> group = new ArrayList<>();
        List<List<String>> finalGroupings = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();

        // get starting nodes
        for (String key: g.usage.keySet()) {
            if (g.usage.get(key) == 0) {
                queue.add(key);
            }
        }

        if (queue.isEmpty()) {
            throw new Exception("No logical starting point.");
        }

        while(!queue.isEmpty()) {
            String key = queue.remove();
            group.add(key);
//            basic.add(key);

            for(String nextItem: g.list.get(key)) {
                Integer count = g.usage.get(nextItem);
                count--;
                g.usage.replace(nextItem, count);

                if (g.usage.get(nextItem) == 0) {
                    // close instruction grouping and start a new one
                    if (!group.isEmpty()) {
                        finalGroupings.add(group);
                        group = new ArrayList<>();
                    }
                    queue.add(nextItem);
                }
            }
        }

        // catch the last run of the sort
        if (!group.isEmpty()) {
            finalGroupings.add(group);
        }

        for(String key: g.usage.keySet()) {
            if (g.usage.get(key) != 0) {
                throw new Exception("Circular data found, cannot sort properly.");
            }
        }

        StringBuilder builder = new StringBuilder();
        for(List<String> items: finalGroupings) {
            Collections.sort(items);
            builder.append(String.join(", ", items) + "\n");
        }
        System.out.println(builder.toString());
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