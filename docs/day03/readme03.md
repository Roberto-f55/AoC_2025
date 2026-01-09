# Advent of Code - Día 3: Lobby

Esta es mi solución para el desafío del **Día 3** de Advent of Code. El problema consiste en restaurar la energía de las escaleras mecánicas seleccionando baterías específicas de bancos de energía para maximizar el voltaje de salida (*Joltage*).

## El Problema

El objetivo es formar el número más alto posible seleccionando una subsecuencia de dígitos de una cadena dada, manteniendo estrictamente el orden original relativo de los dígitos.

1.  **Parte 1:** Seleccionar exactamente **2 baterías** para maximizar el valor.
2.  **Parte 2:** Seleccionar exactamente **12 baterías** para maximizar el valor.

La dificultad radica en que elegir el número más alto disponible demasiado tarde podría impedir completar la secuencia requerida (ej. en `1239`, si necesito 3 dígitos y elijo el `9`, ya no puedo completar la secuencia).

---

## 1. Fundamentos de Diseño

* **Alta Cohesión:**
  La clase `Voltage` mantiene sus responsabilidades segregadas. `add/batteries` gestionan la entrada de datos, `sum` orquesta la transformación utilizando Streams, y `maxVol` encapsula la complejidad algorítmica pura.

* **Abstracción Algorítmica:**
  El problema se reduce a encontrar la "subsecuencia lexicográfica máxima". He abstraído este problema permitiendo que el algoritmo funcione para cualquier cantidad de baterías (`maxBatts`), lo que resolvió la Parte 2 sin cambios en la lógica, simplemente cambiando el parámetro de entrada.

* **Inmutabilidad (Input):**
  Trato las cadenas de entrada como inmutables. No modifico el banco de baterías original; en su lugar, construyo la solución (`StringBuilder`) paso a paso basándome en lecturas posicionales.

---

## 2. Principios de Diseño

* **Principio DRY (Don't Repeat Yourself):**
  La lógica para encontrar 2 baterías y 12 baterías es idéntica. Utilicé el parámetro `maxBatts` en el método `sum` y `maxVol` para reutilizar el 100% del código entre ambas partes del problema.

* **Principio de Responsabilidad Única (SRP):**
  El método `maxVol` tiene una única razón de ser: calcular el máximo local de una cadena. No sabe de sumas totales ni de parsing de longs, solo devuelve la cadena óptima. El método `sum` se encarga de la conversión de tipos y la agregación.

* **Principio OCP (Open/Closed Principle):**
  El diseño es robusto ante cambios en los requerimientos de longitud. Si el día de mañana se requieren 50 baterías, el algoritmo escala naturalmente sin necesidad de refactorizar la lógica interna.

* **Principio de Mínimo Compromiso:**
  La interfaz de un objeto muestra sólo lo necesario para su operación y nada más.
    * **Aplicación:** Los métodos `addBats` y `maxVol` son `private`. Esto evita exponer detalles internos que no son relevantes para el usuario y reduce la dependencia entre partes del sistema.

* **Principio de Mínima Sorpresa:**
  Las abstracciones deben comportarse de manera predecible, sin efectos secundarios inesperados.
    * **Aplicación:** La clase es inmutable respecto a su entrada durante el cálculo. Llamar a `sum()` no altera la lista `batteries`, garantizando que el sistema funcione de manera intuitiva.

---

## 3. Patrones de Diseño

* **Fluent Interface (Interfaz Fluida):**
  Implementé los métodos `create`, `add` y `batteries` para permitir una construcción legible y encadenada del objeto:
  `Voltage.create().add(input).sum(12)`

* **Factory Method:**
  Utilizo `Voltage.create()` como método de entrada estático, encapsulando la instanciación y ofreciendo una API limpia.

* **Patrón Iterator (vía Streams):**
  [Proporciona una manera de acceder secuencialmente a los elementos de un objeto agregado sin exponer su representación subyacente.
    * **Uso:** Utilizo `batteries.stream()` para recorrer los bancos de baterías. Esto separa la lógica de iteración de la estructura de datos, mejorando la modularidad.

---

## Lógica de Resolución: Algoritmo Voraz (Greedy)

Para resolver este problema de manera eficiente y evitar la fuerza bruta exponencial, implementé un **Algoritmo Greedy**.

### La Estrategia
Para formar el número más grande posible de longitud $K$, el primer dígito debe ser el número más grande disponible en un rango que me asegure que *todavía quedan suficientes números a la derecha* para llenar los $K-1$ espacios restantes.

### Implementación (`maxVol`)

```java
private String maxVol(String bats, int maxBatts) {
    // ...
    int limit = bats.length() - (maxBatts - i);
    // ...
}
````
1.  **Iteración Externa:**
    Repito el proceso tantas veces como baterías necesite (`maxBatts`).

2.  **Cálculo del Límite:**
    Esta es la clave matemática.
    ```java
    int limit = bats.length() - (maxBatts - i);
    ```
    La variable `limit` define hasta dónde puedo buscar el dígito más alto.
    * *Razón:* Si necesito recoger 3 números más, no puedo elegir un dígito que esté en la última o penúltima posición del string. El límite me obliga a reservar espacio para los dígitos futuros.

3.  **Selección Voraz:**
    En el rango `[start, limit]`, busco el dígito con el valor ASCII más alto (`best`).

4.  **Avance:**
    Una vez seleccionado el mejor dígito, muevo el puntero de inicio (`start`) justo después de él (`bestIndex + 1`) para mantener el orden relativo.

#### Ejemplo Visual
**Cadena:** `8 1 1 1 9` | **Objetivo:** 2 dígitos.

* **Paso 1:** Busco el mayor en el rango seguro (debo dejar 1 libre al final).
    * Ventana de búsqueda: `[8 1 1 1] 9`
    * Selección: **8**.
    * *El puntero avanza después del 8.*

* **Paso 2:** Busco el mayor en el resto (ya no necesito reservar nada).
    * Ventana de búsqueda: `_ [1 1 1 9]`
    * Selección: **9**.

* **Resultado Final:** `89`.

Este enfoque garantiza la solución óptima sin necesidad de retroceder (*backtracking*).