
#  1. Introducción
El siguiente proyecto constituye una SDK en java, que simplifica la ejecución de llamadas al servicio Ecommerce de Sipay.

# 2 . Quickstart
Con el siguiente ejemplo podrás, en pocos pasos, instalar la SDK y efectuar una venta.

```bash
git submodule add https://github.com/Sipay/java-sdk
cd java-sdk
git checkout origin/master
```
#### * Añadimos a settings.gradle:
```bash
include 'java-sdk'
```
#### * Añadimos a build.gradle:
```bash
dependencies {
    compile project(':java-sdk')
}
```
#### * En nuestro ejemplo añadimos
```bash
Ecommerce ecommerce = new Ecommerce("config.properties");
Amount amount = new Amount("100", "EUR");
Card card = new Card("4242424242424242", 2050, 3);

Authorization auth = ecommerce.authorization(card, amount);
if (auth.getCode() == 0) {
	System.out.println("Operación procesada correctamente");
}
```

Por ultimo, compilamos y luego ejecutamos

# 3. Instalación
## Pre-requisitos
* Versión de java 1.8.

## Pasos
### Gradle
#### 1ª Opción
```bash
git submodule add https://github.com/Sipay/java-sdk
cd java-sdk
git checkout origin/master
```

#### * Añadimos a settings.gradle:
```bash
include 'java-sdk'
```

#### * Añadimos a build.gradle:
```bash
dependencies {
   ...
   ...
    compile project(':java-sdk')
}
```

#### 2ª Opción
##### * Nota: el fichero .jar esta en la ruta jar/sipay-version.jar
##### * Copiamos el .jar en nuestro proyecto, en este ejemplo en la carpeta libs.
##### * Añadimos a build.gradle:
```bash
dependencies {
   ...
   ...
    compile files('libs/sipay-version.jar')
    compile group: 'com.github.java-json-tools', name: 'json-schema-validator', version: '2.2.8'
    compile group: 'org.json', name: 'json', version: '20160810'
    compile group: 'org.apache.httpcomponents', name: 'httpclient', version: '4.5.5'
}
```

### Maven
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
### Java
##### * Añadimos el .jar y las siguientes las librerías:
```bash
org.apache.httpcomponents:httpclient:4.5.5
org.json:json:20160810
com.github.java-json-tools:json-schema-validator:2.2.8
```

# 4. Configuración
Una vez que se ha instalado la SDK, se deben actualizar los parámetros de configuración asociados a:
* Sistema de trazas.
* Credenciales de acceso (Se gestionan con el departamento de integraciones de Sipay).
* Entorno y versión de la API.
* Tiempo máximo de espera de respuestas (Timeout).

Un ejemplo de configuraciones se muestra a continuación:
```ini
# **************************************************************
# LOGGER
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Configuración asociada al sistema de trazas.
#
# file: Nombre del archivo (Nota: Aconsejable usar rutas absolutas para que se pueda ejecutar l módulo desde diferentes localizaciones)
# level: nivel minimo de trazas [debug, info, warning, error, critical]
# maxFileSize: Tamaño máximo del fichero de trazas [bytes]
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
resource=resource-sipay
resource-altp=resource-altp


# **************************************************************
# API
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Configuracion de la API.
#
# environment: Entorno al que se deben enviar las peticiones ['sandbox', 'staging', 'live']
# version: Versión de la api a usar actualmente solo existe v1
# mode: Modo de encriptacion de la firma, [sha256, sha512]
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

# 5. Documentación extendida Ecommerce

A través de peticiones a Sipay mediante Ecommerce, se pueden realizar operativas de:
* Autorizaciones (sección 5.2.1).
* Cancelaciones (sección 5.2.2).
* Devoluciones (sección 5.2.3).
* Búsquedas de operaciones o querys (sección 5.2.4).
* Tokenización* de tarjetas (sección 5.2.5).
* Búsqueda de tarjetas tokenizadas (sección 5.2.6).
* Dar de baja una tarjeta tokenizada (sección 5.2.7).


_* Tokenización_: Es un proceso por el cual el PAN (_Primary Account Number_ – Número Primario de Cuenta) de la tarjeta se sustituye por un valor llamado token. Esta funcionalidad permite que Sipay guarde los datos de la tarjeta del cliente, para agilizar el proceso de pagos y evitar que se deba introducir, cada vez, los datos de tarjeta, en pagos repetitivos. Sipay realiza el almacenamieno de los datos de forma segura, cumpliendo con las normativas PCI.

Para llevar a cabo de forma correcta las operativas Ecommerce, se requiere el dominio de los objetos `Amount`, `Card`, `StoredCard` y `FastPay`, los cuales identifican el importe y el método de pago a utilizar.

## 5.1. Objetos necesarios en las operativas de Ecommerce

### **5.1.1. `Amount(amount,currency)`**

#### Definición
  Este objeto representa una cantidad monetaria, por tanto esta cantidad debe ser mayor que cero (0). Para instanciar un objeto de este tipo se necesita una cantidad (amount) y una moneda (currency) en formato ISO4217 (https://en.wikipedia.org/wiki/ISO_4217).
  La cantidad se puede especificar de dos formas:
  * -   Con un  `string`  con la cantidad estandarizada y con el carácter punto (`.`) como separador de decimales (Ejemplo:`"1.56"`).
  * -   Con un  `string`  que represente la cantidad en la unidad básica e indivisible de la moneda (Ejemplo:  `"156"`).
  * -   Con un  `int`  que represente la cantidad en la unidad básica e indivisible de la moneda, es decir, la moneda Euro sería el céntimo (Ejemplo:  `156`).

#### Parámetros
* **`amount`:**  [_obligatorio_] Es la cantidad de dinero a procesar. Se puede representar con un `string` o un `int`. Supongamos que queremos procesar 1.56 €, la cantidad (1.56) como un `string` sería `'1.56'` o  `'156'`; como un `int` sería `156`.
* **`currency`:** [_obligatorio_] Es un `string` que representa el código de la moneda (ISO4217).

#### Atributos
* **`amount`:** `int` que representa la cantidad de procesamiento. Será este tipo de dato, independientemente de si se ha instanciado con un `string` previamente.
* **`currency`:** `string` que representa el código de la moneda (ISO4217).

#### Métodos

-   **`getAmount()`:**  Devuelve el atributo amount.
-   **`getCurrency()`:**  Devuelve el atributo currency.
-   **`get()`:**  Devuelve el amount y el currency como un string.

#### Ejemplo
  ```java
java import ecommerce.Amount;

// Con string
Amount amount = new Amount("1.56", "EUR");
System.out.println(amount.getAmount()); // Imprime 156
System.out.println(amount.getCurrency())); // Imprime EUR
System.out.println(amount.get()); // Imprime 1.56 EUR

// Con unidad indivisible
Amount amount = new Amount(156, "EUR");
System.out.println(amount.getAmount()); // Imprime 156
System.out.println(amount.getCurrency())); // Imprime EUR
System.out.println(amount.get()); // Imprime 1.56 EUR

// Con string en unidad indivisible
Amount amount = new Amount("156", "EUR");
System.out.println(amount.getAmount()); // Imprime 156
System.out.println(amount.getCurrency())); // Imprime EUR
System.out.println(amount.get()); // Imprime 1.56 EUR
  ```

**Nota:** En el caso de iniciarlo con un `string` que incluya un punto es imprescindible que tenga el número de decimales que indica el estándar ISO4217. Ejemplo para la moneda euro es correcto indicar un amount "1.40" pero no es correcto "1.4"

### **5.1.2. `Card(cardNumber, year, month)`**

#### Definición
Este objeto representa una tarjeta que se puede utilizar en las diferentes operativas de Ecommerce. Para obtener una instancia de `Card`, los parámetros se indican a continuación.

#### Parámetros
* **`cardNumber`:** [_obligatorio_] Es un `string` con  longitud entre 14 y 19 dígitos. Representa el número de la tarjeta.
* **`year`:** [_obligatorio_] Es un `int` de 4 dígitos que indica el año de caducidad de la tarjeta.
* **`month`:** [_obligatorio_] Es un `int` de 2 dígitos con valores entre 1 y 12 que correspondiente al mes de caducidad de la tarjeta.

#### Atributos
* **`cardNumber`:** Es el número de la tarjeta en una instancia de `Card`. Es un `string` con longitud entre 14 y 19 dígitos.
* **`year`:** Es al año de caducidad de la tarjeta en una instancia de `Card`. Es un  `int` de 4 dígitos.
* **`month`:** Es el mes de caducidad de la tarjeta en una instancia de `Card`. Es un `int` de 2 dígitos entre 1 y 12.

#### Métodos
* **`setExpirationDate(year, month)`:** Permite asignar una fecha de caducidad sobre una instancia de `Card`. Los parámetros `year` y `month` son obligatorios.
* **`isExpired()`:** Permite evaluar si una instancia de `Card` tiene una fecha de caducidad válida. Retorna `True` si la tarjeta está expirada.  
* **`getCardNumber()`:**  Devuelve el atributo  PAN de la tarjeta.
* **`getYear()`:**  Devuelve el año.
* **`getMonth()`:**  Devuelve el mes.
* **`setCardNumber(cardNumber)`:**  Permite asignar el PAN de la tarjeta.

#### Ejemplo
```java
java import paymethod.Card;

Card card = new Card(panExample, 2020, 3);
System.out.println(card.getCardNumber());
System.out.println(card.getYear());
System.out.println(card.getMonth());

card.setExpirationDate(2021, 12);
System.out.println(card.getYear());
System.out.println(card.getMonth());

card.setCardNumber("123451234512345");
System.out.println(card.getCardNumber());
```

### **5.1.3. `StoredCard(token)`**
#### Definición
Este objeto representa una tarjeta almacenada en Sipay que puede utilizarse en operativas Ecommerce. Para obtener una instancia de `StoredCard` se requieren los siguiente parámetros.

#### Parámetros
* **`token`:** [_obligatorio_] Es un `string` de longitud entre 6 y 128 caracteres de tipo alfanuméricos y guiones.

#### Atributos
* **`token`:** `string` de longitud entre 6 y 128 caracteres.

#### Métodos
* **`setToken()`:**  Permite asignar el token.
* **`getToken()`:**  Devuelve el token.

#### Ejemplo
  ```java
java import paymethod.StoredCard;

StoredCard card = new StoredCard("token");
System.out.println(card.getToken());

card.setToken("new-token-card");
System.out.println(card.getToken());
  ```
### **5.1.4. `FastPay(token)`**

#### Definición
Este objeto representa una tarjeta obtenida mediante el método de pago FastPay. Se utiliza en los consecutivos pasos de la operativas de pago de este método.

#### Parámetros
* **`token`:** [_obligatorio_] Es un `string` de longitud 32 con caracteres de tipo hexadecimal.

#### Atributos
* **`token`:**`string` de longitud 32 caracteres de tipo hexadecimal.

#### Métodos

* **`setToken`:**  Permite asignar el token.
* **`getToken`:**  Devuelve el token.

#### Ejemplo
```java
import sipay.paymethod.FastPay;

FastPay card = new FastPay("token-fast-pay");
System.out.println(card.getToken());

card.setToken("new-token-fast-pay");
System.out.println(card.getToken());
```

## 5.2. Operativas de Ecommerce - `Ecommerce(String path)`

#### Descripción
Las operativas de Ecommerce forman parte de los métodos definidos en la clase `Ecommerce`. Para instanciar un objeto de este tipo se requiere el archivo de configuración.

#### Parámetros
* **`path`** [_obligatorio_] Es un `string` con la ruta del archivo de configuraciones.

#### Ejemplo
```java
import sipay.Ecommerce;

Ecommerce ecommerce = new Ecommerce("config.properties");
```
#### Atributos
Los siguientes atributos se asignan en el archivo de configuraciones. Sin embargo, son accesibles en instancias de `Ecommerce`. Se sugiere que sean utilizados en modo de consulta.
* **`key`:** corresponde al key de las credenciales.
* **`secret`:** corresponde al secret de las credenciales.
* **`resource`:** corresponde al resource de las credenciales.
* **`environment`:** corresponde al entorno al cual se está apuntando.
* **`mode`:** corresponde el modo de cifrado de las peticiones.
* **`version`:** corresponde a la versión de la API a la cual se apunta.
* **`connection`:** Corresponde al tiempo de espera máximo en establecer una conexión.
* **`process`:** Corresponde al tiempo de espera máximo en esperar la respuesta de un proceso.

#### Métodos
Todos los atributos indicados tienen sus métodos de asignación con  `set[Nombre_del_atributo]`  y sus métodos de consulta con  `get[Nombre_del_atributo]`. Ejemplo de get/set `getSecret()`, `setSecret('secret')` .

* **`authorization(parameters)`:**  Permite hacer peticiones de autorización haciendo uso de los diferentes métodos de pago (ver sección 5.2.1).
* **`cancellation(parameters)`:**  Permite enviar peticiones de cancelaciones (ver sección 5.2.2).
* **`refund(parameters)`:**  Permite hacer devoluciones (ver sección 5.2.3).
* **`query(parameters)`:**  Permite hacer peticiones de búsqueda de operaciones (ver sección 5.2.4).
* **`register(parameters)`:**  Permite tokenizar una tarjeta (ver sección 5.2.5).
* **`card(parameters)`:**  Se utiliza para buscar una tarjeta que fue tokenizada (ver sección 5.2.6).
* **`unregister(parameters)`:**  Se utiliza para dar de baja una tarjeta tokenizada (ver sección 5.2.7).


## 5.2.1 **`authorization(payMethod, amount, options)`**

### Definición
 Este método de `Ecommerce` permite enviar una petición de venta a Sipay.
### Parámetros
* **`payMethod`:**[_obligatorio_] Corresponde a una instancia  `Card`, `StoredCard` o `FastPay` que indica el método de pago a utilizar.
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` que representa el importe de la operación.
*  **`options `:**  [_opcional_] Es un `JSONObject`  que puede contener los siguientes elementos:
	* **`order `:** [_opcional_] Es un `string` que representa el ticket de la operación.
	* **`reconciliation `:** [_opcional_] Es un `string` que identifica la conciliación bancaria.
	* **`custom_01` :** [_opcional_] Es un `string` que representa un campo personalizable.
	* **`custom_02` :** [_opcional_] Es un `string` que representa un campo personalizable.
	* **`token`:** [_opcional_] Es un `string` que representa un token a almacenar. Se utiliza cuando el método de pago es de tipo `Card` o `Fpay`, y se desea asignar un token específico a la tarjeta utilizada.

### Salida
El método `authorization` devuelve un objeto `Authorization`.

### Ejemplo
 **- Autorización con tarjeta**
 ```java
import sipay.responses.Authorization;
import sipay.Amount;
import sipay.paymethod.Card;

Amount amount = new Amount("100", "EUR");
Card card = new Card("4242424242424242", 2050, 3);

Authorization auth = ecommerce.authorization(card, amount);
 ```

**- Autorización con FastPay**
 ```java
import sipay.responses.Authorization;
import sipay.Amount;
import sipay.paymethod.FastPay;

Amount amount = new Amount(100, "EUR"); // 1€
FastPay card = new FastPay("830dc0b45f8945fab229000347646ca5");

Authorization auth = ecommerce.authorization(card, amount);
 ```

## 5.2.2 `cancellation(transactionId)`

### Definición
Este método permite enviar una petición de cancelación a Sipay.

### Parámetros
* **`transactionId`:** [_obligatorio_] Es un `string` con el identificador de la transacción.

### Salida
El método `cancellation` devuelve un objeto `Cancellation`.

### Ejemplo
**- Cancelación de operación**
  ```java
import sipay.responses.Cancellation;

Cancellation cancel = ecommerce.cancellation("transactionId");
  ```

## 5.2.3 `refund(identificator, amount, options)`

### Definición
Este método `Ecommerce` permite enviar una petición de devolución a Sipay.

### Parámetros
* **`identificator`:** [_obligatorio_] Es una instancia del método de pago (`Card`, `StoredCard` o `FastPay`) o, un `string` que representa el identificador de transacción.
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.
*  **`options `:**  [_opcional_] Es un `JSONObject`  que puede contener los siguientes elementos:
	* **`order `:** [_opcional_] Es un `string` que representa el número de ticket o boleta de la operación.
	* **`reconciliation `:** [_opcional_] Es un `string` que identifica la conciliación bancaria.
	* **`custom_01` :** [_opcional_] Es un `string` que representa un campo personalizable.
	* **`custom_02` :** [_opcional_] Es un `string` que representa un campo personalizable.
	* **`token`:** [_opcional_] Corresponde a un `string` que representa un token a almacenar. Se utiliza cuando el identificador es de tipo `Card` o `FastPay`, y se desea asignar un token específico para la tarjeta utilizada.

### Salida
El método `refund` devuelve un objeto `Refund`.

### Ejemplo
**- Devolución con tarjeta**
  ```java
import sipay.Amount;
import sipay.paymethod.StoredCard;
import sipay.responses.Refund;

Amount amount = new Amount("100", "EUR"); // 1€
StoredCard card = new StoredCard("bd6613acc6bd4ac7b6aa96fb92b2572a");

Refund refund = ecommerce.refund(card, amount);
  ```

**- Devolución con transactionId**
  ```java
import sipay.Amount;
import sipay.responses.Refund;

JSONObject options = new JSONObject();  
options.put("custom_01", "custom_01");

Amount amount = new Amount(100, "EUR"); // 1€

Refund refund = ecommerce.refund("transactionId", amount, options);
  ```

## 5.2.5 `query(JSONObject options)`

### Definición
Este método `Ecommerce` permite enviar una petición a Sipay para buscar de una operación concreta.

### Parámetros

El método puede tener los siguientes parámetros:
* **`order`:**  [_opcional_]  `string`  que representa el ticket de la operación.
* **`transaction_id`:**  [_opcional_]  `string`  que representa el identificador de la transacción.

### Salida
El método `query` devuelve un objeto `Query`.

### Ejemplo
**- Búsqueda de transacciones**

  ```java
JSONObject options = new JSONObject();  
options.put("order", "order-reference");  

Query query = ecommerce.query(options);
  ```

## 5.2.6 `register(card, token)`

### Definición
Este método `Ecommerce` permite enviar una petición de tokenización de tarjeta a Sipay.

### Parámetros
  * **`card`:** [_obligatorio_] Instancia de tipo `Card` con la tarjeta a tokenizar.
  * **`token`:**[_obligatorio_] `string` que representa el token que se asociará a la tarjeta.

### Salida
  El método `register` devuelve un objeto `Register`.

###  Ejemplo

**- Registro de tarjeta**
  ```java
import sipay.responses.Register;
import sipay.paymethod.Card;

Card card = new Card("4242424242424242", 2050, 2);
Register register = ecommerce.register(card, "newtoken");
  ```

## 5.2.7 `card(token)`

### Definición
Este método `Ecommerce` permite enviar una petición a Sipay con la finalidad de obtener información de una tarjeta que está tokenenizada.

### Parámetros
* **`token`:**[_obligatorio_] `string` que representa el token asociado a la tarjeta.

### Salida
El método `card` devuelve un objeto `Card` del apartado Responses.

###  Ejemplo
**- Búsqueda de tarjeta**
  ```java
import sipay.responses.Card;

Card card = ecommerce.card("newtoken");
  ```

## 5.2.8 `unregister(token)`

### Definición
Este método `Ecommerce` permite enviar una petición a Sipay con la finalidad de dar de baja una tarjeta tokenizada.

### Parámetros
* **`token`:**[_obligatorio_] `string` que representa el token asociado a la tarjeta.

### Salida
El método `unregister` devuelve un objeto `Unregister`.

###  Ejemplo
**- Borrar una tarjeta del registro**

  ```java
import sipay.responses.Unregister;

Unregister unregister = ecommerce.unregister("newtoken");
  ```

### 5.3 Responses
Todos los objetos obtenidos como respuestas de operativas `Ecommerce` tienen los siguientes atributos.

#### 5.3.1 Atributos comunes
* **`type`:** Es un `enum[string]` que identifica el tipo de respuesta:
    * success
    * warning
    * error
* **`code`:** Es un `int` con el código identificador del resultado. Es un código orientativo y no está ligado estrictamente con motivo de la respuesta, es decir, el código no identifica unívocamente la respuesta.
    - si `code` es `0`, implica que el resultado es un _success_
    - si `code` es mayor a `0`, implica que el resultado es un _warning_
    - si `code` es menor a `0`, implica que el resultado es un _error_
* **`detail`:**  Es un `string` con el código alfanumérico separado con guiones bajos y sin mayúsculas que identifica unívocamente la respuesta. Útil para la gestión de los diferentes casos de uso de una operación.
* **`description`:** Es un `string` con la descripción literal del mensaje de respuesta.
* **`uuid`:** Es un `string` con el identificador único de la petición, imprescindible para la trazabilidad.
* **`requestId`:** Es un `string` utilizado en la finalización de algunas operaciones. Se indicarán aquellas en las que sea necesario.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getCode()`.

#### 5.3.2 `Authorization`
Este objeto añade los siguientes atributos:
* **`amount`:** Objeto de de tipo `Amount` con el importe de la operación.
* **`order`:** Es un `string` con el ticket de la operación.
* **`cardTrade`:** Es un `string` que describe el emisor de la tarjeta.
* **`cardType`:**  Es un `string` con el tipo de la tarjeta.
* **`maskedCard`:**  Es un `string` con el número de la tarjeta enmascarado.
* **`reconciliation`:**  Es un `string` identificador para la conciliación bancaria (p37).
* **`transactionId`:**  Es un `string` identificador de la transacción.
* **`aproval`:**  Es un `string` con el código de aprobación de la entidad.
* **`authorizator`:**  Es un `string` con la entidad autorizadora de la operación.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getApproval()`.

#### 5.3.3 `Refund`
Este objeto añade los atributos:
* **`amount`** Objeto de tipo `Amount` con el importe de la operación.
* **`order`:** Es un `string` con el ticket de la operación.
* **`cardTrade`:** Es un `string` con el emisor de la tarjeta.ú* **`cardType`:** Es un `string` con el tipo de la tarjeta.
* **`maskedCard`:** Es un `string` con con el número de la tarjeta enmascarado.
* **`reconciliation`:** Es un `string` identificador para la conciliación bancaria (p37).
* **`transactionId`:** Es un `string` identificador de la transacción.
* **`aproval`:** Es un `string` con el código de aprobación de la entidad.
* **`authorizator`:** Es un `string` con la entidad autorizadora de la operación.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getTransactionId()`.

#### 5.3.4 `Query`
Este objeto añade una lista de transacciones, cada objeto transacción tiene:

**`Transaction`**
* **`description`:**  Es un `string` con descripción literal del estado de la operación.
* **`order`:** Es un `string` con el ticket de la operación.
* **`maskedCard`:** Es un `string` con el número de la tarjeta enmascarado.
* **`operationName`:** Es un `string` con el nombre literal del tipo de operación.
* **`operation`:** Es un `string` identificador del tipo de operación.
* **`transactionId`:** Es un `string` identificador de la transacción.
* **`status`:** Es un `string` identificador del estado de la operación.
* **`amount`:** Es un objeto `Amount`  con el importe de la operación.
* **`authorizationId`:** Es un `string`  identificador de la entidad autorizadora.
* **`channelName`:** Es un `string`  con el nombre literal del canal de pago.
* **`channel`:** Es un `string`  identificador del canal de pago.
* **`method`:** Es un `string`  identificador del método de pago.
* **`methodName`:** Es un `string`  identificador literal del método de pago.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getOperation()`.

#### 5.3.5 `Register`
Este objeto añade lo atributos:
* **`cardMask`:** Es un `string` con el número de la tarjeta enmascarado.
* **`expiredAt`:** Es un `date` con fecha de la expiración.
* **`token`:** Es un `string` identificador de la tarjeta.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getCardMask()`.

#### 5.3.6 `Cancellation`
Este objeto no añade nada a lo indicado en los atributos comunes.

#### 5.3.7 `Card`
* **`cardMask`:** Es un `string` con el número de la tarjeta enmascarado.
* **`expiredAt`:** Parámetro de tipo `date` con la fecha de expiración de la tarjeta.
* **`token`:** Es un `string` identificador de la tarjeta.

**Nota:** Los atributos indicados tienen sus métodos de consulta con `get[Nombre_del_atributo]`. Ejemplo `getExpiredAt()`.

#### 5.3.8 `Unregister`
Este objeto no añade nada a lo descrito en los atributos comunes.


# 6. Documentación extendida ALTP

A través de peticiones a Sipay mediante ALTP, se pueden realizar operativas de:
* Paypal (sección 6.1).
* Movistar (sección 6.2).
* Sofort (sección 6.3).
* Paga más tarde (PMT) (sección 6.4).
* Querys (sección 6.5).


## 6.1. Paypal (path)


### Definición
Esta clase permite enviar peticiones de Paypal, mediante los siguientes métodos:
 - GenericMethods expressCheckoutMethods  
 - GenericConfirm   expressCheckoutConfirm 
 - GenericMethods referenceTransactionMethods
 - GenericConfirm referenceTransactionConfirm  
 - Payment referenceTransactionPayment
 

### 6.1.1 GenericMethods expressCheckoutMethods(@Nonnull ExpressCheckoutMethods body, @Nonnull Amount amount) 

### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para obtener los métodos disponibles.  Para instanciar un `body` de tipo `ExpressCheckout`  se necesita de forma obligatoria los siguientes parámetros:  `string order`, `string reconciliation`,  `string title`,  `string logo`, `JSONObject notify`,  `JSONObject policyData`, cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método `expressCheckoutMethods` devuelve un objeto `GenericMethods`, el cual contiene
* Un Json con los métodos de pagos disponibles y,
* Los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1).

### Ejemplo
#### Solicitud de los métodos disponibles
```java 
Paypal paypal = new Paypal(path);

JSONObject payload = new JSONObject();
JSONObject notify = new JSONObject();

// El campo order tiene que ser único
payload.put("order", "prueba-order-00000000201");
payload.put("reconciliation", "reconciliation");
payload.put("title", "Sipay Pruebas");
payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

notify.put("result", "https://www.sipay.es");
payload.put("notify", notify);
payload.put("policyData", new JSONObject());

Amount amount = new Amount("10000", "EUR");

ExpressCheckoutMethods expressCheckout = new ExpressCheckoutMethods(payload);
GenericMethods methods = paypal.expressCheckoutMethods(expressCheckout, amount);
if (methods == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (methods.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + methods.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


### 6.1.2 GenericConfirm expressCheckoutConfirm(@Nonnull String requestId) 

### Parámetros
* **`String requestId)`:** [_obligatorio_] Es el identificador de la operación, obtenido de la respuesta de la llamada `expressCheckoutMethods`.

### Salida
El método `expressCheckoutConfirm` devuelve un objeto `GenericConfirm`,  que permite confirmar el pago. Es un Json con los parámetros de respuesta definidos en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales.

### Ejemplo
#### Petición de confirmación
```java 
GenericConfirm confirm = paypal.expressCheckoutConfirm("5ae726d81d65fb000196dad4");
if (confirm == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (confirm.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + confirm.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```

### 6.1.3 GenericMethods referenceTransactionMethods(@Nonnull ReferenceTransactionMethods body, @Nonnull Amount amount) 


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para obtener los métodos disponibles.  Para instanciar un `body` de tipo `ReferenceTransactionMethods`  se necesita de forma obligatoria los siguientes parámetros:  `string order`, `string reconciliation`,  `string title`,  `string logo`, `JSONObject notify`,  `JSONObject policyData`, `JSONObject billing` y, ` string customId` cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método `referenceTransactionMethods` devuelve un objeto `GenericMethods`, el cual contiene:
* Un Json con los métodos de pagos disponibles y,
* Los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1).

### Ejemplo
#### Solicitud de los métodos disponibles
```java 
JSONObject payload = new JSONObject();
JSONObject billing = new JSONObject();
JSONObject notify = new JSONObject();

payload.put("order", "prueba-order-00000000202");
payload.put("reconciliation", "reconciliation");
payload.put("title", "Sipay Pruebas");
payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");
payload.put("customId", "90");
notify.put("result", "https://www.sipay.es");
payload.put("notify", notify);

billing.put("description", "prueba subscription");
payload.put("billing", billing);
payload.put("policyData", new JSONObject());

Amount amount = new Amount("0", "EUR");
ReferenceTransactionMethods referenceTransactionMethods = new ReferenceTransactionMethods(payload2);
GenericMethods resp = paypal.referenceTransactionMethods(referenceTransactionMethods, amount2);
if (resp == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (resp.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + resp.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


### 6.1.4 GenericConfirm referenceTransactionConfirm(@Nonnull String requestId) 

### Parámetros
* **`String requestId)`:** [_obligatorio_] Es el identificador de la operación, obtenido de la respuesta de la llamada `referenceTransactionMethods`.

### Salida
El método ` referenceTransactionConfirm` devuelve un objeto `GenericConfirm`,  que permite confirmar el pago. Es un Json con los parámetros de respuesta definidos en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales.

### Ejemplo
#### Petición de confirmación
```java 
GenericConfirm confirm = paypal.referenceTransactionConfirm("5ae728121d65fb000196dad6");
if (confirm == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (confirm.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + confirm.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


### 6.1.5  Payment referenceTransactionPayment(@Nonnull ReferenceTransactionPayment body, @Nonnull Amount amount) 


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para concretar un pago, con previa realización de una suscripción.  Para instanciar un `body` de tipo `ReferenceTransactionPayment`  se necesita de forma obligatoria los siguientes parámetros:  `string order`, `string reconciliation`,    `JSONObject notify`,  `JSONObject billing` y, `string customId` cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método `referenceTransactionPayment` devuelve un objeto `Payment`, el cual contiene
* Un Json con los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1) y, 
* Los siguientes datos adicionales:    `String paymentType`,`String amount`, `String transactionId`, `String datetime`, `String pendingReason`, `String transactionType`,  `String currency`, `String reasonCode`,`String fee`,`String tax`,`String status`,`String billingId`,`String requestId`



### Ejemplo
#### Solicitud de llamada payment
```java 
JSONObject payload = new JSONObject();
JSONObject notify = new JSONObject();

payload.put("order", "prueba-order-00000000203");
notify.put("result", "https://www.sipay.es");
payload.put("notify", notify);
payload.put("billingId", "B-1YF41782NT322241B");
payload.put("reconciliation", "reconciliation");

ReferenceTransactionPayment referenceTransactionPayment = new ReferenceTransactionPayment(payload3);
Payment payment = paypal.referenceTransactionPayment(referenceTransactionPayment, amount);
if (payment == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (payment.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + payment.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


## 6.2. Movistar (path)

### Definición
Esta clase permite enviar peticiones de Movistar, mediante los siguientes métodos:
 - GenericMethods methods
 - GenericConfirm confirm
 - PurchaseFromToken purchaseFromToken
 

### 6.2.1  GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount)


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para obtener los métodos disponibles.  Para instanciar un `body` de tipo `Methods`  se necesita de forma obligatoria los siguientes parámetros:  `String order`, `String msisdn`, `JSONObject notify`, `JSONObject policyData`, cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método `methods` devuelve un objeto `GenericMethods`, el cual contiene
* Un Json con los métodos de pagos disponibles y,
* Los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1).

### Ejemplo
#### Solicitud de los métodos disponibles
```java 
JSONObject payload = new JSONObject();
JSONObject notify = new JSONObject();
notify.put("result", "url");

// El campo order tiene que ser único
payload.put("order", "prueba-order-00000000200");
payload.put("msisdn", "34611111111");
payload.put("notify", notify);
payload.put("policyData", new JSONObject());

Methods methods = new Methods(payload);
Amount amount = new Amount("100", "EUR");

GenericMethods resp = movistar.methods(methods, amount);
if (resp == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (resp.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + resp.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


### 6.2.2   GenericConfirm confirm(@Nonnull String requestId)


### Parámetros
* **`String requestId)`:** [_obligatorio_] Es el identificador de la operación, obtenido de la respuesta de la llamada `methods`.

### Salida
El método  `confirm` devuelve un objeto `GenericConfirm`,  que permite confirmar el pago. Es un Json con los parámetros de respuesta definidos en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales.

### Ejemplo
#### Petición de confirmación
```java 
GenericConfirm confirm = movistar.confirm("6a571dffcc3ac117e5aefced");
if (confirm == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (confirm.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + confirm.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```

### 6.2.3  PurchaseFromToken purchaseFromToken(@Nonnull Purchase body, @Nonnull Amount amount)


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para realizar un pago.  Para instanciar un `body` de tipo `Purchase`  se necesita de forma obligatoria los siguientes parámetros:  `String authToken`, `String clientIpAddress` cuyas definiciones concretas están disponibles en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método  `purchaseFromToken` devuelve un objeto `PurchaseFromToken`, el cual contiene
* Un Json con los datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1) y, 
* Los siguientes campos adicionales: `String authToken`, `String responseType`, `JSONObject userId`,`String purchaseCode`, `String amount`, `String order`, `String transactionId`, `String status`

### Ejemplo
#### Solicitud de purchase from token
```java 
JSONObject payload = new JSONObject();
payload.put("authToken", "34611111111");
payload.put("clientIpAddress", "127.0.0.1");

Purchase purchase = new Purchase(payload);
Amount amount = new Amount("100", "EUR");

PurchaseFromToken resp = movistar.purchaseFromToken(purchase, amount);
if (resp == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (resp.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + resp.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```



## 6.3. Sofort (path)

### Definición
Esta clase permite enviar peticiones de Sofort, mediante los siguientes métodos:
 - GenericMethods methods
 - GenericConfirm confirm
 
### 6.3.1   GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount)


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para obtener los métodos disponibles.  Para instanciar un `body` de tipo `Methods`  se necesita de forma obligatoria los siguientes parámetros:  `String order`, `String reconciliation`, `String title`, `String logo`, `String customId`, `JSONObject notify`, `JSONObject policyData`, cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método  `methods` devuelve un objeto `GenericMethods`, el cual contiene:
* Un Json con los métodos de pagos disponibles y,
* Los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1).

### Ejemplo
#### Solicitud de los métodos disponibles
```java 
JSONObject payload = new JSONObject();
JSONObject notify = new JSONObject();

// El campo order tiene que ser único
payload.put("order", "prueba-order-0000000020");
payload.put("reconciliation", "reconciliation");
payload.put("title", "Sipay Pruebas");
payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

notify.put("result", "url");
payload.put("notify", notify);
payload.put("policyData", new JSONObject());

Amount amount = new Amount("10000", "EUR");

Methods methods = new Methods(payload);
GenericMethods resp = sofort.methods(methods, amount);
if (resp == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (resp.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + resp.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


### 6.3.2   GenericConfirm confirm(@Nonnull String requestId)


### Parámetros
* **`String requestId)`:** [_obligatorio_] Es el identificador de la operación, obtenido de la respuesta de la llamada `methods`.

### Salida
El método `confirm` devuelve un objeto `GenericConfirm`,  que permite confirmar el pago. Es un Json con los parámetros de respuesta definidos en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales.

### Ejemplo
#### Solicitud de confirmación
```java 
GenericConfirm confirm = sofort.confirm("6a571dffcc3ac117e5aefced");
if (confirm == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (confirm.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + confirm.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


## 6.4. Pmt (path)

### Definición
Esta clase permite enviar peticiones de Pmt (Paga Más Tarde), mediante el siguiente método:
 - GenericMethods methods
 
### 6.4.1   GenericMethods methods(@Nonnull Methods body, @Nonnull Amount amount)


### Parámetros
* **`body`:** [_obligatorio_] Es un objeto que contiene los campos que se envían al servicio de ALTP para obtener los métodos disponibles.  Para instanciar un `body` de tipo `Methods`  se necesita de forma obligatoria los siguientes parámetros: `String order`, `String reconciliation`, `String title`, `String logo`, `JSONObject notify`, `JSONObject customer`, `JSONObject policyData` cuya definición concreta está disponible en https://sandbox.sipay.es/docm/altp/es/#/peticiones/listado-de-los-metodos-actuales
* **`amount `:** [_obligatorio_] Corresponde a una instancia de `Amount` con el importe de la operación.

### Salida
El método `methods` devuelve un objeto `GenericMethods`, el cual contiene:
* Un Json con los métodos de pagos disponibles y,
* Los mismos datos generales de las respuestas que se especificaron en la sección Ecommerce (Ver 5.3.1).

### Ejemplo
#### Solicitud de los métodos disponibles
```java 
JSONObject payload = new JSONObject();
JSONObject notify = new JSONObject();
JSONObject customer = new JSONObject();

// El campo order tiene que ser único
payload.put("order", "prueba-order-00000000204");
payload.put("reconciliation", "reconciliation");
payload.put("title", "Sipay Pruebas");
payload.put("logo", "https://www.sipay.es/wp-content/uploads/Sipay_payment-solutions_1DEBAJO-min.png");

notify.put("result", "url");
payload.put("notify", notify);

customer.put("email", "email@example.com");
customer.put("full_name", "John Doe");

payload.put("customer", customer);
payload.put("policyData", new JSONObject());

Methods methods = new Methods(payload);
Amount amount = new Amount("100", "EUR");

GenericMethods resp = pmt.methods(methods, amount);
if (resp == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (resp.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + resp.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```


## 6.5. Querys (path)

### Definición
Esta clase permite obtener el estado de cualquiera de las operaciones de ALTP. El método utilizado es:
- getStatusOperation

### 6.5.1  StatusOperation getStatusOperation(@Nonnull String requestId)

### Parámetros
* **`String requestId)`:** [_obligatorio_] Es el identificador de la operación, obtenido de la respuesta de la llamada `methods`.

### Salida
El método `getStatusOperation` devuelve un objeto `StatusOperation `,  con los datos del estado de la operación: `JSONObject request`, `JSONObject response`, `String status`, `String confirm`

### Ejemplo
#### Solicitud de estado de una operación
```java 
StatusOperation statusOperation = querys.getStatusOperation("5947b6f3e6129d0001e7dad6");
if (statusOperation == null) {
	System.out.println("Failure in operation. Error connecting to the service");
} else if (statusOperation.getCode() != 0) {
	System.out.println("Failure in operation. Error:" + statusOperation.getDescription());
} else {
	System.out.println("Operation processed successfully");
}
  ```