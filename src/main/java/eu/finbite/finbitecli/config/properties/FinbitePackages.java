package eu.finbite.finbitecli.config.properties;

import eu.finbite.finbitecli.models.PackageDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.Map;

@ConfigurationProperties(prefix = "finbite")
public record FinbitePackages(
        Map<String, PackageDetails> packages,
        BigDecimal oneMinExtraEur,
        BigDecimal oneSmsExtraEur) {
}
