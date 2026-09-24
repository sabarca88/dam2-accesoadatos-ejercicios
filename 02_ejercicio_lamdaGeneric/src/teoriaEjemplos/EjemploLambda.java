package es.dam.accesodatos.tema02_lambda;

import java.util.function.Predicate;

public class EjemploLambda {
    public static void main(String[] args) {

        // =========================================================================
        // DEFINICIÓN DE LA LAMBDA
        // =========================================================================
        Predicate<Integer> esMayorDeEdad = (edad) -> edad >= 18;

        int edadAlumno1 = 20;
        int edadAlumno2 = 16;

        // =========================================================================
        // USO DE LA LAMBDA (Evaluación con .test())
        // =========================================================================
        boolean resultado1 = esMayorDeEdad.test(edadAlumno1);
        System.out.println("¿El alumno de " + edadAlumno1 + " años es mayor de edad? " + resultado1);

        boolean resultado2 = esMayorDeEdad.test(edadAlumno2);
        System.out.println("¿El alumno de " + edadAlumno2 + " años es mayor de edad? " + resultado2);
    }
}

