import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ejercicioPlaylist {

    public static void main(String[] args) {
        Map<Integer, String> canciones = cargarCanciones();
        List<Integer> ordenPlaylist = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Queue<Integer> colaReproduccion = new LinkedList<>();
        Map<Integer, Integer> reproducciones = cargarReproducciones();

        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
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
        Map<Integer, String> canciones = new HashMap<>();
        canciones.put(1, "Blinding Lights - The Weeknd - pop");
        canciones.put(2, "Lose Yourself - Eminem - rap");
        canciones.put(3, "Despechá - Rosalía - pop");
        canciones.put(4, "Seven Nation Army - The White Stripes - rock");
        canciones.put(5, "Levitating - Dua Lipa - pop");
        return canciones;
    }

    private static Map<Integer, Integer> cargarReproducciones() {
        Map<Integer, Integer> reproducciones = new HashMap<>();
        reproducciones.put(1, 12);
        reproducciones.put(2, 7);
        reproducciones.put(3, 18);
        reproducciones.put(4, 4);
        reproducciones.put(5, 10);
        return reproducciones;
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SOUNDWAVE ===");
        System.out.println("1. Mostrar playlist");
        System.out.println("2. Buscar canción por ID");
        System.out.println("3. Añadir canción a la cola");
        System.out.println("4. Reproducir siguiente");
        System.out.println("5. Mostrar artistas únicos");
        System.out.println("6. Mostrar canción más reproducida");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
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
