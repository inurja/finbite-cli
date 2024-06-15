package eu.finbite.finbitecli.services;

import eu.finbite.finbitecli.config.properties.FinbitePackages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@Slf4j
@AllArgsConstructor
public class ScannerInputValidationService {

    private final FinbitePackages finbitePackages;

    public String getCorrectPhonePackage(Scanner scanner) {
        String packageType;
        int loopCounter = 0;

        while (true) {
            // hacky way to stop infinite loop/dos protection
            if (loopCounter >= 5) {
                throw new IllegalArgumentException("Invalid package type entered too many times: " + loopCounter + " times");
            }
            System.out.print("Enter your package type " + finbitePackages.packages().keySet() + ": ");
            packageType = scanner.next();
            // TODO: this relies on no errors from config
            if (!finbitePackages.packages().containsKey(packageType.toLowerCase())) {
                System.out.println("Error: Invalid package type. Please enter one of " + finbitePackages.packages().keySet() + ".");
                log.debug("User entered invalid package type: {}", packageType);
            } else {
                return packageType;
            }
            loopCounter += 1;
        }

    }

    public int getPositiveInteger(Scanner scanner, String inputName) throws IllegalArgumentException {
        int positiveInteger;
        int loopCounter = 0;

        // Ask for a positive integer
        while (true) {
            // hacky way to stop infinite loop/dos protection
            if (loopCounter >= 5) {
                throw new IllegalArgumentException("Invalid " + inputName + " entered too many times: " + loopCounter + " times");
            }
            System.out.print("Enter " + inputName + ": ");
            if (scanner.hasNextInt()) {
                positiveInteger = scanner.nextInt();
                if (positiveInteger >= 0) {
                    return positiveInteger; // valid positive integer
                } else {
                    log.debug("User entered invalid {}: {}", inputName, positiveInteger);
                    System.out.println("Error: Invalid " + inputName + ". Please enter a number 0 or higher.");
                }
            } else {
                System.out.println("Error: Invalid " + inputName + ". Please enter a number 0 or higher.");
                log.debug("User entered invalid {}: {}", inputName, scanner.next()); // discard the non-integer input/log it
            }
            loopCounter += 1;
        }
    }

}
