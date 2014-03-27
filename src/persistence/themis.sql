-- phpMyAdmin SQL Dump
-- version 4.0.6deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Jeu 27 Mars 2014 à 14:08
-- Version du serveur: 5.5.35-0ubuntu0.13.10.2
-- Version de PHP: 5.5.3-1ubuntu2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `themis`
--

-- --------------------------------------------------------

--
-- Structure de la table `corps_enqueteur`
--

CREATE TABLE IF NOT EXISTS `corps_enqueteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `enqueteur`
--

CREATE TABLE IF NOT EXISTS `enqueteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_titre` int(11) NOT NULL,
  `id_service` int(11) NOT NULL,
  `nom` varchar(60) NOT NULL,
  `prenom` varchar(60) NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `telephone_pro` varchar(14) NOT NULL,
  `email` varchar(60) NOT NULL,
  `fax_pro` varchar(14) NOT NULL,
  `telephone_perso` varchar(14) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `service_enqueteur`
--

CREATE TABLE IF NOT EXISTS `service_enqueteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_corps_enqueteur` int(11) NOT NULL,
  `libelle` varchar(255) NOT NULL,
  `telephone` varchar(14) NOT NULL,
  `lieu` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `titre_enqueteur`
--

CREATE TABLE IF NOT EXISTS `titre_enqueteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;