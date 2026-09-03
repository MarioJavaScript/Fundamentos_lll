package pruebaArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

class nameCount {
    String name;
    int count;

    public nameCount(String name) {
        this.name = name;
        this.count = 1;
    }

    public void incrementCount() {
        this.count++;
    }

    public String getName() {
        return this.name + "(" + this.count + ")";
    }
}

public class NombresArrayList {
    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\Users\\thexl\\OneDrive\\Desktop\\fundamentos3\\Fundamentos_lll\\fundamentos_3_2026\\pruebaArrayList\\listado.txt");
        ArrayList<String> namesList = readNamesFromFile(filePath.toString());
        printNames(namesList);
        
       
        ArrayList<String> countedList = countNames(namesList);
        System.out.println("\n--- Nombres Contados ---");
        for (String cName : countedList) {
            System.out.println(cName);
        }
    }
    
    public static ArrayList<String> readNamesFromFile(String filePath) {
        ArrayList<String> names = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            for (String line : (Iterable<String>) lines::iterator) {
                names.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
        return names;
    }
    
    public static void printNames(ArrayList<String> names) {
        for (String name : names) {
            System.out.println(name);
        }
    }
    
    
    public static ArrayList<String> countNames(ArrayList<String> names) {
        int pos = 0; // Posición a evaluar en el split
        ArrayList<nameCount> countedNames = new ArrayList<>();
        
        for (String name : names) {
            String[] splitName = name.split(" ");
            if (splitName.length > pos) {
                String targetName = splitName[pos];
                boolean found = false;
                
                for (nameCount nc : countedNames) {
                    
                    if (nc.name.equals(targetName)) {
                        nc.incrementCount();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    countedNames.add(new nameCount(targetName));
                }
            }
        }
        
        ArrayList<String> result = new ArrayList<>();
        for (nameCount nc : countedNames) {
            result.add(nc.getName());
        }
        return result;
    }
}