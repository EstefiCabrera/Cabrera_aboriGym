/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cabrera_aborigym;

import java.time.LocalDate;
import java.time.Month;
import modelo.Entrenador;
import modelo.Pase;
import modelo.Socio;
import modelo.miConexion;
import persistencia.EntrenadorData;
import persistencia.PaseData;
import persistencia.SocioData;


/**
 *
 * @author estef
 */
public class Cabrera_aboriGym {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Socio juan = new Socio("34899874", "Juan", "Sosa", 20, "juan@gmail.com", "2664305896", true);
        miConexion conexion = new miConexion();
        SocioData soc = new SocioData(conexion);
        Socio pedro = new Socio("23456789", "Pedro", "Gutierrez", 19, "pedro@gmail.com", "2665987894", true);
        //soc.guardarSocio(pedro);
        //soc.guardarSocio(juan);
        
        Entrenador pablo = new Entrenador("30456987", "Pablo", "Perez", "Crossfit", true);
        Entrenador ale = new Entrenador("20654789", "Alejandro", "Gutierrez", "Musculación", true);
        EntrenadorData ent = new EntrenadorData(conexion);
        //ent.guardarEntrenador(pablo);
        //soc.bajaSocio(2);
        //soc.altaSocio(2);
        //juan.setIdSocio(1);
        //juan.setTelefono("2664000000");
        //soc.modificarSocio(juan);
        //ent.guardarEntrenador(ale);
        //ent.bajantrenador(3);
        //ent.altaEntrenador(3);
        //ale.setIdEntrenador(3);
        //ale.setNombre("Mario Alejandro");
        //ent.modificarSocio(ale);
        
        PaseData pd = new PaseData(conexion);
        Pase pase1 = new Pase(pedro, 12, LocalDate.now(), LocalDate.of(2026, 11, 30), 30.000, true);
        pd.guardarPase(pase1);
        
    }    
}
