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
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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

    public void consultarHistorialSocio(DefaultTableModel modeloTabla, int idSocio, JLabel lblConsumidas, JLabel lblRestantes){
        modeloTabla.setRowCount(0);
        String sqlHistorial = "SELECT a.fechaAsistencia, c.disciplina, c. horario "
                            + "FROM asistencia a "
                            + "JOIN clasegym c ON a.idClase = c.idClase "
                            + "WHERE a.idSocio = ? "
                            + "ORDER BY a.fechaAsistencia DESC ";
        String sqlPase = "SELECT cantidadEntradas FROM pase WHERE idSocio =? AND estado = 1";

        int entradasConsumidas =0;
        int entradasRestantes =0;

        try{
            PreparedStatement psH = con.prepareStatement(sqlHistorial);
            psH.setInt(1, idSocio);
            ResultSet rsH = psH.executeQuery();

            while(rsH.next()){
                entradasConsumidas++;
                String horaFormateada = rsH.getTime("horario").toString().substring(0, 5);

                modeloTabla.addRow(new Object[]{rsH.getDate("fechaAsistencia").toString(), rsH.getString("disciplina"), horaFormateada + " hs"});            
            }
            psH.close();

            PreparedStatement psP = con.prepareStatement(sqlPase);
            psP.setInt(1, idSocio);
            ResultSet rsP = psP.executeQuery();
                if(rsP.next()){
                    entradasRestantes = rsP.getInt("cantidadEntradas");               
                }
                psP.close();
                lblConsumidas.setText("Entradas consumidas: "+entradasConsumidas);
                lblRestantes.setText("Entradas Restantes del Pase actual: "+entradasRestantes);       
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al consultar historial del socio: "+ ex.getMessage());
        }     
      }

    public void informeConcurrencia(DefaultTableModel modeloTabla, int idClase, LocalDate fecha, JLabel lblTotal){
        modeloTabla.setRowCount(0);

        String sql = "SELECT s.idSocio, s.dni, s.apellido, s.nombre "
                   + "FROM asistencia a "
                   + "JOIN socio s ON a.idSocio = s.idSocio "
                   + "WHERE a.idClase =? AND a.fechaAsistencia =? "
                   + "ORDER BY s.apellido ASC ";
        int contadorAlumno =0;

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClase);
            ps.setDate(2, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contadorAlumno++;

                modeloTabla.addRow(new Object[]{rs.getInt("idSocio"), rs.getString("dni"), rs.getString("apellido"), rs.getString("nombre")});
            }
            ps.close();
            lblTotal.setText("Concurrencia total: "+contadorAlumno + " alumnos.");
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al generar informe de concurrencia"+ex.getMessage());
        }
    }

    public Asistencia buscarAsistencia(int idAsistencia){
        Asistencia a = null;
        String sql = "SELECT * FROM asistencia WHERE idAsistencia=?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAsistencia);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                a = new Asistencia();
                a.setIdAsistencia(rs.getInt("idAsistencia"));
                a.setSocio(socioData.buscarSocioPorId(rs.getInt("idSocio")));
                a.setClase(claseGymData.buscarClase(rs.getInt("idClase")));
                a.setFechaAsistencia(rs.getDate("fechaAsistencia").toLocalDate());              
            }else{
                JOptionPane.showMessageDialog(null, "No existe una asistencia con ese ID");
            }
            ps.close();
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Error al buscar asistencia: "+ex.getMessage());
        }
        return a;
    }
    
    public void modificarAsistencia(Asistencia a){
        String sql = "UPDATE asistencia SET idSocio =?, idClase =?, fechaAsistencia =? WHERE idAsistencia =?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a.getSocio().getIdSocio());
            ps.setInt(2, a.getClase().getIdClase());
            ps.setDate(3, Date.valueOf(a.getFechaAsistencia()));
            ps.setInt(4, a.getIdAsistencia());
            
            int exito = ps.executeUpdate();
            if(exito ==11){
                JOptionPane.showMessageDialog(null, "Asistencia modificada con éxito");
            }
            ps.close();
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Error al modificar asistencia: "+ex.getMessage());
        }
    }
    
    public void eliminarAsistencia (int idAsistencia, int idSocio){
        String sql = "DELETE FROM asistencia WHERE idAsistencia = ?";
        String sqlDevolucion = "UPDATE pase SET cantidadEntradas = cantidadEntradas +1 WHERE idSocio=? AND estado =1";
        
        try{
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAsistencia);
            int exito = ps.executeUpdate();
            ps.close();         
            if(exito >0) {
                PreparedStatement psD = con.prepareStatement(sqlDevolucion);
                psD.setInt(1, idSocio);
                psD.executeUpdate();
                psD.close();
                con.commit();
                JOptionPane.showMessageDialog(null, "Asistencia eliminada y entrada devuelta al pase del socio");
            }else{
                con.rollback();
                JOptionPane.showMessageDialog(null, "No se ha encontrado la asistencia para eliminar");
            }  
        }catch(SQLException ex){
            try{ con.rollback();} catch(SQLException e){}
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla asistencia"+ ex.getMessage());
        }finally{
            try{ con.setAutoCommit(true);} catch(SQLException e){}
        }
    }
    
    public int contarInscriptos(int idClase, LocalDate fecha){
        int total = 0;
        String sql = "SELECT COUNT(*) FROM asistencia WHERE idClase =? AND fechaAsistencia =? ";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idClase);
            ps.setDate(2, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt(1);
            }
            ps.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al contar inscriptos: "+ ex.getMessage());
        }
        return total;
    }
   
}
