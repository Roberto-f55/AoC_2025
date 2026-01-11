# 🎄 Advent of Code - Día 8: Playground

Esta es mi solución para el desafío del **Día 8**. El objetivo es ayudar a los Elfos a diseñar un sistema de iluminación conectando cajas de conexiones eléctricas en un espacio 3D, priorizando las conexiones más cortas para formar circuitos más grandes.

## El Problema

El desafío presenta una lista de coordenadas `(X, Y, Z)` que representan cajas de conexiones. Inicialmente, cada caja es un circuito independiente.

### Parte 1: Eficiencia de Cableado
Se busca conectar los pares de cajas más cercanos entre sí. Al conectar dos cajas, sus circuitos se fusionan. El objetivo es realizar un número específico de conexiones óptimas (1000) y calcular el tamaño de los circuitos resultantes más grandes.

### Parte 2: Unificación Total
El proceso debe continuar hasta que todas las cajas formen parte de un único circuito unificado. Se requiere calcular un valor basado en las coordenadas de la última conexión realizada que logró la unificación.

---

## 1. Fundamentos de Diseño



* **Alta Cohesión:**
  Se ha logrado una segregación estricta donde cada clase tiene una responsabilidad única y acotada:
    * **`CircuitManager`**: Actúa como el orquestador principal y dueño del estado (persistencia de datos).
    * **`CircuitConnector`**: Encapsula exclusivamente la complejidad algorítmica y la lógica de fusión de grafos.
    * **`Circuit`**: Abstrae la estructura de datos, agrupando las cajas que están conectadas entre sí.
    * **`Coordinate`**: Define la unidad atómica de posición en el espacio 3D.
    * **`Pair`**: Modela la relación entre dos coordenadas y encapsula el cálculo matemático de su distancia.

* **Bajo Acoplamiento:**
  El sistema reduce las dependencias rígidas. `CircuitConnector` no instancia sus propios datos, sino que trabaja sobre abstracciones entregadas, permitiendo que la lógica de conexión sea independiente de cómo se crean o almacenan los datos.

* **Abstracción:**
  Se ocultan los detalles de implementación de bajo nivel. El uso de **Java Records** (`Coordinate`, `Pair`) permite tratar los datos como valores inmutables del dominio, mientras que `Circuit` oculta la gestión interna de la lista de coordenadas, exponiendo solo comportamientos semánticos.

* **Código Expresivo:**
  Se prioriza la legibilidad como documentación viva. Nombres de métodos como `connectClosestPairs`, `transferCoordinates` o `associateCircuitWithCoordinate` narran explícitamente las reglas de negocio, eliminando la ambigüedad de variables genéricas.

---

## 2. Principios de Diseño y Contrato

* **Principio de Responsabilidad Única (SRP):**
  Cada componente tiene una única razón para cambiar, alineándose con la alta cohesión descrita anteriormente. Separar el "qué" (Manager) del "cómo" (Connector) facilita el mantenimiento y las pruebas unitarias.

* **Principio de Mínima Sorpresa:**
  El comportamiento de los objetos es intuitivo. El método estático `Coordinate.from(String)` realiza el parsing esperado sin efectos secundarios, y la inmutabilidad de los Records garantiza que las coordenadas no cambien inesperadamente durante la ejecución.

* **Principio de Mínimo Compromiso:**
  La visibilidad de los métodos está restringida al mínimo necesario. Métodos auxiliares complejos como `generateAllPairs` o `mergeCircuits` son privados en el `Connector`, exponiendo únicamente una API pública limpia (`connect`) que define el contrato claro con el cliente.

---

## 3. Patrones de Diseño

* **Patrón de Inyección de Dependencias:**
  Utilizado en `CircuitConnector` para recibir las listas y mapas necesarios a través de su constructor. Esto desacopla la creación de los objetos de su uso, facilitando la testabilidad y cumpliendo con el principio de inversión de control sobre los datos.

* **Factory Method:**
  Se implementan métodos de fabricación estáticos (`create`, `from`) en lugar de constructores públicos complejos. Esto permite una sintaxis fluida y encapsula la lógica de instanciación (`CircuitManager.create().with(...)`).

* **Delegation Pattern:**
  El `CircuitManager` delega la tarea compleja de conectar nodos a un especialista, el `CircuitConnector`. El Manager mantiene el control del flujo, pero "subcontrata" el trabajo pesado, manteniendo su propio código limpio.

---

## Lógica de Resolución

### Algoritmo de Agrupamiento (Clustering)
La solución implementa una variación de un algoritmo de **Componentes Conectados** similar al algoritmo de Kruskal, pero gestionando conjuntos de listas explícitas.



### 1. Inicialización y Mapeo
Cada coordenada se envuelve inicialmente en su propio objeto `Circuit`. Se utiliza un mapa (`Map<Coordinate, Circuit>`) para tener acceso $O(1)$ al circuito al que pertenece cualquier caja en cualquier momento.

### 2. Generación y Ordenación de Pares
Se calculan las distancias entre todos los pares posibles de cajas y se ordenan de menor a mayor (`Pair::distance`). Esto asegura que el algoritmo sea "codicioso" (Greedy), priorizando siempre la conexión más corta disponible.

### 3. Fusión de Circuitos (Merge)
El núcleo del algoritmo reside en `CircuitConnector`. Se iteran los pares ordenados:
1.  Se verifica si las dos cajas del par pertenecen a circuitos diferentes (`!circuitA.equals(circuitB)`).
2.  Si son diferentes, se ejecuta `mergeCircuits`:
    * Se identifica el circuito más pequeño y el más grande.
    * **Transferencia:** Se mueven todas las coordenadas del circuito menor al mayor (`transferCoordinates`).
    * **Actualización:** Se actualiza el mapa de referencias para que todas las cajas transferidas apunten al nuevo circuito padre.
    * **Eliminación:** El circuito vacío se elimina de la lista principal.

Este proceso reduce iterativamente el número de circuitos hasta cumplir la condición de parada (límite de iteraciones o unificación total).