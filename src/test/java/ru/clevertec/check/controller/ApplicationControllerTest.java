package ru.clevertec.check.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.ApplicationTestData;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.validator.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @Mock
    private CheckService applicationService;

    @Mock
    private Validator validator;

    @InjectMocks
    private ApplicationController applicationController;

    @Test
    void generateCheckShouldCallAllMethods() throws BadRequestException, NotEnoughMoneyException {
        applicationController.generateCheck(ApplicationTestData.getArgs());

        verify(applicationService).setSaveToFile(ApplicationTestData.getArgs());
        verify(validator).validateArgs(ApplicationTestData.getArgs());
        verify(applicationService).parseArgs(ApplicationTestData.getArgs());
        verify(applicationService).generateCheck(any());
        verify(applicationService).printCheck(anyString());

    }
}