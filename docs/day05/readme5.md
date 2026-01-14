# Advent of Code - Día 5: Cafetería

Esta es mi solución para el desafío del **Día 5**. El sistema gestiona una base de datos de ingredientes utilizando rangos de identificación para determinar la frescura de los alimentos en la cocina de los Elfos.

## El Problema

El inventario se organiza en una base de datos con dos secciones diferenciadas por una línea vacía:
1.  **Rangos de Frescura:** Intervalos numéricos inclusivos donde los ingredientes se consideran aptos.
2.  **Ingredientes Disponibles:** Una lista de IDs específicos que deben ser verificados.

El reto requiere calcular:
* **Parte 1:** La cantidad de ingredientes disponibles que caen dentro de al menos un rango de frescura.
* **Parte 2:** El número total de IDs únicos que son considerados frescos (la unión de todos los rangos), gestionando correctamente los solapamientos y rangos contiguos.

---

## 1. Fundamentos de Diseño

* **Alta Cohesión:**
  El sistema se divide en tres componentes con responsabilidades únicas:
    * `Ranges`: Objeto de dominio que encapsula la lógica de pertenencia a un intervalo.
    * `Inventory`: Núcleo de lógica de negocio encargado de los cálculos de frescura y algoritmos de fusión.
    * `InventoryBuilder`: Componente especializado en el parsing y segmentación de datos de entrada.

* **Abstracción:**
  Se oculta la complejidad del algoritmo de "Fusión de Intervalos" y el manejo de índices de listas. El usuario interactúa con una interfaz limpia que acepta tanto bloques de texto (`String`) como colecciones (`List<String>`).

* **Código Expresivo:**
  Uso de terminología de dominio clara (`low`, `high`, `isFresh`, `fusionAllRanges`) para que el flujo de datos sea evidente sin necesidad de documentación externa.

* **Modularidad:** El sistema se divide en módulos independientes, facilitando la comprensión de la red de operaciones interconectadas.

---

## 2. Principios de Diseño

* **Principio de Responsabilidad Única (SRP):**
  `InventoryBuilder` es el único que "conoce" la estructura del input (el separador `-` y la línea vacía). Si el formato de la base de datos cambia, `Inventory` y `Ranges` permanecen inalterados.

* **Principio de Mínimo Compromiso:**
  La clase `Inventory` recibe objetos ya procesados en su constructor. Esto permite que la lógica de negocio sea independiente del mecanismo de persistencia o del formato original del archivo.

* **Principio de Mínima Sorpresa:**
  El uso de `subList` y `indexOf("")` para particionar el inventario sigue una lógica predecible para archivos con delimitadores de sección.

* **Principio YAGNI:** Se ha evitado la sobreingeniería, manteniendo el código lo más simple y efectivo posible para resolver el puzzle.

---

## 3. Patrones de Diseño

* **Patrón Builder / Static Factory:**
  `InventoryBuilder` centraliza la creación compleja del inventario. Ofrece flexibilidad al permitir crear la instancia desde diferentes tipos de entrada mediante sobrecarga de métodos.

* **Inmutabilidad:**
  El uso de `record Ranges` garantiza que los datos de los intervalos no puedan ser modificados accidentalmente durante el proceso de cálculo, asegurando la integridad de la base de datos.

* **Patrón Iterator:** Implementado mediante el uso de la API de Streams, permitiendo recorrer y manipular los conjuntos de datos de manera uniforme y eficiente.

---

## Lógica de Resolución

La solución implementa una estrategia de **geometría computacional** para resolver la unión de intervalos de forma eficiente.

### Parte 1: Verificación de Disponibilidad
Se utiliza una evaluación mediante Streams donde cada ingrediente disponible se valida contra la colección de rangos. Gracias a que la clase `Ranges` encapsula su propio método `contains`, la detección se realiza sin necesidad de expandir los intervalos en memoria.

### Parte 2: Consolidación y Fusión de Rangos
Sumar las longitudes de los rangos directamente sería erróneo debido a los **solapamientos** y **rangos contiguos**.

**El proceso de fusión (Merge):**
1.  **Ordenación:** Se ordenan todos los rangos por su límite inferior (`low`). Esto garantiza que solo necesitemos comparar el rango actual con el último consolidado.
2.  **Detección de Continuidad:** Al iterar, comparamos el inicio del rango actual (`r.low()`) con el final del rango que estamos construyendo (`currentHigh`).
    * **Fusión:** Si `r.low() <= currentHigh + 1` (el `+1` gestiona rangos contiguos como `3-5` y `6-8`), se expande `currentHigh` al valor máximo entre ambos.
    * **Discontinuidad:** Si hay un "hueco", cerramos el rango actual, sumamos su tamaño al total y comenzamos uno nuevo.
3.  **Cálculo Matemático:** Para cada segmento consolidado, la cantidad de IDs únicos se obtiene mediante la fórmula inclusiva:
    $$Longitud = High - Low + 1$$
