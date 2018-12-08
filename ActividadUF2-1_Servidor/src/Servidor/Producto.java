package Servidor;
/**
 * 
 * Esta es la clase Producto, en ella tendremos el constructor para crear
 * objetos con los parametros indicados 
 * El objetivo es crear objetos de la clase Productos y guardarlos como valor en el TreeMap de la clase Servidor
 *
 */
public class Producto {

	// Declaramos las propiedades de la clase Producto 
	// Creamos una referencia del nombre que apuntara a un String
	private String nombre;
	// Creamos una referencia del stock  que apuntara a un int
	private int stock;
	// Creamos una referencia del precio que apuntara a un float
	private float precio;

	/**
	 * Esta es el constructor, en el tendremos los parametros
	 * 
	 * @param nombre
	 *            , hace referencia al nombre del producto
	 * @param stock,
	 *            hace referencia al stock del producto
	 * @param precio
	 *            hace referencia al precio del producto
	 */
	public Producto(String nombre, int stock, float precio) {
		// Establecemos el valor de la propiedad nombre cuando se crea el objeto de Producto
		//Hacemos una referencia mediante this a la propiedad de la clase Producto, esta apuntara al String nombre que se le pasa al objeto de Producto
		this.nombre = nombre;
		// Establecemos el valor de la propiedad stock cuando se crea el objeto de Producto
		//Hacemos una referencia mediante this a la propiedad de la clase Producto, esta apuntara al int stock que se le pasa al objeto de Producto
		this.stock = stock;
		// Establecemos el valor de la propiedad precio cuando se crea el objeto de Producto
		//Hacemos una referencia mediante this a la propiedad de la clase Producto, esta apuntara al float precio que se le pasa al objeto de Producto
		this.precio = precio;
		
	}

// Getter y Setter //
	// Realmente no hacen falta porque al final devolvemos al cliente el valor de todo mediante el toString()
	public String getNombre() {
		return nombre;
	}

	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	public double getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	/**
	 * Sobreescribimos el metodo toString() para que cuando lo invoquemos imprima por
	 * pantalla los valores  del nombre, el stock y el precio del codigo del producto del TreeMap de la clase Servidor
	 */
	@Override
	public String toString() {
		return " El codigo corresponde a : " + nombre + ", Cantidad=" + stock + ", Precio=" + precio + ".";
	}

}
