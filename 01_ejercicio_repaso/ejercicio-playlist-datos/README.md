# Ejercicio: SoundWave, estructuras para una app musical

## Duración orientativa

60 minutos.

## Contexto profesional

Vas a colaborar en el desarrollo de una aplicación musical llamada **SoundWave**. La aplicación todavía no tiene base de datos: durante esta práctica trabajarás con datos en memoria y elegirás estructuras de datos adecuadas para cada operación.

No debes crear clases propias. Toda la solución se realizará mediante funciones `static` dentro de `ejercicioPlaylist`.

## Objetivos

- Repasar variables, bucles, condicionales y funciones.
- Utilizar `ArrayList`, `LinkedList`, `HashMap` y `HashSet`.
- Separar la lógica del programa en funciones.
- Elegir una estructura según la operación que se realiza.
- Practicar el flujo `clone` -> cambios -> ejecución -> commit -> push.

## Cómo empezar

```bash
git clone URL_DEL_REPOSITORIO
cd ejercicio-playlist-datos
```

Abre la carpeta en IntelliJ IDEA y ejecuta `ejercicioPlaylist`.

## Requisitos mínimos

Completa las funciones marcadas con `TODO` para que el programa pueda:

1. Mostrar las canciones en el orden de la playlist.
2. Buscar una canción por su identificador.
3. Añadir canciones a una cola de reproducción.
4. Reproducir la siguiente canción y actualizar sus reproducciones.
5. Mostrar los artistas únicos.
6. Mostrar la canción más reproducida.

## Condiciones

- No crear clases propias.
- Usar funciones independientes para cada operación.
- No duplicar la lógica del menú dentro de cada opción.
- Añadir Control de Errores: Controlar IDs que no existan.
- No eliminar elementos de la playlist al reproducirlos: solo deben salir de la cola.

## Entrega

- Código funcionando.
- Enlace al repositorio compartido con la profesora.

## Pistas

- El `HashMap` resulta útil cuando se busca por ID.
- El `ArrayList` resulta cómodo para conservar el orden de la playlist.
- La cola puede representarse mediante `LinkedList`.
- Un `HashSet` evita repetir artistas.

## Ejercicios de Ampliación

- Buscar canciones por artista.
- Mostrar solo canciones de un género.
- Crear una segunda playlist.
- Mostrar las tres canciones más reproducidas.
- Añadir una opción para eliminar una canción de la playlist.
