import Shapes.*;

import java.io.*;
import java.util.ArrayList;


public class GeometricShapes {

    public static void main(String[] args) {

        if (args.length > 0) {
            switch (args[0]) {
                case "-c" -> {
                    Shape shape = ShapesUtils.createShape(args);
                    ShapesUtils.addShape(shape);
                }
                case "-d" -> ShapesUtils.deleteShapes(args);
                case "-r" -> ShapesUtils.printListOfShapes();
            }
        }
    }
}

