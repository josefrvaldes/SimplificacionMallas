package valdes;

/**
 * Created by neiklotv on 1/6/17.
 */
@Deprecated
public class NodoDouble extends Nodo {

    private double x;
    private double y;

    public NodoDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y + " i:" + super.getContador();
    }
}
