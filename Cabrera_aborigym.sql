-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-10-2026 a las 23:29:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `aborigym`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `idSocio` int(11) NOT NULL,
  `idClase` int(11) NOT NULL,
  `fechaAsistencia` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clasegym`
--

CREATE TABLE `clasegym` (
  `idClase` int(11) NOT NULL,
  `disciplina` varchar(50) NOT NULL,
  `idEntrenador` int(11) NOT NULL,
  `horario` time NOT NULL,
  `capacidad` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenador`
--

CREATE TABLE `entrenador` (
  `idEntrenador` int(11) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `especialidad` varchar(50) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `entrenador`
--

INSERT INTO `entrenador` (`idEntrenador`, `dni`, `nombre`, `apellido`, `especialidad`, `estado`) VALUES
(1, '30456987', 'Pablo', 'Perez', 'Crossfit', 1),
(3, '20654789', 'Mario Alejandro', 'Gutierrez', 'Musculación', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pase`
--

CREATE TABLE `pase` (
  `idPase` int(11) NOT NULL,
  `idSocio` int(11) NOT NULL,
  `cantidadEntradas` int(11) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `costo` decimal(10,0) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socio`
--

CREATE TABLE `socio` (
  `idSocio` int(11) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `edad` int(11) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `socio`
--

INSERT INTO `socio` (`idSocio`, `dni`, `nombre`, `apellido`, `edad`, `correo`, `telefono`, `estado`) VALUES
(1, '34899874', 'Juan', 'Sosa', 20, 'juan@gmail.com', '2664000000', 1),
(2, '23456789', 'Pedro', 'Gutierrez', 19, 'pedro@gmail.com', '2665987894', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`),
  ADD KEY `idSocio` (`idSocio`),
  ADD KEY `idClase` (`idClase`);

--
-- Indices de la tabla `clasegym`
--
ALTER TABLE `clasegym`
  ADD PRIMARY KEY (`idClase`),
  ADD KEY `idEntrenador` (`idEntrenador`);

--
-- Indices de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  ADD PRIMARY KEY (`idEntrenador`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `pase`
--
ALTER TABLE `pase`
  ADD PRIMARY KEY (`idPase`),
  ADD UNIQUE KEY `idSocio` (`idSocio`);

--
-- Indices de la tabla `socio`
--
ALTER TABLE `socio`
  ADD PRIMARY KEY (`idSocio`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `clasegym`
--
ALTER TABLE `clasegym`
  MODIFY `idClase` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  MODIFY `idEntrenador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pase`
--
ALTER TABLE `pase`
  MODIFY `idPase` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `socio`
--
ALTER TABLE `socio`
  MODIFY `idSocio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `asistencia_ibfk_1` FOREIGN KEY (`idSocio`) REFERENCES `socio` (`idSocio`),
  ADD CONSTRAINT `asistencia_ibfk_2` FOREIGN KEY (`idClase`) REFERENCES `clasegym` (`idClase`);

--
-- Filtros para la tabla `clasegym`
--
ALTER TABLE `clasegym`
  ADD CONSTRAINT `clasegym_ibfk_1` FOREIGN KEY (`idEntrenador`) REFERENCES `entrenador` (`idEntrenador`);

--
-- Filtros para la tabla `pase`
--
ALTER TABLE `pase`
  ADD CONSTRAINT `pase_ibfk_1` FOREIGN KEY (`idSocio`) REFERENCES `socio` (`idSocio`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
