package valdes;

import java.util.List;

/**
 * Created by neiklotv on 1/6/17.
 */
@Deprecated
public class PropiedadesMallaInt {

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public PropiedadesMallaInt(List<NodoInt> nodos) {
        this.obtenerValoresMinimosYMaximos(nodos);
    }

    private void obtenerValoresMinimosYMaximos(List<NodoInt> nodos) {
        if (nodos.size() > 0) {
            this.minX = nodos.get(0).getX();
            this.minY = nodos.get(0).getY();
            this.maxX = nodos.get(0).getX();
            this.maxY = nodos.get(0).getY();
            for (int i = 1; i < nodos.size(); i++) {
                NodoInt nodoActual = nodos.get(i);
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

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int[] getResolucion() {
        int[] salida = new int[2];

        int resX = (this.maxX - this.minX) + 1;
        int resY = (this.maxY - this.minY) + 1;

        salida[0] = resX;
        salida[1] = resY;
        return salida;
    }
}
