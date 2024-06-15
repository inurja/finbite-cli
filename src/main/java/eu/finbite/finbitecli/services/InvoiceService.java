package eu.finbite.finbitecli.services;

import eu.finbite.finbitecli.config.properties.FinbitePackages;
import eu.finbite.finbitecli.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceService {

    private final FinbitePackages finbitePackages;

    public Invoice calculateCosts(String packageType, int minutes, int sms) {
        log.debug("Start calculating costs");
        int availableMinutes = finbitePackages.packages().get(packageType.toLowerCase()).getMinutes();
        int availableSms = finbitePackages.packages().get(packageType.toLowerCase()).getSms();
        BigDecimal packageCost = finbitePackages.packages().get(packageType.toLowerCase()).getCostEur();
        BigDecimal extraMinutesCost = new BigDecimal(0);
        BigDecimal extraSmsCost = new BigDecimal(0);
        BigDecimal totalCost = new BigDecimal(0).add(packageCost);

        Invoice invoice = new Invoice();
        invoice.addPackageCost(packageCost);


        int minsUsed = availableMinutes - minutes;
        if (minsUsed < 0) {
            extraMinutesCost = extraMinutesCost.add(new BigDecimal(minsUsed * -1).multiply(finbitePackages.oneMinExtraEur()));
            invoice.addExtraMinutesCost(extraMinutesCost);
            totalCost = totalCost.add(extraMinutesCost);
        }

        int smsUsed = availableSms - sms;
        if (smsUsed < 0) {
            extraSmsCost = extraSmsCost.add(new BigDecimal(smsUsed * -1).multiply(finbitePackages.oneSmsExtraEur()));
            invoice.addExtraSmsCost(extraSmsCost);
            totalCost = totalCost.add(extraSmsCost);
        }

        invoice.addTotalCost(totalCost);
        log.debug("Finish calculating costs");
        return invoice;
    }
}
