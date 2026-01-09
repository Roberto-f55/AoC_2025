# Advent of Code - Día 4: Printing Department

Esta es mi solución para el desafío del **Día 4**. El objetivo es ayudar a los Elfos del departamento de impresión a optimizar el movimiento de grandes rollos de papel para que tengan tiempo de derribar una pared y acceder a la cafetería.

## El Problema

El desafío consiste en gestionar un almacén representado por una cuadrícula de caracteres, donde los rollos de papel están marcados con el símbolo `@`.

### Parte 1: Identificación de Acceso
Una carretilla elevadora solo puede acceder a un rollo si este tiene **menos de cuatro** rollos adyacentes (en las 8 posiciones que lo rodean). Debemos contar cuántos rollos cumplen esta condición inicialmente.

### Parte 2: Proceso de Eliminación en Cadena
Al retirar un rollo accesible, los rollos vecinos que antes estaban "bloqueados" por la densidad de papel pueden volverse accesibles. El objetivo es calcular el total de rollos que se pueden retirar si repetimos el proceso de forma iterativa hasta que no quede ningún rollo que cumpla la condición de acceso.

---

## 1. Fundamentos de Diseño

* **Alta Cohesión:**
  Las responsabilidades están segregadas de forma estricta. `PapersManager` se dedica a la construcción y almacenamiento de la estructura de datos, mientras que `Matrix` encapsula la lógica algorítmica de vecindad y la simulación del estado.

* **Abstracción:**
  Se oculta la complejidad del manejo de matrices bidimensionales y la lógica de detección de vecinos detrás de métodos de alto nivel como `maxNumberOfPapers()`. El usuario obtiene el resultado sin conocer los detalles de implementación.

* **Código Expresivo:**
  Se ha priorizado un código autoexplicativo mediante el uso de nombres descriptivos. Métodos como `iAmAPaper` o `if4ConsecutivesIsBlocked` eliminan la necesidad de comentarios extensos al narrar la lógica por sí mismos.

---

## 2. Principios de Diseño y Contrato

* **Principio de Responsabilidad Única (SRP):**
  Cada clase tiene una única razón para cambiar. `Matrix` gestiona las reglas del almacén, mientras que `PapersManager` actúa como el punto de entrada y orquestador de los datos.

* **Principio de Mínima Sorpresa:**
  El comportamiento del sistema es predecible. En la simulación de la Parte 2, se utiliza explícitamente `paperPositions.clear()` para garantizar que el estado interno se resetee en cada iteración, evitando efectos secundarios por datos residuales.

* **Principio de Mínimo Compromiso:**
  La interfaz de los objetos muestra solo lo necesario. Métodos de apoyo como `changeMatrix`, `outOfOrder` o el record `Position` son privados, protegiendo los detalles internos y reduciendo el acoplamiento.

* **Principio DRY (Don't Repeat Yourself):**
  No hay duplicación de lógica. El cálculo de la Parte 2 reutiliza el motor de detección de la Parte 1, aplicando la misma regla de negocio de forma iterativa.

---

## 3. Patrones de Diseño

* **Factory Method:**
  Se implementa `PapersManager.create()` como método estático de fabricación, encapsulando la instanciación y ofreciendo una API fluida para el cliente.

* **Patrón Iterator (vía Streams):**
  El uso de Java Streams para recorrer las filas y procesar las eliminaciones permite abstraer la iteración de la estructura de datos subyacente, mejorando la modularidad.

---

## Lógica de Resolución

### Regla de Negocio
La solución se divide en tres fases lógicas:

### 1. Regla de Acceso (Condición de Vecindad)
Un rollo de papel (`@`) se considera **accesible** si y solo si tiene **menos de cuatro** rollos adyacentes. El algoritmo verifica las 8 posiciones posibles alrededor de cada coordenada (norte, sur, este, oeste y las cuatro diagonales), asegurándose de no contar fuera de los límites de la matriz (`outOfOrder`).



### 2. Identificación Inicial (Parte 1)
En esta fase, el sistema realiza un escaneo completo de la matriz:
* Para cada celda que contiene un papel, se ejecuta el método `if4ConsecutivesIsBlocked`.
* Si el método devuelve `false`, significa que el papel es accesible para la carretilla.
* El resultado es el conteo total de estos papeles en el estado inicial del almacén.

### 3. Simulación de "Pelado" de Capas (Parte 2)
Para la Parte 2, el algoritmo implementa un proceso iterativo de eliminación que modifica la estructura del almacén en tiempo real:

1.  **Escaneo:** Se buscan todos los `@` que cumplen la regla de acceso en el estado actual.
2.  **Registro:** Se almacenan las coordenadas de los papeles encontrados en la lista `paperPositions`.
3.  **Eliminación:** Se transforman esos rollos en espacios vacíos (`.`) mediante el método `changeMatrix`.
4.  **Reinicio de Estado:** Se ejecuta `paperPositions.clear()` para vaciar la memoria temporal y preparar la siguiente ronda.
5.  **Finalización:** El bucle continúa hasta que una pasada completa no encuentra ningún nuevo rollo que pueda ser retirado.

Este enfoque permite que los rollos que estaban originalmente en el centro del bloque queden expuestos progresivamente a medida que las carretillas retiran las capas exteriores.