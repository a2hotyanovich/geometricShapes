package Shapes;

public class Triangle extends Shape {

    public Triangle(String name, double size1, double size2, double angle) {
        this.name = name;
        this.dimensions = new double[]{size1, size2, angle};
    }

    @Override
    public double getArea() {
        double radian = Math.toRadians(this.dimensions[2]);
        return (0.5 * this.dimensions[0] * this.dimensions[1] *
                Math.sin(radian));
    }
}
