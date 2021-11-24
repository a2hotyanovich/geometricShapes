import Shapes.*;

import java.io.*;
import java.util.ArrayList;

public class ShapesUtils {
    public static final File FILE;

    static {
        FILE = new File("./files/shapes.txt");
        FILE.getParentFile().mkdirs();
        try {
            FILE.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Shape createShape(String[] shapeParameters) {
        if (shapeParameters.length > 1) {
            return switch(shapeParameters[1].toLowerCase()) {
                case "rectangle":
                    if (shapeParameters.length < 5) {
                        throw new IllegalArgumentException("Not enough parameters to create a rectangle");
                    }
                    yield new Rectangle(shapeParameters[2], Double.parseDouble(shapeParameters[3]),
                            Double.parseDouble(shapeParameters[4]));
                case "circle":
                    if (shapeParameters.length < 4) {
                        throw new IllegalArgumentException("Not enough parameters to create a circle");
                    }
                    yield new Circle(shapeParameters[2], Double.parseDouble(shapeParameters[3]));
                case "square":
                    if (shapeParameters.length < 4) {
                        throw new IllegalArgumentException("Not enough parameters to create a square");
                    }
                    yield new Square(shapeParameters[2], Double.parseDouble(shapeParameters[3]));
                case "triangle":
                    if (shapeParameters.length < 6) {
                        throw new IllegalArgumentException("Not enough parameters to create a triangle");
                    }
                    yield new Triangle(shapeParameters[2], Double.parseDouble(shapeParameters[3]),
                            Double.parseDouble(shapeParameters[4]), Double.parseDouble(shapeParameters[5]));
                default:
                    throw new IllegalArgumentException("Invalid shape value has been provided");
            };
        } else {
            throw new IllegalArgumentException("Please provide a shape value to create a shape");
        }

    }

    public static void addShape(Shape shape) {
        String type = shape.getClass().getSimpleName();
        String name = shape.getName();
        double area = shape.getArea();
        try {
            FileWriter fileWriter = new FileWriter(FILE, true);
            fileWriter.write(String.format("%s,%s,%.2f\n", type, name, area));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteShapes(String[] deleteParameters) {
        if (deleteParameters.length > 2) {
            String operator = deleteParameters[1];
            double areaCriteria = Double.parseDouble(deleteParameters[2]);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FILE));
                ArrayList<String> lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    double area = getAreaFromFileLine(line);
                    if (!areaMatchesCriteria(area, areaCriteria, operator)) {
                        lines.add(line);
                    }
                }
                reader.close();

                FileWriter fileWriter = new FileWriter(FILE);

                for (String s : lines) {
                    fileWriter.write(s);
                }

                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Not enough parameters to remove shapes");
        }

    }

    public static void printListOfShapes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] elements = line.split(",");
                System.out.printf("%-10s %-10s %-10s\n", elements[0], elements[1], elements[2]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static double getAreaFromFileLine(String line) {
        String[] elements = line.split(",");
        return Double.parseDouble(elements[2]);
    }

    private static boolean areaMatchesCriteria (double area, double criteria, String operator) {
        return switch (operator) {
            case ">": yield (area > criteria);
            case "<": yield (area < criteria);
            case ">=": yield (area >= criteria);
            case "<=": yield (area <= criteria);
            case "=": yield (area == criteria);
            case "!=": yield (area != criteria);
            default:
                throw new IllegalStateException("Unexpected comparison operator: " + operator);
        };
    }
}