package ru.clevertec.check.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.ApplicationTestData;
import ru.clevertec.check.csv.CsvWriter;
import ru.clevertec.check.dao.DiscountCardDao;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.entity.CheckData;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.parser.ArgumentParser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private DiscountCardDao cardDao;

    @Mock
    private ArgumentParser parser;

    @Mock
    private CsvWriter writer;

    @InjectMocks
    private CheckServiceImpl service;

    private final String[] args = ApplicationTestData.getArgs();
    private final CheckData parsedArgs = ApplicationTestData.getParsedArgs();
    private final Product product = ApplicationTestData.getProduct();
    private final DiscountCard discountCard = ApplicationTestData.getDiscountCard();
    private final String checkString = ApplicationTestData.getCheckString();
    private final String resultPath = ApplicationTestData.getSaveToFile();

    @Test
    void parseArgsShouldCallParserMethodParseArguments() throws BadRequestException {
        when(parser.parseArguments(args)).thenReturn(parsedArgs);

        service.parseArgs(args);

        verify(parser, times(1)).parseArguments(args);
    }

    @Test
    void generateCheckShouldGenerateCheck() throws NotEnoughMoneyException, BadRequestException {
        StringBuilder builder = new StringBuilder();
        when(parser.parseArguments(args)).thenReturn(parsedArgs);
        when(productDao.getEntityById(1L)).thenReturn(Optional.of(product));
        when(cardDao.getCardByCardNumber(1111)).thenReturn(Optional.of(discountCard));

        service.parseArgs(args);
        service.generateCheck(builder);

        assertTrue(builder.toString().contains(checkString));
    }

    @Test
    void printCheckShouldCallWriterMethodWriteDataToCsv() throws BadRequestException {
        service.printCheck(checkString);

        verify(writer, times(1)).writeDataToCsv(checkString, resultPath);
    }

    @Test
    void setSaveToFileShouldFillSaveToFileField() throws BadRequestException {
        String argSaveToFile = ApplicationTestData.getArgSaveToFile();
        service.setSaveToFile(new String[]{argSaveToFile});
        service.printCheck(checkString);

        verify(writer, times(1)).writeDataToCsv(checkString,
                argSaveToFile.split("=")[1]);
    }
}