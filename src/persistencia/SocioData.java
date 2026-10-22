/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.miConexion;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Socio;

/**
 *
 * @author estef
 */
public class SocioData {
     private Connection con = null;
    
    public SocioData(miConexion conexion){
        this.con = conexion.buscarConexion();
    }
    
    public void guardarSocio(Socio s){    
        String sql = "INSERT INTO socio(dni, nombre, apellido, edad, correo, telefono, estado) VALUES (?,?,?,?,?,?,?)";  
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            ps.setString(1, s.getDni());
            ps.setString(2, s.getNombre());
            ps.setString(3, s.getApellido());
            ps.setInt(4, s.getEdad());
            ps.setString(5, s.getCorreo());
            ps.setString(6, s.getTelefono());
            ps.setBoolean(7, s.isEstado());
            ps.executeUpdate();     
            
            ResultSet rs = ps.getGeneratedKeys(); 
            if(rs.next()){
                s.setIdSocio(1);
            }else{
                System.out.println("No se pudo tener ID");
            }
            ps.close();
            System.out.println("Guardado!");
        } catch (SQLException ex) {
            System.out.println("No se puede insertar");    
        }
    }
    
    public void borrarSocio (int id){  //bajaLogica
        String query = "UPDATE socio SET estado =0 WHERE idSocio=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, id);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Socio dado de baja");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra el socio con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla socio");
           }
    }
    
    public void altaSocio (int id){  //bajaLogica
        String query = "UPDATE socio SET estado =1 WHERE idSocio=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, id);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Socio dado de alta");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra el socio con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla socio");
           }
    }
    
    
    
    public void modificarSocio (Socio s){
        String query = "UPDATE socio SET dni=?, nombre =?, apellido =?, edad=?, correo=?, telefono=?, estado=1 WHERE idSocio =?";  
        try {
             PreparedStatement ps = con.prepareStatement(query); 
             ps.setString(1, s.getDni());
             ps.setString(2, s.getNombre());
             ps.setString(3, s.getApellido());
             ps.setInt(4, s.getEdad());
             ps.setString(5, s.getCorreo());
             ps.setString(6, s.getTelefono());
             ps.setInt(7, s.getIdSocio());
             
             int exito = ps.executeUpdate();     
             if(exito==1){
                    JOptionPane.showMessageDialog(null, "Socio modificado");
             }else{
                JOptionPane.showMessageDialog(null, "No se encuentra el socio");
            }
             ps.close();  
        } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla socio");
                }
}

    
}
