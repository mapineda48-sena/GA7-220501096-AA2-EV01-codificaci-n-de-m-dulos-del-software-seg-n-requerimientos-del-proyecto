-- Creación de la base de datos AgapeApp
CREATE DATABASE AgapeApp;

-- Uso de la base de datos AgapeApp
USE AgapeApp;

-- Creación de la tabla Personas
CREATE TABLE Personas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    CorreoElectronico VARCHAR(100)
);
