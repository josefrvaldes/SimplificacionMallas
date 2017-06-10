package valdes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neiklotv on 1/6/17.
 */
public class CSVHelper {

    public static List<Nodo> leerNodosDeCSV(String nombreArchivo, boolean isInt) {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";
        List<Nodo> salida = new ArrayList<Nodo>();

        try {

            br = new BufferedReader(new FileReader("./" + nombreArchivo));
            while ((line = br.readLine()) != null) {
                String[] valores = line.split(cvsSplitBy);
                boolean error = false;
                int x = 0;
                int y = 0;
                try {
                    x = Integer.valueOf(valores[0]);
                    y = Integer.valueOf(valores[1]);
                } catch (Exception e) {
                    System.out.println("Hubo un error al convertir a decimal");
                    error = true;
                }
                if (!error) {
                    if (isInt) {
                        NodoInt n = new NodoInt(x, y);
                        salida.add(n);
                    } else {
                        NodoDouble n = new NodoDouble(x, y);
                        salida.add(n);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("El archivo introducido no existe");
        } catch (IOException e) {
            System.out.println("Hubo un error abriendo el archivo");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Hubo un error cerrando el archivo");
                }
            }
        }
        return salida;
    }

    public static void escribirNodosIntEnCSV(List<NodoInt> nodos, String nombreArchivo) {
        BufferedWriter writer = null;
        try {

            File file = new File(nombreArchivo);

            writer = new BufferedWriter(new FileWriter(file));
            for (Nodo n : nodos) {
                writer.write(((NodoInt) n).getX() + ";" + ((NodoInt) n).getY() + System.getProperty("line.separator"));
            }

        } catch (IOException e) {
            System.out.println("Hubo un error al escribir archivo CSV");
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.println("Hubo un error al escribir archivo CSV");
            }
        }
    }

}
