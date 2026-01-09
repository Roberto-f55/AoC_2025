# 🎄 Advent of Code - Día 7: Tachyons Tree Simulator

Esta es mi solución para el desafío del **Día 7**. El objetivo es simular el comportamiento de partículas subatómicas (taquiones) descendiendo a través de una estructura de laboratorio, resolviendo el problema mediante dos enfoques: una simulación física iterativa y un cálculo cuántico recursivo.

## El Problema

El desafío consiste en modelar un colector de taquiones representado por una matriz de caracteres. Los taquiones entran por un punto `S`, descienden verticalmente a través del espacio vacío (`.`) y, al encontrar divisores (`^`), se bifurcan hacia la izquierda y derecha.

### Parte 1: Simulación Física
Se debe calcular cuántas veces se activan los divisores simulando el flujo físico del haz. Esto implica mutar el estado del laboratorio, llenando los caminos recorridos con tuberías (`|`).

### Parte 2: Líneas Temporales Cuánticas
Se requiere calcular el número total de caminos posibles (líneas temporales) que una sola partícula podría tomar. Esto requiere un enfoque no destructivo que explore todas las posibilidades recursivamente.

---

## 1. Fundamentos de Diseño

* **Alta Cohesión:**
  Las responsabilidades están segregadas de forma estricta, cumpliendo con la idea de que las partes de un módulo deben estar estrechamente relacionadas y enfocadas en una única tarea. `TachyonsTree` se dedica exclusivamente al almacenamiento de datos, mientras que `TachyonsTreeManager` encapsula la lógica algorítmica.

* **Bajo Acoplamiento:**
  Se ha diseñado el sistema para tener pocas interdependencias. El `TachyonsTreeManager` no instancia el árbol internamente, sino que lo recibe a través de su constructor (Inyección de Dependencias), permitiendo que el modelo de datos cambie sin romper la lógica de negocio.

* **Abstracción:**
  Consiste en ocultar los detalles complejos detrás de una interfaz simple. `TachyonsTree` oculta la complejidad de la lista de caracteres (`List<char[]>`) y expone métodos semánticos como `isPoint` o `hasStick`. Se enfoca en la visión externa del objeto, resaltando sus comportamientos mientras oculta detalles innecesarios.

* **Código Expresivo:**
  El código es claro y comprensible, facilitando la lectura y el mantenimiento. Métodos como `whileStickCanContinue`, `hasAStickBehind` o `propagateStick` narran la lógica del problema mediante nombres descriptivos, evitando la necesidad de comentarios excesivos para explicar qué hace el código.

---

## 2. Principios de Diseño y Contrato

* **Principio de Responsabilidad Única (SRP):**
  Cada clase tiene una sola razón para cambiar, reflejando la alta cohesión. `TachyonsTreeBuilder` construye, `TachyonsTree` almacena y `TachyonsTreeManager` procesa.

* **Principio de Mínima Sorpresa:**
  Las abstracciones se comportan de manera predecible, sin efectos secundarios inesperados. El uso explícito de `tree.reset()` dentro de los métodos del `Manager` garantiza que la ejecución de la Parte A no contamine el estado para la Parte B, asegurando la idempotencia de las operaciones.

* **Principio de Mínimo Compromiso:**
  La interfaz del objeto muestra solo lo necesario para su operación y nada más. `TachyonsTree` no expone la lista cruda `structure` al exterior mediante getters públicos, protegiendo los detalles internos y reduciendo la dependencia entre partes del sistema.

* **Principio DRY (Don't Repeat Yourself):**
  Cada pieza de conocimiento tiene una representación única inequívoca. La lógica de validación de límites (`outOfLimits`) y verificación de tipos (`isPoint`) está centralizada en el modelo y reutilizada tanto por el algoritmo iterativo como por el recursivo.

---

## 3. Patrones de Diseño

* **Patrón Factory Method (Builder):**
  Se utiliza un enfoque creacional similar al Factory Method, donde se llama a un método estático que encapsula la creación del objeto. `TachyonsTreeBuilder.create()` permite configurar y ensamblar el sistema complejo (`Manager` + `Tree`) tras una interfaz fluida.

* **Patrón Iterator:**
  Proporciona una manera de acceder secuencialmente a los elementos sin exponer su representación subyacente. En la Parte A, el `Manager` recorre las filas

---

## Lógica de Resolución

### 1. Modelo de Datos (Dumb Tree)
La clase `TachyonsTree` actúa como el sustrato de la simulación. Mantiene una copia del input original para permitir el reseteo del estado, asegurando que cada sección del código se comporte de manera predecible.

### 2. Simulación Física (Parte A - Iterativa)
Esta fase utiliza un enfoque imperativo con mutación de estado.

* **Propagación:** El algoritmo recorre la matriz fila por fila.

* **División:** Si se encuentra un divisor (`^`) y la celda superior tiene una tubería (`|`) (verificado con `hasAStickBehind`), se activan nuevos flujos a izquierda y derecha.

* **Mutación:** El método `propagateStick` escribe físicamente un `|` en la matriz, lo que servirá de señal para las filas siguientes.

### 3. Simulación Cuántica (Parte B - Recursiva DFS)
Para esta parte, se requiere explorar todas las líneas temporales sin modificar la estructura física, utilizando un algoritmo de Búsqueda en Profundidad (DFS).

* **Optimización (Look-ahead):** El método `whileStickCanContinue` avanza el índice de la fila rápidamente mientras haya espacio vacío (`.`), evitando la sobrecarga de llamadas recursivas en tramos rectos.

* **Memoización:** Se utiliza un mapa (`Map<State, Long>`) para almacenar resultados de coordenadas (`row`, `col`) ya calculadas. Esto evita re-calcular ramas enteras del árbol que convergen en el mismo punto, optimizando drásticamente el rendimiento.

* **Bifurcación:** Al encontrar un divisor (`^`), la función se llama a sí misma para las coordenadas izquierda y derecha, sumando el total de caminos resultantes.