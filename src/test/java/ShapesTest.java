import Shapes.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.*;

public class ShapesTest {

    @Test
    @DisplayName("Correct area is returned for a circle shape")
    void getCircleAreaTest() {
        Shape circle = new Circle("OR", 5);
        assertEquals(PI * 5 * 5, circle.getArea());
    }

    @Test
    @DisplayName("Correct area is returned for a square shape")
    void getSquareAreaTest() {
        Shape square = new Square("ABCD", 5);
        assertEquals(5 * 5, square.getArea());
    }

    @Test
    @DisplayName("Correct area is returned for a rectangle shape")
    void getRectangleAreaTest() {
        Shape rectangle = new Rectangle("ABCD", 5, 8);
        assertEquals(5 * 8, rectangle.getArea());
    }

    @Test
    @DisplayName("Correct area is returned for a triangle shape")
    void getTriangleAreaTest() {
        Shape triangle = new Triangle("ABC", 5, 8, 30);
        assertEquals(sin(toRadians(30)) * 5 * 8 * 0.5, triangle.getArea());
    }
}
