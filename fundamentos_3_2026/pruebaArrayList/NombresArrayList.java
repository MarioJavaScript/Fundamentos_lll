package pruebaArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class NombresArrayList {
    public static void main(String[] args) {
     Path filePath = Paths.get("C:\\Users\\thexl\\OneDrive\\Desktop\\fundamentos3\\Fundamentos_lll\\fundamentos_3_2026\\pruebaArrayList\\listado.txt");
     ArrayList<String> namesList = readNamesFromFile(filePath.toString());
     printNames(namesList);

     }
     public static void printNames(ArrayList<String> names) {
        

     }
     public static ArrayList<String> readNamesFromFile(String filePath) {
         ArrayList<String> names = new ArrayList<>();
         try{
             names = new ArrayList<>();
             Stream<String> lines = Files.lines(Paths.get(filePath));
             for(String line : (Iterable<String>) lines::iterator) {
                 names.add(line);
                 
             }

         }catch(Exception e){
             System.out.println("Error al crear el ArrayList");
         }
         return names;

     }
     

}

