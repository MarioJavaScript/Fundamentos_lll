package pruebaArrayList;
import java.util.ArrayList;

public class PruebaArrayList {
    // author Mario Francisco Ramirez Morales
    //date 2025-08-31
    public static void main(String[] args) {
        ArrayList<String> personalities = new ArrayList<>();
        personalities.add("Ada Lovelace");
        personalities.add("Alan Turing");
        personalities.add("Grace Hopper");
        String name = personalities.get(1);
        System.out.println(name);
        personalities.size();
        personalities.remove(0);
        personalities.set(1, "Margaret Hamilton");
        System.out.println(personalities);

    }

}
