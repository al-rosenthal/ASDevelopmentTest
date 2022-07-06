import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started");

        /// File reading class
        try {
            List<Relation> data = readFile("./src/clothing.txt");

            HashMap<String, List<String>> map = new HashMap<>();
            // add first item
            // add relation to first item
            // add second item if not exists
            for(Relation r: data) {
                if (!map.containsKey(r.start)) {
                    map.put(r.start, new ArrayList<>());
                }
                if (!map.containsKey(r.end)) {
                    map.put(r.end, new ArrayList<>());
                }

                map.get(r.end).add(r.start);
            }


            StringBuilder builder = new StringBuilder();
            for (String key: map.keySet()) {
                builder.append(key + ": ");
                for (String item: map.get(key)) {
                    builder.append(item + ", ");
                }
                builder.append("\n");
            }

            System.out.println(builder.toString());

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
class Graphs {
    List<List<String>> data = new ArrayList<>();

    Graphs(List<Relation> relations) {

        for (Relation r : relations) {

        }
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