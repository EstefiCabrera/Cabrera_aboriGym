/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cabrera_aborigym;

import modelo.Entrenador;
import modelo.Socio;
import modelo.miConexion;
import persistencia.EntrenadorData;
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
        //soc.borrarSocio(2);
        //soc.altaSocio(2);
        //juan.setIdSocio(1);
        //juan.setTelefono("2664000000");
        //soc.modificarSocio(juan);
        //ent.guardarEntrenador(ale);
        //ent.borrarEntrenador(3);
        //ent.altaEntrenador(3);
        ale.setIdEntrenador(3);
        ale.setNombre("Mario Alejandro");
        ent.modificarSocio(ale);
    }
    
    
}
