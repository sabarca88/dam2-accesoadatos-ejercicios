package es.dam.accesodatos.tema02_lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class EjercicioIncidenciasTemplate {
    public static void main(String[] args) {
        FuenteDatos<?> fuenteGenerica = FuenteDatosFactory.crear("incidencias");

        @SuppressWarnings("unchecked") // La fábrica devuelve una interfaz
        FuenteDatos<Incidencia> fuente = (FuenteDatos<Incidencia>) fuenteGenerica;

        List<Incidencia> incidencias = fuente.cargar();

        // TODO Function: transformación de un objeto a un texto.
        // Genera un String tipo: #1 | Ana | ALTA | No puede acceder al sistema
        Function<Incidencia, String> resumen = null;

        // TODO - Muestra por pantalla todas las incidencias usando lambda

        // TODO Predicate: validación y filtrado.
        // Verificar si los datos del usuario y descripción son válidos (no vacios)
        Predicate<Incidencia> datosValidos = null;
        //
        Predicate<Incidencia> abiertas = null;
        Predicate<Incidencia> prioridadAlta = null;

        // TODO Consumer: acción que no devuelve ningún resultado.
        // Imprime por pantalla: AUDITORÍA: procesada incidencia Nincidencia
        Consumer<Incidencia> auditoria = null;

        // Comparator: primero las prioridades más altas y, en caso de empate,
        // el identificador más pequeño.
        Comparator<Incidencia> porPrioridad = null;

        // Mostrar y procesar las incidencias abiertas y con prioridad alta
        System.out.println("--------- Incidencias abiertas de prioridad alta ---------:");
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia inc : incidencias) {
            if (datosValidos.test(inc) && abiertas.and(prioridadAlta).test(inc)) {
                resultado.add(inc);
            }
        }
        resultado.sort(porPrioridad);
        resultado.forEach(auditoria);

        fuente.guardar(resultado);

        // TODO: mostrar y procesar las incidencias con prioridad media

    }

    // -------------------------------------------------------------------------
    // EJERCICIO BASE: importación, validación y tratamiento de incidencias
    // -------------------------------------------------------------------------

    public record Incidencia(
            int id,
            String usuario,
            String descripcion,
            Prioridad prioridad,
            boolean resuelta
    ) {
    }

    public enum Prioridad {
        BAJA(1), MEDIA(2), ALTA(3);

        private final int peso;

        Prioridad(int peso) {
            this.peso = peso;
        }

        public int peso() {
            return peso;
        }
    }

    /** Interfaz común para cualquier fuente de datos. */
    public interface FuenteDatos<T> {
        List<T> cargar();

        void guardar(List<T> datos);
    }

    /** Simula una fuente de datos que podría ser un fichero CSV o JSON. */
    public static class FuenteIncidencias implements FuenteDatos<Incidencia> {

        @Override
        public List<Incidencia> cargar() {
            return List.of(
                    new Incidencia(1, "Ana", " No puede acceder al sistema ", Prioridad.ALTA, false),
                    new Incidencia(2, "Luis", "Error de impresión", Prioridad.MEDIA, true),
                    new Incidencia(3, "Marta", "Contraseña caducada", Prioridad.ALTA, false),
                    new Incidencia(4, "Pablo", "Consulta sobre el informe", Prioridad.BAJA, false),
                    new Incidencia(5, "Sara", "El programa se cierra", Prioridad.ALTA, true),
                    new Incidencia(6, "Sonia", " No puede acceder al sistema ", Prioridad.ALTA, false),
                    new Incidencia(7, "Álvaro", "Error de impresión", Prioridad.BAJA, true),
                    new Incidencia(8, "Mateo", "Contraseña caducada", Prioridad.ALTA, true),
                    new Incidencia(9, "Manuel", "Consulta sobre el informe", Prioridad.BAJA, true),
                    new Incidencia(10, "Davinia", "El programa se cierra", Prioridad.ALTA, true)
            );
        }

        @Override
        public void guardar(List<Incidencia> datos) {
            System.out.println("Guardadas " + datos.size() + " incidencias procesadas.");
        }
    }

    /** Fábrica sencilla: selecciona la clase según el tipo de dato solicitado. */
    public static class FuenteDatosFactory {

        public static FuenteDatos<?> crear(String tipo) {
            return switch (tipo.toLowerCase()) {
                case "incidencias" -> new FuenteIncidencias();
                default -> throw new IllegalArgumentException(
                        "No existe una fuente para el tipo: " + tipo);
            };
        }
    }
}
