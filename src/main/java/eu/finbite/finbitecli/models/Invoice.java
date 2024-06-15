package eu.finbite.finbitecli.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class Invoice {
    private BigDecimal packageCost = new BigDecimal(0);
    private BigDecimal extraMinutesCost = new BigDecimal(0);
    private BigDecimal extraSmsCost = new BigDecimal(0);
    private BigDecimal totalCost = new BigDecimal(0);

    public void addPackageCost(BigDecimal packageCost) {
        this.packageCost = this.packageCost.add(packageCost);
    }

    public void addExtraMinutesCost(BigDecimal extraMinutesCost) {
        this.extraMinutesCost = this.extraMinutesCost.add(extraMinutesCost);
    }

    public void addExtraSmsCost(BigDecimal extraSmsCost) {
        this.extraSmsCost = this.extraSmsCost.add(extraSmsCost);
    }

    public void addTotalCost(BigDecimal totalCost) {
        this.totalCost = this.totalCost.add(totalCost);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "packageCost=" + packageCost + " EUR" +
                ", extraMinutesCost=" + extraMinutesCost + " EUR" +
                ", extraSmsCost=" + extraSmsCost + " EUR" +
                ", totalCost=" + totalCost + " EUR" +
                '}';
    }
}
