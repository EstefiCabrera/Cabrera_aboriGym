/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.Asistencia;
import modelo.miConexion;

/**
 *
 * @author estef
 */
public class AsistenciaData {
    private Connection con = null;
    private SocioData socioData;
    private ClaseGymData claseGymData;
    
    public AsistenciaData(miConexion conexion){
        this.con = conexion.buscarConexion();
        this.socioData = new SocioData(conexion);
        this.claseGymData = new ClaseGymData(conexion);
    }
    
public void registrarAsistencia(Asistencia a){
    String sqlAsist = "INSERT INTO asistencia (idSocio, idClase, fechaAsistencia) VALUES (?,?,?)";
    //descuento del pase
    String sqlPase = "UPDATE pase SET cantidadEntradas = cantidadEntradas-1 WHERE idSocio=? AND estado =1 AND cantidadEntradas>0";
    
    try{
        con.setAutoCommit(false); //incio de transaccion por seguridad
        
        PreparedStatement psA = con.prepareStatement(sqlAsist, Statement.RETURN_GENERATED_KEYS); 
        psA.setInt(1, a.getSocio().getIdSocio());
        psA.setInt(2, a.getClase().getIdClase());
        psA.setDate(3, Date.valueOf(a.getFechaAsistencia()));
        psA.executeUpdate();
        
        ResultSet rs = psA.getGeneratedKeys();
        if(rs.next()){
            a.setIdAsistencia(rs.getInt(1));
        }
        psA.close();
        
        PreparedStatement psP = con.prepareStatement(sqlPase, Statement.RETURN_GENERATED_KEYS);
        psP.setInt(1, a.getSocio().getIdSocio());
        int filasAfectadas = psP.executeUpdate();
        psP.close();
        
        if(filasAfectadas >0){
            con.commit(); //para ver si salio bien y guardo los cambios
            JOptionPane.showMessageDialog(null, "Asistencia registrada con número de ID: "+a.getIdAsistencia()+ ". Entrada descontada");            
        }else {
            con.rollback(); //si el socio no tenia pase activo o se quedo sin entradas
            JOptionPane.showMessageDialog(null, "Error el socio no posee pase activo con entradas disponibles");  
        }
    }catch (SQLException ex){
        try{con.rollback();}catch(SQLException e){}
        JOptionPane.showMessageDialog(null, "Error en la base de datos al registrar asistencia: "+ ex.getMessage());
    }finally{
        try{con.setAutoCommit(true);} catch (SQLException e){}
    }
}


}
    



