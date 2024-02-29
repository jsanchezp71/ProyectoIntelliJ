-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 29-02-2024 a las 17:43:11
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bar`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesas`
--

CREATE TABLE `mesas` (
  `ID_mesa` int(11) NOT NULL,
  `num_mesa` int(11) NOT NULL,
  `precio_total` double DEFAULT NULL,
  `fecha` varchar(255) DEFAULT NULL,
  `hora` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mesas`
--

INSERT INTO `mesas` (`ID_mesa`, `num_mesa`, `precio_total`, `fecha`, `hora`) VALUES
(2, 1, 6, '2024-02-18', '19:07:49'),
(3, 1, 19, '2024-02-20', '17:37:25'),
(4, 1, 9.75, '2024-02-20', '17:37:58'),
(5, 2, 17, '2024-02-20', '17:39:00'),
(6, 5, 6, '2024-02-27', '19:16:28'),
(7, 3, 18.1, '2024-02-27', '19:21:41'),
(8, 3, 14, '2024-02-27', '19:23:04'),
(9, 3, 7.5, '2024-02-27', '19:25:40'),
(10, 3, 8.75, '2024-02-27', '19:26:45'),
(11, 2, 42.6, '2024-02-27', '19:31:15'),
(12, 3, 9, '2024-02-27', '19:32:56'),
(13, 5, 15.25, '2024-02-29', '16:24:27');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mesas`
--
ALTER TABLE `mesas`
  ADD PRIMARY KEY (`ID_mesa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mesas`
--
ALTER TABLE `mesas`
  MODIFY `ID_mesa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
