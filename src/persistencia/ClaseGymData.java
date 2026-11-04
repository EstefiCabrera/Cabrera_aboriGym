/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import modelo.ClaseGym;
import modelo.miConexion;
import java.sql.Time;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Entrenador;

/**
 *
 * @author estef
 */
public class ClaseGymData {
    private Connection con = null;
    private EntrenadorData entrenadorData;
    
    public ClaseGymData(miConexion conexion){
        this.con = conexion.buscarConexion();
        this.entrenadorData = entrenadorData;
    }
    
    public void guardarClaseGym (ClaseGym c){    
        String sql = "INSERT INTO claseGym (disciplina, idEntrenador, horario, capacidad, estado) VALUES (?,?,?,?,?)";  
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            ps.setString(1, c.getDisciplina());
            ps.setInt(2, c.getEntrenador().getIdEntrenador());
            ps.setTime(3, Time.valueOf(c.getHorario()));
            ps.setInt(4, c.getCapacidad());
            ps.setBoolean(5, c.isEstado());
            ps.executeUpdate();     
            
            ResultSet rs = ps.getGeneratedKeys(); 
            if(rs.next()){
                c.setIdClase(rs.getInt(1));
                JOptionPane.showMessageDialog(null,"Clase guardada exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"No se pudo guardar la clase");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al acceder a la tabla claseGym: "+ex.getMessage());    
        }
    }
    
    public void modificarClaseGym (ClaseGym c){
        String sql = "UPDATE claseGym SET disciplina=?, idEntrenador=?, horario =?, capacidad=?, estado=? WHERE idClase =? AND estado=1";  
        try {
             PreparedStatement ps = con.prepareStatement(sql); 
             ps.setString(1, c.getDisciplina());
             ps.setInt(2, c.getEntrenador().getIdEntrenador());
             ps.setTime(3, Time.valueOf(c.getHorario()));
             ps.setInt(4, c.getCapacidad());;
             ps.setBoolean(5, c.isEstado());
             ps.setInt(5, c.getIdClase());
             
             int exito = ps.executeUpdate();     
             if(exito==1){
                    JOptionPane.showMessageDialog(null, "Clase actualizada exitosamente");
             }else{
                JOptionPane.showMessageDialog(null, "No se encuentra la clase");
            }
             ps.close();  
        } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla claseGym");
                }
    }
    
    public List<ClaseGym> listarClaseGymPorDisciplina (String disciplina){
        List<ClaseGym> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM claseGym WHERE disciplina=? AND estado = 1";
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString (1, disciplina);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                ClaseGym c = new ClaseGym();
                c.setIdClase(rs.getInt("idClase"));
                c.setDisciplina(rs.getString("disciplina"));
                c.setHorario(rs.getTime("horario").toLocalTime());
                c.setCapacidad(rs.getInt("capacidad"));
                c.setEstado(rs.getBoolean("estado"));             
                
                int idEntrenador = rs.getInt("idEntrenador");
                Entrenador e = entrenadorData.buscarEntrenador(idEntrenador);
                c.setEntrenador(e);
                lista.add(c);              
            }
            if(lista.isEmpty()){
                JOptionPane.showMessageDialog(null,"No existe un clase con esa disciplina");
            }
            ps.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al listar clases: "+ ex.getMessage());
        }
        return lista;
    }
    
    public void bajaClase (int idClase){ 
        String query = "UPDATE claseGym SET estado =0 WHERE idClase=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idClase);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Clase dada de baja");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra clase con ese ID");
            }
            ps.close();
        }catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla clase");
         }
    }
     
    public void altaClase (int idClase){  
        String query = "UPDATE claseGym SET estado =1 WHERE idClase=?";  
        try {
            PreparedStatement ps = con.prepareStatement(query); 
            ps.setInt(1, idClase);
            int exito = ps.executeUpdate();    
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Clase dada de alta");
           }else{
                JOptionPane.showMessageDialog(null, "No se encuentra clase con ese ID");
            }
            ps.close();
      } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "Error al acceder a la tabla clase");
           }
    }
    
    public void eliminarClase (int idClase){
        String sql = "DELETE FROM claseGym WHERE idClase = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClase);
            int exito = ps.executeUpdate();
            if(exito == 1) {
                JOptionPane.showMessageDialog(null, "Clae eliminado correctamente");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado una clase con dicho id");
            }
            ps.close();        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla clase"+ ex.getMessage());
        }
    }  
    
    public ClaseGym buscarClase(int idClase){
        ClaseGym c = null;
        
        String sql = "SELECT * FROM classeGym WHERE idClase=?";
        
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, idClase);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                c= new ClaseGym();
                c.setIdClase(rs.getInt("idClase"));
                c.setDisciplina(rs.getString("disciplina"));
                c.setHorario(rs.getTime("horario").toLocalTime());
                c.setCapacidad(rs.getInt("capacidad"));
                c.setEstado(rs.getBoolean("estado"));
                
                int idEntrenador = rs.getInt("idEntrenador");
                Entrenador e = entrenadorData.buscarEntrenador(idEntrenador);
                c.setEntrenador(e);
            }else{
                JOptionPane.showMessageDialog(null,"No existe una clase con esa disciplina");
            }
            ps.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla clase: "+ ex.getMessage());
        }
        return c;
    }
    
    public boolean comprobarSuperposicion(int idEntrenador, int hora) {
        boolean ocupado = false;
        String sql = "SELECT COUNT(*) FROM claseGym WHERE idEntrenador =? AND HOUR(horario) =? AND estado =1;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ps.setInt(2, hora);
            ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    ocupado = true;
                }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al validar horario: " + ex.getMessage());
        }
        return ocupado;
    }
}
