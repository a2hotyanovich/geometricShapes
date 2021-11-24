package Shapes;

public class Circle extends Shape {

    public Circle(String name, double radius) {
        this.name = name;
        this.dimensions = new double[]{radius};
    }

    @Override
    public double getArea() {
        return (Math.PI * Math.pow(this.dimensions[0], 2));
    }
}
