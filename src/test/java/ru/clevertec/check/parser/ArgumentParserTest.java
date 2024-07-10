package ru.clevertec.check.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.ApplicationTestData;
import ru.clevertec.check.entity.ParsedArgs;
import ru.clevertec.check.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArgumentParserTest {

    @InjectMocks
    private ArgumentParser argumentParser;

    @Test
    void parseArguments() throws BadRequestException {
        ParsedArgs expected = ApplicationTestData.getParsedArgs();

        ParsedArgs actual = argumentParser.parseArguments(ApplicationTestData.getArgs());

        assertEquals(expected, actual);
    }
}