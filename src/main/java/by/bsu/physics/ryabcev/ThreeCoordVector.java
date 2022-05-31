package by.bsu.physics.ryabcev;
public class ThreeCoordVector extends AnnotatedVector {
    @Coordinate(0)
    double x;

    @Coordinate(2)
    double z;

    @Coordinate(1)
    double y;


    public ThreeCoordVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
