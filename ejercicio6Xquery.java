import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;
public class ejercicio6Xquery {
	public static void main(String[] args) {
		try {
			XQDataSource server = new ExistXQDataSource();
			server.setProperty ("serverName", "192.168.56.102");
			server.setProperty ("port","8080");
			server.setProperty ("user","admin");
			server.setProperty ("password","austria");
			// Configuramos conexión como hemos hecho en ocasiones anteriores 
			XQConnection conn = server.getConnection();
			XQExpression consulta = conn.createExpression();
			// Ejecutamos la expresión XQuery: actualiza el apellido del empleado con EMP_NO=7369 a 1009
			String insertar = "update insert " +" <DEP_ROW><DEPT_NO>50</DEPT_NO><DNOMBRE>INFORMÁTICA</DNOMBRE><LOC>Valencia</LOC></DEP_ROW>" +" into /departamentos";
			consulta.executeCommand(insertar);
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
}
