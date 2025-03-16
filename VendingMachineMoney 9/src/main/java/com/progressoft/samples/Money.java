package com.progressoft.samples;

import java.util.ArrayList;
import java.util.List;

public class Money {

    // Common money instances
    public static final Money Zero = new Money(0.00);
    public static final Money OnePiaster = new Money(0.01);
    public static final Money FivePiasters = new Money(0.05);
    public static final Money TenPiasters = new Money(0.10);
    public static final Money TwentyFivePiasters = new Money(0.25);
    public static final Money FiftyPiasters = new Money(0.50);
    public static final Money OneDinar = new Money(1.00);
    public static final Money FiveDinars = new Money(5.00);
    public static final Money TenDinars = new Money(10.00);
    public static final Money TwentyDinars = new Money(20.00);
    public static final Money FiftyDinars = new Money(50.00);

    private final double denominationValue;
    private int count;
    private List<Money> denominationsList;

    // Default constructor
    public Money() {
        this.denominationValue = 0;
        this.count = 0;
        this.denominationsList = createEmptyDenominationsList();
    }

    // Constructor for a specific denomination
    public Money(double amount) {
        this();
        addDenomination(amount);
    }

    // Constructor with a list of denominations
    public Money(List<Money> denominations) {
        this.denominationValue = 0;
        this.count = 0;
        this.denominationsList = new ArrayList<>(denominations);
    }

    // Constructor for setting a specific denomination value and count
    private Money(double denominationValue, int count) {
        if (denominationValue < 0 || count < 0) throw new IllegalArgumentException("Values cannot be negative");
        this.denominationValue = denominationValue;
        this.count = count;
    }

    private List<Money> createEmptyDenominationsList() {
        List<Money> list = new ArrayList<>();
        list.add(new Money(50.00, 0));
        list.add(new Money(20.00, 0));
        list.add(new Money(10.00, 0));
        list.add(new Money(5.00, 0));
        list.add(new Money(1.00, 0));
        list.add(new Money(0.50, 0));
        list.add(new Money(0.25, 0));
        list.add(new Money(0.10, 0));
        list.add(new Money(0.05, 0));
        list.add(new Money(0.01, 0));

        return list;
    }

    private void addDenomination(double amount) {
        for (Money denomination : this.denominationsList) {
            if (denomination.getDenominationValue() == amount) {
                denomination.setDenominationCount(denomination.getDenominationCount() + 1);
                return;
            }
        }
    }

    public double getDenominationSum() {
        return this.denominationValue * this.count;
    }

    public double getDenominationValue() {
        return this.denominationValue;
    }

    public int getDenominationCount() {
        return this.count;
    }

    public void setDenominationCount(int count) {
        if (count < 0) throw new IllegalArgumentException("Count cannot be negative");
        this.count = count;
    }

    public double amount() {
        return denominationsList.stream().mapToDouble(Money::getDenominationSum).sum();
    }

    public Money times(int count) {
        if (count < 0) throw new IllegalArgumentException("Multiplier cannot be negative");

        Money result = this.clone();
        result.denominationsList.forEach(d -> d.setDenominationCount(d.getDenominationCount() * count));

        return result;
    }

    public static Money sum(Money... items) {
        Money result = new Money();
        for (Money item : items) {
            result = result.plus(item);
        }
        return result;
    }

    public Money plus(Money other) {
        Money result = this.clone();

        for (Money otherDenom : other.denominationsList) {
            for (Money resultDenom : result.denominationsList) {
                if (otherDenom.getDenominationValue() == resultDenom.getDenominationValue()) {
                    resultDenom.setDenominationCount(resultDenom.getDenominationCount() + otherDenom.getDenominationCount());
                }
            }
        }

        return result;
    }

    public Money minus(Money other) {
        Money result = this.clone();
        double remainingAmount = other.amount();

        if (this.amount() < remainingAmount) throw new IllegalArgumentException("Result cannot be negative");

        for (Money resultDenom : result.denominationsList) {
            while (remainingAmount >= resultDenom.getDenominationValue() && resultDenom.getDenominationCount() > 0) {

                resultDenom.setDenominationCount(resultDenom.getDenominationCount() - 1);

                remainingAmount -= resultDenom.getDenominationValue();
            }
        }

        if (remainingAmount > 0) throw new IllegalArgumentException("Subtraction cannot be completed");

        return result;
    }

    public Money clone() {
        List<Money> clonedList = new ArrayList<>();
        this.denominationsList.forEach(d -> clonedList.add(new Money(d.getDenominationValue(), d.getDenominationCount())));

        return new Money(clonedList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount(), this.amount()) == 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount());
    }
}