import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started");

        /// File reading class
        try {
            DataGraph data = readFile("./src/clothing.txt");
//            DataOrganizer d = new DataOrganizer(data);



            // need to find the end
//            for (String key: map.keySet()) {
//                for (String item: map.get(key)) {
//                    // look for the end and work backwards
//                    if (item.length() == 0) {
//                        reverse.get(item);
//                    }
//                }
//            }

//            System.out.println("OG: \n" + d.printData(d.map));
//            System.out.println("Reverse: \n" + d.printData(d.reverse));

//            d.orderMap(d.findEnd());

           System.out.println(data.nodes.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
        return value + ": " + next + ".";
    }
}


class DataGraph {
    List<Relation> nodes = new ArrayList<>();

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
}

/*

    Steps
    1. Read a file
    2. Parse data into useful format
    3. Place data into structure (Directed Graph)
    4. Print out correct order

    // Bonus prompt user for a file?

 */