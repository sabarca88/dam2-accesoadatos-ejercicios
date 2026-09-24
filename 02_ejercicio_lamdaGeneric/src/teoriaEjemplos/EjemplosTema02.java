package es.dam.accesodatos.tema02_lambda;

import java.util.Locale;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * <p><b>Composición de funciones.</b> Predicate.and y or combinan condiciones con cortocircuito; negate invierte el resultado. Function.andThen aplica primero esta función y después la recibida, mientras compose invierte ese orden. Consumer.andThen encadena efectos; si falla el primero, no se ejecuta el segundo. Comparator.thenComparing añade un desempate y reversed invierte el criterio construido.</p>
 *
 * Diez usos independientes de expresiones lambda y referencias de método. Una
 * expresión lambda permite implementar de forma breve el único método abstracto
 * de una interfaz funcional. Los parámetros aparecen a la izquierda de -> y el
 * cuerpo de la operación aparece a la derecha. Son especialmente útiles cuando
 * se necesita pasar comportamiento como dato, por ejemplo para filtrar,
 * transformar, ordenar o ejecutar acciones.
 *
 * <p>
 * Predicate representa una condición que devuelve boolean. Function transforma
 * un valor de un tipo en otro. Consumer recibe un valor y realiza una acción
 * sin devolver resultado. Supplier devuelve un valor sin recibir parámetros.
 * UnaryOperator es una especialización de Function en la que entrada y salida
 * son del mismo tipo.
 * </p>
 *
 * <p>
 * <code>@FunctionalInterface</code> indica que una interfaz está diseñada para tener un
 * único método abstracto. El compilador comprueba esta condición y genera un
 * error si posteriormente se añade otro método abstracto. Los métodos default y
 * static no cuentan como métodos abstractos, por lo que pueden existir dentro
 * de una interfaz funcional.
 * </p>
 *
 * <p>
 * En aplicaciones reales, las lambdas aparecen continuamente en Streams,
 * validaciones, procesamiento de colecciones, eventos, callbacks, ordenaciones,
 * transformación de datos y acceso a información.
 * </p>
 */
public final class EjemplosTema02 {
	private EjemplosTema02() {
	}

	/**
	 * Ejecuta diez demostraciones independientes.
	 */
	public static void main(String[] args) {
		ejemplo01Predicate();
		ejemplo02Function();
		ejemplo03Consumer();
		ejemplo04Supplier();
		ejemplo05Comparator();
		ejemplo06InterfazPropia();
		ejemplo07ReferenciaDeMetodo();
		ejemplo08CapturaDeVariable();
		ejemplo09Composicion();
		ejemplo10ForEachDeMapa();
	}

	/**
	 * IntPredicate representa una condición que recibe un int y devuelve true o
	 * false. El método test ejecuta la condición. Se utiliza IntPredicate en lugar
	 * de Predicate&lt;Integer&gt; para evitar convertir automáticamente el int en
	 * Integer.
	 *
	 * <p>
	 * Uso real: validar datos numéricos antes de guardarlos, por ejemplo comprobar
	 * que una edad sea válida, que una cantidad sea positiva o que un identificador
	 * cumpla una regla concreta.
	 * </p>
	 */
	static void ejemplo01Predicate() {
        IntPredicate positivo = numero -> numero > 0;
        IntPredicate par = numero -> numero % 2 == 0;
        IntPredicate admisible = positivo.and(par);
        for (int numero : List.of(-4, 0, 3, 8, 10)) System.out.println(numero + " -> " + admisible.test(numero));
        Predicate<String> noVacio = texto -> texto != null && !texto.isBlank();
        Predicate<String> largo = texto -> texto.strip().length() >= 5;
        verificar(noVacio.and(largo).test("  datos  "), "La composición acepta un nombre suficiente");
        verificar(!noVacio.and(largo).test(null), "El cortocircuito evita evaluar length sobre null");
        System.out.println("No admisible: " + admisible.negate().test(3));
    }

	/**
	 * Function&lt;T,R&gt; representa una transformación. T indica el tipo recibido y R el
	 * tipo devuelto. El método apply ejecuta la transformación definida por la
	 * lambda.
	 *
	 * <p>
	 * Uso real: convertir datos entre capas de una aplicación, por ejemplo
	 * transformar una entidad Usuario en un UsuarioDTO, convertir texto a números o
	 * calcular información derivada antes de mostrarla.
	 * </p>
	 */
	static void ejemplo02Function() {
        Function<String, Integer> longitudSinEspacios = texto -> texto.strip().length();
        List<String> textos = List.of("  datos  ", " XML ", "");
        List<Integer> longitudes = textos.stream().map(longitudSinEspacios).toList();
        Function<Integer, String> clasificar = longitud -> longitud >= 4 ? "largo" : "corto";
        List<String> etiquetas = textos.stream().map(longitudSinEspacios.andThen(clasificar)).toList();
        System.out.println("Longitudes: " + longitudes + "; etiquetas: " + etiquetas);
        verificar(longitudes.equals(List.of(5,3,0)), "La función trata también la cadena vacía");
    }

	/**
	 * Consumer&lt;T&gt; recibe un valor pero no devuelve ninguno. Su método principal es
	 * accept. Se utiliza cuando interesa ejecutar una acción con un dato.
	 *
	 * <p>
	 * Uso real: guardar datos, escribir logs, enviar información a otro servicio,
	 * actualizar una colección o ejecutar una acción sobre cada elemento recuperado
	 * de una base de datos.
	 * </p>
	 */
	static void ejemplo03Consumer() {
        List<String> almacen = new ArrayList<>();
        List<String> auditoria = new ArrayList<>();
        Consumer<String> guardar = texto -> almacen.add(texto.strip().toUpperCase(Locale.ROOT));
        Consumer<String> registrar = texto -> auditoria.add("recibido:" + texto);
        Consumer<String> proceso = guardar.andThen(registrar);
        List.of(" xml ", "jdbc", "rest").forEach(proceso);
        System.out.println("Almacén: " + almacen);
        System.out.println("Auditoría: " + auditoria);
        verificar(almacen.size() == auditoria.size(), "Cada entrada produce un registro de auditoría");
    }

	/**
	 * Supplier&lt;T&gt; representa una operación que no recibe parámetros y devuelve un
	 * valor. El método get ejecuta la creación u obtención del valor.
	 *
	 * <p>
	 * Uso real: crear objetos bajo demanda, generar identificadores, proporcionar
	 * configuraciones o retrasar una operación costosa hasta que realmente sea
	 * necesaria.
	 * </p>
	 */
	static void ejemplo04Supplier() {
        Supplier<List<String>> fabricaLista = ArrayList::new;
        List<String> primera = fabricaLista.get();
        List<String> segunda = fabricaLista.get();
        primera.add("dato privado de la primera lista");
        System.out.println("Primera: " + primera + "; segunda: " + segunda);
        verificar(primera != segunda && segunda.isEmpty(), "La fábrica no comparte accidentalmente el estado");
        Supplier<String> mensaje = () -> "Tamaño calculado ahora: " + primera.size();
        System.out.println(mensaje.get());
        primera.add("otro dato");
        System.out.println(mensaje.get());
    }

	/**
	 * Comparator define cómo se comparan dos objetos para establecer un orden.
	 * comparingInt crea un comparador utilizando un valor entero obtenido del
	 * objeto.
	 *
	 * <p>
	 * Uso real: ordenar productos por precio, usuarios por edad, pedidos por fecha,
	 * alumnos por nota o resultados obtenidos de una base de datos antes de
	 * mostrarlos.
	 * </p>
	 */
	static void ejemplo05Comparator() {
        List<String> lenguajes = new ArrayList<>(List.of("Ruby", "Java", "SQL", "JavaScript", "Go"));
        Comparator<String> porLongitudYNombre = Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder());
        lenguajes.sort(porLongitudYNombre);
        System.out.println("Longitud y desempate alfabético: " + lenguajes);
        List<String> inverso = new ArrayList<>(lenguajes);
        inverso.sort(porLongitudYNombre.reversed());
        System.out.println("Orden inverso: " + inverso);
        verificar(lenguajes.getFirst().equals("Go") && inverso.getFirst().equals("JavaScript"), "El criterio se invierte completo");
    }

	/**
	 * Una interfaz funcional propia permite representar una operación específica
	 * del dominio. La lambda implementa directamente su único método abstracto sin
	 * necesidad de crear una clase aparte.
	 *
	 * <p>
	 * Uso real: definir reglas de negocio variables, por ejemplo calcular
	 * descuentos, comisiones, impuestos, precios o puntuaciones dependiendo de la
	 * estrategia elegida.
	 * </p>
	 */
	static void ejemplo06InterfazPropia() {
        Calculadora descuento = (importe, porcentaje) -> {
            if (importe < 0 || porcentaje < 0 || porcentaje > 100) throw new IllegalArgumentException("Importe o porcentaje inválido");
            return importe * (1.0 - porcentaje / 100.0);
        };
        for (double porcentaje : List.of(0.0, 15.0, 100.0)) System.out.println("Descuento " + porcentaje + "%: " + descuento.calcular(80, porcentaje));
        try { descuento.calcular(80, 120); }
        catch (IllegalArgumentException ex) { System.out.println("Rechazado: " + ex.getMessage()); }
        verificar(descuento.calcular(80,100) == 0, "El descuento del 100% produce cero");
    }

	/**
	 * Una referencia de método reutiliza directamente un método ya existente.
	 * String::strip equivale a texto -> texto.strip() cuando la firma esperada es
	 * compatible.
	 *
	 * <p>
	 * Uso real: reutilizar métodos existentes en Streams, transformaciones,
	 * ordenaciones o procesamiento de colecciones sin escribir una lambda que
	 * únicamente invoque otro método.
	 * </p>
	 */
	static void ejemplo07ReferenciaDeMetodo() {
        UnaryOperator<String> limpiar = String::strip;
        UnaryOperator<String> limpiarConLambda = texto -> texto.strip();
        List<String> datos = List.of(" JDBC ", " XML ", "");
        List<String> porReferencia = datos.stream().map(limpiar).toList();
        List<String> porLambda = datos.stream().map(limpiarConLambda).toList();
        System.out.println(porReferencia);
        verificar(porReferencia.equals(porLambda), "La referencia de método y la lambda son equivalentes aquí");
        System.out.println("La entrada permanece intacta: " + datos);
    }

	/**
	 * Una lambda puede utilizar variables locales declaradas fuera de ella siempre
	 * que sean finales o efectivamente finales. Una variable es efectivamente final
	 * cuando no se modifica después de asignarle un valor.
	 *
	 * <p>
	 * Uso real: aplicar configuraciones externas dentro de una transformación, como
	 * un porcentaje de IVA, un límite máximo, una tasa de descuento o un valor
	 * leído previamente de configuración.
	 * </p>
	 */
	static void ejemplo08CapturaDeVariable() {
        double iva = 0.21;
        Function<Double, Double> precioFinal = base -> base * (1.0 + iva);
        List<Double> bases = List.of(10.0, 100.0, 0.0);
        System.out.println("Precios con el IVA capturado: " + bases.stream().map(precioFinal).toList());
        // iva no se reasigna: una variable local capturada debe ser final o efectivamente final.
        List<String> eventos = new ArrayList<>();
        Consumer<String> anotar = eventos::add;
        anotar.accept("La referencia no cambia, pero el contenido de la lista sí");
        System.out.println(eventos);
        verificar(Math.abs(precioFinal.apply(100.0) - 121.0) < 0.000001, "El cálculo usa el valor capturado");
    }

	/**
	 * andThen permite encadenar funciones. Primero se ejecuta la función situada a
	 * la izquierda y su resultado se pasa automáticamente a la siguiente. El tipo
	 * devuelto por la primera función debe ser compatible con el tipo recibido por
	 * la segunda.
	 *
	 * <p>
	 * Uso real: crear tuberías de transformación, por ejemplo limpiar un texto,
	 * validarlo, convertirlo y finalmente almacenarlo o enviarlo a otra capa de la
	 * aplicación.
	 * </p>
	 */
	static void ejemplo09Composicion() {
        Function<String, String> limpiar = String::strip;
        Function<String, Integer> medir = String::length;
        Function<String, Integer> medianteAndThen = limpiar.andThen(medir);
        Function<String, Integer> medianteCompose = medir.compose(limpiar);
        for (String entrada : List.of("  REST  ", "NIO", " ")) {
            int longitud = medianteAndThen.apply(entrada);
            System.out.println("[" + entrada + "] -> " + longitud);
            verificar(longitud == medianteCompose.apply(entrada), "compose y andThen expresan el mismo orden en esta combinación");
        }
    }

	/**
	 * Map.forEach recorre todas las parejas clave-valor de un mapa. La lambda
	 * recibe dos parámetros: la clave y el valor asociado.
	 *
	 * <p>
	 * Uso real: recorrer configuraciones, resultados agrupados, estadísticas,
	 * inventarios o datos recuperados de una base de datos cuando están organizados
	 * mediante clave y valor.
	 * </p>
	 */
	static void ejemplo10ForEachDeMapa() {
        Map<String, Integer> horas = new LinkedHashMap<>();
        horas.put("NIO",4); horas.put("JDBC",6); horas.put("REST",3);
        Map<String, String> resumen = new LinkedHashMap<>();
        horas.forEach((tema, cantidad) -> resumen.put(tema, cantidad >= 4 ? "bloque largo" : "bloque breve"));
        resumen.forEach((tema, etiqueta) -> System.out.println(tema + ": " + etiqueta));
        // La colección recorrida no se modifica estructuralmente desde el propio forEach.
        horas.replaceAll((tema, cantidad) -> cantidad + 1);
        System.out.println("Plan con una hora más por bloque: " + horas);
        verificar(horas.get("JDBC") == 7, "replaceAll sustituye valores manteniendo las claves");
    }

	/**
	 * Interfaz funcional propia utilizada para representar operaciones binarias con
	 * importes.
	 *
	 * <p>
	 * Uso real: implementar diferentes estrategias de cálculo sin modificar el
	 * código que las utiliza, por ejemplo descuento normal, descuento premium o
	 * recargo especial.
	 * </p>
	 */
	@FunctionalInterface
	private interface Calculadora {
		double calcular(double importe, double porcentaje);
	}


/**
 * Comprueba una propiedad observada por la demostración y falla si no se cumple.
 * No depende de activar las aserciones de Java con -ea: siempre se ejecuta.
 * El mensaje describe la regla que se está verificando, no solo un valor booleano.
 * @param condicion propiedad que debe ser verdadera
 * @param mensaje explicación de la propiedad
 */
private static void verificar(boolean condicion, String mensaje) {
    if (!condicion) throw new IllegalStateException(mensaje);
    System.out.println("Comprobado: " + mensaje);
}

}
