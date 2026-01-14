# Advent of Code - Día 1: Secret Entrance

Esta es mi solución para el desafío del **Día 1** de Advent of Code. El problema simula una caja fuerte del Polo Norte con un dial giratorio (0-99), donde debo descifrar la contraseña interpretando una secuencia de rotaciones.

## El Problema

El desafío se divide en dos partes resueltas en la clase `Dial`:
1.  **Parte A:** Calcular cuántas veces el dial se detiene exactamente en el **0** al final de cada instrucción.
2.  **Parte B:** Calcular cuántas veces el dial pasa por el **0** (incluyendo paradas y cruces) usando el algoritmo de seguridad "0x434C49434B".

---

## 1. Fundamentos de Diseño
Para la arquitectura base de la solución, me enfoqué en las cualidades inherentes deseables en el sistema.

* **Abstracción:**
  La abstracción me permitió manejar la complejidad ocultando los detalles matemáticos. Implementé el método `normalize(int value)` para encapsular la fórmula de la aritmética modular `((value % 100) + 100) % 100`.De esta forma, separé el comportamiento esencial (moverse por el dial) de los detalles complejos (manejo de residuos negativos).

* **Alta Cohesión:**
  Diseñé la clase principal (Dial) para que sus partes estén estrechamente relacionadas y enfocadas. Por otro lado la clase Order es un record diseñada para apoyar a la lógica de la clase principal.
    * `parse` se enfoca solo en interpretar texto.
    * `add` se enfoca solo en guardar el estado.
    * `count` se enfoca solo en el cálculo del resultado.

* **Código Expresivo:**
  Busqué que el código fuera claro y comprensible para facilitar su lectura. Utilicé nombres descriptivos y auto explicativos como `signOf`, `valueOf` y `step`, evitando la necesidad de comentarios excesivos.

---

## 2. Principios de Diseño
Apliqué normas y guías específicas para orientar la aplicación de los fundamentos anteriores.

* **Principio DRY (Don't Repeat Yourself):**
  Cada pieza de conocimiento debe tener una representación única. Centralicé la lógica de "vuelta al círculo" en `normalize`. Si no lo hubiera hecho, habría tenido que repetir la fórmula matemática en `position`, `countA` y `countB`.

* **Principio de Responsabilidad Única (SRP):**
  Cada módulo debe tener una sola razón para cambiar. Al separar la lógica de parseo (`parse`) de la lógica de negocio (`countA/B`), aseguro que si cambia el formato de entrada, no rompo la lógica de cálculo, y viceversa.

* **Principio de Mínimo Compromiso (Diseño por Contrato):**
  La interfaz del objeto muestra solo lo necesario para su operación. He declarado la lista `orders` y los métodos auxiliares como `private`, evitando exponer detalles internos que no son relevantes para el usuario y reduciendo dependencias.

* **Principio de Mínima Sorpresa:**
  Busqué que el componente fuera intuitivo y predecible. El método `add` acepta argumentos variables (`String...`), lo que permite ingresar datos de forma natural sin efectos secundarios inesperados.

---

## 3. Patrones de Diseño
Utilicé soluciones típicas a problemas comunes para estructurar la creación y el comportamiento de los objetos[cite: 46].

* **Patrón Factory Method:**
  En lugar de usar directamente el constructor, implementé el método estático `Dial.create()`. Esto encapsula la creación del objeto, dándome control sobre cómo se instancia la clase sin afectar al cliente.

* **Patrón Iterator (vía Streams):**
  Este patrón proporciona una manera de acceder secuencialmente a los elementos sin exponer su representación subyacente. Al usar `orders.stream()` e `iterate()`, permito recorrer y manipular los datos de manera eficiente manteniendo la lista encapsulada.

---

## Lógica de Resolución

Para resolver el ejercicio, dividí la lógica en tres fases: Ingesta de datos, Simulación de estados (Parte A) y Cálculo de intersecciones (Parte B).

### 1. Normalización y Aritmética Modular
El núcleo de la solución es el método `normalize`. Dado que el dial es un círculo de 0 a 99, cualquier operación matemática debe respetar estos límites.
* **El Reto:** Java devuelve residuos negativos (ej. `-10 % 100 = -10`), lo cual es incorrecto para índices de arrays o posiciones circulares.
* **La Solución:** `((value % 100) + 100) % 100`. Esta fórmula fuerza al número a entrar en el rango positivo correcto, permitiendo rotaciones infinitas en cualquier dirección.

### 2. Parte A: Simulación de Estados Finales (`countA`)
El objetivo era saber dónde termina el dial después de la instrucción 1, luego después de la 2, etc.
* **Estrategia:** En lugar de un bucle tradicional, utilicé **Java Streams** para crear un flujo de datos declarativo.
* **Algoritmo:**
    1. Genero un rango de números (`iterate`) que representa "la primera orden", "las primeras 2 órdenes", etc.
    2. Para cada paso, calculo la suma acumulada de movimientos (`sumPartial`).
    3. Normalizo el resultado y filtro aquellos que son exactamente `0`.
    4. Cuento las ocurrencias.

### 3. Parte B: Cálculo Matemático de Intersecciones (`countB`)
Aquí el problema cambiaba: debía contar cuántas veces el dial *pasa* por el 0 durante el giro, no solo dónde termina. 
* **Estrategia:** Implementé una solución que calcula los cruces basándome en la distancia y la posición actual.

#### Lógica para Giros a la Derecha (Positivos)
Al sumar valores, nos alejamos del 0 actual hacia números mayores.
* **Fórmula:** `count += (pos + d) / 100`
* **Explicación:** Si estoy en la posición 90 y avanzo 120 pasos, teóricamente llego al 210. Al dividir entre 100 (el tamaño del dial), obtengo cuántas vueltas completas (y por tanto, cruces por el 0) he realizado.

#### Lógica para Giros a la Izquierda (Negativos)
Al restar, nos acercamos al 0 "bajando" (50, 49...). Este caso es más complejo y lo manejé con lógica condicional:
1.  **Si `pos == 0`:** Ya estamos en 0. Para volver a cruzarlo, necesitamos dar una vuelta completa (100 pasos). Cruces = `distancia / 100`.
2.  **Si `distancia < pos`:** No llegamos al 0. (Ej. Estoy en 50, retrocedo 10 hasta 40). Cruces = `0`.
3.  **Si `distancia >= pos`:** Cruzamos el 0 al menos una vez.
    * **Fórmula:** `1 + (distancia - pos) / 100`.
    * El `1` representa el primer impacto con el 0 (gastamos `pos` pasos en llegar).
    * El resto `(distancia - pos) / 100` calcula cuántas vueltas completas adicionales damos con la inercia restante.