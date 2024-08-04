package org.example;

import org.example.strategy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalTest {

    static Stream<StorageStrategy> argsProviderFactory() {
        return Stream.of(new HashMapStorageStrategy(),
                new OurHashMapStorageStrategy(),
                new FileStorageStrategy(),
                new HashBiMapStorageStrategy(),
                new DualHashBidiMapStorageStrategy(),
                new OurHashBiMapStorageStrategy());
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testStorage(StorageStrategy strategy) {
        Shortener shortener = new Shortener(strategy);

        String testString1 = "Test string 1";
        String testString2 = "Test string 2";
        String testString3 = "Test string 1";

        Long testId1 = shortener.getId(testString1);
        Long testId2 = shortener.getId(testString2);
        Long testId3 = shortener.getId(testString3);

        assertEquals(testString1, shortener.getString(testId1));
        assertEquals(testString2, shortener.getString(testId2));
        assertEquals(testString3, shortener.getString(testId3));

        Assertions.assertNotEquals(testId1, testId2);
        Assertions.assertNotEquals(testId2, testId3);
        assertEquals(testId1, testId3);
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testContainsValue(StorageStrategy strategy) {
        // Добавляем элементы в таблицу
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        // Проверяем, что метод containsValue работает корректно
        assertTrue(strategy.containsValue("key1")); // Должно вернуть true
        assertFalse(strategy.containsValue("false")); // Должно вернуть false
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testContainsKey(StorageStrategy strategy) {
        // Добавляем элементы в таблицу
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        // Проверяем, что метод containsKey работает корректно
        assertTrue(strategy.containsKey(1L)); // Должно вернуть true
        assertFalse(strategy.containsKey(999L)); // Должно вернуть false
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testGetKey(StorageStrategy strategy) {
        // Добавляем элементы в таблицу
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        // Проверяем, что метод getKey возвращает правильные ключи
        assertEquals(1L, strategy.getKey("key1")); // Должно вернуть ключ "key1"
        assertEquals(2L, strategy.getKey("key2")); // Должно вернуть ключ "key2"
        assertNull(strategy.getKey("nonexistentValue")); // Должно вернуть null
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testGetValue(StorageStrategy strategy) {
        // Добавляем элементы в таблицу
        strategy.put(1L, "value1");
        strategy.put(2L, "value2");

        // Проверяем, что метод getValue возвращает правильные ключи
        assertEquals("value1", strategy.getValue(1L)); // Должно вернуть ключ "key1"
        assertEquals("value2", strategy.getValue(2L)); // Должно вернуть ключ "key2"
        assertNull(strategy.getValue(999L)); // Должно вернуть null
    }

    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testPut(StorageStrategy strategy) {
        // Добавляем элементы в таблицу
        strategy.put(1L, "value1");
        strategy.put(2L, "value2");

        // Проверяем, что элементы добавились корректно
        assertEquals("value1", strategy.getValue(1L));
        assertEquals("value2", strategy.getValue(2L));

        assertEquals(1L, strategy.getKey("value1"));
        assertEquals(2L, strategy.getKey("value2"));
    }
}

