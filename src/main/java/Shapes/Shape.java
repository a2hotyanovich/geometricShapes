package Shapes;

import java.util.Arrays;

public abstract class Shape {

    protected String name;
    protected double[] dimensions;

    public abstract double getArea();

    public String getName() {
        return this.name;
    }

}