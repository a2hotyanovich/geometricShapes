import Shapes.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ShapesUtilsTest {

    @Nested
    class createShapeTest{

       @Test
       @DisplayName("Circle shape is created")
       void createCircleShapeTest() {
           String[] circleParameters = {"-c", "circle", "OR", "5"};
           Shape circle = ShapesUtils.createShape(circleParameters);
           assert circle instanceof Circle;
       }

       @Test
       @DisplayName("Square shape is created")
       void createSquareShapeTest() {
           String[] circleParameters = {"-c", "square", "ABCD", "4.0"};
           Shape shape = ShapesUtils.createShape(circleParameters);
           assert shape instanceof Square;
       }

       @Test
       @DisplayName("Rectangle shape is created")
       void createRectangleShapeTest() {
           String[] circleParameters = {"-c", "rectangle", "KLMP", "8.5", "9"};
           Shape shape = ShapesUtils.createShape(circleParameters);
           assert shape instanceof Rectangle;
       }

       @Test
       @DisplayName("Triangle shape is created")
       void createTriangleShapeTest() {
           String[] circleParameters = {"-c", "triangle", "BCE", "6", "8", "60"};
           Shape shape = ShapesUtils.createShape(circleParameters);
           assert shape instanceof Triangle;
       }

       @Test
       @DisplayName("Shape is not created when shape value is not provided")
       void createShapeNotValidShape() {
           Exception exception = assertThrows(IllegalArgumentException.class,
                   () -> ShapesUtils.createShape(new String[] {"-c"}));
           assertEquals("Please provide a shape value to create a shape", exception.getMessage());
       }

       @Test
       @DisplayName("Shape is not created when invalid shape value is provided")
       void createShapeNoShapeValue() {
           Exception exception = assertThrows(IllegalArgumentException.class,
                   () -> ShapesUtils.createShape(new String[] {"-c" , "cicle"}));
           assertEquals("Invalid shape value has been provided", exception.getMessage());
       }

       @Test
       @DisplayName("Shape is not created when not enough parameters are provided")
       void createShapeNotEnoughParametersTest() {
           assertAll("exception",
                   () -> {
                       Exception exception = assertThrows(IllegalArgumentException.class,
                               () -> ShapesUtils.createShape(new String[] {"-c" , "circle"}));
                       assertEquals("Not enough parameters to create a circle", exception.getMessage());
                   },
                   () -> {
                       Exception exception = assertThrows(IllegalArgumentException.class,
                               () -> ShapesUtils.createShape(new String[] {"-c" , "square", "ABCD"}));
                       assertEquals("Not enough parameters to create a square", exception.getMessage());
                   },
                   () -> {
                       Exception exception = assertThrows(IllegalArgumentException.class,
                               () -> ShapesUtils.createShape(new String[] {"-c" , "rectangle", "ABCD", "0"}));
                       assertEquals("Not enough parameters to create a rectangle", exception.getMessage());
                   },
                   () -> {
                       Exception exception = assertThrows(IllegalArgumentException.class,
                               () -> ShapesUtils.createShape(new String[] {"-c" , "triangle", "ABC", "65", "4"}));
                       assertEquals("Not enough parameters to create a triangle", exception.getMessage());
                   });

       }
   }

    @Nested
    class addShapeTest{

        @BeforeEach
        @AfterEach
        void removeAllDataFromFile() {
            ShapesUtils.deleteShapes(new String[]{"-d", ">=", "0"});
        }

        @Test
        @DisplayName("Circle shape is added to the file")
        void addCircleTest() {
            Shape circle = new Circle("OR", 5);
            String expectedLine = String.format("Circle,%s,%.2f", circle.getName(), circle.getArea());
            ShapesUtils.addShape(circle);
            assertTrue(shapeIsAddedToFile(expectedLine));
        }

        @Test
        @DisplayName("Square shape is added to the file")
        void addSquareTest() {
            Shape square = new Square("ABCD", 5.5);
            String expectedLine = String.format("Square,%s,%.2f", square.getName(), square.getArea());
            ShapesUtils.addShape(square);
            assertTrue(shapeIsAddedToFile(expectedLine));
        }

        @Test
        @DisplayName("Rectangle shape is added to the file")
        void addRectangleTest() {
            Shape rectangle = new Rectangle("ABCD", 5.5, 8);
            String expectedLine = String.format("Rectangle,%s,%.2f", rectangle.getName(), rectangle.getArea());
            ShapesUtils.addShape(rectangle);
            assertTrue(shapeIsAddedToFile(expectedLine));
        }

        @Test
        @DisplayName("Triangle shape is added to the file")
        void addTriangleTest() {
            Shape triangle = new Triangle("ABCD", 5.5, 8, 120);
            String expectedLine = String.format("Triangle,%s,%.2f", triangle.getName(), triangle.getArea());
            ShapesUtils.addShape(triangle);
            assertTrue(shapeIsAddedToFile(expectedLine));
        }

        @Test
        @DisplayName("Null value is not accepted")
        void addNullShapeTest() {
            assertThrows(NullPointerException.class, () -> ShapesUtils.addShape(null));
        }

        boolean shapeIsAddedToFile(String expectedLine) {
            boolean isFound = false;
            String line;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(ShapesUtils.FILE));
                while ((line = reader.readLine()) != null) {
                    if (line.equals(expectedLine)) {
                        isFound = true;
                        break;
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return isFound;
        }
    }

    @Nested
    class deleteShapesTest{
//new comment
        @Test
        @DisplayName("All shapes are removed from the file")
        void deleteAllShapesTest() {
            ShapesUtils.addShape(new Circle("OR1", 5));
            ShapesUtils.addShape(new Square("ABCD", 5));
            ShapesUtils.addShape(new Rectangle("KLMP", 5, 6));
            ShapesUtils.addShape(new Triangle("ABC", 10, 18, 60));

            ShapesUtils.deleteShapes(new String[]{"-d", ">=", "0"});
            try {
                FileReader reader = new FileReader(ShapesUtils.FILE);
                assertEquals(-1, reader.read());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Test
        @DisplayName("Exception is thrown when not enough paramters have been provided")
        void deleteShapesNotEnoughParametersTest() {
            assertAll("exception", () -> {
                Exception exception = assertThrows(IllegalArgumentException.class,
                        () -> ShapesUtils.deleteShapes(new String[]{"-d"}));
                assertEquals("Not enough parameters to remove shapes", exception.getMessage());
            }, () -> {
                Exception exception = assertThrows(IllegalArgumentException.class,
                        () -> ShapesUtils.deleteShapes(new String[]{"-d", "="}));
                assertEquals("Not enough parameters to remove shapes", exception.getMessage());
            });
        }

        @Test
        @DisplayName("Not valid comparison operator is not accepted")
        void deleteShapesNotValidOperatorTest() {

            ShapesUtils.addShape(new Circle("OR1", 5));

            assertAll("exception", () -> {
                Exception exception = assertThrows(IllegalStateException.class,
                        () -> ShapesUtils.deleteShapes(new String[]{"-d", "equals", "50"}));
                assertEquals("Unexpected comparison operator: equals", exception.getMessage());
            }, () -> {
                Exception exception = assertThrows(IllegalStateException.class,
                        () -> ShapesUtils.deleteShapes(new String[]{"-d", "==", "100"}));
                assertEquals("Unexpected comparison operator: ==", exception.getMessage());
            });
        }

    }


}