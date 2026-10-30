/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Entrenador;
import modelo.Pase;
import modelo.Socio;
import modelo.miConexion;

/**
 *
 * @author estef
 */
public class PaseData {
    private Connection con = null;
    private SocioData socioData;
    
    public PaseData(miConexion conexion){
        this.con = conexion.buscarConexion();
    }
    
    public void guardarPase (Pase p){    
        String sql = "INSERT INTO pase (idSocio, cantidadEntradas, fechaInicio, fechaFin, costo, estado) VALUES (?,?,?,?,?,?)";  
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            ps.setInt(1, p.getSocio().getIdSocio());
            ps.setInt(2, p.getCantidadEntradas());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setDouble(5, p.getCosto());
            ps.setBoolean(6, p.isEstado());
           
            ps.executeUpdate();     
            
            ResultSet rs = ps.getGeneratedKeys(); 
            if(rs.next()){
                p.setIdPase(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Pase guardado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo guardar Pase");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar Pase: "+ex.getMessage()); 
        }
    }
        
    public void modificarPase (Pase p){    
        String sql = "UPDATE pase SET idSocio =?, cantidadEntradas =?, fechaInicio=?, fechaFin=?, costo=?, estado=? WHERE idPase=?";  
        
        try {
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setInt(1, p.getSocio().getIdSocio());
            ps.setInt(2, p.getCantidadEntradas());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setDouble(5, p.getCosto());
            ps.setBoolean(6, p.isEstado());
            ps.setInt(7, p.getIdPase());
            
            int exito= ps.executeUpdate();
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Pase modificado exitosamente");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentró el Pase para modificar");
            }
            ps.close();
        }catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla pase: "+ex.getMessage());
           }
    }
        
    public Pase buscarPasePorSocio (int idSocio){
        Pase p = null;
        
        String sql = "SELECT * FROM pase WHERE idSocio=? AND estado = 1";
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                p= new Pase();
                p.setIdPase(rs.getInt("idPase"));
                
                Socio s = socioData.buscarSocio(idSocio);
                p.setSocio(s);
                
                p.setCantidadEntradas(rs.getInt("cantidadEntradas"));
                p.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                p.setCosto(rs.getDouble("costo"));
                p.setEstado(rs.getBoolean("estado"));
            }else{
                JOptionPane.showMessageDialog(null,"No existe un Pase con ese ID");
            }
            ps.close();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener el pase: "+ e.getMessage());
        }
        return p;
    }
    
    public void darBajaPase (int idPase){  
        String sql = "UPDATE pase SET estado =0 WHERE idPase=?";  
        try {
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setInt(1, idPase);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Pase dado de baja");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra pase con ese ID");
            }
            ps.close();
        }catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla pase");
         }
    }
     
    public void darAltaPase (int idPase){  
        String query = "UPDATE pase SET estado =1 WHERE idPase=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idPase);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Pase dado de alta");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra pase con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla pase");
           }
    }
    
    public void eliminarPase (int idPase){
        String sql = "DELET FROM pase WHERE idPase = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPase);
            int exito = ps.executeUpdate();
            if(exito == 1) {
                JOptionPane.showMessageDialog(null, "Pase eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado un pase con dicho id");
            }
            ps.close();        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla pase"+ ex.getMessage());
        }
    }  
    
    public List <Pase> listarPasesPorEntradas (int cantidadEntradas){     
        String sql = "SELECT p.*, s.nombre, s.apellido, s.dni FROM pase p"
                + "JOIN socio s ON (p.idSocio = s.idSocio) WHERE p.cantidadEntradas =?";
        ArrayList<Pase> pases = new ArrayList<>();
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, cantidadEntradas);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Pase p = new Pase();
                p.setIdPase(rs.getInt("idPase"));
                p.setCantidadEntradas(rs.getInt("cantidadEntradas"));
                p.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                p.setFechaFin(rs.getDate("fechaFin").toLocalDate());
                p.setCosto(rs.getDouble("costo"));
                p.setEstado(rs.getBoolean("estado"));
                
                Socio s = new Socio();
                s.setIdSocio(rs.getInt("idSocio"));
                s.setDni(rs.getString("dni"));
                s.setNombre(rs.getString("nombre"));
                s.setApellido(rs.getString("apellido"));
                
                p.setSocio(s);
                pases.add(p);
            }
            ps.close();
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Error al listar especialidad: "+ ex.getMessage());            
    }
        return pases;
    }
}
