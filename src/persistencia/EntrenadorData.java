/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import modelo.Entrenador;
import modelo.miConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author estef
 */
public class EntrenadorData {
    private Connection con = null;
    
    public EntrenadorData(miConexion conexion){
        this.con = conexion.buscarConexion();
    }
    
    public void guardarEntrenador (Entrenador e){    
        String sql = "INSERT INTO entrenador (dni, nombre, apellido, especialidad, estado) VALUES (?,?,?,?,?)";  
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            ps.setString(1, e.getDni());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getApellido());
            ps.setString(4, e.getEspecialidad());
            ps.setBoolean(5, e.isEstado());
            ps.executeUpdate();     
            
            ResultSet rs = ps.getGeneratedKeys(); 
            if(rs.next()){
                e.setIdEntrenador(1);
            }else{
                System.out.println("No se pudo tener ID");
            }
            ps.close();
            System.out.println("Guardado!");
        } catch (SQLException ex) {
            System.out.println("No se puede insertar");    
        }
    }
    
     public void borrarEntrenador (int id){  //bajaLogica
        String query = "UPDATE entrenador SET estado =0 WHERE idEntrenador=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, id);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Entrenador dado de baja");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra entrenador con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador");
           }
    }
     
     public void altaEntrenador (int id){  //bajaLogica
        String query = "UPDATE entrenador SET estado =1 WHERE idEntrenador=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, id);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Entrenador dado de alta");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra entrenador con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador");
           }
    }
     
      public void modificarSocio (Entrenador e){
        String query = "UPDATE entrenador SET dni=?, nombre =?, apellido =?, especialidad=?, estado=1 WHERE idEntrenador =?";  
        try {
             PreparedStatement ps = con.prepareStatement(query); 
             ps.setString(1, e.getDni());
             ps.setString(2, e.getNombre());
             ps.setString(3, e.getApellido());
             ps.setString(4, e.getEspecialidad());

             ps.setInt(5, e.getIdEntrenador());
             
             int exito = ps.executeUpdate();     
             if(exito==1){
                    JOptionPane.showMessageDialog(null, "Entrenador modificado");
             }else{
                JOptionPane.showMessageDialog(null, "No se encuentra el entrenador");
            }
             ps.close();  
        } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador");
                }
}

}
