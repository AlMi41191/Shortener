package org.example;

import org.example.strategy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionalTest {
    @Test
    public void testHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }

    public void testStorage(Shortener shortener) {
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
}

