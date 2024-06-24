
--
-- Base de données : `airfrance`

CREATE DATABASE airfrance;
Use airfrance;


DELIMITER $$
--
-- Procédures
--
DROP PROCEDURE IF EXISTS `DeleteAeroport`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteAeroport` (IN `idAeroport` INT)   BEGIN
    -- Mettre à jour les vols pour supprimer la référence à l'aéroport de départ
    UPDATE vols SET AeroportDepart = NULL WHERE AeroportDepart = idAeroport;

    -- Mettre à jour les vols pour supprimer la référence à l'aéroport d'arrivée
    UPDATE vols SET AeroportArrivee = NULL WHERE AeroportArrivee = idAeroport;

    -- Supprimer l'aéroport
    DELETE FROM aeroports WHERE ID_Aeroport = idAeroport;
END$$

DROP PROCEDURE IF EXISTS `DeleteAvion`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteAvion` (IN `avionID` INT)   BEGIN
    -- Mettre à jour les vols pour supprimer la référence à l'avion
    UPDATE vols SET Avion = NULL WHERE Avion = avionID;

    -- Supprimer l'avion
    DELETE FROM avions WHERE ID_Avion = avionID;
END$$

DROP PROCEDURE IF EXISTS `DeletePassager`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeletePassager` (IN `passagerID` INT)   BEGIN
    -- Supprimer les réservations associées au passager
    DELETE FROM Reservations
    WHERE ID_Passager = passagerID;

    -- Supprimer le passager de la table Passagers
    DELETE FROM Passagers
    WHERE ID_Passager = passagerID;
END$$

DROP PROCEDURE IF EXISTS `DeleteVol`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteVol` (IN `idVol` INT)   BEGIN
    -- Mettre à jour les réservations pour supprimer la référence au vol
    UPDATE reservations SET ID_Vol = NULL WHERE ID_Vol = idVol;

    -- Mettre à jour les membres d'équipage pour supprimer la référence au vol
    UPDATE membresequipage SET ID_Vol = NULL WHERE ID_Vol = idVol;

    -- Supprimer le vol
    DELETE FROM vols WHERE ID_Vol = idVol;
END$$

DROP PROCEDURE IF EXISTS `InsertMembreEquipage`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertMembreEquipage` (IN `pNom` VARCHAR(255), IN `pPrenom` VARCHAR(255), IN `pRole` VARCHAR(255), IN `pDateEmbauche` DATE, IN `pID_Vol` INT)   BEGIN
    DECLARE lastPersonID INT;

    -- Insérer une nouvelle personne
    INSERT INTO personne (Nom, Prenom)
    VALUES (pNom, pPrenom);

    -- Obtenir l'ID de la personne insérée
    SET lastPersonID = LAST_INSERT_ID();

    -- Insérer un nouveau membre d'équipage en utilisant l'ID de la personne
    INSERT INTO membresequipage (ID_Personne, Role, DateEmbauche, ID_Vol)
    VALUES (lastPersonID, pRole, pDateEmbauche, pID_Vol);

    -- Insérer un nouveau passager en utilisant l'ID de la personne
    INSERT INTO passagers (ID_Personne)
    VALUES (lastPersonID);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `ID_Admin` int NOT NULL AUTO_INCREMENT,
  `Prenom` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `MotDePasse` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Admin`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`ID_Admin`, `Prenom`, `Email`, `MotDePasse`) VALUES
(1, 'ibrahima', 'ibr@airfrance.com', '$2y$10$KoAsf46SU7.JO1hEG78BV.a9a0hIn.UWWQNPVScxcOeLWsJX2b4u6'),
(4, 'Antoine', 'newadmin@airfrance.com', '141afbccbc5cf3bd3b9ccc2fca1e116e0b3c916b3acbe264a778aa32cb292fb9');

-- --------------------------------------------------------

--
-- Structure de la table `aeroports`
--

DROP TABLE IF EXISTS `aeroports`;
CREATE TABLE IF NOT EXISTS `aeroports` (
  `ID_Aeroport` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Localisation` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Aeroport`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `aeroports`
--

INSERT INTO `aeroports` (`ID_Aeroport`, `Nom`, `Localisation`) VALUES
(1, 'Aéroport de Paris-Charles de Gaulle (CDG)', 'FRANCE '),
(2, 'Aéroport de Paris-Orly (ORY)', 'FRANCE'),
(6, 'Aéroport de Marseille-Provence (MRS)', 'FRANCE 2'),
(7, 'Aéroport de Bordeaux-Mérignac (BOD)', 'FRANCE'),
(8, 'Aéroport de Nantes Atlantique (NTE)', 'FRANCE'),
(9, 'Aéroport de Strasbourg-Entzheim (SXB)', 'FRANCE'),
(10, 'Aéroport de Genève (GVA)', 'SUISSE'),
(11, 'Aéroport de Amsterdam-Schiphol (AMS)', 'Pays-Bas'),
(12, 'Aéroport de New York-JFK (JFK)', 'États-Unis'),
(14, 'Aéroport de Londres-Heathrow (LHR)', 'Royaume-Uni'),
(15, 'Aéroport de Tokyo-Narita (NRT)', 'Japon');

-- --------------------------------------------------------

--
-- Structure de la table `avions`
--

DROP TABLE IF EXISTS `avions`;
CREATE TABLE IF NOT EXISTS `avions` (
  `ID_Avion` int NOT NULL AUTO_INCREMENT,
  `Modele` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `NombrePlaces` int DEFAULT NULL,
  PRIMARY KEY (`ID_Avion`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avions`
--

INSERT INTO `avions` (`ID_Avion`, `Modele`, `NombrePlaces`) VALUES
(1, 'Airbus A380-1000 \"SkyGiant\"', 600),
(2, 'Boeing 797 ', 5500),
(3, 'Embraer E999 \"SkyCruiser\"', 200),
(4, 'Bombardier B1000 \"StarJet\"', 300),
(5, 'Cessna C888 \"SkyHawk\"', 10),
(6, 'Gulfstream G7000 \"StarLux\"', 20),
(8, 'Boeing 899X \"SuperEagle\"', 500),
(10, 'Bombardier BD1000 \"DreamStar\"', 350),
(11, 'Airbus A330', 340),
(13, 'Airbus A350-1000', 400),
(14, 'Boeing 787 Dreamliner', 300),
(15, 'Embraer E190', 150),
(16, 'Bombardier CRJ900', 100),
(17, 'Airbus A320', 200);

-- --------------------------------------------------------

--
-- Structure de la table `membresequipage`
--

DROP TABLE IF EXISTS `membresequipage`;
CREATE TABLE IF NOT EXISTS `membresequipage` (
  `ID_MembreEquipage` int NOT NULL AUTO_INCREMENT,
  `ID_Personne` int DEFAULT NULL,
  `Role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `DateEmbauche` date DEFAULT NULL,
  `ID_Vol` int DEFAULT NULL,
  PRIMARY KEY (`ID_MembreEquipage`),
  KEY `ID_Personne` (`ID_Personne`),
  KEY `fk_id_vol` (`ID_Vol`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `membresequipage`
--

INSERT INTO `membresequipage` (`ID_MembreEquipage`, `ID_Personne`, `Role`, `DateEmbauche`, `ID_Vol`) VALUES
(6, 11, 'Pilote', '2023-01-15', 10),
(7, 12, 'Copilote', '2023-01-20', 4),
(8, 13, 'Pilote', '2003-02-10', 12),
(9, 14, 'Steward', '2023-03-05', 12),
(10, 15, 'Steward', '2023-03-05', 9),
(12, 1, 'Copilote', '1985-12-25', 9),
(46, 6, 'Steward', '2023-03-05', 4),
(47, 7, 'Pilote', '2023-01-15', 5),
(48, 8, 'Copilote', '2023-01-20', 4),
(50, 10, 'Steward', '2023-02-10', 8),
(51, 11, 'Pilote', '2023-03-05', 5),
(52, 12, 'Copilote', '1985-12-25', 4),
(53, 13, 'Pilote', '2023-01-15', 7),
(54, 14, 'Copilote', '2023-01-20', 7),
(55, 15, 'Pilote', '2003-02-10', 7),
(56, 16, 'Steward', '2023-02-10', 7),
(57, 17, 'Steward', '2023-03-05', 7);

-- --------------------------------------------------------

--
-- Structure de la table `passagers`
--

DROP TABLE IF EXISTS `passagers`;
CREATE TABLE IF NOT EXISTS `passagers` (
  `ID_Passager` int NOT NULL AUTO_INCREMENT,
  `ID_Personne` int DEFAULT NULL,
  `NumPasseport` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Passager`),
  KEY `ID_Personne` (`ID_Personne`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `passagers`
--

INSERT INTO `passagers` (`ID_Passager`, `ID_Personne`, `NumPasseport`) VALUES
(1, 1, 'AB123456'),
(4, 4, 'GH901234'),
(5, 5, 'IJ567890'),
(6, 6, 'KL123456'),
(7, 7, 'MN789012'),
(8, 8, 'OP345678'),
(9, 9, 'QR901234'),
(10, 10, 'ST567890'),
(11, 30, 'B2H35342'),
(12, 31, 'B2H35342'),
(13, 32, 'AA123456'),
(14, 33, 'AQ127367'),
(15, 34, 'AQ123456'),
(17, 36, 'FG652378'),
(18, 37, 'AZ123456'),
(19, 38, 'KZ120987'),
(20, 39, 'HG123456'),
(21, 40, 'G2R23323'),
(22, 41, 'KQ543625'),
(23, 42, 'AQ12345'),
(25, 44, 'AQ678901'),
(26, 45, 'FFA55'),
(27, 46, 'AG5788788'),
(28, 47, 'AQ12443'),
(29, 48, 'AS876543'),
(30, 49, 'AYFF7676'),
(32, 51, 'AQ123456'),
(33, 52, 'AZ123456');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `ID_Personne` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Prenom` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `DateNaissance` date DEFAULT NULL,
  `adresse` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `telephone` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Personne`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`ID_Personne`, `Nom`, `Prenom`, `DateNaissance`, `adresse`, `email`, `telephone`) VALUES
(1, 'Dupont', 'Jean', '1990-01-01', '123 Rue Example, Ville, Pays', 'jean.dupont@example.com', '0612345678'),
(4, 'Leroy', 'Isabelle', '1978-03-20', '321 Rue Demo, Ville, Pays', 'isabelle.leroy@example.com', '0612345680'),
(5, 'Roux', 'Antoine', '1989-11-05', '654 Avenue Exemple, Ville, Pays', 'antoine.roux@example.com', '071234568'),
(6, 'Fournier', 'Marie', '1983-07-13', '987 Boulevard Test, Ville, Pays', 'marie.fournier@example.com', '0612345682'),
(7, 'Moreau', 'Thomas', '1980-09-18', '741 Rue Échantillon, Ville, Pays', 'thomas.moreau@example.com', '0712345683'),
(8, 'Girard', 'Laura', '1975-12-25', '159 Avenue Démo, Ville, Pays', 'laura.girard@example.com', '0612345684'),
(9, 'Bonnet', 'Julien', '1992-08-08', '852 Boulevard Exemple, Ville, Pays', 'julien.bonnet@example.com', '0712345685'),
(10, 'Lambert', 'Camille', '1987-04-14', '369 Rue Test, Ville, Pays', 'camille.lambert@example.com', '0612345686'),
(11, 'Rousseau', 'Nicolas', '1981-06-30', '258 Avenue Échantillon, Ville, Pays', 'nicolas.rousseau@example.com', '0712345687'),
(12, 'Clement', 'Manon', '1979-02-28', '147 Boulevard Démo, Ville, Pays', 'manon.clement@example.com', '0612345688'),
(13, 'Guerin', 'Alexandre', '1984-10-10', '963 Rue Exemple, Ville, Pays', 'alexandre.guerin@example.com', '0712345689'),
(14, 'Fournier', 'Marie', '1983-07-13', '987 Boulevard Test, Ville, Pays', 'marie.fournier@example.com', '0612345682'),
(15, 'Chevalier', 'Emma', '1988-07-19', '741 Boulevard Échantillon, Ville, Pays', 'emma.chevalier@example.com', '0712345691'),
(16, 'Boyer', 'Lucas', '1976-05-06', '369 Rue Démo, Ville, Pays', 'lucas.boyer@example.com', '0612345692'),
(17, 'Andre', 'Julie', '1983-09-23', '258 Avenue Exemple, Ville, Pays', 'julie.andre@example.com', '0712345693'),
(18, 'Caron', 'Paul', '1989-03-15', '147 Boulevard Test, Ville, Pays', 'paul.caron@example.com', '0612345694'),
(19, 'Marchand', 'Inès', '1977-11-11', '963 Rue Échantillon, Ville, Pays', 'ines.marchand@example.com', '0712345695'),
(20, 'Leclerc', 'Hugo', '1982-01-09', '852 Boulevard Démo, Ville, Pays', 'hugo.leclerc@example.com', '0612345696'),
(21, 'Sanchez', 'Louise', '1985-04-27', '741 Avenue Exemple, Ville, Pays', 'louise.sanchez@example.com', '0712345697'),
(22, 'Hubert', 'Maxime', '1978-06-13', '369 Rue Test, Ville, Pays', 'maxime.hubert@example.com', '0612345698'),
(23, 'Leclercq', 'Juliette', '1991-02-20', '258 Avenue Démo, Ville, Pays', 'juliette.leclercq@example.com', '0712345699'),
(24, 'Guillaume', 'Arthur', '1980-08-18', '147 Boulevard Exemple, Ville, Pays', 'arthur.guillaume@example.com', '0612345700'),
(25, 'Meunier', 'Clara', '1986-10-05', '963 Rue Test, Ville, Pays', 'clara.meunier@example.com', '0712345701'),
(26, 'Henry', 'Louis', '1979-12-28', '852 Boulevard Échantillon, Ville, Pays', 'louis.henry@example.com', '0612345702'),
(27, 'Renard', 'Eva', '1984-02-14', '741 Avenue Démo, Ville, Pays', 'eva.renard@example.com', '0712345703'),
(28, 'Duval', 'Mathis', '1981-05-31', '369 Rue Exemple, Ville, Pays', 'mathis.duval@example.com', '0612345704'),
(29, 'Lopez', 'Charlotte', '1976-09-17', '258 Avenue Test, Ville, Pays', 'charlotte.lopez@example.com', '0712345705'),
(30, 'zerrze', 'zerzerzer', '1990-01-01', '20 rue frida kahlo', 'adf@gmail.com', '0678453267'),
(31, 'zerrze', 'zerzerzer', '1990-01-01', '56 avenue pablo picasso', 'adf@gmail.com', '0678453267'),
(32, 'dfdf', 'dfdf', '1990-01-01', '60 avenue leclerc', 'a@gmail.com', '0656786543'),
(33, 'dsdsq', 'dfdf', '1990-01-01', '48 bis pablo neruda', 'auykiè@gmail.com', '0627778910'),
(34, 'sdf', 'dfsdf', '1990-01-01', '51 rue du jardin', 'afrs@gmail.com', '0625841345'),
(35, 'Raveloson', 'Arnaud', '1990-01-01', '97 rue napoléon 1er', 'arnaud@gmail.com', '0611737253'),
(36, 'Raveloson', 'Axel', '2003-01-01', '47 rue des Lilas, Paris', 'axel@gmail.com', '0625673254'),
(37, 'Jinwoo', 'Sung', '1997-09-01', '12 rue du coco, Paris, France', 'a@gmail.com', '0754678721'),
(38, 'Zoldick', 'Kirua', '2012-07-12', '12 rue du chateau, Paris, France', 'zoldickkirua@gmail.com', '0643215612'),
(39, 'zachary', 'charles', '1990-01-01', '65 rue des petites, Paris', 'f@gmail.com', '0654122222'),
(40, 'zdfisd', 'sdfsdf', '1981-01-01', '23 rue de deter', 'gf@gmail.com', '01272228721'),
(41, 'Bristol', 'Joe', '1990-01-01', '21 rue armin, Mantes', 'ff@gmail.com', '0782828762'),
(42, 'Aze', 'rty', '1990-01-01', '12 rue jolie', 'aofj@gmail.com', '0657432512'),
(44, 'blabla', 'car', '1990-01-01', '57 rue des ptis', 'ajy@gmail.com', '0167676767'),
(45, 'df', 'dfds', '1990-01-01', '12 rue acacia', 'zaaze', '9008089'),
(46, 'Lin', 'Franck', '1990-01-01', '12 rue des chinois', 'linfranck@gmail.com', '0621271827'),
(47, 'hgg', 'ghjg', '1990-01-01', '23 rue des ptits', 'ajyu@mail.com', '0654372887'),
(48, 'sfsdf', 'CHinois de la cailler', '1990-01-01', '12 rue des cer', 'a@gmail.com', '0765465478'),
(49, 'dsqdsq', 'suddd', '1990-01-01', 'ssdsdsq', 'sqdsqdsq', '9070700'),
(51, 'zdfsdf', 'sdfsdf', '1990-01-01', '12 rue de', 'a@gmail.com', '0675767577'),
(52, 'zeze', 'zaezae', '1990-01-01', '12 rue des cocotiers', 'A@gmail.com', '0615267823'),
(53, '', '', NULL, NULL, '', '');

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `ID_Reservation` int NOT NULL AUTO_INCREMENT,
  `ID_Passager` int DEFAULT NULL,
  `ID_Vol` int DEFAULT NULL,
  `DateReservation` date DEFAULT NULL,
  `SiegeAttribue` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID_Reservation`),
  KEY `ID_Passager` (`ID_Passager`),
  KEY `ID_Vol` (`ID_Vol`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reservations`
--

INSERT INTO `reservations` (`ID_Reservation`, `ID_Passager`, `ID_Vol`, `DateReservation`, `SiegeAttribue`) VALUES
(7, 1, 10, '2024-03-07', 'A1'),
(10, 6, 4, '2024-03-05', 'K3'),
(11, 1, 18, '2024-03-15', 'J6'),
(20, 8, 12, '2024-03-10', 'A1'),
(21, 12, 5, '2024-03-11', 'B3'),
(22, 1, 4, '2024-03-10', 'C4'),
(23, 14, 8, '2024-03-13', 'D5'),
(24, 10, 4, '2024-03-10', 'E6'),
(26, 17, 10, '2024-03-16', 'G8'),
(27, 14, 10, '2024-03-16', 'H9'),
(28, 1, 8, '2024-03-18', 'I10'),
(29, 4, 10, '2024-03-12', 'J11');

-- --------------------------------------------------------

--
-- Structure de la table `vols`
--

DROP TABLE IF EXISTS `vols`;
CREATE TABLE IF NOT EXISTS `vols` (
  `ID_Vol` int NOT NULL AUTO_INCREMENT,
  `NumeroVol` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `DateDepart` date DEFAULT NULL,
  `HeureDepart` time DEFAULT NULL,
  `AeroportDepart` int DEFAULT NULL,
  `DateArrivee` date DEFAULT NULL,
  `HeureArrivee` time DEFAULT NULL,
  `AeroportArrivee` int DEFAULT NULL,
  `Avion` int DEFAULT NULL,
  PRIMARY KEY (`ID_Vol`),
  KEY `AeroportDepart` (`AeroportDepart`),
  KEY `AeroportArrivee` (`AeroportArrivee`),
  KEY `Avion` (`Avion`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `vols`
--

INSERT INTO `vols` (`ID_Vol`, `NumeroVol`, `DateDepart`, `HeureDepart`, `AeroportDepart`, `DateArrivee`, `HeureArrivee`, `AeroportArrivee`, `Avion`) VALUES
(4, 'LH789', '2024-03-12', '10:00:00', 12, '2024-03-12', '22:00:00', 14, 1),
(5, 'EK012', '2024-03-13', '11:00:00', 1, '2024-03-14', '13:00:00', 8, 6),
(7, 'AF456', '2024-03-16', '13:00:00', 1, '2024-03-16', '15:00:00', 10, 2),
(8, 'BA789', '2024-03-16', '14:00:00', 8, '2024-03-17', '16:00:00', 11, 1),
(9, 'LH012', '2024-03-20', '15:00:00', 10, '2024-03-20', '17:00:00', 1, 1),
(10, 'EK345', '2024-03-19', '16:00:00', 6, '2024-03-19', '18:00:00', 9, 5),
(12, 'AF789', '2024-03-21', '18:45:00', 9, '2024-03-21', '20:00:00', 1, 1),
(13, 'BA012', '2024-03-22', '19:00:00', 1, '2024-03-22', '21:00:00', 8, 2),
(14, 'LH345', '2024-03-23', '20:00:00', 2, '2024-03-23', '22:00:00', 6, 11),
(16, 'QR012', '2024-03-25', '22:00:00', 1, '2024-03-25', '00:00:00', 15, 5),
(18, 'HI785', '2024-06-29', '18:10:00', 11, '2024-06-30', '05:25:00', 7, 8);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vue_passagers`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `vue_passagers`;
CREATE TABLE IF NOT EXISTS `vue_passagers` (
`Email` varchar(255)
,`ID_Passager` int
,`Nom` varchar(255)
,`NumPasseport` varchar(255)
,`Prenom` varchar(255)
,`Telephone` varchar(16)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vue_reservations`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `vue_reservations`;
CREATE TABLE IF NOT EXISTS `vue_reservations` (
`DateReservation` date
,`ID_Reservation` int
,`nom` varchar(255)
,`NumeroVol` varchar(10)
,`prenom` varchar(255)
,`SiegeAttribue` varchar(10)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vue_vols`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `vue_vols`;
CREATE TABLE IF NOT EXISTS `vue_vols` (
`AeroportArrivee` varchar(255)
,`AeroportDepart` varchar(255)
,`Avion` varchar(255)
,`DateArrivee` date
,`DateDepart` date
,`HeureArrivee` time
,`HeureDepart` time
,`ID_Vol` int
,`NumeroVol` varchar(10)
);

-- --------------------------------------------------------

--
-- Structure de la vue `vue_passagers`
--
DROP TABLE IF EXISTS `vue_passagers`;

DROP VIEW IF EXISTS `vue_passagers`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vue_passagers`  AS SELECT `p`.`ID_Passager` AS `ID_Passager`, `pers`.`Nom` AS `Nom`, `pers`.`Prenom` AS `Prenom`, `pers`.`email` AS `Email`, `pers`.`telephone` AS `Telephone`, `p`.`NumPasseport` AS `NumPasseport` FROM (`passagers` `p` join `personne` `pers` on((`p`.`ID_Personne` = `pers`.`ID_Personne`))) ;

-- --------------------------------------------------------

--
-- Structure de la vue `vue_reservations`
--
DROP TABLE IF EXISTS `vue_reservations`;

DROP VIEW IF EXISTS `vue_reservations`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vue_reservations`  AS SELECT `reserve`.`ID_Reservation` AS `ID_Reservation`, `pers`.`Nom` AS `nom`, `pers`.`Prenom` AS `prenom`, `vol`.`NumeroVol` AS `NumeroVol`, `reserve`.`DateReservation` AS `DateReservation`, `reserve`.`SiegeAttribue` AS `SiegeAttribue` FROM (((`reservations` `reserve` left join `passagers` `pass` on((`reserve`.`ID_Passager` = `pass`.`ID_Passager`))) left join `personne` `pers` on((`pass`.`ID_Personne` = `pers`.`ID_Personne`))) left join `vols` `vol` on((`reserve`.`ID_Vol` = `vol`.`ID_Vol`))) ;

-- --------------------------------------------------------

--
-- Structure de la vue `vue_vols`
--
DROP TABLE IF EXISTS `vue_vols`;

DROP VIEW IF EXISTS `vue_vols`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vue_vols`  AS SELECT `v`.`ID_Vol` AS `ID_Vol`, `v`.`NumeroVol` AS `NumeroVol`, `v`.`DateDepart` AS `DateDepart`, `v`.`DateArrivee` AS `DateArrivee`, `v`.`HeureDepart` AS `HeureDepart`, `v`.`HeureArrivee` AS `HeureArrivee`, ifnull(`a`.`Nom`,'Aéroport inconnu') AS `AeroportDepart`, ifnull(`b`.`Nom`,'Aéroport inconnu') AS `AeroportArrivee`, ifnull(`av`.`Modele`,'Aucun avion') AS `Avion` FROM (((`vols` `v` left join `aeroports` `a` on((`v`.`AeroportDepart` = `a`.`ID_Aeroport`))) left join `aeroports` `b` on((`v`.`AeroportArrivee` = `b`.`ID_Aeroport`))) left join `avions` `av` on((`v`.`Avion` = `av`.`ID_Avion`))) ;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `membresequipage`
--
ALTER TABLE `membresequipage`
  ADD CONSTRAINT `fk_id_vol` FOREIGN KEY (`ID_Vol`) REFERENCES `vols` (`ID_Vol`),
  ADD CONSTRAINT `membresequipage_ibfk_1` FOREIGN KEY (`ID_Personne`) REFERENCES `personne` (`ID_Personne`);

--
-- Contraintes pour la table `passagers`
--
ALTER TABLE `passagers`
  ADD CONSTRAINT `passagers_ibfk_1` FOREIGN KEY (`ID_Personne`) REFERENCES `personne` (`ID_Personne`);

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`ID_Passager`) REFERENCES `passagers` (`ID_Passager`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`ID_Vol`) REFERENCES `vols` (`ID_Vol`);

--
-- Contraintes pour la table `vols`
--
ALTER TABLE `vols`
  ADD CONSTRAINT `vols_ibfk_1` FOREIGN KEY (`AeroportDepart`) REFERENCES `aeroports` (`ID_Aeroport`),
  ADD CONSTRAINT `vols_ibfk_2` FOREIGN KEY (`AeroportArrivee`) REFERENCES `aeroports` (`ID_Aeroport`),
  ADD CONSTRAINT `vols_ibfk_3` FOREIGN KEY (`Avion`) REFERENCES `avions` (`ID_Avion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
