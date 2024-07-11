package ru.clevertec.check.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.ApplicationTestData;
import ru.clevertec.check.entity.CheckData;
import ru.clevertec.check.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArgumentParserTest {

    @InjectMocks
    private ArgumentParser argumentParser;

    @Test
    void parseArguments() throws BadRequestException {
        CheckData expected = ApplicationTestData.getParsedArgs();

        CheckData actual = argumentParser.parseArguments(ApplicationTestData.getArgs());

        assertEquals(expected, actual);
    }
}