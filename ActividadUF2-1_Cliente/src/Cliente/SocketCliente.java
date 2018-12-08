package Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Esta es la clase SocketCliente, en ella crearemos un objeto de la clase
 * Socket para el cliente y le indicaremos la direccion IP y el puerto al que
 * tiene que realiza la conexion. Crearemos los canutos de entrada y salida de
 * datos para con ello enviar al servidor y recibir del mismo los datos que nos envie
 *
 */
public class SocketCliente {

	public static void main(String[] args) {
		// Creamos la variable que sera referencia del objeto de la clase Socket 
		Socket socketCliente = null;
		System.out.println("        APLICACIÓN CLIENTE");
		System.out.println("-----------------------------------");

		// Creamos un objeto de la clase Scanner, este guardara lo que escribamos en el teclado ( los inputStream )  mediante el System.in
		Scanner lector = new Scanner(System.in);

		/**
		 * Introducimos una sentencia try/catch. En el try,  declaramos el Socket del cliente junto a la
		 * direccion y puerto al que va a conectarse, generaremos los flujos de
		 * entrada y salida de datos. Comprobaremos el texto escrito en un bucle while, si es FIN
		 * cierra los flujos y la conexion con ese Socket, pero si es distinto a FIN, envia el mensaje al
		 * Servidor por el flujo de salida y recibe el mensaje de respuesta del Servidor mediante el
		 * flujo de entrada
		 *  En el catch capturamos las excepciones y las imprimimos por pantalla
		 */
		try {
			// Creamos un objeto de la clase Socket que sera el del cliente
			socketCliente = new Socket();
			// Mediante la creacion del objeto de la clase InetSocketAdress determinaremos
			// en los parametros la IP y el puerto de conexion a los que se conectara el cliente,
			// el ServerSocket estara enlazado y recibira peticiones de  esta direccion y puerto 
			InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 2000);

			System.out.println("Esperando a que el servidor acepte la conexión");

			// Con el metodo connect() haremos que el objeto Socket del cliente se conecte a
			// la IP y el puerto indicado en el objeto direccionServidor
			socketCliente.connect(direccionServidor);
			
			System.out.println("Comunicación establecida con el servidor");
			// Necesitamos unos flujos de entrada y salida de la informacion
			// Obtenemos el stream de entrada por el que va a viajar la informacion que nos va a devolver el ServerSocket mediante el metodo getInputStream
			InputStream entrada = socketCliente.getInputStream();
			// Obtenemos el stream de salida por el que van a enviarse los datos al ServerSocket mediante el metodo getOutputStream
			OutputStream salida = socketCliente.getOutputStream();
			// Creamos la variable String, texto,  y la inicializamos a un texto vacio 
			String texto = "";

			/**
			 * En este bucle, se envia el texto escrito al servidor mediante el stream de salida
			 * y recibimos  la respuesta mediante el stream de sentrada
			 * Si escibimos FIN, se sale del bucle y cierra las conexiones de ese cliente
			 */
			while (!texto.equals("FIN")) {

				System.out.println("Escribe codigo de articulo (FIN para terminar): ");
				// guardamos en la variable texto lo que ha escrito el cliente mediante el metodo nextLine() del Scanner lector, creado anteriormente
				texto = lector.nextLine();
				// le enviamos al servidor el texto escrito mediante el stream de salida del Socket del cliente y el metodo write()
				salida.write(texto.getBytes());
				// creamos un array de 100 bytes, como lo que responde el servidor es en bytes, lo recogemos en este array
				byte[] mensaje = new byte[100];
				System.out.println("Esperando respuesta ...... ");
				// leemos del servidor el mensaje que nos entra mediante el stream de entrada del Socket del cliente y el metodo read()
				entrada.read(mensaje);
				// imprimimos la respuesta del servidor, que sera un objeto String del mensaje que hemos leido anteriormente
				System.out.println("Servidor responde: " + new String(mensaje));
			}
			// Cuando se sale del bucle while, es  porque el cliente ha escrito FIN
			// cerramos el scanner
			lector.close();
			// cerramos el stream de entrada 
			entrada.close();
			// cerramos el stream de salida
			salida.close();
			// cerramos la conexion del Socket con ese cliente
			socketCliente.close();
			
			System.out.println("Comunicación cerrada");
		
		} catch (UnknownHostException e) {
			System.out.println("No se puede establecer comunicación con el servidor");
			System.out.println(e.getMessage());

		} catch (IOException e) {
			System.out.println("Error de E/S");
			System.out.println(e.getMessage());
		}
	}

}
