package valdes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by neiklotv on 1/6/17.
 */
@Deprecated
public class MallaDouble {

    private List<NodoDouble> nodos;
    private PropiedadesMallaDouble propiedades;
    private byte[][] mallaOriginal;

    /*public valdes.MallaDouble(List<valdes.NodoDouble> nodos) {
        this.nodos = nodos;
        this.propiedades = new valdes.PropiedadesMallaDouble(nodos);
        this.mallaOriginal = this.rellenarMallaOriginal();
    }*/

    /*private byte[][] rellenarMallaOriginal() {
        byte[][] salida = new byte[this.propiedades.getResolucion()[0]][this.propiedades.getResolucion()[1]];
        for (valdes.NodoDouble n : nodos) {
            double i = n.getX() + propiedades.getMinX() * -1; // esto se hace para traducir unas coordenadas realistas a una matriz que empieza en 0,0
            double j = n.getY() + propiedades.getMinY() * -1;
            salida[i][j] = 1;
        }
        return salida;
    }*/

    public List<NodoDouble> simplificar(int numPuntos, double fraccion1, double fraccion2) {
        List<NodoDouble> puntosAleatorios = getPuntosAleatorios(numPuntos);
        int numVeces = 100 * mallaOriginal.length;
        for(int i = 0; i < numVeces; i++) {
            int numNodoElegido = ThreadLocalRandom.current().nextInt(0, mallaOriginal.length);
            NodoDouble nodoOriginalActual = nodos.get(numNodoElegido);
            NodoDouble[] cercanos = this.getCercanos(nodos, nodoOriginalActual);
            cercanos[0].incrementarContador();
            cercanos[1].incrementarContador();
            cercanos[2].incrementarContador();
            this.acercarPuntos(nodoOriginalActual, cercanos[0], fraccion1);
            this.acercarPuntos(nodoOriginalActual, cercanos[1], fraccion2);
            this.acercarPuntos(nodoOriginalActual, cercanos[2], fraccion2);
        }

        for(NodoDouble n : puntosAleatorios) {
            if(n.getContador() == 0) {
                int numNodoElegido = ThreadLocalRandom.current().nextInt(0, mallaOriginal.length);
                NodoDouble nodoOriginalActual = nodos.get(numNodoElegido);
                n.setX(nodoOriginalActual.getX());
                n.setY(nodoOriginalActual.getY());
            }
        }

        return puntosAleatorios;
    }

    private NodoDouble[] getCercanos(List<NodoDouble> nodos, NodoDouble origen) {
        NodoDouble[] cercanos = new NodoDouble[3];
        cercanos[0] = nodos.get(0);
        cercanos[1] = nodos.get(0);
        cercanos[2] = nodos.get(0);
        double distanciaActual = this.getDistancia(cercanos[0], origen);
        for(int i = 1; i < nodos.size(); i++) {
            double distAuxiliar = this.getDistancia(nodos.get(i), origen);
            if(distanciaActual > distAuxiliar) {
                cercanos[0] = nodos.get(i);
                cercanos[1] = cercanos[0];
                cercanos[2] = cercanos[1];
            }
        }
        return cercanos;
    }

    private List<NodoDouble> getPuntosAleatorios(int numPuntos) {
        List<NodoDouble> salida = new ArrayList<>();
        double minX = this.propiedades.getMinX();
        double maxX = this.propiedades.getMaxX();
        double minY = this.propiedades.getMinY();
        double maxY = this.propiedades.getMaxY();
        for (int i = 0; i < numPuntos; i++) {
            double randomX = ThreadLocalRandom.current().nextDouble(minX, maxX + 1);
            double randomY = ThreadLocalRandom.current().nextDouble(minY, maxY + 1);
            salida.add(new NodoDouble(randomX, randomY));
        }
        return salida;
    }

    private void acercarPuntos(NodoDouble origen, NodoDouble p, double fraccion) {
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
        double nuevaX = origen.getX() + r * p.getX() / (1 + r);
        double nuevaY = (origen.getY() + r * p.getY()) / (1 + r);
        p.setX(nuevaX);
        p.setY(nuevaY);
        //return new valdes.NodoInt(nuevaX, nuevaY);
    }

    private double getDistancia(NodoDouble a, NodoDouble b) {
        return Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow(a.getY() - b.getY(), 2));
    }


    public byte[][] getMallaOriginal() {
        return mallaOriginal;
    }


    public PropiedadesMallaDouble getPropiedades() {
        return propiedades;
    }

    /*@Override
    public String toString() {
        return "resolución:" + this.propiedades.getResolucion()[0] + "x" + this.propiedades.getResolucion()[1];
    }*/


}
