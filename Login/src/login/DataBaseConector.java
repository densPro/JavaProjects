package login;
//Importaciones

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseConector {

    private Connection conexion;

    public DataBaseConector() {
        conexion = null;
    }

    public void connect() {
        if (conexion != null) {
            return;
        }
        String url = "jdbc:postgresql://localhost:5432/loginPassword";
        try {
            Class.forName("org.postgresql.Driver");
            //Establecemos el Usuario y la contraseña
            //Usuario= postgres
            //Contraseña= 123
            conexion = DriverManager.getConnection(url, "postgres", "123");
            if (conexion != null) {
                System.out.println("Connected to Data Base");
            }
        } catch (Exception e) {
            System.out.println("there are a Problem Connection");
        }
    }

    public ResultSet search(String username, String password) {
        ResultSet rs = null;
        Statement s = null;
        try {
            s = conexion.createStatement();
            //seleccionamos la tabla de la base de datos la cual lleva por nombre trabajadores
            rs = s.executeQuery("SELECT username, password "
                              + "FROM \"Customer\" "
                              + "WHERE username = '" + username + "'"
                              + "and  password = '" + password + "'");
        } catch (Exception e) {
            System.out.println("there are a trouble with the query");
        }
        return rs;
    }

    public void closeConection() {
        try {
            conexion.close();
        } catch (Exception e) {
            System.out.println("there are a Problem Connection ");
        }
    }
}
