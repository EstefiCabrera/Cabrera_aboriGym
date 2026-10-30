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
import java.util.ArrayList;
import java.util.List;
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
                JOptionPane.showMessageDialog(null, "Socio guardado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"No se pudo guardar socio");
            }
            ps.close();;
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"Error al acceder a la tabla socio");    
        }
    }
    
    public void bajaSocio (int idSocio){  
        String query = "UPDATE socio SET estado =0 WHERE idSocio=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idSocio);
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
    
    public void altaSocio (int idSocio){  
        String query = "UPDATE socio SET estado =1 WHERE idSocio=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idSocio);
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
    
    public Socio buscarSocio(int idSocio){
        Socio s = null;
        
        String sql = "SELECT * FROM socio WHERE idSocio=?";
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                s= new Socio();
                s.setIdSocio(rs.getInt("idSocio"));
                s.setDni(rs.getString("dni"));
                s.setNombre(rs.getString("nombre"));
                s.setApellido(rs.getString("apellido"));
                s.setEdad(rs.getInt("edad"));
                s.setCorreo(rs.getString("correo"));
                s.setTelefono(rs.getString("telefono"));
                s.setEstado(rs.getBoolean("estado"));
            }else{
                JOptionPane.showMessageDialog(null,"No existe un socio con ese ID");
            }
            ps.close();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla socio: "+ e.getMessage());
        }
        return s;
    }
    
    public void eliminarSocio(int idSocio){
        String sql = "DELET FROM socio WHERE idSocio = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idSocio);
            int exito = ps.executeUpdate();
            if(exito == 1) {
                JOptionPane.showMessageDialog(null, "Socio eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado un socio con dicho id");
            }
            ps.close();        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla socio"+ ex.getMessage());
        }
    }  
     
    public List <Socio> listarSocioPorClase(int idClase){     
        String sql = "SELECT s.idSocio s.dni, s.nombre, s.apellido, s.edad, s.correo, s.telefono, s.estado "
                + "FROM asistencia a "
                + "INNER JOIN socio s ON (a.idSocio = s.idSocio " 
                + "WHERE s.idClase = ? AND s.estado=1";
        ArrayList<Socio> socios = new ArrayList<>();
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, idClase);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Socio s = new Socio();
                s.setIdSocio(rs.getInt("idSocio"));
                s.setDni(rs.getString("dni"));
                s.setNombre(rs.getString("nombre"));
                s.setApellido(rs.getString("apellido"));
                s.setEdad(rs.getInt("edad"));
                s.setCorreo(rs.getString("correo"));
                s.setTelefono(rs.getString("telefono"));
                s.setEstado(rs.getBoolean("estado"));
                socios.add(s);
            }
            ps.close();
        }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al listar socios por clase: "+ e.getMessage());            
    }
        return socios;
    }
    
    
}   
