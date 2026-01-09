# Advent of Code - Día 2: Gift Shop

Esta es mi solución para el desafío del **Día 2** de Advent of Code. El problema consiste en auditar una base de datos de la tienda de regalos del Polo Norte para encontrar identificadores de productos inválidos dentro de rangos numéricos masivos.

## El Problema

El objetivo es sumar todos los IDs que cumplen ciertos patrones de repetición dentro de rangos dados (ej. `11-22`, `998-1012`).
1.  **Parte A:** IDs formados por dos mitades idénticas (ej. `1212`, `55`).
2.  **Parte B:** IDs formados por una sub-secuencia repetida $N$ veces (ej. `123123123`, `11111`).

---

## 1. Fundamentos de Diseño
Para la arquitectura de esta solución, me basé en cualidades inherentes de calidad de software para asegurar rendimiento y mantenibilidad.

* **Abstracción:**
  La abstracción es clave para manejar la complejidad. Utilicé `LongStream` para abstraer el concepto de "iteración sobre millones de números". No manipulo bucles `for` manualmente; declaro *qué* quiero hacer (filtrar, sumar) y dejo que la abstracción del Stream maneje el *cómo* de manera eficiente.

* **Alta Cohesión:**
  La clase `Expert` presenta una alta cohesión funcional.
    * `parseRange`: Se enfoca exclusivamente en la conversión de texto a datos numéricos.
    * `calculateSum`: Se enfoca en la orquestación del flujo.
    * `isValid...`: Se enfocan puramente en la lógica matemática.
      Las partes del módulo están estrechamente relacionadas y enfocadas.

* **Bajo Acoplamiento:**
  He desacoplado la lógica de recorrido de la lógica de validación. El método `calculateSum` no conoce las reglas de negocio (si es par, si se repite, etc.); solo sabe que debe aplicar un `Validator`. Esto permite modificar las reglas del negocio sin tocar el motor de procesamiento.

* **Código Expresivo (Clean Code):**
  Prioricé la legibilidad usando nombres que revelan intencionalidad (`lowerHalf`, `upperHalf`, `isValidPartA`). El código se explica a sí mismo, reduciendo la necesidad de documentación externa.

---

## 2. Principios de Diseño
Apliqué normas específicas de ingeniería para orientar la construcción del sistema.

* **Principio OCP (Open/Closed Principle):**
  El diseño está **abierto a la extensión pero cerrado a la modificación**. Si mañana aparece una "Parte C" con una nueva regla, puedo extender la funcionalidad pasando un nuevo predicado a `calculateSum` sin necesidad de modificar el código interno de ese método orquestador.

* **Principio DRY (Don't Repeat Yourself):**
  Identifiqué que el algoritmo de "expandir rangos $\to$ filtrar $\to$ sumar" era idéntico para ambas partes. Centralicé este conocimiento en `calculateSum`. Evité la duplicación de lógica de iteración.

* **Principio de Responsabilidad Única (SRP):**
  Cada método tiene una única razón para cambiar. Separé la lógica de *parsing* (interpretar el input) de la lógica de *cálculo* (matemáticas).

* **Diseño por Contrato (Mínimo Compromiso):**
  La interfaz pública (`sumA`, `sumB`, `add`) expone estrictamente lo necesario. Detalles internos como `parseRange` o `lowerHalf` son `private`, ocultando la implementación y reduciendo la superficie de contacto con el usuario de la clase.

* **Diseño por Contrato (Mínima Sorpresa):**
  El comportamiento de la clase es intuitivo y sin efectos secundarios (stateless). Al no guardar los resultados en listas internas, garantizo que ejecutar `sumA()` varias veces devuelva siempre el mismo resultado, cumpliendo con la expectativa de predictibilidad.

---

## 3. Patrones de Diseño
Utilicé soluciones arquitectónicas probadas para estructurar la comunicación y creación de objetos.

* **Fluent Interface (Interfaz Fluida):**
  Implementé el método `add` retornando `this`. Esto permite encadenar llamadas de manera legible, creando un lenguaje de dominio específico (DSL) pequeño:
  `Expert.create().add("1-10").execute(...)`

* **Strategy Pattern (Funcional):**
  Aplico el patrón Strategy al inyectar el comportamiento de validación. En lugar de herencia, paso la "estrategia" como un parámetro (`LongPredicate`) al método `calculateSum`.
    * Estrategia A: `this::isValidPartA`
    * Estrategia B: `this::isValidPartB`

* **Factory Method:**
  Oculté el constructor y utilicé el método estático `Expert.create()`. Esto me da control sobre la instanciación y sigue las convenciones de creación de objetos limpios.

---

## Lógica de Resolución

El desafío principal era procesar rangos que podrían contener millones de números sin colapsar la memoria (OutOfMemoryError). Para ello, diseñé una solución basada en **Streams Reactivos y Tipos Primitivos**.

### 1. El Pipeline de Procesamiento (`calculateSum`)



La arquitectura se basa en una tubería de datos eficiente que no almacena resultados intermedios:

```java
return ranges.stream()
        .flatMapToLong(this::parseRange) // 1. Expansión
        .filter(validator)               // 2. Filtrado
        .sum();                          // 3. Reducción 
```

* **`flatMapToLong` (Aplanamiento y Generación):**
    Este paso es crítico. Transforma cada rango de texto (ej. `"10-12"`) en un flujo continuo de números primitivos (`10, 11, 12`).
    
* **¿Por qué `LongStream`?**
    A diferencia de un `Stream<Long>` (que usa objetos), `LongStream` trabaja con datos crudos de 64 bits.
    * **Beneficio 1 (Eficiencia):** Evita el coste computacional del *"Boxing/Unboxing"* (convertir números a objetos y viceversa).
    * **Beneficio 2 (Memoria):** Reduce el uso de memoria RAM a casi cero, ya que utiliza **Lazy Evaluation** (Evaluación Perezosa): los números se generan, se procesan y se descartan en tiempo real, sin existir nunca todos a la vez en una lista.

### 2. Algoritmos de Validación

El método `.filter(validator)` actúa como una compuerta lógica que examina cada número individual generado por el stream.

#### Parte A: Simetría
El objetivo es encontrar números que son "dobles exactos", como `1212` o `55`.

* **Optimización:** Primero descarto cualquier número de longitud impar. Matemáticamente, es imposible dividir un string de longitud impar en dos mitades iguales de caracteres enteros.
* **Lógica:** Divido el número en dos cadenas (`lowerHalf` y `upperHalf`) y verifico su igualdad estricta (`equals`).

#### Parte B: Patrones Repetitivos
El objetivo es encontrar números compuestos por una secuencia repetida $N$ veces (ej. `123123` es `123` repetido 2 veces).

* **Estrategia (Fuerza Bruta Inteligente):** En lugar de buscar patrones arbitrarios mediante expresiones regulares (que serían lentas), utilizo las propiedades matemáticas del número.
* **Algoritmo:**
    1.  **Iteración:** Itero sobre los posibles tamaños del patrón candidato ($k$) usando un `IntStream`.
    2.  **Poda (Pruning):** Solo compruebo tamaños $k$ que sean divisores exactos de la longitud total del string, y solo hasta la mitad de dicha longitud (`len / 2`). Esto reduce drásticamente el espacio de búsqueda.
    3.  **Verificación:** Tomo la sub-cadena candidata (de 0 a $k$), la repito las veces necesarias (`repeat()`) para igualar la longitud original, y comparo si el resultado es idéntico al número original.