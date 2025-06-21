package sample;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;


public class Inventory {
    private static final List<String> MEDICINE_LIST = Arrays.asList(
        "Paracetamol", "Biogesic", "Coldzep", "Robitussin", "Cetirizine", "Sting"
    );

    private static final Map<String, Integer> STOCK_LIST = new HashMap<>();

    static {
        STOCK_LIST.put("Paracetamol", 324);
        STOCK_LIST.put("Biogesic", 7);
        STOCK_LIST.put("Coldzep", 249);
        STOCK_LIST.put("Robitussin", 34);
        STOCK_LIST.put("Cetirizine", 94);
        STOCK_LIST.put("Amoxicillin", 7842);
        STOCK_LIST.put("Ibuprofen", 84);
        STOCK_LIST.put("Loratadine", 72);
        STOCK_LIST.put("Omeprazole", 0);
        STOCK_LIST.put("Amoxicillin", 93);
        STOCK_LIST.put("Sting", 9993);
    }
    
    public static List<String> getAllMedicines() {
        List<String> sorted = new ArrayList<>(STOCK_LIST.keySet());
        Collections.sort(sorted); // A-Z sorting
        return sorted;
    }
    
    public static void addMedicine(String name, int stock) {
        if (!MEDICINE_LIST.contains(name)) {
            MEDICINE_LIST.add(name);
        }
        STOCK_LIST.put(name, stock);
    }


    public static int getStock(String medicineName) {
        return STOCK_LIST.getOrDefault(medicineName, 0);
    }

    public static boolean isAvailable(String name) {
        return MEDICINE_LIST.contains(name);
    }

    public static void setStock(String name, int amount) {
        STOCK_LIST.put(name, amount);
    }
}

