package eu.finbite.finbitecli.services;

import eu.finbite.finbitecli.config.properties.FinbitePackages;
import eu.finbite.finbitecli.models.PackageDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScannerInputValidationServiceTest {

    @Mock
    private Scanner scannerMock;

    @Mock
    private FinbitePackages finbitePackages;

    @InjectMocks
    private ScannerInputValidationService service;

    @Test
    public void testCorrectPhonePackage() {
        Map<String, PackageDetails> packages = new HashMap<>();
        PackageDetails packageDetails = new PackageDetails();
        packageDetails.setCostEur(new BigDecimal(5));
        packageDetails.setSms(50);
        packageDetails.setMinutes(10);
        packages.put("s", packageDetails);

        when(finbitePackages.packages()).thenReturn(packages);
        when(scannerMock.next()).thenReturn("s");

        String result = service.getCorrectPhonePackage(scannerMock);

        assertEquals("s", result);
    }

    @Test
    public void testWrongPhonePackage() {
        Map<String, PackageDetails> packages = new HashMap<>();
        PackageDetails packageDetails = new PackageDetails();
        packageDetails.setCostEur(new BigDecimal(5));
        packageDetails.setSms(50);
        packageDetails.setMinutes(10);
        packages.put("s", packageDetails);

        when(finbitePackages.packages()).thenReturn(packages);
        when(scannerMock.next()).thenReturn("test");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getCorrectPhonePackage(scannerMock);
        });

        String expectedMessage = "Invalid package type entered too many times";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPositiveIntegerWithHigherThanZero() {
        // user has inputs a correct aka >= 0 integer at first try
        when(scannerMock.hasNextInt()).thenReturn(true);
        when(scannerMock.nextInt()).thenReturn(5);

        int result = service.getPositiveInteger(scannerMock, "sms");

        assertEquals(5, result);
    }

    @Test
    public void testGetPositiveIntegerWithZero() {
        // user has inputs a correct aka >= 0 integer at first try
        when(scannerMock.hasNextInt()).thenReturn(true);
        when(scannerMock.nextInt()).thenReturn(0);

        int result = service.getPositiveInteger(scannerMock, "sms");

        assertEquals(0, result);
    }

    @Test
    public void testGetPositiveIntegerWithInvalidInputThenValidInput() {
        // user has input a non integer at first (not allowed), then corrects it to an allowed integer
        when(scannerMock.hasNextInt()).thenReturn(false, true);
        when(scannerMock.nextInt()).thenReturn(5);

        int result = service.getPositiveInteger(scannerMock, "sms");

        assertEquals(5, result);
    }

    @Test
    public void testGetPositiveIntegerWithNegativeIntegerThenValidInput() {
        // user has input a negative integer at first (not allowed), then corrects it to an allowed integer
        when(scannerMock.hasNextInt()).thenReturn(true, true);
        when(scannerMock.nextInt()).thenReturn(-1, 5);

        int result = service.getPositiveInteger(scannerMock, "sms");

        assertEquals(5, result);
    }

}
