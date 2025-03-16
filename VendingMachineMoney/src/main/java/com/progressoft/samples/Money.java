package com.progressoft.samples;

import java.util.Arrays;
import java.util.HashMap;

public class Money {
    private final double value;
    private static HashMap<Money, Integer> counts = new HashMap<>();

    // Define constants for each denomination
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

    // Constructor for initializing the Money object with a specific value
    private Money(double value) {
        int V = counts.getOrDefault(this, 0);
        counts.put(this,V++);
        this.value = value;
    }

    // Returns the amount of this Money object
    public double amount() {
        return value;
    }

    // Multiplies this Money object by a count and returns a new Money object
    public Money times(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        return new Money(this.value * count);
    }

    // Returns the sum of multiple Money objects
    public static Money sum(Money... items) {
        double total = Arrays.stream(items).mapToDouble(Money::amount).sum();
        return new Money(total);
    }

    // Adds another Money object to this one and returns a new Money object
    public Money plus(Money other) {
        int V = counts.getOrDefault(other, 0);
        V++;
        counts.put(other,V);
        return new Money(this.value + other.value);
    }

    // Subtracts another Money object from this one and returns a new Money object
    public Money minus(Money other) {
        int V = counts.getOrDefault(other, 0);
        if ((this.value < other.value) || (V==0 && other != Zero) ) {
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
