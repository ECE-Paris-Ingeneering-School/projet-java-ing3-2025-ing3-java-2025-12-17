-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 17, 2025 at 10:30 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projet_java`
--

CREATE DATABASE IF NOT EXISTS `projet_java` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `projet_java`;

DROP TABLE IF EXISTS `attraction`;
DROP TABLE IF EXISTS `reservation`;
DROP TABLE IF EXISTS `client`;


-- --------------------------------------------------------

--
-- Table structure for table `attraction`
--

CREATE TABLE `attraction` (
                              `IdAttraction` int(50) NOT NULL,
    `Type` varchar(50) NOT NULL,
    `Nom` varchar(50) DEFAULT NULL,
    `imagePath` varchar(255) NOT NULL,
    `PrixComplet` double DEFAULT NULL,
    `PrixHabitué` double DEFAULT NULL,
    `PrixJeune` double DEFAULT NULL,
    `PrixSenior` double DEFAULT NULL,
    `Description` varchar(500) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attraction`
--

INSERT INTO `attraction` (`IdAttraction`, `Type`, `Nom`, `imagePath`, `PrixComplet`, `PrixHabitué`, `PrixJeune`, `PrixSenior`, `Description`) VALUES
    (1, 'Montagne Russe', 'Velociraptor', 'asset/Velociraptor.png', 15, 10, 10, 12, 'Le Velociraptor était un dinosaure connu pour sa vitesse. Cette attraction à sensation forte est l\'une des plus célèbre de notre parc et est reconnu en Europe pour la vitesse particulièrement élevé de son train.'),
(2, 'Montagne Russe', 'Tyranosore', 'asset/T-rex.png', 20, 15, 15, 17, 'Le T-Rex est l\'un des dinosaures les plus dangeureux. Cette montagne à l\'une des descente les plus raide du monde. Seulement pour les plus courageux!'),
(3, 'Manege', 'Torosaure', 'asset/T-rex.png', 10, 8, 8, 8, 'Faite un tour de notre parc sur le dos de notre dorosaure. Pour les grands et les petits cette activités conviviale vous ememnera dans les coulisses de notre parc.'),
(4, 'Horreur', 'Llukalkan', 'asset/T-rex.png', 15, 10, 13, 13, 'Tout les dinosaure avait peur du Llukalkan, Serez-vous capable de l\'affronter!!!!');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
                          `IdClient` int(11) NOT NULL,
    `NomUtilisateur` varchar(50) DEFAULT NULL,
    `MotDePasse` varchar(50) DEFAULT NULL,
    `Age` int(11) DEFAULT NULL,
    `Tarif` varchar(50) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`IdClient`, `NomUtilisateur`, `MotDePasse`, `Age`, `Tarif`) VALUES
    (0, 'invité', '0', 25, 'complet'),
    (1, 'Arthur', '1', 20, 'admin'),
    (2, 'Eliott', '12', 90, 'senior'),
    (3, 'theo', '123', 40, 'reduit'),
    (4, 'gabriel', '1234', 35, 'reduit'),
    (5, 'Paupau', '1808', 21, 'jeune');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
                               `IdReservation` int(11) NOT NULL,
    `DateReservation` datetime DEFAULT NULL,
    `NombreBillets` int(11) DEFAULT NULL,
    `PrixUnBillet` double DEFAULT NULL,
    `IdClient` int(11) NOT NULL,
    `IdAttraction` int(11) DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`IdReservation`, `DateReservation`, `NombreBillets`, `PrixUnBillet`, `IdClient`, `IdAttraction`) VALUES
    (4, '2025-04-28 00:00:00', 3, 15, 1, 2),
    (5, '2025-04-11 00:00:00', 1, 12, 2, 1),
    (6, '2025-04-11 00:00:00', 1, 10, 1, 1),
    (7, '2025-04-07 00:00:00', 1, 10, 1, 1),
    (8, '2025-04-06 00:00:00', 1, 10, 1, 1),
    (9, '2025-04-04 00:00:00', 2, 15, 1, 2),
    (10, '2025-04-01 00:00:00', 2, 10, 1, 1),
    (11, '2025-04-01 00:00:00', 2, 15, 1, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attraction`
--
ALTER TABLE `attraction`
    ADD PRIMARY KEY (`IdAttraction`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
    ADD PRIMARY KEY (`IdClient`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
    ADD PRIMARY KEY (`IdReservation`),
    ADD KEY `IdClient` (`IdClient`),
    ADD KEY `IdReservation` (`IdReservation`),
    ADD KEY `etranger` (`IdAttraction`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attraction`
--
ALTER TABLE `attraction`
    MODIFY `IdAttraction` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
    MODIFY `IdReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
    ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`IdClient`) REFERENCES `client` (`IdClient`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
