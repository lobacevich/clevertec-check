package ru.clevertec.check.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.ApplicationTestData;
import ru.clevertec.check.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    private final Validator validator = Validator.getINSTANCE();

    @Test
    void validateArgsShouldThrowException() {

        assertThrows(BadRequestException.class, () -> validator.validateArgs(ApplicationTestData.getInvalidArgs()));
    }
}