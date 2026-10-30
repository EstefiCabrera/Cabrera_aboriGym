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

/**
 *
 * @author estef
 */
public class ClaseGymData {
    private Connection con = null;
    private EntrenadorData entrenadorData;
    
    public ClaseGymData(miConexion conexion){
        this.con = conexion.buscarConexion();
        //this.entrenadorData= new EntrenadorData(conexion);
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
        String sql = "UPDATE claseGym SET disciplina=?, idEntrenador=?, horario =?, capacidad=?, estado=1 WHERE idClase =?";  
        try {
             PreparedStatement ps = con.prepareStatement(sql); 
             ps.setString(1, c.getDisciplina());
             ps.setInt(2, c.getEntrenador().getIdEntrenador());
             ps.setTime(3, Time.valueOf(c.getHorario()));
             ps.setInt(4, c.getCapacidad());
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
    
    
    
}
