import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ejercicioPlaylist {

    public static void main(String[] args) {
        // TODO completar bloque de inicialización
        canciones = cargarCanciones();
        ordenPlaylist = ;// TODO
        colaReproduccion = ;// TODO
        reproducciones = cargarReproducciones();

        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            /*
            Se muestra el menú con las opciones:
            1) Mostrar canciones
            2) Buscar canción por ID
            3) Añádir canción a la cola de reproducción por ID
            4) Reproducir la siguiente canción de la cola
            5) Mostrar artistas
            6) Mostrar la canción más reproducida
            0) Salir
            otro) Control de errores -ª opción no válida
             */
            mostrarMenu();
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> mostrarCanciones(canciones, ordenPlaylist);
                case 2 -> {
                    System.out.print("ID de la canción: ");
                    int id = teclado.nextInt();
                    teclado.nextLine();
                    System.out.println(buscarCancion(canciones, id));
                }
                case 3 -> {
                    System.out.print("ID para añadir a la cola: ");
                    int id = teclado.nextInt();
                    teclado.nextLine();
                    anadirACola(canciones, colaReproduccion, id);
                }
                case 4 -> reproducirSiguiente(canciones, colaReproduccion, reproducciones);
                case 5 -> mostrarArtistas(canciones);
                case 6 -> mostrarMasReproducida(canciones, reproducciones);
                case 0 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        teclado.close();
    }

    private static Map<Integer, String> cargarCanciones() {
        // TODO
        /* Ejemplo de carga de canción
        canciones.put(1, "Blinding Lights - The Weeknd - pop");
        */
        return canciones;
    }

    private static Map<Integer, Integer> cargarReproducciones() {
        //TODO
        /* Ejemplo de registro de número de reproducciones
        reproducciones.put(1, 12);
         */
        return reproducciones;
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        // TODO
    }

    private static void mostrarCanciones(Map<Integer, String> canciones,
                                         List<Integer> ordenPlaylist) {
        // TODO: recorrer ordenPlaylist y mostrar cada canción.
    }

    private static String buscarCancion(Map<Integer, String> canciones, int id) {
        // TODO: devolver la canción o un mensaje si no existe.
        return "TODO";
    }

    private static void anadirACola(Map<Integer, String> canciones,
                                    Queue<Integer> cola, int id) {
        // TODO: comprobar que existe y añadir el ID a la cola.
    }

    private static void reproducirSiguiente(Map<Integer, String> canciones,
                                            Queue<Integer> cola,
                                            Map<Integer, Integer> reproducciones) {
        // TODO: extraer el siguiente ID, mostrarlo y aumentar su contador.
    }

    private static void mostrarArtistas(Map<Integer, String> canciones) {
        // TODO: obtener los artistas sin repetir usando un HashSet.
    }

    private static void mostrarMasReproducida(Map<Integer, String> canciones,
                                              Map<Integer, Integer> reproducciones) {
        // TODO: localizar el ID con mayor número de reproducciones.
    }
}
