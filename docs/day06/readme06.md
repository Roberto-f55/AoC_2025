# Advent of Code - Día 6: Compactador de Basura

Este módulo resuelve problemas matemáticos presentados en una hoja de ejercicios visual con una disposición horizontal, adaptándose a dos sistemas de lectura: el estándar humano (Parte A) y el inverso cefalópodo (Parte B).

## 1. Fundamentos de Diseño

* **Abstracción por Contrato:** Los métodos de factoría (`createA`, `createB`) y el método `build` actúan como un punto de contacto que especifica **qué** debe hacer el objeto, pero no **cómo**. La definición clara de responsabilidades ayuda a mantener la integridad de la lógica de negocio frente a la representación visual.

* **Alta Cohesión:** Las responsabilidades están estrictamente segregadas en componentes enfocados en una única tarea:
    * `OperationsBuilder`: Especializado en el parsing y segmentación visual de la hoja.
    * `OperationsStore`: Responsable de la agregación y orquestación del cálculo total de los resultados.
    * `Operation`: Encapsula la ejecución de una operación aritmética individual.
    * `Operator`: Define el comportamiento funcional y la identidad de las operaciones (+, *).

* **Ocultación de Información:** Los detalles de implementación, como el algoritmo de relleno (`fill`), la gestión de índices de columnas o la concatenación de caracteres, son privados. Esto asegura que cambios en el formato del input no afecten a los clientes de la clase.

* **Bajo Acoplamiento:** Al separar el motor de cálculo de la lectura visual, se reducen las interdependencias. El sistema de cálculo procesa listas de `Long` sin conocer su origen (filas o columnas).

* **Código Expresivo:** Se utiliza una nomenclatura descriptiva (`createOperationWithRow`, `createOperationWithColumn`) que permite comprender la estructura y el funcionamiento del código sin necesidad de desentrañar una compleja red de operaciones interconectadas.



## 2. Principios de Diseño

* **Principio de Mínimo Compromiso:** La interfaz del objeto muestra solo lo necesario para su operación. Al no exponer detalles internos como el manejo de espacios (`strip`) o la lógica de subcadenas, se facilita la mantenibilidad y escalabilidad del código.

* **Principio de Mínima Sorpresa:** El componente es intuitivo. Las abstracciones se comportan de manera predecible según el método de creación elegido; por ejemplo, el usuario de `createB()` obtiene una lectura inversa sin comportamientos ocultos.

* **Principio de Responsabilidad Única (SRP):** Cada clase y función tiene una sola razón para cambiar, lo que evita soluciones excesivamente complejas y hace que el código sea fácil de modificar.

---

## 3. Patrones de Diseño

* **Static Factory Method:** Se emplean métodos estáticos de factoría (`createA`, `createB`) para instanciar el builder. Esto permite parametrizar la estrategia de lectura de forma inmutable y expresiva, estableciendo las reglas del contrato desde el inicio.

* **Inmutabilidad:** El uso de `record` para las entidades de datos asegura que una vez procesada la información, esta permanezca inalterada, eliminando errores de estado lateral durante el cálculo del gran total.

---

## Lógica de Resolución

La resolución se basa en tratar el input como una **matriz de caracteres** normalizada mediante una segmentación por columnas.

### Segmentación de Bloques
El sistema identifica primero la posición de los símbolos (`+`, `*`) en la línea de operadores. Estos índices actúan como anclas para dividir la hoja horizontal en "ventanas" o bloques de procesamiento independientes.



### Estrategia de Extracción Dual
Dentro de cada bloque delimitado, el sistema decide la estrategia de extracción según la configuración del contrato:

1.  **Modo Estándar (Parte A - Lectura Humana):**
* **Dirección:** Lectura por filas (horizontal).
* **Mecánica:** Utiliza `createOperationWithRow`. Se extraen subcadenas horizontales dentro de los límites del bloque y se transforman en operandos.

2.  **Modo Cefalópodo (Parte B - Lectura Vertical):**
* **Dirección:** Lectura por columnas de derecha a izquierda.
* **Mecánica:** Utiliza `createOperationWithColumn`.
* **Reconstrucción:** Para cada columna del bloque, se recorre verticalmente concatenando cada dígito encontrado a un "0" inicial. Esto reconstruye números que están escritos de arriba hacia abajo, asegurando un parsing seguro incluso en columnas con espacios en blanco.

### Normalización Visual
El método `fill` es fundamental para la **integridad de la abstracción**. Al asegurar que todas las líneas tienen el mismo `maxLength`, permitimos que el algoritmo de lectura por columnas (`charAt(colIndex)`) sea seguro y consistente, tratando el texto irregular como una estructura de datos geométrica perfecta.