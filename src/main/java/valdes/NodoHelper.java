package valdes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neiklotv on 10/6/17.
 */
public class NodoHelper {

    public static List<NodoInt> simplificar(List<NodoInt> nodosOriginales, int numPuntos, double fraccion1, double fraccion2) {
        List<NodoInt> nodosAleatorios = getPuntosAleatorios(nodosOriginales, numPuntos);

        int numVeces = 100 * nodosOriginales.size();
        for (int i = 0; i < numVeces; i++) {
            int numNodoElegido = ThreadLocalRandom.current().nextInt(0, nodosOriginales.size());
            NodoInt nodoOriginalActual = nodosOriginales.get(numNodoElegido);
            NodoInt[] cercanos = getCercanos(nodosAleatorios, nodoOriginalActual);
            cercanos[0].incrementarContador();
            cercanos[1].incrementarContador();
            cercanos[2].incrementarContador();
            acercarPuntos(nodoOriginalActual, cercanos[0], fraccion1);
            acercarPuntos(nodoOriginalActual, cercanos[1], fraccion2);
            acercarPuntos(nodoOriginalActual, cercanos[2], fraccion2);
        }

        for (NodoInt n : nodosAleatorios) {
            if (n.getContador() == 0) {
                int numNodoElegido = ThreadLocalRandom.current().nextInt(0, nodosOriginales.size());
                NodoInt nodoOriginalActual = nodosOriginales.get(numNodoElegido);
                n.setX(nodoOriginalActual.getX());
                n.setY(nodoOriginalActual.getY());
            }
        }

        return nodosAleatorios;
    }

    private static List<NodoInt> getPuntosAleatorios(List<NodoInt> original, int numPuntos) {
        List<NodoInt> salida = new ArrayList<>();
        int[] minMax = obtenerValoresMinimosYMaximos(original);
        if (minMax != null) {
            int minX = minMax[0];
            int minY = minMax[1];
            int maxX = minMax[2];
            int maxY = minMax[3];
            for (int i = 0; i < numPuntos; i++) {
                int randomX = ThreadLocalRandom.current().nextInt(minX, maxX + 1);
                int randomY = ThreadLocalRandom.current().nextInt(minY, maxY + 1);
                salida.add(new NodoInt(randomX, randomY));
            }
        }
        return salida;
    }

    public static int[] obtenerValoresMinimosYMaximos(List<NodoInt> nodos) {
        int[] minMax = new int[4];
        if (nodos.size() > 0) {
            int minX = nodos.get(0).getX();
            int minY = nodos.get(0).getY();
            int maxX = nodos.get(0).getX();
            int maxY = nodos.get(0).getY();
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

            minMax[0] = minX;
            minMax[1] = minY;
            minMax[2] = maxX;
            minMax[3] = maxY;
        }
        return minMax;
    }

    private static NodoInt[] getCercanos(List<NodoInt> nodos, NodoInt origen) {
        NodoInt[] cercanos = new NodoInt[3];
        cercanos[0] = nodos.get(0);
        cercanos[1] = nodos.get(0);
        cercanos[2] = nodos.get(0);
        //double distanciaActual = this.getDistancia(cercanos[0], origen);
        for (int i = 1; i < nodos.size(); i++) {
            double distanciaActual = getDistancia(cercanos[0], origen);
            double distAuxiliar = getDistancia(nodos.get(i), origen);
            if (distanciaActual > distAuxiliar) {
                NodoInt aux = nodos.get(i);
                cercanos[2] = cercanos[1];
                cercanos[1] = cercanos[0];
                cercanos[0] = aux;
            }
        }
        return cercanos;
    }

    private static double getDistancia(NodoInt a, NodoInt b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private static void acercarPuntos(NodoInt origen, NodoInt p, double fraccion) {
        /* aquí utilizamos la siguiente fórmula: si queremos obtener el punto que está
         *   a una fracción 1/X entre origen y p, usaremos la siguiente fórmula:
         *   r = 1/(x-1)
         *   Pnuevo-x = (origen.x + r * p.x) / (1 + r)
         *   Pnuevo-y = (origen.y + r * p.y) / (1 + r)
         *
         *   Por ejemplo, si queremos acercar el punto 1/4, obtenemos que r = 1/(4-1) = 1/3
         *   Pnuevo-x = (origen.x + 1/3 * p.x) / (1 + 1/3)
         *   Pnuevo-y = (origen.y + 1/3 * p.y) / (1 + 1/3)
         */
        double x = 1 / fraccion;
        double r = 1 / (x - 1);
        int nuevaX = (int) Math.round((origen.getX() + r * p.getX()) / (1 + r));
        int nuevaY = (int) Math.round((origen.getY() + r * p.getY()) / (1 + r));
        p.setX(nuevaX);
        p.setY(nuevaY);
        //return new valdes.NodoInt(nuevaX, nuevaY);
    }

    public static int[] getResolucion(List<NodoInt> nodos) {
        int[] salida = new int[2];

        int[] minMax = NodoHelper.obtenerValoresMinimosYMaximos(nodos);

        int minX = minMax[0];
        int minY = minMax[1];
        int maxX = minMax[2];
        int maxY = minMax[3];
        int resX = (maxX - minX) + 1;
        int resY = (maxY - minY) + 1;

        salida[0] = resX;
        salida[1] = resY;
        return salida;
    }
}
