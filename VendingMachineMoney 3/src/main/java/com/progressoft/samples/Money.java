package com.progressoft.samples;

import java.util.Arrays;
import java.util.HashMap;

public class Money {
    public double value;

    public static HashMap<Money, Integer> counts = new HashMap<>();

    public static final Money Zero = new Money(.00);
    public static final Money OnePiaster = new Money(.01);
    public static final Money FivePiasters = new Money(.05);
    public static final Money TenPiasters = new Money(.10);
    public static final Money TwentyFivePiasters = new Money(.25);
    public static final Money FiftyPiasters = new Money(.50);
    public static final Money OneDinar = new Money(1.00);
    public static final Money FiveDinars = new Money(5.00);
    public static final Money TenDinars = new Money(10.00);
    public static final Money TwentyDinars = new Money(20.00);
    public static final Money FiftyDinars = new Money(50.00);

    static {
        Zero.counts.put(Zero, 0);
        OnePiaster.counts.put(OnePiaster, 0);
        FivePiasters.counts.put(FivePiasters, 0);
        TenPiasters.counts.put(TenPiasters, 0);
        TwentyFivePiasters.counts.put(TwentyFivePiasters, 0);
        FiftyPiasters.counts.put(FiftyPiasters, 0);
        OneDinar.counts.put(OneDinar, 0);
        FiveDinars.counts.put(FiveDinars, 0);
        TenDinars.counts.put(TenDinars, 0);
        TwentyDinars.counts.put(TwentyDinars, 0);
        FiftyDinars.counts.put(FiftyDinars, 0);
    }

    public Money(double value) {
        this.value = value;
    }

    public double amount() {
        return value;
    }

    public Money times(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }

        int V = counts.getOrDefault(this,0)+count;
        counts.put(this, V);

        double ans = this.value * count;
        return new Money(ans);
    }

    public static Money sum(Money... items) {
        double total = Arrays.stream(items).mapToDouble(Money::amount).sum();
        return new Money(total);
    }

    public Money plus(Money other) {
        double ans = this.value + other.value;

        int V = counts.getOrDefault(this,0)+1;
        counts.put(this, V);

        V = counts.getOrDefault(other,0)+1;
        counts.put(other, V);

        return new Money(ans);
    }

    public Money minus(Money other) {
            if (this.value < other.value) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            return new Money(this.value - other.value);
    }

    // Override toString to format the amount to 2 decimal places
    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    // Override equals to compare based on the value
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return Double.compare(money.value, value) == 0;
    }

    // Override hashCode for consistency with equals
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}

