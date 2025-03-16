package com.progressoft.samples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Money {

    private double value;

    private static HashMap<Money, Integer> counts = new HashMap<>();
    private static final HashMap<Double, Money> values = new HashMap<>();

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
        //values
        values.put(Zero.value, Zero);
        values.put(OnePiaster.value, OnePiaster);
        values.put(FivePiasters.value, FivePiasters);
        values.put(TenPiasters.value, TenPiasters);
        values.put(TwentyFivePiasters.value, TwentyFivePiasters);
        values.put(FiftyPiasters.value, FiftyPiasters);
        values.put(OneDinar.value, OneDinar);
        values.put(FiveDinars.value, FiveDinars);
        values.put(TenDinars.value, TenDinars);
        values.put(TwentyDinars.value, TwentyDinars);
        values.put(FiftyDinars.value, FiftyDinars);

        Zero.counts.put(Zero, 1);
        OnePiaster.counts.put(OnePiaster, 1);
        FivePiasters.counts.put(FivePiasters, 1);
        TenPiasters.counts.put(TenPiasters, 1);
        TwentyFivePiasters.counts.put(TwentyFivePiasters, 1);
        FiftyPiasters.counts.put(FiftyPiasters, 1);
        OneDinar.counts.put(OneDinar, 1);
        FiveDinars.counts.put(FiveDinars, 1);
        TenDinars.counts.put(TenDinars, 1);
        TwentyDinars.counts.put(TwentyDinars, 1);
        FiftyDinars.counts.put(FiftyDinars, 1);
    }

    public Money(double value) {
        this.value = value;
    }

    public double amount() {
        return this.value;
    }

    public Money times(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        Money money = new Money(this.value * count);
        money.counts.put(this, count);
        return money;
    }

    public static Money sum(Money... items) {
        double sum = 0;
        if (items.length == 0) {
            return Zero;
        }
        for (Money item : items) {
            sum += item.value;
        }
        return values.get(sum);
    }

    public Money plus(Money other) {
        if (values.containsKey(this.value + other.value)) {
            return values.get(this.value + other.value);
        } else {
            Money money = new Money(this.value + other.value);
            money.counts.putAll(this.counts);
            if (values.containsValue(other) && other.counts.size() == 0) {
                money.counts.put(other, 1);
            } else {
                money.counts.putAll(other.counts);
            }
            return money;
        }
    }

    public Money minus(Money other) {

        if (other.value > this.value) {
            throw new IllegalArgumentException();
        }

        if (values.containsKey(this.value - other.value)) {
            return values.get(this.value - other.value);
        }

        Map<Money, Integer> newCounts = new HashMap<>();
        double remainingAmount = other.value;

        List<Map.Entry<Money, Integer>> orderedCounts = this.counts.entrySet()
                .stream()
                .sorted((e1, e2) -> (int) (e2.getKey().value - e1.getKey().value))
                .collect(Collectors.toList());

        for (Map.Entry<Money, Integer> e : orderedCounts) {
            //count
            if (e.getValue() <= 0) {
                continue;
            }

            double remaining = remainingAmount - e.getKey().value;
            if (remaining < 0) {
                continue;
            }

            int toRemove = (int) (remainingAmount / e.getKey().value);
            if (toRemove > 0) {
                newCounts.put(e.getKey(), toRemove);
                remainingAmount -= e.getKey().value * toRemove;
            }
        }

        if (remainingAmount > 0) {
            throw new IllegalArgumentException();
        }

        newCounts.forEach((k, v) -> {
            int count = this.counts.get(k) - v;
            if (count == 0) {
                this.counts.remove(k);
            }
            this.counts.replace(k, count);
        });

        return new Money(this.value - other.value);
    }


    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return Double.compare(value, money.value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}

