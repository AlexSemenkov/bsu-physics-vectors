package by.bsu.physics.Mrochko;

public class ThreeCordVector extends AnnotatedVector {
    @Coordinate(0)
    double x;
    @Coordinate(1)
    double y;
    @Coordinate(2)
    double z;

    public ThreeCordVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
