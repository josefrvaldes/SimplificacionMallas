package valdes;

import java.util.List;

/**
 * Created by neiklotv on 10/6/17.
 */
@Deprecated
public class PropiedadesMallaDouble {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public PropiedadesMallaDouble(List<NodoDouble> nodos) {
        this.obtenerValoresMinimosYMaximos(nodos);
    }

    private void obtenerValoresMinimosYMaximos(List<NodoDouble> nodos) {
        if (nodos.size() > 0) {
            this.minX = nodos.get(0).getX();
            this.minY = nodos.get(0).getY();
            this.maxX = nodos.get(0).getX();
            this.maxY = nodos.get(0).getY();
            for (int i = 1; i < nodos.size(); i++) {
                NodoDouble nodoActual = nodos.get(i);
                if (nodoActual.getX() < minX)
                    minX = nodoActual.getX();

                if (nodoActual.getX() > maxX)
                    maxX = nodoActual.getX();

                if (nodoActual.getY() < minY)
                    minY = nodoActual.getY();

                if (nodoActual.getY() > maxY)
                    maxY = nodoActual.getY();
            }
        }
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    /*public int[] getResolucion() {
        int[] salida = new int[2];

        int resX = (this.maxX - this.minX) + 1;
        int resY = (this.maxY - this.minY) + 1;

        salida[0] = resX;
        salida[1] = resY;
        return salida;
    }*/

}
