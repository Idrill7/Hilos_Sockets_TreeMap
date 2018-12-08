package Servidor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Alejandro Gonzalez Casado
 * @version 1.0
 * 
 *          Esta es la clase Servidor, en ella crearemos un Objeto de la clase
 *          ServerSocket y lo enlazaremos a una dirección IP junto a un puerto
 *          Con un demonio aceptaremos  todas las solicitudes que vaya recibiendo al servidor 
 *          por parte de  un Socket (cliente), crearemos un Socket,  y arrancaremos un hilo a cada conexion aceptada
 *          mediante la creacion de un objeto de la clase HiloEscuchador,  al cual le pasaremos
 *          el objeto Socket y la funcion insertarMap, que contiene un TreeMap de productos, su clave y el valor
 */
public class Servidor {

	public static void main(String[] args) {
		System.out.println("APLICACIÓN DE SERVIDOR MULTITAREA");
		System.out.println("----------------------------------");
		/**
		 * Introducimos una sentencia try/catch, en el try,  la creacion del objeto ServerSocket
		 * y un objeto de la clase InetSocketAdress que sera
		 * el enlace a direccion y el puerto donde se podra establecer la conexion con
		 * el ServerSocket. El while sera un demonio que estara en continuo funcionamiento, este aceptara 
		 * las conexiones de los clientes y a su vez crearemos un objeto de la clase
		 * HiloEscuchador, arrancando un hilo por cada cliente que se conecte al servidor
		 *  En el catch capturamos las excepciones y las imprimimos por pantalla
		 */
		// Creamos la variable que sera referencia del objeto de la clase ServerSocket
		ServerSocket servidor = null;
		try {
			// Creamos un objeto de la clase ServerSocket que sera el Socket del servidor
			servidor = new ServerSocket();

			// Mediante la creacion del objeto de la clase InetSocketAdress determinaremos
			// en los parametros la IP y el puerto de conexion 
			InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
			// Enlazamos el servidor a la direccion anterior mediante el metodo bind()
			// El servidor arranca y empezara a escuchar peticiones que llegaran a esa direccion y puerto
			servidor.bind(direccion);
	
			System.out.println("Servidor listo para aceptar solicitudes");
			System.out.println("Dirección IP: " + direccion.getAddress());

			// En este while creamos un bucle conocido como "demonio", de forma que sea infinito mientras dure el Servidor arrancado
			while (true) {
				// Esperamos a que llegue un cliente
				
				// El servidor.accept lo que hace es que cuando se acepta una conexion que le entra al servidor
				//  crea un objeto de tipo Socket 
				Socket enchufeAlCliente = servidor.accept();
				
				System.out.println("Comunicación entrante");
				// Creamos un objeto de la clase HiloEscuchador, este arranca un hilo para cada
				// cliente, a este le pasamos como parametro el objeto Socket creado anteriormente
				// junto la funcion insertarMap
				new HiloEscuchador(enchufeAlCliente, insertarMap());
			}
			// Capturamos las excepciones generada
		} catch (IOException e) {
			// Si hay alguna excepcion la imprimimos
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Esta es la funcion insertarMap, en ella mediante la referencia de Map,
	 * crearemos un objeto de la clase TreeMap este recibira como clave un String y
	 * como valor un objeto de la clase Producto, estos objetos seran anadidos con el
	 * nombre, stock y precio del producto mediante el metodo put()
	 * 
	 * @return devuelve el TreeMap productos, que contendra todas las claves y
	 *         valores asignadas
	 */

	private static Map<String, Producto> insertarMap() {
		Map<String, Producto> productos = new TreeMap<String, Producto>();
		// Con el metodo put() anadimos la clave y el valor al TreeMap
		productos.put("PL", new Producto("Peras limoneras", 14, 5f));

		productos.put("PC", new Producto("Peras conferencia", 12, 7f));

		productos.put("PN", new Producto("Plátano canario", 5, 2.5f));

		productos.put("BN", new Producto("Bananas", 7, 1.3f));

		productos.put("TP", new Producto("Tomates tipo pera", 8, 1.7f));

		productos.put("TR", new Producto("Tomates Raf", 7, 5.3f));

		productos.put("UN", new Producto("Uvas negras", 8, 3.2f));

		productos.put("UB", new Producto("Uvas blancas", 5, 2.7f));

		productos.put("PT", new Producto("Picotas", 8, 4.3f));

		productos.put("CR", new Producto("Ciruelas rojas", 10, 2.8f));

		productos.put("MR", new Producto("Melocotones rojos", 3, 2.5f));

		productos.put("MA", new Producto("Melocotones amarillos", 4, 3.2f));
		return productos;
	}

}
