package org.example;

import org.example.strategy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FunctionalTest {

    @ValueSource
    public Stream<StorageStrategy> streamListStorageStrategy() {
        return Stream.of(new HashMapStorageStrategy(),
                new HashBiMapStorageStrategy(),
                new OurHashMapStorageStrategy(),
                new OurHashBiMapStorageStrategy(),
                new FileStorageStrategy());
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testStorage(StorageStrategy strategy) {
        Shortener shortener = new Shortener(strategy);

        String testString1 = "Test string 1";
        String testString2 = "Test string 2";
        String testString3 = "Test string 1";

        Long testId1 = shortener.getId(testString1);
        Long testId2 = shortener.getId(testString2);
        Long testId3 = shortener.getId(testString3);

        Assertions.assertEquals(testString1, shortener.getString(testId1));
        Assertions.assertEquals(testString2, shortener.getString(testId2));
        Assertions.assertEquals(testString3, shortener.getString(testId3));

        Assertions.assertNotEquals(testId1, testId2);
        Assertions.assertNotEquals(testId2, testId3);
        Assertions.assertEquals(testId1, testId3);
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testContainsKey(StorageStrategy strategy) {
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        Assertions.assertTrue(strategy.containsKey(1L));
        Assertions.assertTrue(strategy.containsKey(2L));
        Assertions.assertFalse(strategy.containsKey(999L));
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testContainsValue(StorageStrategy strategy) {
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        Assertions.assertTrue(strategy.containsValue("key1"));
        Assertions.assertTrue(strategy.containsValue("key2"));
        Assertions.assertFalse(strategy.containsValue("999false"));
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testPut(StorageStrategy strategy) {
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        Assertions.assertEquals(1L, strategy.getKey("key1"));
        Assertions.assertEquals(2L, strategy.getKey("key2"));

        Assertions.assertEquals("key1", strategy.getValue(1L));
        Assertions.assertEquals("key2", strategy.getValue(2L));
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testGetKey(StorageStrategy strategy) {
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        Assertions.assertEquals(1L, strategy.getKey("key1"));
        Assertions.assertEquals(2L, strategy.getKey("key2"));
        Assertions.assertNull(strategy.getKey("999false"));
    }

    @ParameterizedTest
    @MethodSource("streamListStorageStrategy")
    public void testGetValue(StorageStrategy strategy) {
        strategy.put(1L, "key1");
        strategy.put(2L, "key2");

        Assertions.assertEquals("key1", strategy.getValue(1L));
        Assertions.assertEquals("key2", strategy.getValue(2L));
        Assertions.assertNull(strategy.getValue(999L));
    }
}

