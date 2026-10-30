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
import java.util.ArrayList;
import java.util.List;
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
                JOptionPane.showMessageDialog(null,"Entrenador guardado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"No se pudo guardar entrenador");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador");    
        }
    }
    
    public void bajaEntrenador (int idEntrenador){ 
        String query = "UPDATE entrenador SET estado =0 WHERE idEntrenador=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idEntrenador);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Entrenador dado de baja");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra entrenador con ese ID");
            }
            ps.close();
        }catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador");
         }
    }
     
    public void altaEntrenador (int idEntrenador){  
        String query = "UPDATE entrenador SET estado =1 WHERE idEntrenador=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idEntrenador);
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
     
    public void modificarEntrenador (Entrenador e){
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
      
    public void eliminarEntrenador(int idEntrenador){
        String sql = "DELET FROM entrenador WHERE idEntrenador = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEntrenador);
            int exito = ps.executeUpdate();
            if(exito == 1) {
                JOptionPane.showMessageDialog(null, "Entrenador eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado un entrenador con dicho id");
            }
            ps.close();        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador"+ ex.getMessage());
        }
    }  
      
    public Entrenador buscarEntrenador(int idEntrenador){
        Entrenador e = null;
        
        String sql = "SELECT * FROM entrenador WHERE idEntrenador=?";
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                e= new Entrenador();
                e.setIdEntrenador(rs.getInt("idEntrenador"));
                e.setDni(rs.getString("dni"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setEstado(rs.getBoolean("estado"));
            }else{
                JOptionPane.showMessageDialog(null,"No existe un entrenador con ese ID");
            }
            ps.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla entrenador: "+ ex.getMessage());
        }
        return e;
    }
    
    public List <Entrenador> listarEntrenadorPorEspecialidad (String especialidad){     
        String sql = "SELECT * FROM entrenador WHERE especialidad =? AND estado =1";
        ArrayList<Entrenador> entrenadores = new ArrayList<>();
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1, especialidad);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Entrenador e = new Entrenador();
                e.setIdEntrenador(rs.getInt("idEntrenador"));
                e.setDni(rs.getString("dni"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setEstado(rs.getBoolean("estado"));
                entrenadores.add(e);
            }
            ps.close();
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Error al listar especialidad: "+ ex.getMessage());            
    }
        return entrenadores;
    }
}
