package valdes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neiklotv on 1/6/17.
 */
@Deprecated
public class MallaInt {

    private List<NodoInt> nodos;
    private PropiedadesMallaInt propiedades;
    private byte[][] mallaOriginal;

    public MallaInt(List<NodoInt> nodos) {
        this.nodos = nodos;
        this.propiedades = new PropiedadesMallaInt(nodos);
        this.mallaOriginal = this.rellenarMallaOriginal();
    }

    private byte[][] rellenarMallaOriginal() {
        byte[][] salida = new byte[this.propiedades.getResolucion()[0]][this.propiedades.getResolucion()[1]];
        for (NodoInt n : nodos) {
            int i = n.getX() + propiedades.getMinX() * -1;
            int j = n.getY() + propiedades.getMinY() * -1;
            salida[i][j] = 1;
        }
        return salida;
    }

    public List<NodoInt> simplificar(int numPuntos, double fraccion1, double fraccion2) {
        List<NodoInt> puntosAleatorios = getPuntosAleatorios(numPuntos);
        CSVHelper.escribirNodosIntEnCSV(puntosAleatorios, "puntos_aleatorios.csv");
        int numVeces = 100 * mallaOriginal.length;
        for(int i = 0; i < numVeces; i++) {
            int numNodoElegido = ThreadLocalRandom.current().nextInt(0, mallaOriginal.length);
            NodoInt nodoOriginalActual = nodos.get(numNodoElegido);
            NodoInt[] cercanos = this.getCercanos(nodos, nodoOriginalActual);
            cercanos[0].incrementarContador();
            cercanos[1].incrementarContador();
            cercanos[2].incrementarContador();
            this.acercarPuntos(nodoOriginalActual, cercanos[0], fraccion1);
            this.acercarPuntos(nodoOriginalActual, cercanos[1], fraccion2);
            this.acercarPuntos(nodoOriginalActual, cercanos[2], fraccion2);
        }

        for(NodoInt n : puntosAleatorios) {
            if(n.getContador() == 0) {
                int numNodoElegido = ThreadLocalRandom.current().nextInt(0, mallaOriginal.length);
                NodoInt nodoOriginalActual = nodos.get(numNodoElegido);
                n.setX(nodoOriginalActual.getX());
                n.setY(nodoOriginalActual.getY());
            }
        }

        return puntosAleatorios;
    }

    private NodoInt[] getCercanos(List<NodoInt> nodos, NodoInt origen) {
        NodoInt[] cercanos = new NodoInt[3];
        cercanos[0] = nodos.get(0);
        cercanos[1] = nodos.get(0);
        cercanos[2] = nodos.get(0);
        //double distanciaActual = this.getDistancia(cercanos[0], origen);
        for(int i = 1; i < nodos.size(); i++) {
            double distanciaActual = this.getDistancia(cercanos[0], origen);
            double distAuxiliar = this.getDistancia(nodos.get(i), origen);
            if(distanciaActual > distAuxiliar) {
                NodoInt aux = nodos.get(i);
                cercanos[2] = cercanos[1];
                cercanos[1] = cercanos[0];
                cercanos[0] = aux;
            }
        }
        return cercanos;
    }

    private List<NodoInt> getPuntosAleatorios(int numPuntos) {
        List<NodoInt> salida = new ArrayList<>();
        int minX = this.propiedades.getMinX();
        int maxX = this.propiedades.getMaxX();
        int minY = this.propiedades.getMinY();
        int maxY = this.propiedades.getMaxY();
        for (int i = 0; i < numPuntos; i++) {
            int randomX = ThreadLocalRandom.current().nextInt(minX, maxX + 1);
            int randomY = ThreadLocalRandom.current().nextInt(minY, maxY + 1);
            salida.add(new NodoInt(randomX, randomY));
        }
        return salida;
    }

    private void acercarPuntos(NodoInt origen, NodoInt p, double fraccion) {
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
        double x = 1/fraccion;
        double r = 1/(x-1);
        int nuevaX = (int) Math.round((origen.getX() + r * p.getX()) / (1 + r));
        int nuevaY = (int) Math.round((origen.getY() + r * p.getY()) / (1 + r));
        p.setX(nuevaX);
        p.setY(nuevaY);
        //return new valdes.NodoInt(nuevaX, nuevaY);
    }

    private double getDistancia(NodoInt a, NodoInt b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow(a.getY() - b.getY(), 2));
    }


    public byte[][] getMallaOriginal() {
        return mallaOriginal;
    }


    public PropiedadesMallaInt getPropiedades() {
        return propiedades;
    }

    @Override
    public String toString() {
        return "resolución:" + this.propiedades.getResolucion()[0] + "x" + this.propiedades.getResolucion()[1];
    }
}
