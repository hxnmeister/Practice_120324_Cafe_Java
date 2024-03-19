package com.ua.project.service;

import com.ua.project.exception.FileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.setProperty;

public class TxtFileReaderTest {
    @BeforeAll
    static void createProperty() {
        setProperty("test", "true");
    }

    @Test
    void readFile_ShouldReturnListOfStringsFromFile_WenCalled() {
        TxtFileReader txtFileReader = new TxtFileReader("data.patronymics");

        try {
            List<String> actual =  txtFileReader.readFile();
            List<String> expected = new ArrayList<String>(List.of(
                    "Oleksandrovich",
                    "Nikolaevich",
                    "Olekseevich",
                    "Maksimovich",
                    "Yakovich",
                    "Anatolievich",
                    "Andreevich",
                    "Olegovich"
            ));

            Assertions.assertEquals(expected, actual);
        } catch (FileException e) {
            throw new RuntimeException(e);
        }
    }
}
