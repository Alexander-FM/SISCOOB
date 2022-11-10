-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-11-2022 a las 05:23:14
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdsiscoob`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `IDEQUIPO` int(11) NOT NULL,
  `CODIGO_PATRIMONIO` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `ORDEN_COMPRA` varchar(500) COLLATE utf8_spanish_ci NOT NULL,
  `SERIE_NUMERO` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `NOMBRE_BIEN` varchar(25) COLLATE utf8_spanish_ci NOT NULL,
  `IDESTADO` int(11) NOT NULL,
  `IDMARCA` int(11) NOT NULL,
  `FECHA_OC` date DEFAULT NULL,
  `FECHA_REGISTRO` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`IDEQUIPO`, `CODIGO_PATRIMONIO`, `ORDEN_COMPRA`, `SERIE_NUMERO`, `NOMBRE_BIEN`, `IDESTADO`, `IDMARCA`, `FECHA_OC`, `FECHA_REGISTRO`) VALUES
(1, '740892000336', '466-2010', 'DFXNJN1', 'SERVIDOR', 1, 4, '2022-10-08', '2022-10-22'),
(2, '740892000370', '504-2010', 'JKLNQN1', 'SERVIDOR', 2, 3, '2022-10-05', '2022-10-22'),
(7, '33832832832', '2983-24', 'DILKA8', 'LAPTOP', 1, 1, '2022-10-23', '2022-10-29'),
(10, '243523452345234', '567-654', '4JJN3ND', 'PROYECTOR', 4, 1, '2022-11-09', '2022-11-09'),
(11, '3223232323', '983-2022', '4FO3HJF', 'PROYECTOR POWER', 2, 3, '2022-11-09', '2022-11-09'),
(12, '1418418320', '342-2022', '3I0J32J', 'TELEVISOR 65\"', 4, 3, '2022-11-09', '2022-11-09'),
(13, '425242452', '463-2021', '5645EE', 'TELEVISOR 75\"', 1, 3, '2022-11-09', '2022-11-09'),
(14, '4343434353', '343-565', '894F87H', 'MICROFONO', 2, 3, '2022-11-09', '2022-11-09');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `IDESTADO` int(11) NOT NULL,
  `ESTADO` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`IDESTADO`, `ESTADO`) VALUES
(1, 'Inoperativo mantenimiento correctivo'),
(2, 'Inoperativo obsolescencia tecnica'),
(3, 'Operativo obsolescencia tecnica'),
(4, 'Operativo sin observaciones'),
(5, 'Sin Revisar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ficha`
--

CREATE TABLE `ficha` (
  `IDFICHA` int(11) NOT NULL,
  `NRO_FICHA` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `IDPERSONA` int(11) NOT NULL,
  `IDUSUARIO` int(11) NOT NULL,
  `FECHA_CREACION` date NOT NULL,
  `ESTADO` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fichadetalle`
--

CREATE TABLE `fichadetalle` (
  `IDFICHADETALLE` int(11) NOT NULL,
  `IDEQUIPO` int(11) NOT NULL,
  `IDFICHA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `IDMARCA` int(11) NOT NULL,
  `MARCA` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`IDMARCA`, `MARCA`) VALUES
(1, 'DELL'),
(2, 'SAMSUNG'),
(3, 'LG'),
(4, 'LENOVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `IDPERSONA` int(11) NOT NULL,
  `NOMBRES` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `APELLIDOS` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `IDROL` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`IDPERSONA`, `NOMBRES`, `APELLIDOS`, `IDROL`) VALUES
(1, 'Esmeralda', 'Hernandez Torres', 3),
(2, 'Alexander', 'Fuentes Medina', 8),
(3, 'Carlos', 'Sanchez Oliva', 8),
(6, 'Rodrigo', 'Suarez Pastor', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `IDROL` int(11) NOT NULL,
  `ROL` varchar(100) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`IDROL`, `ROL`) VALUES
(1, 'Registrador'),
(2, 'Verificador'),
(3, 'Consolidador'),
(5, 'Coordinador'),
(6, 'Operador GAD'),
(7, 'Operador GITE'),
(8, 'Técnico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `IDUSUARIO` int(11) NOT NULL,
  `USUARIO` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `CLAVE` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `ESTADO` bit(1) NOT NULL,
  `IDPERSONA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`IDUSUARIO`, `USUARIO`, `CLAVE`, `ESTADO`, `IDPERSONA`) VALUES
(1, 'admin', 'admin', b'1', 1),
(5, 'admin', 'admin123', b'1', 6);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`IDEQUIPO`),
  ADD KEY `FK_EQUIPO_ESTADO` (`IDESTADO`),
  ADD KEY `FK_EQUIPO_MARCA` (`IDMARCA`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`IDESTADO`);

--
-- Indices de la tabla `ficha`
--
ALTER TABLE `ficha`
  ADD PRIMARY KEY (`IDFICHA`),
  ADD KEY `FK_FICHA_USUARIO` (`IDUSUARIO`),
  ADD KEY `FK_FICHA_PERSONA` (`IDPERSONA`);

--
-- Indices de la tabla `fichadetalle`
--
ALTER TABLE `fichadetalle`
  ADD PRIMARY KEY (`IDFICHADETALLE`),
  ADD KEY `fk_fichadetallle_equipo` (`IDEQUIPO`),
  ADD KEY `fk_fichadetallle_ficha` (`IDFICHA`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`IDMARCA`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`IDPERSONA`),
  ADD KEY `FK_PERSONA_ROL` (`IDROL`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`IDROL`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`IDUSUARIO`),
  ADD KEY `FK_USUARIO_PERSONA` (`IDPERSONA`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `IDEQUIPO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `IDESTADO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ficha`
--
ALTER TABLE `ficha`
  MODIFY `IDFICHA` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `fichadetalle`
--
ALTER TABLE `fichadetalle`
  MODIFY `IDFICHADETALLE` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `IDMARCA` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `IDPERSONA` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `IDROL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `IDUSUARIO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `FK_EQUIPO_ESTADO` FOREIGN KEY (`IDESTADO`) REFERENCES `estado` (`IDESTADO`),
  ADD CONSTRAINT `FK_EQUIPO_MARCA` FOREIGN KEY (`IDMARCA`) REFERENCES `marca` (`IDMARCA`);

--
-- Filtros para la tabla `ficha`
--
ALTER TABLE `ficha`
  ADD CONSTRAINT `FK_FICHA_PERSONA` FOREIGN KEY (`IDPERSONA`) REFERENCES `persona` (`IDPERSONA`),
  ADD CONSTRAINT `FK_FICHA_USUARIO` FOREIGN KEY (`IDUSUARIO`) REFERENCES `usuario` (`IDUSUARIO`);

--
-- Filtros para la tabla `fichadetalle`
--
ALTER TABLE `fichadetalle`
  ADD CONSTRAINT `fk_fichadetallle_equipo` FOREIGN KEY (`IDEQUIPO`) REFERENCES `equipo` (`IDEQUIPO`),
  ADD CONSTRAINT `fk_fichadetallle_ficha` FOREIGN KEY (`IDFICHA`) REFERENCES `ficha` (`IDFICHA`);

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `FK_PERSONA_ROL` FOREIGN KEY (`IDROL`) REFERENCES `rol` (`IDROL`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK_USUARIO_PERSONA` FOREIGN KEY (`IDPERSONA`) REFERENCES `persona` (`IDPERSONA`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
