
## URL Rest:

	


* **/User/{userId}**
  
        GET: Recupera la información del usuario 'userId'.
          
  
* **/User/{userId}/Login**
  
        POST: Loguea el usuario 'userId', creandolo si no existe

  
* **/User/{userId}/Items**
  
        GET: Recupera todos los items de un usuario        
        
* **/User/{userId}/Item/{itemId}**

        GET: Recupera los datos de un item perteneciente al usuario 'userId'
        
        POST: Crea un Item para el usuario 'userid'
        
        DELETE: Elimina un Item  del usuario 'userid'
        

        
* **/User/{userId}/Amigos/{idFriend}/Items**

        GET: Recupera los items de un amigo
        
* **/User/{userId}/Amigos/{idFriend}/Items/{itemId}**

        GET: Recupera un item particular de un amigo
        
* **/User/{userId}/Solicitudes**

        GET: Recupera todas las solicitudes del usuario       
        

