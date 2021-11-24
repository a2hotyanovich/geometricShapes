package Shapes;

public class Rectangle extends Shape {

    public Rectangle(String name, double length, double width) {
        this.name = name;
        this.dimensions = new double[]{length, width};
    }

    @Override
    public double getArea() {
        return (this.dimensions[0] * this.dimensions[1]);
    }

}
