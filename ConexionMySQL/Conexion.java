package ConexionMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection linea = null;
    
    public Connection Conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            linea = DriverManager.getConnection("jdbc:mysql://localhost:3306/vgs", "root", "");
        } catch (SQLException sql) {
            System.out.println("Error " + sql);
        } catch (ClassNotFoundException ex) {
            System.out.println("Clase no encontrada " + ex);
        }
        
        return linea;
    }
    
    public void Desconectar() {
        if(linea != null){
            try {
                if(!linea.isClosed()){
                    linea.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al desconectar " + ex);
            }
        }
    }   
}
