package Proyecto;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * M6-UF3 Proyecto MongoDB
 * @author ERIC
 *
 */
public class ConexionMongoDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean programaFuncionando = true;

		MongoClient  cliente = new MongoClient(); 
		MongoDatabase db = cliente.getDatabase ("mibasedatos"); 
		MongoCollection<org.bson.Document> coleccion = db.getCollection("M6");
		while(programaFuncionando == true) {
			System.out.println("");
			System.out.println("--------------------------------------------");
			System.out.println("Que funcion desea realizar: ");
			System.out.println("1- Insertar alumno: ");
			System.out.println("2- Visualizar alumnos: ");
			System.out.println("3- Visualizar alumno por Nombre: ");
			System.out.println("4- Modificar alumno por Nombre: ");
			System.out.println("5- Eliminar alumno por Nombre: ");
			System.out.println("6- Volcar datos alumno a fichero: ");
			System.out.println("7- Salir: ");
			System.out.println("--------------------------------------------");
			System.out.println("");
			int seleccion = sc.nextInt();
			if(seleccion == 1) {
				insertarDatosAlumno(coleccion, sc);
			} else if(seleccion == 2) {
				visualizarDatosAlumnos(coleccion);
			} else if(seleccion == 3) {
				visualizarDatosAlumnosIndicadoByName(coleccion, sc);
			}else if(seleccion == 4) {
				modificarDatosAlumnosByName(coleccion, sc);
			} else if(seleccion == 5) { 
				borrarDatosAlumnosByName(coleccion, sc);
			} else if(seleccion == 6){
				volcarDatosAlumnoAFicheroByName(coleccion, sc);
			} else if(seleccion == 7) {
				System.out.println("");
				System.out.println("Hasta la próxima.");
				System.out.println("");
				programaFuncionando = false;
			} else {
				System.out.println("Opción no válida, introduzca una de las que aparece en la lista");
			}
		}
	}

	/**
	 * Insertar alumno intoduciendo los datos requeridos por el método
	 * @param coleccion
	 * @param sc
	 */
	public static void insertarDatosAlumno(MongoCollection<org.bson.Document> coleccion, Scanner sc) {
		Document alumno = new Document(); 

		System.out.println("Escribe el nombre del alumno: ");
		String nombre = sc.next();
		if((nombre.indexOf("0") == -1) && (nombre.indexOf("1") == -1) && (nombre.indexOf("2") == -1) && (nombre.indexOf("3") == -1) && (nombre.indexOf("4") == -1) && (nombre.indexOf("5") == -1) && (nombre.indexOf("6") == -1) && (nombre.indexOf("7") == -1) && (nombre.indexOf("8") == -1) && (nombre.indexOf("9") == -1)) {
			alumno.put("nombre", nombre); 
		} else {
			System.out.println("Vuelve a introducir el nombre. Recuerda que no debe contener números: ");
			nombre = sc.next();
			alumno.put("nombre", nombre); 
		}
		System.out.println("Escribe el teléfono del alumno: ");
		int numTelefono = sc.nextInt();
		if(Integer.toString(numTelefono).length() == 9) {
			alumno.put("teléfono", numTelefono);
		} else {
			System.out.println("Vuelve a introducir el número de telefono. Recuerda que debe contener 9 digitos: ");
			numTelefono = sc.nextInt();
			alumno.put("teléfono", numTelefono);
		}
		System.out.println("Escribe el curso del alumno: ");
		String curso = sc.next();
		alumno.put("curso",curso); 
		alumno.put("fecha", new Date() );
		coleccion.insertOne(alumno);
	}

	/**
	 * Visualizar Datos de cada uno de los documentos insertados en nuestra base de datos
	 * @param coleccion
	 */
	public static void visualizarDatosAlumnos(MongoCollection<org.bson.Document> coleccion) {
		List<Document> consulta = coleccion.find().into(new ArrayList<Document> ()); 
		for (int i =0; i < consulta.size(); i++) { 
			System.out.println(" - " +  consulta.get(i).toString()); 
		}
	}

	/**
	 * Visualizar Datos de uno de los documentos insertados en nuestra base de datos, filtrando por su Nombre.
	 * @param coleccion
	 * @param sc
	 */
	public static void visualizarDatosAlumnosIndicadoByName(MongoCollection<org.bson.Document> coleccion, Scanner sc) {
		MongoCursor<Document> cursor = coleccion.find().iterator(); 
		System.out.println("Escribe el nombre del alumno a visualizar: ");
		String nombre = sc.next();
		while (cursor.hasNext()) {
			Document doc = cursor.next(); 
			if(doc.getString("nombre").equals(nombre)) {
				System.out.println (doc.toJson().toString()); 
			}
		} 
		cursor.close();
	}

	/**
	 * Modificar Datos de uno de los documentos insertados en nuestra base de datos, filtrando por su Nombre.
	 * @param coleccion
	 * @param sc
	 */
	public static void modificarDatosAlumnosByName(MongoCollection<org.bson.Document> coleccion, Scanner sc) {
		Document newDocument = new Document();
		System.out.println("Escribe el nuevo nombre del alumno: ");
		String newNombre = sc.next();
		newDocument.append("$set", new Document().append("nombre", newNombre));
		System.out.println("Escribe el nombre del alumno a modificar: ");
		String oldNombre = sc.next();		
		Document oldDocument = new Document().append("nombre", oldNombre);

		coleccion.updateOne(oldDocument, newDocument);
	}

	/**
	 * Borrar Datos de uno de los documentos insertados en nuestra base de datos, filtrando por su Nombre.
	 * @param coleccion
	 * @param sc
	 */
	public static void borrarDatosAlumnosByName(MongoCollection<org.bson.Document> coleccion, Scanner sc) {
		System.out.println("Escribe el nombre del alumno a eliminar: ");
		String nombre = sc.next();
		coleccion.deleteOne(new Document("nombre", nombre));
	}

	/**
	 * Vocar Datos de uno de los documentos insertados en nuestra base de datos al documento indicado, filtrando por su Nombre.
	 * @param coleccion
	 * @param sc
	 */
	public static void volcarDatosAlumnoAFicheroByName(MongoCollection<org.bson.Document> coleccion, Scanner sc) {
		MongoCursor<Document> cursor = coleccion.find().iterator(); 
		FileWriter fichero = null;
		PrintWriter pw = null;
		try{
			fichero = new FileWriter("C:\\Users\\ERIC\\Desktop\\pruebaMongo\\pruebaMongo.txt");
			pw = new PrintWriter(fichero);

			System.out.println("Escribe el nombre del alumno a volcar al fichero: ");
			String nombre = sc.next();
			while (cursor.hasNext()) {
				Document doc = cursor.next(); 
				if(doc.getString("nombre").equals(nombre)) {
					pw.print(doc.toJson()); 
				}
			} 
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
