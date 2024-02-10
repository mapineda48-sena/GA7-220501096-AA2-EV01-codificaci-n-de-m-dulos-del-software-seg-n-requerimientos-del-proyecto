/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicioconexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author win
 */
public class EjercicioConexion {

    private static final String URL = "jdbc:mysql://localhost:3306/AgapeApp";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "tu_contraseña";  // Reemplaza con tu contraseña real

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        crearNuevaPersona("Miguel", "Pineda", "1993-12-14", "mapineda48@soy.sena.edu.co");
        
        consultarPersonas();
        
        actualizarNombrePersonas("AprendizSENA");
        
        consultarPersonas();
        
        eliminarTodasLasPersonas();
        
        consultarPersonas();
    }

    public static void crearNuevaPersona(String nombre, String apellido, String fechaNacimiento, String correoElectronico) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);

            // Crear la sentencia SQL para insertar datos
            String sql = "INSERT INTO Personas (Nombre, Apellido, FechaNacimiento, CorreoElectronico) VALUES (?, ?, ?, ?)";

            // Preparar la sentencia
            pstmt = conn.prepareStatement(sql);

            // Insertar el primer registro
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setDate(3, java.sql.Date.valueOf(fechaNacimiento));
            pstmt.setString(4, correoElectronico);
            pstmt.executeUpdate();

            System.out.println("Registros insertados con éxito.");

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos");
            e.printStackTrace();
        } finally {
            // Cerrar los recursos en orden inverso a su apertura
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión");
                e.printStackTrace();
            }
        }
    }

    public static void consultarPersonas() {
        String sql = "SELECT * FROM Personas";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql); var rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID")
                        + ", Nombre: " + rs.getString("Nombre")
                        + ", Apellido: " + rs.getString("Apellido")
                        + ", Fecha de Nacimiento: " + rs.getDate("FechaNacimiento")
                        + ", Correo Electrónico: " + rs.getString("CorreoElectronico"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void actualizarNombrePersonas(String nombre) {
        String sql = "UPDATE Personas SET Nombre = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Persona actualizada con éxito.");
            } else {
                System.out.println("No se encontró un registro con el ID proporcionado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void eliminarTodasLasPersonas() {
        String sql = "DELETE FROM Personas";
        
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int affectedRows = pstmt.executeUpdate();

            System.out.println(affectedRows + " registros eliminados con éxito.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
