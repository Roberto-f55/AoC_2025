# Advent of Code - Día 11: Reactor

Esta es mi solución para el desafío del **Día 11**. El objetivo es analizar la red de cableado de un reactor para encontrar rutas específicas a través de un grafo dirigido acíclico (DAG) de dispositivos.

## El Problema

El desafío consiste en trazar rutas de datos a través de una red de dispositivos interconectados:

### Parte 1: Rutas Simples
Se debe encontrar el número total de caminos posibles desde el dispositivo `you` hasta la salida `out`.
* **Mecánica:** Búsqueda en profundidad (DFS) estándar en un grafo dirigido.

### Parte 2: Rutas con Requisitos Intermedios
Se deben encontrar las rutas desde el servidor `svr` hasta `out`, pero con una restricción estricta: el camino debe pasar obligatoriamente por los dispositivos `dac` (convertidor digital-analógico) y `fft` (transformada rápida de Fourier).
* **Mecánica:** Búsqueda de caminos con validación de estado acumulado.

---

## Fundamentos de Diseño

Basado en los principios de ingeniería de software para asegurar calidad y mantenibilidad:

* **Alta Cohesión:**
  Las partes del módulo están estrechamente relacionadas y enfocadas en una única tarea.
    * **`DeviceManager`**: Se dedica exclusivamente a la infraestructura: creación del objeto, parsing del input y coordinación. No contiene lógica matemática.
    * **`PathSolver`**: Se dedica exclusivamente a la lógica algorítmica (recursividad y gestión de estados).
    * **`State`**: Un record interno que cohesiona los tres valores que definen un estado único en el grafo (nodo actual y flags de visita).

* **Bajo Acoplamiento:**
  Se han diseñado componentes con pocas interdependencias. El `DeviceManager` crea instancias efímeras de `PathSolver` para cada cálculo. Esto desacopla el ciclo de vida de los datos (que persisten) del ciclo de vida de la ejecución del algoritmo (que requiere una memoria limpia cada vez).

* **Abstracción:**
  La solución oculta los detalles complejos detrás de una interfaz simple. La clase `DeviceManager` actúa como una fachada que abstrae todo el proceso. Ofrece métodos directos como `solveA()` o `solveB()`, ocultando al cliente la existencia de la clase `PathSolver` y la recursividad.

* **Código Expresivo:**
  El código es intuitivo y auto explicativo, usando nombres descriptivos para evitar comentarios excesivos. El uso de términos como `buildDevices`, `foundDac` o `memo` facilita la lectura y comprensión inmediata del código.

---

## Principios de Diseño y Contrato

* **Principio DRY (Don't Repeat Yourself):**
  Este es el principio rector de esta solución. En lugar de escribir dos algoritmos diferentes (uno para caminos simples y otro para caminos con requisitos), se ha implementado **un único algoritmo universal**. La Parte A se trata como un caso especial de la Parte B donde los requisitos "ya se han cumplido" al inicio.

* **Principio de Responsabilidad Única (SRP):**
  Cada clase se enfoca en realizar una sola tarea o responsabilidad. Se ha separado la responsabilidad de leer y almacenar el grafo (`DeviceManager`) de la responsabilidad de recorrerlo (`PathSolver`).

* **Principio YAGNI (You Aren't Gonna Need It):**
  Se aconseja no añadir funcionalidad hasta que sea necesaria. Se ha evitado la sobreingeniería, no creando una clase `Graph` compleja ni nodos genéricos. Un simple `Map<String, List<String>>` es la estructura más efectiva.

* **Principio de Mínima Sorpresa:**
  Las abstracciones se comportan de manera predecible. El uso de métodos estáticos de creación (`create`) y fluidez en la carga de datos (`with`) sigue convenciones estándares.

---

## Patrones de Diseño

* **Patrón Factory Method:**
  En lugar de usar directamente el constructor para crear objetos, se llama a un método estático que encapsula la creación (`DeviceManager.create()`), permitiendo flexibilidad futura.

* **Memoization (Programación Dinámica):**
  Se utiliza dentro de `PathSolver` para almacenar resultados de sub-problemas ya resueltos. Dado que el número de caminos crece exponencialmente, guardar el resultado de un estado evita recalcular ramas idénticas.

* **Value Objects (vía Records):**
  El uso del record `State` simplifica la gestión de las claves del mapa de memoria. Al ser inmutable y tener `equals`/`hashCode` automáticos, garantiza la integridad de la caché.

---

## Lógica de Resolución

La solución utiliza un algoritmo unificado, variando únicamente los parámetros iniciales para resolver ambas partes con el mismo código.

### Parte 1: Simulación de Requisitos Cumplidos
Se invoca al solver asumiendo que los nodos requeridos ya se tienen (`true`, `true`).
1.  **Inicio:** Se llama a `solve("you", true, true)`.
2.  **Consecuencia:** Al llegar al final, la validación `(foundDac && foundFft)` siempre es verdadera, comportándose como un DFS estándar.

### Parte 2: Búsqueda Estricta
Se invoca al solver indicando que los nodos requeridos faltan (`false`, `false`).
1.  **Inicio:** Se llama a `solve("svr", false, false)`.
2.  **Acumulación:** Si el recorrido pasa por `dac` o `fft`, los booleanos cambian a `true`.
3.  **Validación:** Al llegar al final, solo se devuelve 1 si ambos booleanos se han convertido en `true` durante el trayecto.

### Algoritmo General
1.  **Actualización de Estado:** Se verifica si el nodo actual es uno de los objetivos.
2.  **Caso Base:** Si es `out`, se comprueba la condición de los booleanos.
3.  **Memoización:** Se consulta si el estado `{nodo, dac, fft}` ya existe en el mapa.
4.  **Recursividad:** Se suman los caminos válidos de todos los vecinos.

