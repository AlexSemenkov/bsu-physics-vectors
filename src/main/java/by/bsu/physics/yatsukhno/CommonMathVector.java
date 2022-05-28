package by.bsu.physics.yatsukhno;

import by.bsu.physics.Vector;
import by.bsu.physics.VectorMultipliable;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

class CommonMathVector extends Vector implements VectorMultipliable<CommonMathVector> {
    private final Vector3D vector3D;

    public CommonMathVector(double x, double y, double z) {
        vector3D = new Vector3D(x, y, z);

    }

    public CommonMathVector(Vector3D vector3D) {
        this.vector3D = vector3D;

    }


    public double getX() {
        return vector3D.getX();
    }

    public double getY() {
        return vector3D.getY();
    }

    public double getZ() {
        return vector3D.getZ();
    }

    @Override
    public double getLength() {
        return vector3D.getNorm();
    }

    @Override
    public double getScalarProduct(Vector vector) {
        CommonMathVector that = (CommonMathVector) vector;
        return vector3D.dotProduct(that.vector3D);

    }

    @Override
    public double getCosAngle(Vector vector) {
        return getScalarProduct(vector) / (vector.getLength() * this.getLength());
    }

    @Override
    public CommonMathVector multiply(CommonMathVector vector) {
        CommonMathVector newVector = new CommonMathVector(vector3D.crossProduct(vector.vector3D));
        return newVector;
    }


}
