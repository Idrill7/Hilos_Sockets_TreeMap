package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
/**
 * Esta es la clase HiloEscuchador, implementa Runnable para que la clase pueda crear los hilos y estos  ejecuten  la funcion run() una vez iniciados en el constructor
 * En esta clase cada vez que se cree un objeto de la misma, mediante el constructor se creara un objeto de la clase Thread, un hilo, que tendra una referencia al mismo objeto creado de esta clase
 * La finalidad del run() es que el cliente teclee un codigo del TreeMap y el servidor le devuelva el valor, a no ser que escriba FIN y cierre la conexion con el Servidor
 *
 */
public class HiloEscuchador implements Runnable {
	// Declaramos las propiedades de la clase HiloEscuchador
	// Se guarda una referencia en el atributo hilo que apunta a un objeto de la clase Thread 
	private Thread hilo;
	// Creamos una variable estatica que pertenece a la clase y a la region de
		// memoria conocida "memoria inmortal" de java, esta se ira sumando en el constructor por cada hilo creado ( por cada cliente)
	private static int numCliente = 0;
	// Guardamos una referencia en el atributo enchufeAlCliente que apunta a un objeto de la clase Socket
	private Socket enchufeAlCliente;
	// Guardamos una referencia en el atributo productos que apuntara a un objeto de la interfaz Map por el cual entrara un String como clave y un objeto de la clase Producto como valor
	private Map<String, Producto> productos;
	/**
	 * Este es el constructor de la clase HiloEscuchador, sera compuesto por los siguientes parametros
	 * @param cliente
	 * 					que hace referencia al objeto de la clase Socket
	 * @param productos
	 * 					que hace referencia al objeto de la clase Map
	 */
	public HiloEscuchador(Socket cliente, Map<String, Producto> productos) {
		// Aumentamos en uno la variable cada vez que se crea un objeto de la clase HiloEscuchador
		numCliente++;
		// Creamos un objeto de la clase Thread, el cual tendra como referencia el mismo objeto de la clase HiloEscuchador, y como nombre el Cliente y numeroCliente
		hilo = new Thread(this, "Cliente_"+numCliente);
		// Hacemos una referencia mediante this, esta apuntara  al Socket del cliente creado en la clase Servidor cuando se ejecuta el metodo accept() y acepta una conexion
		this.enchufeAlCliente = cliente;
		// Hacemos una referencia mediante this, esta apuntara al TreeMap de productos
		this.productos = productos;
		// Iniciamos el hilo
		hilo.start();
	}
	/**
	 * Sobreescribimos el metodo run(), una vez el hilo sea iniciado sera ejecutado
	 */
	@Override
	public void run() {
		
		System.out.println("Estableciendo comunicación con " + hilo.getName());
		/**
		 * Introducimos una sentencia try/catch, en el try obtendremos el texto mediante los stream de entrada y de salida
		 * Si el texto es "FIN", acabara la conexion, pero si el texto no es "FIN", comprobara si es una de las claves del TreeMap
		 * Si lo es, sacara el valor de esa clave, y si no lo es indicara que no lo encuentra
		 * En el catch capturamos las excepciones y las imprimimos por pantalla
		 * 
		 */
		try {
			// Necesitamos unos flujos de entrada y salida para obtener los datos del Socket del cliente y para enviarle los datos
			// Obtenemos el stream de entrada por el que va a viajar la informacion que nos va a enviar el Socket del cliente  mediante el metodo getInputStream
			InputStream entrada = enchufeAlCliente.getInputStream();
			// Obtenemos el stream de salida por el que van a enviarse los datos al Socket del cliente mediante el metodo getOutputStream
			OutputStream salida = enchufeAlCliente.getOutputStream();
			// Creamos la variable String  texto y la inicializamos a un texto vacio 
			String texto = "";
			/**
			 * En este while  leera el mensaje que llega por el stream de entrada , comprobaremos si esta como clave en el TreeMap, 
			 * si la contiene imprimira al cliente  el valor con el metodo sobreescrito toString() de la clase Producto y si no informara de que no se encuentra
			 * 
			 *  Como el texto lo hemos iniciado a blanco(""), va a entrar en el bucle, lee el  mensaje y si escribimos FIN , se despedira del cliente,
			 *  la proxima vez que este intenta entrar en el bucle, texto sera igual a FIN, no entrara en el bucle y entonces se cerraran las conexiones con ese cliente
			 */
			
			while (!texto.trim().equals("FIN")) {
				// creamos un array de 100 bytes, para recoger el mensaje de entrada del cliente 
				byte[] mensaje = new byte[100];
				// leemos el texto que viene del stream de  entrada y lo guardamos en el array de bytes
				entrada.read(mensaje);
				// en la variable texto, guardamos un objeto de la clase String y le pasaremos como parametro el array de bytes, el mensaje
				texto = new String(mensaje);
				// removemos del mensaje recibido los espacios en blanco al principio y al final
				texto = texto.trim();
				
				System.out.println(hilo.getName() + " consulta producto: " + texto);
				// si dentro de el TreeMap de productos se encuentra como clave el texto del cliente
				if (productos.containsKey(texto)) {	
					// el servidor escribe al cliente mediante el stream de salida la informacion del producto con el metodo toString()
					salida.write(productos.get(texto).toString().getBytes());
					// si escribimos "FIN", entrara en este else if  y no volvera a entrar en el bucle
			      } else if (texto.equals("FIN")) {
			    	  // nos despedimos del cliente,  , le escribimos mediante el stream de salida
					salida.write("Hasta pronto, gracias por establecer conexión".getBytes());
					// escribimos en el servidor que se ha cerrado la comunicacion con ese cliente
					System.out.println(hilo.getName()+" ha cerrado la comunicación");
				} else {
					// si no es FIN, y no se encuentra como clave, se le escribe al cliente mediante el stream de salida que no se ha encontrado esa clave en el servidor
					String noEncontrado = "El codigo: "  + texto + "," + " no se encuentra en el servidor";
					salida.write(noEncontrado.getBytes());
				}
			}
			// cuando sale del while, es decir, cuando el cliente escribe "FIN"
			// cerramos el stream de entrada 
			entrada.close();
			// cerramos el stream de salida
			salida.close();
			//cerramos el Socket del cliente que se creo en la clase Servidor
			enchufeAlCliente.close();
			//capturamos las excepciones
		} catch (IOException e) {
			// Imprimimos la excepcion que se ha dado
			System.out.println(e.getMessage());
		}
		
	}
}