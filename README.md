
# Requermientos
##### JAVA >= 1.8
# Instalacón
## Gradle
### 1ª Opción
```bash
git submodule add https://github.com/sipay/jsdk
cd jsdk
git checkout origin/master 
```

#### * Añadimos a settings.gradle:
```bash
include 'jsdk'
```

#### * Añadimos a build.gradle:
```bash
dependencies {
   ...
   ...
    compile project(':jsdk')
}
```

### 2ª Opción
##### * Copiamos el .jar en nuestro proyecto, en este ejemplo en la carpeta libs.
##### * Añadimos a build.gradle:
```bash
dependencies {
   ...
   ...
    compile files('libs/sipay-0.1.0.jar')
    compile group: 'com.github.java-json-tools', name: 'json-schema-validator', version: '2.2.8'
    compile group: 'org.json', name: 'json', version: '20160810'
    compile group: 'org.apache.httpcomponents', name: 'httpclient', version: '4.5.5'
}
```

## Maven
##### * Añadimos el .jar como librería, por ejemplo con IntelliJ IDEA es en File >> Project Structure >> Libraries.
##### * Añadimos a pom.xml:
```bash
<dependencies>
        <dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20160810</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.3</version>
        </dependency>
        <dependency>
            <groupId>com.github.java-json-tools</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>2.2.8</version>
        </dependency>
    </dependencies>
```
## Java
##### * Añadimos el .jar y las siguientes las librerías: 
```bash
org.apache.httpcomponents:httpclient:4.5.5
org.json:json:20160810
com.github.java-json-tools:json-schema-validator:2.2.8
```
###### * Nota: en IntelliJ IDEA es en File >> Project Structure >> Libraries pulsamos + y aparece From Maven.
# Documentación
### Objetos
#### Namespace Sipay

**Amount:** 
 Este objeto representa una cantidad monetaria, por tanto esta cantidad no puede ser menor a 0. Para iniciar un objeto de este tipo se necesita una cantidad y una moneda (código ISO4217).
La cantidad se puede especificar de tres formas o con un string con la cantidad estandarizada y con el caracter de separación de decimales `.`, o con un entero de la cantidad en la unidad básica e indivisible de la moneda (por ejemplo de la moneda Euro sería el céntimo) o un string con esta misma cantidad. 

**Atributos:**

 - **Amount**: Contiene un entero de la cantidad en la unidad básica e indivisible de la moneda(en el caso de haber introducido un string con decimales, en este campo se almacenará el entero en la unidad básica e indivisible de la moneda).
 - **Currency:** Contiene un string del código de la moneda(ISO4217).


Ejemplos:

  ```java
  import ecommerce.Amount;
  
  // Con string
  Amount amount = new Amount("1.56", "EUR");
  System.out.println(amount.getAmount());
  // Imprime 156
  System.out.println(amount.getCurrency()));
  // Imprime EUR
 System.out.println(amount.get());
  // Imprime 1.56 EUR
  
  // Con unidad indivisible
  Amount amount = new Amount(156, "EUR");
  System.out.println(amount.getAmount());
  // Imprime 156
  System.out.println(amount.getCurrency()));
  // Imprime EUR
 System.out.println(amount.get());
  // Imprime 1.56 EUR
  
// Con string en unidad indivisible
  Amount amount = new Amount("156", "EUR");
  System.out.println(amount.getAmount());
  // Imprime 156
  System.out.println(amount.getCurrency()));
  // Imprime EUR
 System.out.println(amount.get());
  // Imprime 1.56 EUR
  ```
  
  **Nota:** En el caso de iniciarlo con el string decimal es imprescindible que tenga el número de decimales que indica el estándar ISO4217

### Namespace Sipay\Paymethods

  * **Card:**
    Representa el método de pago con tarjeta, para inicializarlo se necesita:

      * **Número de tarjeta:** String que tiene entre 14 y 19 dígitos.
      * **Año de caducidad:** Entero de 4 dígitos.
      * **Mes de caducidad:** Entero de 2 dígitos entre 1 y 12.

    Ejemplo:
    ```java
    import paymethod.Card;
    Card card = new Card("4242424242424242", 2050, 12);
    ```

  * **StoredCard:**
  Representa el método de pago con tarjeta almacenada en Sipay, para inicializarlo se necesita:

    * **token asociado a una tarjeta:** String que tiene entre 6 y 128 caracteres alfanúmericos y guiones.

    Ejemplo:
    ```java
    import paymethod.StoredCard;
    StoredCard card = new StoredCard("token");  
    ```
  
  * **FastPay:**
  Representa el método de pago con tarjeta almacenada en Sipay mediante Fast Pay, para inicializarlo se necesita:

    * **token asociado a una tarjeta:** String que tiene entre 6 y 128 caracteres alfanúmericos y guiones.

    Ejemplo:
    ```java
    import paymethod.FastPay;
    FastPay card = new FastPay("token");
    ```

### Namespace Sipay\Responses

Todos los objetos de esta sección tienen los siguientes atributos:
- **Atributos comunes:**
  - **type (enum[string]):** Tipo de respuesta:
    * success
    * warning
    * error
  - **code (string):** Código identificador del resultado. Es un código orientativo y no está ligado estrictamente con motivo de la respuesta, es decir, el código no identifica unívocamente la respuesta.
    - 0 -> success
    - mayor a 0 -> warning
    - menor a 0 -> error
  - **detail (string):** Código alfanumérico separado con guiones bajos y sin mayúsculas que identifica unívocamente la respuesta. Útil para la gestión de los diferentes casos de uso de una operación.
  - **description (string):** Descripción literal del mensaje de respuesta.
  - **uuid (string):** Identificador único de la petición, imprescindible para la trazabilidad.
  - **request_id (string):** Necesario para la finalización de algunas operaciones. Se indicarán aquellas en las que sea necesario.
  - **_request(dictionary):** Son los datos de la petición que se ha hecho al servidor.
  - **_response(dictionary):** Son los datos 'raw' de respuesta.


* **Authorization:**

  Este objeto añade lo atributos:
  * **amount (Amount):** Importe de la operación.
  * **order (string):** Ticket de la operación.
  * **card_trade (string):** Emisor de la tarjeta. Solicite más información.
  * **card_type (string):** Tipo de la tarjeta. Solicite más información.
  * **masked_card (string):** Número de la tarjeta enmascarado.
  * **reconciliation (string):** Identificador para la conciliación bancaria (p37).
  * **transaction_id (string):** Identificador de la transacción.
  * **aproval (string):** Código de aprobación de la entidad.
  * **authorizator (string):** Entidad autorizadora de la operación.


* **Cancellation:**

  Este objeto no añade nada a los atributos anteriores.

* **Card:**

  * **masked_card (string):** Número de la tarjeta enmascarado
  * **expired_at (date):** Fecha de la expiración
  * **token (string):** Identificador de la tarjeta
  * **card(StoredCard):** Objeto tarjeta asociado a la tarjeta devuelta.


* **Query:**

  Este objeto añade una lista de objetos transacciones, cada objeto transacción tiene:

  **Transaction:**
    * **description (string):** Descripción literal del estado de la operación.
    * **date (datetime):** Fecha y hora de la operación.
    * **order (string):** Ticket de la operación.
    * **masked_card (string):** Número de la tarjeta enmascarado.
    * **operation_name (string):** Nombre literal del tipo de operación.
    * **operation (string):** Identificador del tipo de operación.
    * **transaction_id (string):** Identificador de la transacción.
    * **status (string):** Identificador del estado de la operación.
    * **amount (Amount):** Importe de la operación.
    * **authorization_id (string):** Identificador de la entidad autorizadora.
    * **channel_name (string):** Nombre literal del canal de pago.
    * **channel (string):** Identificador del canal de pago.
    * **method (string):** Identificador del método de pago.
    * **method_name (string):** Identificador literal del método de pago.


* **Refund:**

  Este objeto añade lo atributos:
  * **amount (Amount):** Importe de la operación.
  * **order (string):** Ticket de la operación.
  * **card_trade (string):** Emisor de la tarjeta. Solicite más información.
  * **card_type (string):** Tipo de la tarjeta. Solicite más información.
  * **masked_card (string):** Número de la tarjeta enmascarado.
  * **reconciliation (string):** Identificador para la conciliación bancaria (p37).
  * **transaction_id (string):** Identificador de la transacción.
  * **aproval (string):** Código de aprobación de la entidad.
  * **authorizator (string):** Entidad autorizadora de la operación.


* **Register:**


* **masked_card (string):** Número de la tarjeta enmascarado
* **expired_at (date):** Fecha de la expiración
* **token (string):** Identificador de la tarjeta
* **card(StoredCard):** Objeto tarjeta asociado a la tarjeta devuelta.

* **Unregister:**

  Este objeto no añade nada a los atributos anteriores.

## **Ecommerce**

Para utilizar la SDK del middleware, hay que importar el paquete y crear el objeto con la ruta del archivo de configuración.

  ```java
  import ecommerce.Ecommerce;
  Ecommerce ecommerce = new Ecommerce("config.properties");
  ```

El archivo de configuración tiene que ser similar al siguiente:
```ini
# **************************************************************  
# LOGGER  
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
# Configuración asociada al sistema de trazas.  
#  
# file: Nombre del archivo (Nota: Aconsejable usar rutas absolutas para que se pueda ejecutar el módulo desde diferentes localizaciones)  
# level: nivel minimo de trazas \[debug, info, warning, error, critical\]  
# maxFileSize: Tamaño máximo del fichero de trazas \[bytes\]  
# backupFileRotation: Número de ficheros de backup  
# ------------------------------------------------------------//  
[logger]  
file=/documentos/logs.log  
level=warning  
maxFileSize=51200000  
backupFileRotation=5

# **************************************************************  
# CREDENTIALS  
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
# Credenciales para obtener acceso al recurso.  
#  
# key: Key del cliente  
# secret: Secret del cliente  
# resouce: Recurso al que se quiere acceder  
# ------------------------------------------------------------//
[credentials]
key=api-key
secret=api-secret
resource=resource

# **************************************************************  
# API  
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
# Configuracion de la API.  
#  
# environment: Entorno al que se deben enviar las peticiones \['sandbox', 'staging', 'live'\]  
# version: Versión de la api a usar actualmente solo existe v1  
# mode: Modo de encriptacion de la firma, \[sha256, sha512\]  
# ------------------------------------------------------------//  
[api]
environment=sandbox
version=v1
mode=sha256

# **************************************************************  
# TIMEOUT  
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
# Cofiguracion de los tiempos de timeout.  
#  
# connection: Timeout de connexión en segundos  
# process: Timeout de procesamiento en segundos  
# ------------------------------------------------------------//  
[timeout]
connection=3  
process=27
```

Tras iniciar el objeto `ecommerce` se puede realizar las siguientes llamadas:
 * **Authorization**

  * **payMethod(PayMethod):** método de pago [Card, StoredCard, FastPay]
  * **amount(Amount):** importe de la operación
  * **Atributos opcionales(JSONObject)**:
    * **order (string):** Ticket de la operación.
    * **reference (string):** Identificador para la conciliación bancaria.
    * **custom_01 (string):** Campo personalizable.
    * **custom_02 (string):** Campo personalizable.
    * **token(string):** Si el método de pago no es una StoredCard, y el valor de token es un str no vacío almacena la tarjeta asociada el token pasado en este campo.

 **Ejemplo:**

 Autorización con tarjeta.

  ```java
  JSONObject payload = new JSONObject();  
  payload.put("custom_01", "custom_01");  

  Amount amount = new Amount("100", "EUR"); // 1€  
  Card card = new Card("4242424242424242", 2050, 12);  
  
  Authorization auth = ecommerce.authorization(card, amount, payload);
```

 Autorización con Fast Pay.

  ```java
  JSONObject payload = new JSONObject();  
  payload.put("custom_01", "custom_01");  
  payload.put("token", "2977e78d1e3e4c9fa6b70");

  Amount amount = new Amount("100", "EUR");   // 1€
  FastPay card = new FastPay("830dc0b45f8945fab229000347646ca5");
  Authorization auth = ecommerce.authorization(card, amount, payload);
```

 El método authorization devuelve un objeto Authorization.

* **Refund**

  * **identificator(PayMethod or string):** Método de pago [Card, StoredCard, FastPay] o la id de transacción.
  * **amount (Amount):** Importe de la operación
  * **Atributos opcionales(JSONObject)**:
    * **order (string):** Ticket de la operación.
    * **reference (string):** Identificador para la conciliación bancaria.
    * **custom_01 (string):** Campo personalizable.
    * **custom_02 (string):** Campo personalizable.
    * **token(string):** Si el método de pago no es una StoredCard, y el valor de token es un str no vacío almacena la tarjeta asociada el token pasado en este campo.

Ejemplo:

Devolución con tarjeta.

  ```java
  JSONObject payload = new JSONObject();  
  payload.put("custom_01", "custom_01");  

  Amount amount = new Amount("100", "EUR");  // 1€
  StoredCard card = new StoredCard("bd6613acc6bd4ac7b6aa96fb92b2572a");

  Refund refund = ecommerce.refund(card, amount, payload);
 ```

  Devolución con transaction_id.

  ```java
  JSONObject payload = new JSONObject();  
  payload.put("custom_01", "custom_01");  

  Amount amount = new Amount(100,  "EUR");  // 1€
  Refund refund = ecommerce.refund("transaction_id", amount, payload);
  ```

  El método refund devuelve un objeto Refund.

* **Register**

  * **card(Card):** Tarjeta a registrar.
  * **token(string):** Token con el que se le asocia a la tarjeta.

  Ejemplo:

  Registro de tarjeta.

  ```java
  Card card = new Card("4242424242424242", 2050, 12);  
  Register register = ecommerce.register(card, "newtoken");
  ```

  El método register devuelve un objeto Register.

* **Card**

  * **token(string):** Token asociado a la tarjeta.

  Ejemplo:

  Búsqueda de tarjeta.

  ```java
  Card maskedCard = ecommerce.card("newtoken");
  ```

  El método card devuelve un objeto Card del apartado Responses.

* **Unregister**

  * **token(string):** Token asociado a la tarjeta.

  Ejemplo:

  Borrar una tarjeta del registro.

  ```java
  Unregister unregister = ecommerce.unregister("newtoken")
  ```

  El método unregister devuelve un objeto Unregister.

* **Cancellation**

  * **transaction_id(string):** identificador de la transacción.

  Ejemplo:

  Cancelación de operación.

  ```java
  Cancellation cancel = ecommerce.cancellation("transaction_id");
  ```

  El método cancellation devuelve un objeto Cancellation.

* **Query**
* **payload(JSONObject):**
    * **order(string):** Ticket de la operación.
    * **transaction_id(string):** identificador de la transacción.

  Ejemplo:

  Búsqueda de transacciones.

  ```java
  JSONObject payload = new JSONObject();  
  payload.put("transaction_id", "transaction_id");
  
  Query query = ecommerce.query(payload);
  ```

  El método query devuelve un objeto Query.