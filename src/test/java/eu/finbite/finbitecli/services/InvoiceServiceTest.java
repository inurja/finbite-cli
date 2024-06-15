package eu.finbite.finbitecli.services;

import eu.finbite.finbitecli.config.properties.FinbitePackages;
import eu.finbite.finbitecli.models.Invoice;
import eu.finbite.finbitecli.models.PackageDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    private FinbitePackages finbitePackages;

    @InjectMocks
    private InvoiceService service;

    @BeforeEach
    void init() {
        Map<String, PackageDetails> packages = new HashMap<>();
        PackageDetails packageDetails = new PackageDetails();
        packageDetails.setCostEur(new BigDecimal(5));
        packageDetails.setSms(50);
        packageDetails.setMinutes(10);
        packages.put("s", packageDetails);

        when(finbitePackages.packages()).thenReturn(packages);
    }

    @Test
    public void testInvoiceCalculation_AllExtraCosts() {
        when(finbitePackages.oneSmsExtraEur()).thenReturn(new BigDecimal("0.3"));
        when(finbitePackages.oneMinExtraEur()).thenReturn(new BigDecimal("0.2"));

        Invoice result = service.calculateCosts("s", 20, 100);

        assertEquals(new BigDecimal("5"), result.getPackageCost());
        assertEquals(new BigDecimal("15.0"), result.getExtraSmsCost());
        assertEquals(new BigDecimal("2.0"), result.getExtraMinutesCost());
        assertEquals(new BigDecimal("22.0"), result.getTotalCost());
    }

    @Test
    public void testInvoiceCalculation_NoExtraCosts() {
        Invoice result = service.calculateCosts("s", 5, 10);

        assertEquals(new BigDecimal("5"), result.getPackageCost());
        assertEquals(new BigDecimal("0"), result.getExtraSmsCost());
        assertEquals(new BigDecimal("0"), result.getExtraMinutesCost());
        assertEquals(new BigDecimal("5"), result.getTotalCost());
    }

    @Test
    public void testInvoiceCalculation_SmsExtraCosts() {
        when(finbitePackages.oneSmsExtraEur()).thenReturn(new BigDecimal("0.3"));

        Invoice result = service.calculateCosts("s", 5, 100);

        assertEquals(new BigDecimal("5"), result.getPackageCost());
        assertEquals(new BigDecimal("15.0"), result.getExtraSmsCost());
        assertEquals(new BigDecimal("0"), result.getExtraMinutesCost());
        assertEquals(new BigDecimal("20.0"), result.getTotalCost());
    }

    @Test
    public void testInvoiceCalculation_MinutesExtraCosts() {
        when(finbitePackages.oneMinExtraEur()).thenReturn(new BigDecimal("0.2"));

        Invoice result = service.calculateCosts("s", 20, 10);

        assertEquals(new BigDecimal("5"), result.getPackageCost());
        assertEquals(new BigDecimal("0"), result.getExtraSmsCost());
        assertEquals(new BigDecimal("2.0"), result.getExtraMinutesCost());
        assertEquals(new BigDecimal("7.0"), result.getTotalCost());
    }

}
