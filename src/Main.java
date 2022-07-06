import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started");

        /// File reading class
        try {
            List<Relation> data = readFile("./src/clothing.txt");
            DataOrganizer d = new DataOrganizer(data);


            // need to find the end
//            for (String key: map.keySet()) {
//                for (String item: map.get(key)) {
//                    // look for the end and work backwards
//                    if (item.length() == 0) {
//                        reverse.get(item);
//                    }
//                }
//            }

            System.out.println("OG: \n" + d.printData(d.map));
            System.out.println("Reverse: \n" + d.printData(d.reverse));

            d.orderMap(d.findEnd());

//           System.out.println(data.toString());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static List<Relation> readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        List<Relation> data = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pairs = line.split("->");

            // assume all lines in file will have only 2 items
            if (pairs.length == 2) {
                data.add(new Relation(pairs[0], pairs[1]));
            }
        }
        scanner.close();

        return data;
    }
}


class Relation {
    String start, end;
    Relation(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
class DataOrganizer {
    HashMap<String, List<Relation>> map = new HashMap<>();
    HashMap<String, List<Relation>> reverse = new HashMap<>();

    DataOrganizer(List<Relation> relations) {
        for(Relation r: relations) {
            // add first item
            // add relation to first item
            // add second item if not exists
            if (!map.containsKey(r.start)) {
                map.put(r.start, new ArrayList<>());
            }
            if (!map.containsKey(r.end)) {
                map.put(r.end, new ArrayList<>());
            }

            if (!reverse.containsKey(r.start)) {
                reverse.put(r.start, new ArrayList<>());
            }
            if (!reverse.containsKey(r.end)) {
                reverse.put(r.end, new ArrayList<>());
            }

            map.get(r.start).add(r);
            reverse.get(r.end).add(r);
        }
    }

    public List<String> findEnd() {
        List<String> end = new ArrayList<>();

        for(String key: map.keySet()) {
            List<Relation> item = map.get(key);
            if (item.isEmpty()) {
                // found an end piece
                end.add(key);
            }
        }
        return end;
    }

    public void orderMap(List<String> node) {
        System.out.println(node);
        // probably would be better for work with these as relations
        // map them as a list of relations
        // then we can check what the parent is without having to scan the map?
        /*
            !empty
            for each item, look for them in the reverse
            check for relations up?


         */
        if(!node.isEmpty()) {
            for (String key: node) {
                List<String> items = new ArrayList<>();
                for (Relation r: reverse.get(key)) {
                    items.add(r.start);
                }
                orderMap(items);
            }
        } else {
            // I guess we are all done
        }

    }

    public String printData(HashMap<String, List<Relation>> data) {
        StringBuilder b = new StringBuilder();
        for (String key: data.keySet()) {
            b.append(key + ": ");
            for (Relation item: data.get(key)) {
                b.append(item.end + ", ");
            }
            b.append("\n");
        }
        return b.toString();
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