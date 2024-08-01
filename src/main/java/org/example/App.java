package org.example;

import org.example.strategy.*;

import java.util.*;

public class App {
    public static void main(String[] args) {
        long elementsNumber = 10000;

        List<StorageStrategy> list = new ArrayList<>();

        list.add(new OurHashMapStorageStrategy());
        list.add(new OurHashBiMapStorageStrategy());
        list.add(new DualHashBidiMapStorageStrategy());
        list.add(new HashMapStorageStrategy());
        list.add(new HashBiMapStorageStrategy());
        list.add(new FileStorageStrategy());

        for (StorageStrategy strategy : list) {
            testStrategy(strategy, elementsNumber);
        }
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName() + ":");

        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < elementsNumber; ++i) {
            origStrings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date startTimestamp = new Date();
        Set<Long> keys = getIds(shortener, origStrings);
        Date endTimestamp = new Date();
        long time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time);

        startTimestamp = new Date();
        Set<String> strings = getStrings(shortener, keys);
        endTimestamp = new Date();
        time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time);

        if (origStrings.equals(strings))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");

        Helper.printMessage("");
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> keys = new HashSet<>();
        for (String s : strings) {
            keys.add(shortener.getId(s));
        }
        return keys;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> strings = new HashSet<>();
        for (Long k : keys) {
            strings.add(shortener.getString(k));
        }
        return strings;
    }
}
