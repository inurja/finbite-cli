package eu.finbite.finbitecli.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PackageDetails {
    private int minutes;
    private int sms;
    private BigDecimal costEur;
}
