# Advent of Code - Día 9: Cine del Polo Norte

Esta es mi solución para el desafío del **Día 9**. El objetivo es ayudar a los Elfos a redecorar el suelo del cine, encontrando el área rectangular más grande posible definida por baldosas rojas, aplicando diferentes reglas de validación según la parte del problema.

## El Problema

El desafío presenta una lista de coordenadas que representan baldosas rojas en una cuadrícula.

### Parte 1: Maximización Sin Restricciones
En esta fase, el objetivo es encontrar el rectángulo con la mayor área posible utilizando dos baldosas rojas cualesquiera como esquinas opuestas.
* **Regla:** No hay restricciones sobre el contenido del rectángulo; simplemente se busca la mayor superficie matemática ($base \times altura$) entre dos puntos rojos.

### Parte 2: Restricción de Zona Verde
Los Elfos introducen una restricción geométrica: el rectángulo solo puede existir sobre baldosas rojas o verdes.
* **Regla:** Las baldosas rojas forman un bucle (un polígono) y las verdes son las que quedan encerradas dentro. Por lo tanto, cualquier rectángulo candidato debe estar geométricamente **contenido** dentro de este polígono para ser válido.

---

## Fundamentos de Diseño

Basado en los principios de ingeniería de software para asegurar calidad y mantenibilidad:

* **Alta Cohesión:**
  Las partes del módulo están estrechamente relacionadas y enfocadas en una única tarea.
    * **`RectangleSolver`**: Se enfoca exclusivamente en la iteración y la lógica de maximización.
    * **`Polygon`**: Encapsula estrictamente la complejidad matemática y geométrica.
    * **`Coordinate` / `PairTile`**: Estructuras de datos que definen las propiedades básicas del dominio.

* **Bajo Acoplamiento:**
  Se han diseñado componentes con pocas interdependencias. El `RectangleSolver` no conoce los detalles de implementación de cómo se detecta una intersección; simplemente delega esa pregunta al `Polygon` a través de una interfaz pública clara (`contains`).

* **Abstracción:**
  La solución oculta los detalles complejos detrás de una interfaz simple. `Polygon` actúa como una barrera entre lo esencial (saber si es válido) y lo secundario (el algoritmo de *Ray Casting*), permitiendo manejar la complejidad ocultando detalles innecesarios.

* **Código Expresivo:**
  El código es intuitivo y auto explicativo, usando nombres descriptivos para evitar comentarios excesivos. El uso de términos como `enforcePolygonRules` o `width` facilita la lectura y comprensión inmediata del código.

---

## Principios de Diseño y Contrato

* **Principio de Responsabilidad Única (SRP):**
  Cada clase se enfoca en realizar una sola tarea o responsabilidad. `Polygon` solo resuelve geometría, y `Solver` solo resuelve la búsqueda del máximo. Esto refleja una alta cohesión, donde cada clase tiene una sola razón para cambiar.

* **Principio YAGNI (You Aren't Gonna Need It):**
  Se aconseja no añadir funcionalidad hasta que sea necesaria. En lugar de crear una jerarquía de clases compleja para diferenciar la Parte 1 de la 2, se utilizó una solución simple con una bandera de control (`boolean`), evitando soluciones excesivamente complejas cuando hay alternativas más simples.

* **Principio de Mínima Sorpresa:**
  Las abstracciones se comportan de manera predecible. El uso de `Records` para las coordenadas garantiza que los datos sean inmutables y no tengan efectos secundarios inesperados.

* **Principio de Mínimo Compromiso:**
  La clase `Polygon` expone solo lo necesario (`contains`). Los detalles internos como la clase `Segment` o el método `rectangleTouchPolygon` son privados, reduciendo la dependencia entre partes del sistema.

* **Principio DRY (Don't Repeat Yourself):**
  Cada pieza de conocimiento tiene una representación única. Se utiliza la misma lógica de iteración y cálculo de áreas para ambas partes del problema, variando únicamente la condición de validación.

---

## Patrones de Diseño

* **Patrón Factory Method:**
  En lugar de usar directamente el constructor para crear objetos, se llama a un método estático que encapsula la creación. Esto se observa en `Coordinate.from(String)` y `RectangleSolver.create(boolean)`, simplificando la instanciación desde el cliente.

* **Value Objects (vía Records):**
  Se modelan `Coordinate` y `PairTile` como objetos inmutables cuya igualdad se basa en su valor, no en su identidad.

---

## Lógica de Resolución

### Parte 1: Cálculo Directo
El `RectangleSolver` se inicializa con `enforceRules = false`.
1.  Se generan todos los pares posibles de baldosas rojas.
2.  Se calcula el área de cada par.
3.  **Optimización:** Al no haber restricciones (`!enforcePolygonRules`), el objeto `Polygon` ni siquiera se instancia (es `null`), ahorrando memoria y ciclos de CPU. Cualquier par es válido, por lo que simplemente se guarda el área máxima encontrada.

### Parte 2: Validación Geométrica
El `RectangleSolver` se inicializa con `enforceRules = true`.

1.  **Construcción del Polígono:** Se crea una instancia de `Polygon` que transforma las coordenadas en una lista de segmentos (`Segment`).
2.  **Poda por Área:** Si el área de un candidato es menor que el máximo actual (`area <= maxArea`), se descarta inmediatamente antes de hacer cálculos complejos.
3.  **Algoritmo de Inclusión (Punto en Polígono):**
    * **Intersección:** Se verifica si los bordes del rectángulo cortan los bordes del polígono.
    * **Ray Casting:** Se lanza un rayo desde el centro del rectángulo. Si cruza un número impar de bordes, está dentro.

Esta lógica permite resolver ambas partes reutilizando el 90% del código, manteniendo el sistema robusto y fácil de mantener.

## Ejemplo de Uso

```java
List<String> input = Reader.readFile("input.txt");

// --- Parte 1 ---
// 'false' desactiva la validación geométrica (más rápido)
long part1 = RectangleSolver.create(false)
                            .with(input)
                            .solve();

// --- Parte 2 ---
// 'true' activa la creación del Polígono y las reglas de contención
long part2 = RectangleSolver.create(true)
                            .with(input)
                            .solve();