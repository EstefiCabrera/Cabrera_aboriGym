/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author estef
 */
public class miConexion {
        private String url;
    private String usuario; 
    private String password;
    
    private static Connection conexion = null;    
    
    public miConexion(){     
    }
    
    public Connection buscarConexion(){
        if (conexion==null) {  
            try {
              
                Class.forName("org.mariadb.jdbc.Driver");  
                conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/aborigym", "root", "");  
            } catch (SQLException | ClassNotFoundException ex) { 
                System.out.println("No se puede conectar o no se puede cargar el driver. Error: "+ ex.getMessage());
            }
        }
        return conexion; 
    }
  }

