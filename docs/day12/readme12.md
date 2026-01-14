# Advent of Code - Día 12: Tree Farm Solver

Esta es mi solución para el desafío del **Día 12**. El objetivo es resolver un problema complejo de empaquetado 2D, determinando si un conjunto de regalos con formas de poliminó puede encajar dentro de diferentes áreas de cultivo definidas.

## El Problema

El desafío consiste en validar si una configuración específica de regalos cabe en una parcela de terreno sin superponerse:

### Parte 1: Validación de Ajuste Geométrico
Se debe determinar cuántas de las regiones propuestas pueden albergar la lista de regalos solicitada.
* **Mecánica:** Se requiere manipular formas geométricas (rotación y espejo), generar combinaciones de posiciones en la cuadrícula y verificar colisiones.
* **Reto:** El espacio de búsqueda es masivo, requiriendo un enfoque que evite la fuerza bruta ingenua mediante el uso de generadores perezosos y heurísticas.

---

## Fundamentos de Diseño

El diseño de la solución se adhiere a fundamentos clave para gestionar la complejidad inherente a la combinatoria geométrica, asegurando la calidad y mantenibilidad del sistema.

* **Alta Cohesión:**
  Las partes de cada módulo están estrechamente relacionadas y enfocadas en una única tarea.
    * **`GiftShape`**: Se limita exclusivamente a la manipulación geométrica. Sus métodos (`mirror`, `turn90`) solo operan sobre coordenadas, sin contener lógica sobre las reglas del juego.
    * **`PermutationUtils`**: Cohesiona toda la lógica matemática de combinatoria en un solo lugar, sin conocimiento del contexto de negocio.

* **Bajo Acoplamiento:**
  Se han diseñado módulos con interdependencias mínimas.
    * `PermutationUtils` es una clase genérica `<T>` totalmente desacoplada de la lógica de los regalos.
    * `TreeArea` y `Point2D` son `records` inmutables que transportan datos, permitiendo que las clases interactúen sin crear dependencias de estado complejas.

* **Abstracción:**
  Se ocultan los detalles complejos detrás de una interfaz simple.
    * La clase `LogisticsEngine` ofrece el método público `checkFit()`. Este método actúa como una fachada que oculta la complejidad de la generación de permutaciones y validación de colisiones, permitiendo comprender su uso sin analizar la implementación interna.

* **Código Expresivo:**
  El código es claro y comprensible, facilitando la lectura y el mantenimiento.
    * Se usan nombres descriptivos para evitar la necesidad de comentarios excesivos. Métodos como `turn90()`, `mirror()` o `attemptPlacement()` describen la acción exacta, haciendo que el código sea auto explicativo.

---

## Principios de Diseño y Contrato

* **Principio de Responsabilidad Única (SRP):**
  Cada clase tiene una sola razón para cambiar.
    * **`TreeFarmSolver`**: Se encarga únicamente de la orquestación e infraestructura (parsing de datos).
    * **`LogisticsEngine`**: Se encarga únicamente de la lógica algorítmica de colocación.

* **Diseño por Contrato (Principio de Mínimo Compromiso):**
  La interfaz de un objeto muestra sólo lo necesario para su operación.
    * `LogisticsEngine` expone solo `initialize` y `checkFit`. Los métodos complejos como `attemptPlacement`, `resolvePlacement` o `retrieveShapes` son privados, evitando exponer detalles internos no relevantes para el consumidor de la clase.

* **Principio Abierto Cerrado (OCP):**
  Las clases están abiertas para la extensión, pero cerradas para la modificación.
    * `PermutationUtils` está diseñado para aceptar cualquier lista de objetos. Puede reutilizarse para permutar otros tipos de datos sin modificar su código interno.

* **Principio de Mínima Sorpresa:**
  Las abstracciones se comportan de manera predecible y sin efectos secundarios.
    * El uso de `record` para `Point2D` y `GiftShape` garantiza inmutabilidad. Al llamar a `turn90()`, no se modifica el objeto original, sino que se devuelve uno nuevo, evitando errores por estado compartido.

---

## Patrones de Diseño

Se han aplicado soluciones probadas para problemas comunes de diseño:

* **Patrón Iterator:**
  Proporciona una manera de acceder secuencialmente a los elementos sin exponer su estructura interna.
    * En `PermutationUtils`, se implementa un `Iterator` personalizado ("Lazy Iterator"). Esto permite generar combinaciones masivas bajo demanda sin desbordar la memoria RAM, calculando el siguiente paso solo cuando se solicita.

* **Patrón Factory Method:**
  Se utiliza un método estático que encapsula la creación y configuración inicial del objeto.
    * `LogisticsEngine.initialize(shapes)` actúa como una factoría que realiza el pre-procesamiento pesado (generar las 8 mutaciones de rotación/espejo) antes de devolver la instancia lista.
    * `GiftShape.parseShape(...)` encapsula la lógica de conversión de texto a coordenadas vectoriales.

---

## Lógica de Resolución

La solución utiliza una estrategia de **Fuerza Bruta Inteligente** optimizada:

1.  **Pre-cálculo Geométrico:**
    Al inicio, el motor genera las 8 variaciones del grupo diedral $D_4$ (rotaciones y espejos) para cada regalo.

2.  **Heurísticas de Poda:**
    Antes de intentar colocar nada, se verifica:
    * Si el área es muy grande respecto a los regalos, se asume ajuste (`true`).
    * Si el área física de los regalos supera el área de la cuadrícula, se descarta inmediatamente (`false`).

3.  **Algoritmo de Doble Combinatoria:**
    * **Nivel 1 (Posición):** Se usa `PermutationUtils` para generar combinaciones de coordenadas (anclajes) donde poner los regalos.
    * **Nivel 2 (Orientación):** Para cada conjunto de posiciones, se genera otra combinatoria de orientaciones posibles.
    * **Validación:** Se colocan los regalos virtualmente y se usa un `Set<Point2D>` para detectar colisiones. Si el tamaño del set es igual a la suma total de bloques, la solución es válida.