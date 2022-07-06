import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started");

        /// File reading class
        try {
            DataGraph data = readFile("./src/clothing.txt");

           System.out.println(data.toString());
//            DataGraph.sortData(data);
            DataGraph.newSort(data);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static DataGraph readFile(String fileName) throws FileNotFoundException {
        DataGraph graph = new DataGraph();
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pairs = line.split("->");

            // assume all lines in file will have only 2 items
            if (pairs.length == 2) {
                Relation start = new Relation(pairs[0]);
                Relation end = new Relation(pairs[1]);

                Relation existing = graph.find(start.value);

                // not in graph
                if (existing == null) {
                    start.add(end.value);
                    graph.add(start);
                } else {
                    existing.add(end.value);
                }

                // not in graph
                if (graph.find(end.value) == null) {
                    graph.add(end);
                }


                // track this to find entry point into graph
                if (!graph.dataMap.containsKey(start.value)) {
                    graph.dataMap.put(start.value, new ArrayList<>());
                }
                if (!graph.dataMap.containsKey(end.value)) {
                    graph.dataMap.put(end.value, new ArrayList<>());
                }
                graph.dataMap.get(end.value).add(start.value);

                /*
                HashMap<String, List<String>> map = new HashMap<>();
                    HashMap<String, List<String>> reverse = new HashMap<>();

                    DataOrganizer(List<Relation> relations) {
                        for(Relation r: relations) {

                            if (!reverse.containsKey(r.start)) {
                                reverse.put(r.start, new ArrayList<>());
                            }
                            if (!reverse.containsKey(r.end)) {
                                reverse.put(r.end, new ArrayList<>());
                            }

                            reverse.get(r.end).add(r.start);
                        }
                    }
                 */
            }
        }
        scanner.close();

        return graph;
    }
}


class Relation {
    String value;
    List<String> next;
    Relation(String start) {
        this.value = start;
        this.next = new ArrayList<>();
    }

    public void add(String n) {
        this.next.add(n);
    }

    @Override
    public String toString() {
        return value + ": " + next;
    }
}

class DataGraph {
    List<Relation> nodes = new ArrayList<>();
    HashMap<String, List<String>> dataMap = new HashMap<>();

    public void add(Relation r) {
        this.nodes.add(r);
    }

    public Relation find(String key) {
        Relation found = null;
        for(Relation r: nodes) {
            if(r.value.equalsIgnoreCase(key)) {
                found = r;
            }
        }

        return found;
    }

    public static List<String> entryPoints(DataGraph g) {
        List<String> entry = new ArrayList<>();
        for (String key: g.dataMap.keySet()) {
            if (g.dataMap.get(key).isEmpty()) {
                entry.add(key);
            }
        }
        return entry;
    }

    /*
      L ← Empty list that will contain the sorted elements
      S ← Set of all nodes with no incoming edge

      while S is not empty do
          remove a node n from S
          add n to L
          for each node m with an edge e from n to m do
              remove edge e from the graph
              if m has no other incoming edges then
                  insert m into S

      if graph has edges then
          return error   (graph has at least one cycle)
      else
          return L   (a topologically sorted order)
   */
    public static void newSort(DataGraph g) throws Exception {
        // get starting points
        List<String> entryPoints = DataGraph.entryPoints(g);
        List<String> finalOrder = new ArrayList<>();

        int n = g.nodes.size();
        int[] inDegree = new int[n];

        for (int i = 0; i < g.dataMap.keySet().size(); i++) {

        }

        for (String key: g.dataMap.keySet()) {
            for(String edge: g.dataMap.get(key)) {

            }
        }

//        for(List<String> edges: g.dataMap) {
//
//        }


        if (entryPoints.size() > 0) {
            while(!entryPoints.isEmpty()) {
                String key = entryPoints.remove(0);
                finalOrder.add(key);


            }
        } else {
            throw new Exception("Data has no logical start point.");
        }
        System.out.println(entryPoints);
    }

    public static void sortData(DataGraph g) {
        List<String> finalOrder = new ArrayList<>();
        // track any node/ values that have already been checked
        HashMap<String, Boolean> checked = new HashMap<>();
        for (Relation r: g.nodes) {
            checked.put(r.value, false);
        }



        for(Relation r: g.nodes) {
            if (!checked.get(r.value)) {
                checkItem(r.value, g, checked, finalOrder);
            }
        }
        Collections.reverse(finalOrder);
        for(String item: finalOrder) {
            System.out.println(item);
        }
    }

    public static void checkItem(String value, DataGraph g, HashMap<String, Boolean> checked, List<String> finalOrder) {
        // item has been checked
        checked.replace(value, true);
        Relation node = g.find(value);
        System.out.println("Item to Check: " + value);
        if (node != null) {
            // check other relations
            for (String next : node.next) {
                if (!checked.get(next)) {
                    checkItem(next, g, checked, finalOrder);
                }
            }
        }

        finalOrder.add(value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(Relation r: nodes) {
            builder.append(r.toString());
            builder.append("\n");
        }

        return builder.toString();
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