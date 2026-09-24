package es.dam.accesodatos.tema02_lambda;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// =========================================================================
// ENTIDADES
// =========================================================================

class Cliente {
    private String id;
    private String nombre;

    public Cliente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Cliente [ID: " + id + ", Nombre: " + nombre + "]";
    }
}

class Producto {
    private String codigo;
    private double precio;

    public Producto(String codigo, double precio) {
        this.codigo = codigo;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return "Producto [Código: " + codigo + ", Precio: " + precio + "€]";
    }
}

// =========================================================================
// ESTRUCTURA GENÉRICA (Contrato e Implementación del Repositorio)
// =========================================================================

// Interfaz genérica que define el comportamiento esperado para cualquier entidad
interface Repositorio<T> {
    void guardar(T entidad);
    List<T> obtenerTodos();
}

// Implementación genérica que simula el almacenamiento de datos en memoria
class RepositorioEnMemoria<T> implements Repositorio<T> {
    private final List<T> baseDeDatos = new ArrayList<>();

    @Override
    public void guardar(T entidad) {
        baseDeDatos.add(entidad);
        System.out.println("Guardado correctamente en el sistema.");
    }

    @Override
    public List<T> obtenerTodos() {
        // Devolvemos una copia de la lista para proteger los datos originales
        return new ArrayList<>(baseDeDatos);
    }
}


// =========================================================================
// USOS 1 - Paginación y filtrado universal de datos
// =========================================================================

// Estructura universal para CUALQUIER respuesta paginada de la base de datos
class Pagina<T> {
    private List<T> contenido;      // Los datos reales (Clientes, Productos, etc.)
    private int paginaActual;       // Número de página (ej. Página 2)
    private int totalPaginas;       // Páginas totales en la BD
    private long totalElementos;    // Total de filas en la BD

    public Pagina(List<T> contenido, int paginaActual, int totalPaginas, long totalElementos) {
        this.contenido = contenido;
        this.paginaActual = paginaActual;
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
    }
    // Getters...
}


// =========================================================================
// USOS 2 - Respuestas Unificadas de API (Wrappers de API REST)
// =========================================================================
class RespuestaAPI<T> {
    private String estado;       // "EXITO" o "ERROR"
    private String mensaje;      // Mensaje informativo
    private T datos;             // El objeto o lista devuelto por la base de datos
    private long timestamp;

    // Constructor para respuestas exitosas
    public static <T> RespuestaAPI<T> exito(T datos) {
        RespuestaAPI<T> respuesta = new RespuestaAPI<>();
        respuesta.estado = "SUCCESS";
        respuesta.datos = datos;
        return respuesta;
    }
}

// =========================================================================
// USOS 3 - Caché de Datos Multitipo (In-Memory Caching) - Consultas frecuentes
// =========================================================================
class GestorCache<K, V> {
    // K = Tipo de la Clave (ej: String, Integer)
    // V = Tipo del Valor guardado (ej: Entidad de negocio)
    private final Map<K, V> mapaCache = new HashMap<>();

    public void registrar(K clave, V valor) {
        mapaCache.put(clave, valor);
    }

    public V recuperar(K clave) {
        return mapaCache.get(clave);
    }
}

// =========================================================================
// Main ejmeplo repositorio
// =========================================================================
public class Generics {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO SISTEMA COMERCIAL GENÉRICO ===\n");

        // --- GESTIÓN DE CLIENTES ---
        System.out.println("--- Gestionando Área de Clientes ---");
        Repositorio<Cliente> repoClientes = new RepositorioEnMemoria<>();

        repoClientes.guardar(new Cliente("C01", "Ana Gómez"));
        repoClientes.guardar(new Cliente("C02", "Carlos Ruiz"));

        // Recuperamos los datos de forma directa y segura (sin conversiones manuales)
        for (Cliente cliente : repoClientes.obtenerTodos()) {
            System.out.println(cliente);
        }

        System.out.println("\n---------------------------------------------\n");

        // --- GESTIÓN DE PRODUCTOS ---
        System.out.println("--- Gestionando Área de Inventario ---");
        // Reutilizamos la misma lógica y estructura de código, pero cambiando el tipo
        Repositorio<Producto> repoProductos = new RepositorioEnMemoria<>();

        repoProductos.guardar(new Producto("PROD-100", 29.99));
        repoProductos.guardar(new Producto("PROD-200", 89.50));

        for (Producto producto : repoProductos.obtenerTodos()) {
            System.out.println(producto);
        }

        // =========================================================================
        // Eror en tiempo de compilación.
        // El compilador protege el negocio impidiendo mezclar tipos de datos erróneos.
        // =========================================================================
        // repoClientes.guardar(new Producto("PROD-300", 15.0));
    }
}