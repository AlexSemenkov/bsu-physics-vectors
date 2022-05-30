

public class TwoCoordVector extends AnnotatedVector {
    @Coordinate(0)
    double x;
    @Coordinate(1)
    double y;

    public TwoCoordVector(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
