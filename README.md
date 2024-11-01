# Parcial segundo corte 

Este proyecto despliega una aplicación web que permite realizar dos algoritmos esenciales en la ciencia de la computación: la búsqueda binaria y la búsqueda lineal, a través de una arquitectura de microservicios. Un servidor proxy recibe las solicitudes de los usuarios y las distribuye entre dos servidores de búsqueda disponibles mediante un algoritmo de round-robin.

## Introducción
Las instrucciones a continuación te permitirán obtener una copia de este proyecto y ponerlo en funcionamiento tanto en tu máquina local como en un servidor EC2 en AWS para fines de desarrollo y pruebas.

## Prerrequisitos
Para poder ejecutar este proyecto, necesitas instalar las siguientes herramientas y configurar sus dependencias:

1. **Java(version 17 o superior)**
    ```sh
    java -version
    ```
    Debería mostrar algo como:
    ```sh
    java version "17.0.7"
    OpenJDK Runtime Environment (build 17.0.7+7-LTS)
    OpenJDK 64-Bit Server VM (build 17.0.7+7-LTS, mixed mode, sharing)
    ```
2. **Maven**
    - Descarga maven [aqui](http://maven.apache.org/download.html)
    - Sigue las siguientes instrucciones [aqui](http://maven.apache.org/download.html#Installation)

    Verifica la descarga
    ```sh
    mvn -version
    ```
    Debería mostrar algo como:
    ```sh
    Apache Maven 3.2.5 (12a6b3acb947671f09b81f49094c53f426d8cea1; 2014-12-14T12:29:23-05:00)
    Maven home: /Users/dnielben/Applications/apache-maven-3.2.5
    Java version: 1.8.0, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/jre
    Default locale: es_ES, platform encoding: UTF-8
    OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"
    ```

3. **Git**
    - Instala git siguiendo las siguientes instrucciones [aqui](http://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

    verifica la instalación
    ```sh
    git --version
    ```
    Debería mostrar algo como:
    ```sh
    git version 2.31.1
    ```



## Uso local de ParcialSegundoCorte

### Características
La aplicación permite:

1. **Buscar mediante búsqueda lineal**: dada una lista de números, busca un valor específico y devuelve la posición si se encuentra.
2. **Buscar mediante búsqueda binaria**: ordena la lista y aplica búsqueda binaria para encontrar el valor.
3. **Distribuir carga**: el proxy distribuye las solicitudes usando round-robin entre dos instancias de Math Service.

### Pasos para correr la aplicación localmente

1. Clona el repositorio 

```bash
    git clone https://github.com/alexandrac1420/ParcialSegundoCorte.git
    cd ParcialSegundoCorte
```

2. Realiza el empaquetado del archivo 

```bash
    mvn package
```

3. Abre tres terminales diferentes y ejecuta el comando en cada uno de estas

* **Proxy** : Para la terminal donde se vera el proxy
    ```bash
        java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8080
    ```

* **MathServer1**: Para la temrminal donde se ver uno de los Math Service
    ```bash
        java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8081
    ```

* **MathServer2**:Y en la ultima terminal donde se vera el otro Math service
    ```bash
        java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8082
    ```

![alt text](https://github.com/alexandrac1420/ParcialSegundoCorte/blob/master/pictures/terminales.png)

4. Ingresa a http://localhost:8080


En caso de que no se encuentre el numero en la lista se mostrara -1 en la parte de output del mensaje json

![alt text](https://github.com/alexandrac1420/ParcialSegundoCorte/blob/master/pictures/local.png)


La terminal del proxy mostrará a que servicios manda la solicitud de los servidores Math

### Ejemplo de Respuesta
![image](https://github.com/user-attachments/assets/03b1432a-628b-44c7-aedb-b0b7b15f0e64)



## Despliegue en AWS EC2

![AWS](https://github.com/alexandrac1420/Seguridad_Nube/blob/master/Pictures/Funcionamiento%20AWS.gif)

Para desplegar la aplicación en AWS EC2, debes configurar varias instancias para el proxy y los Math Services, utilizando direcciones IP públicas para permitir que las instancias se comuniquen entre sí.

### Pasos para Desplegar en EC2

1. **Configurar las Instancias de EC2**
   - Crea tres instancias EC2 en AWS:
     - **Una instancia para el Proxy**.
     - **Dos instancias para los Math Services**.
   - Asegúrate de asignar IPs públicas a cada instancia y de habilitar las reglas de seguridad necesarias:
     - **Permitir tráfico en el puerto 8080** en todas las instancias para permitir el acceso y comunicación entre los servicios.

2. **Modificar la Configuración del Proxy**
   - En el archivo `HttpConnectionExample` del proyecto, actualiza la lista de servidores con las IPs públicas de tus instancias de Math Services, todas escuchando en el puerto `8080`:
     ```java
     private final List<String> servers = Arrays.asList("http://<EC2-IP-1>:8080", "http://<EC2-IP-2>:8080");
     ```

3. **Subir el Archivo .jar a las Instancias**
   - Empaqueta el proyecto usando Maven:
     ```bash
     mvn package
     ```
   - Usa SFTP para transferir el archivo .jar (`ParcialSegundoCorte-0.0.1-SNAPSHOT.jar`) a cada instancia de EC2.
     ```bash
     sftp -i <tu-llave.pem> ec2-user@<EC2-IP>
     put ParcialSegundoCorte-0.0.1-SNAPSHOT.jar
     ```

4. **Instalar Java en Cada Instancia**
   - Conéctate a cada instancia EC2 usando SSH:
     ```bash
     ssh -i <tu-llave.pem> ec2-user@<EC2-IP>
     ```
   - Instala Java (si aún no está instalado):
     ```bash
     sudo yum install java-17-amazon-corretto -y
     ```

5. **Ejecutar el Servicio en Cada Instancia**
   - En la instancia del Proxy:
     ```bash
     java -jar ParcialSegundoCorte-0.0.1-SNAPSHOT.jar 
     ```
   - En cada instancia de Math Service:
     ```bash
     java -jar ParcialSegundoCorte-0.0.1-SNAPSHOT.jar 
     ```

6. **Acceder a la Aplicación**
   - Ingresa a la dirección IP pública de la instancia del Proxy en el puerto `8080` (por ejemplo, `http://<Proxy-EC2-IP>:8080`) para realizar las búsquedas y demostrar el funcionamiento.
   - El proxy distribuirá las solicitudes entre las instancias de Math Services utilizando balanceo de carga round-robin.

    ![Image](image.png)


## Arquitectura
La arquitectura del sistema utiliza una configuración distribuida con varias instancias EC2 en AWS:

1. **Browser**: Cliente que envía solicitudes de búsqueda (lineal o binaria) a través de una interfaz web.
2. **Service Proxy (en EC2)**: Punto de entrada para todas las solicitudes de búsqueda, que distribuye las solicitudes a través de un balanceo de carga round-robin entre los servicios de matemáticas.
3. **Math Services (en dos instancias EC2)**: Servidores que procesan la lógica de búsqueda, ejecutando la búsqueda lineal o binaria según lo solicitado y devolviendo los resultados al proxy.

![alt text](https://github.com/alexandrac1420/ParcialSegundoCorte/blob/master/pictures/Arquitectura.png)


## Diagrama de clases
El sistema se compone de las siguientes clases principales:

1. **ProxyController**

* Recibe solicitudes de búsqueda del cliente y las delega a HttpConnectionExample para su distribución entre los servidores de Math.


2. **HttpConnectionExample**

* Gestiona la distribución de solicitudes a las instancias de Math Services mediante round-robin, asegurando un balanceo de carga efectivo.

3. **MathController**

* Ejecuta los algoritmos de búsqueda lineal y binaria, retornando el resultado.

4. **ParcialSegundoCorteApplication**

* Clase principal que arranca la aplicación Spring Boot y configura el contexto de Spring.


## Construido con

* [Maven](https://maven.apache.org/) - Manejo de dependencias
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework backend
* [Git](http://git-scm.com/) - Control de versiones


## Authores

* **Alexandra Cortes Tovar** - [alexandrac1420](https://github.com/alexandrac1420)


## License

Este proyecto está licenciado bajo GNU.