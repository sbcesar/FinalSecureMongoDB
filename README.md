# API REST SEGURA CON MONGODB

### DESCRIPCIÓN BREVE
Esta es una implementación de una API REST segura usando de método de almacenamiento de datos un sistema **No Relacional** con MongoDB (documentos).

Es una aplicación sencilla en la que se gestionarán las tareas de casa. También, permitirá un registro de usuarios y un manejo de tareas que explicaré mas a detalle en adelante...

### DESCRIPCIÓN DETALLADA
Los documentos que compondrán esta aplicación son:
1. Usuarios
   1. username: String (nombre del usuario)
   2. password: String (contraseña del usuario)
   3. roles: Rol (Enum class con los roles: USER, ADMIN)
   4. direccion: Direccion (dirección del usuario)
   5. tareas: List<Tareas> (tareas que tiene el usuario)
2. Dirección
   1. calle: String (nombre de la calle)
   2. num: Int (número del hogar)
   3. provicia: String (provincia del usuario)
   4. municipio: String (municipio del usuario)
   5. cp: Int (código postal del usuario)
3. Tarea
   1. titulo: String (titulo de la tarea)
   2. descripcion: String (breve descripcion de la tarea)
   3. estado: String (estado de la tarea PENDIENTE/COMPLETADO)
   4. usuario: Usuario (usuario propietario de la tarea)

###  ENDPOINTS

 * Usuarios {/usuarios}
   * register {/register} -> Registra a un usuario en la bd
   * login {/login} -> Inicia sesion
 * Tareas {/tareas}
   * seeAllTasks {/show} -> Muestra todas las tareas
   * seeTaskById {/show/{id}} -> Muestra la tarea por id
   * createTask {/create} -> Da de alta una tarea
   * updateTask {/update} -> Actualiza los datos de una tarea
   * completeTask {/complete/{id}} -> Marca una tarea como completada
   * deleteTask {/delete/{id}} -> Borra una tarea

### EXCEPCIONES

 * 400 - BadRequestException: Indica que el servidor no puede cumplir con las solicitudes debido a un error por parte del cliente
 * 401 - UnauthorizedException: Indica que el So deniega el acceso debido a un error de seguridad
 * 404 - NotFoundException: Indica que el recurso solicitado por el cliente no se encuentra en el servidor

### RESTRICCIONES DE SEGURIDAD

Para "privar" a los usuarios de cualquier accion he decidido implementar un sistema de roles compuesto por dos tipos:
 * ADMIN (tiene acceso a ver, eliminar y dar de alta cualquier tarea de cualquier usuario)
 * USER (el resto de funciones que no sean las de admin)

Además, se utilizará cifrado asimétrico con clave pública y clave privada, junto con JWT (JSON Web Token), para el control de acceso.

### PRUEBAS DE INSOMNIA

#### USUARIO

 * register (localhost:8081/usuario/register)
 
Esta es una imagen de un registro válido (USER)

!["Foto de un registro válido"](src/main/resources/documentation/registro%20valido.png)

Esta es la confirmación del registro válido previo

!["Foto de la confirmación de un registro válido"](src/main/resources/documentation/confirmado%20registro%20valido.png)

Esta es una imagen de un registro válido (ADMIN)

!["Foto de un registro valido"](src/main/resources/documentation/usuario%20admin%20creado.png)

Esta es la confirmación del registro válido previo

!["Foto de la confirmación de un registro válido"](src/main/resources/documentation/confirmacion%20admin%20creado.png)

Esta es una imagen con un registro inválido (municipio no encontrado)

!["Foto de una peticion invalida](src/main/resources/documentation/registro%20invalido%20municipio%20erroneo.png)

Esta es una imagen con un registro inválido (campo vacio)

!["Foto de una peticion invalida"](src/main/resources/documentation/registro%20invalido%20campo%20vacio.png)

Esta es una imagen con un registro inválido (contraseñas desiguales)

!["Foto de una peticion invalida"](src/main/resources/documentation/contraseñas%20no%20iguales.png)

 * login (localhost:8081/usuario/login)

Esta es una imagen de un login válido (USER)

!["Foto de un login válido"](src/main/resources/documentation/login%20correcto%20user.png)

Esta es una imagen de un login válido (ADMIN)

!["Foto de un login válido"](src/main/resources/documentation/LOGIN%20CORRECTO%20ADMIN.png)

Esta es una imagen de un login inválido (usuario no encontrado)

!["Foto de un login inválido"](src/main/resources/documentation/usuario%20no%20encontrado.png)

Esta es una imagen de un login inválido (contraseña incorrecta)

!["Foto de un login inválido"](src/main/resources/documentation/contraseña%20incorrecta.png)

#### TAREAS

* seeAllTasks (/tareas/show)
  
   Este endpoint muestra todas las tareas existentes de la base de datos y solo es accesible mediante los usuarios que tengan el rol **ADMIN**, de lo contrario te saltará un error 403 Forbidden que indica que no tienes permisos para acceder a este endpoint.

**Enunciado**: Usuario autenticado con rol **USER**

**Respuesta**: 403 Forbidden - Inicias sesion con un rol que no es admin

!["403 Forbidden Error"](src/main/resources/documentation/mostrar%20todas%20las%20tareas%20login%20user.png)

**Enunciado**: Usuario autenticado con rol **ADMIN**

**Respuesta**: 200 Ok - Muestra todas las tareas

!["200 OK"](src/main/resources/documentation/mostrar%20todas%20las%20tareas%20login%20admin.png)

**Enunciado**: Usuario no se ha autenticado

**Respuesta**: 401 Unauthorized - No se ha autenticado correctamente

!["No pones token"](src/main/resources/documentation/mostrar%20todas%20las%20tareas%20no%20toke.png)

* getMyTasks (/tareas/showTask)

   Este endpoint muestra las tareas propias del usuario logueado previamente, si tiene permisos de **USER** muestra todas sus tareas, si es **ADMIN** no te deja usar el endpoint.

**Enunciado**: Usuario autenticado con rol **USER**

**Respuesta**: 200 OK - Muestra todas las tareas propias

!["200 OK"](src/main/resources/documentation/Mostrar%20tarea%20propia%20login%20user.png)

**Enunciado**: Usuario autenticado con rol **ADMIN**

**Respuesta**: 403 Forbidden - No tienes permisos para acceder al endpoint

!["403 Forbidden"](src/main/resources/documentation/mostrar%20tareas%20propias%20rol%20admin.png)

* createTask (/tareas/create)

   Este endpoint crea una tarea introduciendole los campos de TareaDTO, si estás logueado como un usuario solo tienes acceso a crear tareas propias, si estás logueado como administrador puedes crear tareas a cualquier usuario con el id especificado (obviamente en el JSON)

Tarea creada con usuario **ADMIN**
**Enunciado**: Usuario autenticado con rol **ADMIN**

**Respuesta**: 201 Created - Crea la tarea con el id aportado

!["Se loguea como admin"](src/main/resources/documentation/Tarea%20creada%20con%20usuario%20admin.png)

Tarea creada con usuario **ADMIN** (se usa el id de otro usuario) -- No funciona, revisar porque usa el usuario admin, no el que introduces

![""]()

Tarea creada con usuario **ADMIN** (campos vacios) (menos en estado)

!["Falta titulo"](src/main/resources/documentation/tarea%20creada%20usuario%20vacio.png)

!["Falta descripcion"](src/main/resources/documentation/tarea%20creada%20descripcion%20vacia.png)

!["Falta id de usuario"](src/main/resources/documentation/tarea%20creada%20usuario%20id%20vacio.png)

Tarea creada con usuario **USER**

![""](src/main/resources/documentation/tarea%20creada%20con%20user.png)

Tarea creada con usuario **USER** (se usa id de otro usuario) -- No funciona,pilla el usuario que esta registrado, no el nuevo

![""]()

* updateTask (/tareas/update) -- No lo pide la rubrica pero está hecho en código


* completeTask (/tareas/complete/{id})

Completar una tarea **USER** (Forbidden) (no es suya)

!["Login con rol user pilla una tarea que no es suya"](src/main/resources/documentation/completar%20tarea%20login%20user%20id%20otro%20user.png)

Completar una tarea **USER** (si es suya)

!["Login con rol user pilla una tarea que si es suya"](src/main/resources/documentation/completar%20tarea%20login%20user.png)

Completar una tarea **USER** (Conflict)

!["Intenta completar una tarea ya completada"](src/main/resources/documentation/completar%20tarea%20completada.png)

Completar una tarea **ADMIN**

!["Admin completa una tarea"](src/main/resources/documentation/admin%20completa%20tarea.png)


* deleteTask (/tareas/delete/{id})

Borrar tarea **ADMIN**

!["Admin borra una tarea"](src/main/resources/documentation/admin%20borra%20tarea.png)

Borrar tarea **USER** otro usuario (Unauthorized)

!["Usuario borra una tarea que no es suya"](src/main/resources/documentation/user%20borra%20tarea%20no%20es%20suya.png)

Borrar tarea **USER**

!["Usuario borra tarea propia"](src/main/resources/documentation/user%20borra%20tarea.png)



### SCREENSHOTS INICIALES

* Datos del Spring Initializr
!["Foto de los datos de spring initializr"](src/main/resources/documentation/spring_initializr.png)
* Colaborador Lainezz Añadido
!["Foto de como añado un colaborador](src/main/resources/documentation/collaborator.png)
* Base de datos creada en MongoDB
!["Foto de la base de datos creada en MongoDB"](src/main/resources/documentation/base%20de%20datos%20de%20mongo.png)