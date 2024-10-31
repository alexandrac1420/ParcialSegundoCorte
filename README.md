# Parcial segundo corte 

Se despliega una aplicacion web que permite realizar dos algoritmos de busqueda esenciales en la ciencia de la computación como la busqueda binaria y la busqueda lineal.

## ¿Que debe tener?
1. Descarga git
2. Descarga java version mayor a la 17
3. Descarga maven


## ¿Como usarlo localmente?
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

Para la terminal donde se vera el proxy
```bash
    java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8080
```

Para la temrminal donde se ver uno de los Math Service
```bash
    java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8081
```

Y en la ultima terminal donde se vera el otro Math service
```bash
    java -jar target/ParcialSegundoCorte-0.0.1-SNAPSHOT.jar --server.port=8082
```

![alt text](image.png)

4. Ingresa a http://localhost:8080


En caso de que no se encuentre el numero en la lista se mostrara -1 en la parte de output del mensaje json

![alt text](image.png)


La terminal del proxy mostrará a que servicios manda la solicitud de los servidores Math


## Usarlo en EC2
Falta la implemetación de esta parte.


## Arquitectura
Se utiliza una arquitectura de microservicios la cual tiene un servicio para computar las funciones de ordenamiento, ademas de implementar un proxy que recibe las solicitudes de los clientes y las delega a las dos instacias de los servicios de ordenamiento usando el algoritmo round-robin, que distribuye las cargas entre los dos servidores de (MathService)
![alt text](image.png)


## Diagrama de clases
Se utilizaron 3 clases principales

1. HttpConectionExample: Encargada de la distribución de la solicitud que entra al proxy al servicio Math (En esta se aplica el algoritmo round-robin)
2. MathController: Encargada de las solicitudes y logica de los dos algoritmos de busqueda
3. Proxy: Encargado de atender las solicitudes generadas por el usario y mandarlas a HttpConectionExample

