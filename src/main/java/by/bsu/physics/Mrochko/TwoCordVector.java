package by.bsu.physics.Mrochko;

public class TwoCordVector extends AnnotatedVector {
    @Coordinate(0)
    double x;
    @Coordinate(1)
    double y;

    public TwoCordVector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

