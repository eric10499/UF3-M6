import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.exist.ExistXQDataSource;

public class ejercicio4XQJ {

	public static void main(String[] args) throws IOException {
		try{XQDataSource server = new ExistXQDataSource();
		server.setProperty ("serverName", "192.168.56.102");
		server.setProperty ("port","8080");
		server.setProperty ("user","admin");
		server.setProperty ("password","austria");
		XQConnection conn = server.getConnection();
		XQPreparedExpression consulta;
		XQResultSequence resultado;
		BufferedWriter writer;
		Scanner scanner = new Scanner(System.in);
		int codZona = scanner.nextInt();
		consulta = conn.prepareExpression ( 
				"for $zona in /productos/produc[cod_zona=" + codZona + "] let $cod_prod:=$zona/cod_prod/text() let $denominacion:=$zona/denominacion/text() let $precio:=$zona/precio/text() let $stock:=$zona/(stock_actual - stock_minimo) let $nombre:=/zonas/zona[cod_zona=" + codZona + "]/nombre/text()let $director:=/zonas/zona[cod_zona=" + codZona + "]/director/text()return <zona10><cod_prod>{$cod_prod}</cod_prod><denominacion>{$denominacion}</denominacion><precio>{$precio}</precio><stock>{$stock}</stock><nombre>{$nombre}</nombre><director>{$director}</director></zona10>");

		resultado = consulta.executeQuery();
		File file = new File("/home/cf17eric.visier/Escriptori/zona" + codZona + ".xml");
		writer = new BufferedWriter(new FileWriter(file));
		writer.write("<?xml version='1.0' encoding='UTF-8'?>");
		writer.newLine();
		while (resultado.next()) {
			String cad = resultado.getItemAsString(null);
			writer.write(cad);
			writer.newLine();}
		writer.close();
		
		conn.close();
	} catch (XQException ex) {
		System.out.println("Error al operar"+ex.getMessage());}

	}
}
