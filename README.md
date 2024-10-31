# Parcial segundo corte 

Se despliega una aplicacion web que permite realizar dos algoritmos de busqueda esenciales en la ciencia de la computación como la busqueda binaria y la busqueda lineal.

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