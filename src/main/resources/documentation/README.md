# API REST SEGURA CON MONGODB

### DESCRIPCIÓN BREVE
Esta es una implementación de una API REST segura usando de método de almacenamiento de datos un sistema **No Relacional** con MongoDB (documentos).

Es una aplicación sencilla en la que se gestionarán las tareas de casa. También, permitirá un registro de usuarios y un manejo de tareas que explicaré mas a detalle en adelante...

### DESCRIPCIÓN DETALLADA
Los documentos que compondrán esta aplicación son:
1. Usuarios
   2. username: String (nombre del usuario)
   3. password: String (contraseña del usuario)
   4. roles: Rol (Enum class con los roles: USER, ADMIN)
   5. direccion: Direccion (dirección del usuario)
   5. tareas: List<Tareas> (tareas que tiene el usuario)
2. Dirección
   3. calle: String (nombre de la calle)
   4. num: Int (número del hogar)
   5. provicia: String (provincia del usuario)
   6. municipio: String (municipio del usuario)
   7. cp: Int (código postal del usuario)
3. Tarea
   4. titulo: String (titulo de la tarea)
   5. descripcion: String (breve descripcion de la tarea)
   6. estado: String (estado de la tarea PENDIENTE/COMPLETADO)
   7. usuario: Usuario (usuario propietario de la tarea)

### SCREENSHOTS INICIALES

* Datos del Spring Initializr
!["Foto de los datos de spring initializr"](spring_initializr.png)
* Colaborador Lainezz Añadido
!["Foto de como añado un colaborador](collaborator.png)
* Base de datos creada en MongoDB
!["Foto de la base de datos creada en MongoDB"](base%20de%20datos%20de%20mongo.png)