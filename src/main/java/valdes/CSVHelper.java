package valdes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neiklotv on 1/6/17.
 */
public class CSVHelper {

    public static List<Nodo> leerNodosDeCSV(String nombreArchivo, boolean isInt) throws IOException {
        String line;
        String cvsSplitBy = ";";
        List<Nodo> salida = new ArrayList<>();


        BufferedReader writer = new BufferedReader(new FileReader("./" + nombreArchivo));
        while ((line = writer.readLine()) != null) {
            String[] valores = line.split(cvsSplitBy);

            int x = Integer.valueOf(valores[0]);
            int y = Integer.valueOf(valores[1]);

            if (isInt) {
                NodoInt n = new NodoInt(x, y);
                salida.add(n);
            } else {
                NodoDouble n = new NodoDouble(x, y);
                salida.add(n);
            }
        }
        writer.close();

        return salida;
    }

    public static void escribirNodosIntEnCSV(List<NodoInt> nodos, String nombreArchivo) throws IOException {
        File file = new File(nombreArchivo);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Nodo n : nodos) {
            writer.write(((NodoInt) n).getX() + ";" + ((NodoInt) n).getY() + System.getProperty("line.separator"));
        }
        writer.close();
    }

}
