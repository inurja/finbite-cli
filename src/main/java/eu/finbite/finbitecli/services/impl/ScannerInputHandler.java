package eu.finbite.finbitecli.services.impl;

import eu.finbite.finbitecli.services.InputHandlerService;
import eu.finbite.finbitecli.services.InvoiceService;
import eu.finbite.finbitecli.services.ScannerInputValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
@AllArgsConstructor
public class ScannerInputHandler implements InputHandlerService {

    private final InvoiceService invoiceService;
    private final ScannerInputValidationService scannerInputValidationService;

    public void handleUserInput() {
        log.debug("Start handling user input with scanner");
        Scanner scanner = new Scanner(System.in);
        String packageType;
        int minutes;
        int sms;

        // Get user input
        try {
            packageType = scannerInputValidationService.getCorrectPhonePackage(scanner);
            minutes = scannerInputValidationService.getPositiveInteger(scanner, "minutes");
            sms = scannerInputValidationService.getPositiveInteger(scanner, "sms");
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            System.exit(0);
            return;
        }

        // Calculate and show invoice
        System.out.println(invoiceService.calculateCosts(packageType, minutes, sms));
    }

}
