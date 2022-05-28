package by.bsu.physics.maslovsky;

public class ThreeCoordVector extends AnnotatedVector {
    @Coordinate(0)
    double x;

    @Coordinate(1)
    double y;

    @Coordinate(2)
    double z;


    public ThreeCoordVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
