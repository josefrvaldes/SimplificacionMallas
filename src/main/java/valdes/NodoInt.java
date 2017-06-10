package valdes;

/**
 * Created by neiklotv on 1/6/17.
 */
public class NodoInt extends Nodo {

    private int x;
    private int y;

    public NodoInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y + " i:" + super.getContador();
    }
}
