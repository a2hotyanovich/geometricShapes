package Shapes;

public class Square extends Shape {

    public Square(String name, double size) {
        this.name = name;
        this.dimensions = new double[]{size};
    }

    @Override
    public double getArea() {
        double size = this.dimensions[0];
        return size * size;
    }

}
