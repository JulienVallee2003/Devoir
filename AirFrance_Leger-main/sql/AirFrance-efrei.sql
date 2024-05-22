SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `admin` (
  `ID_Admin` int(11) NOT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `MotDePasse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `admin` (`ID_Admin`, `Prenom`, `Email`, `MotDePasse`) VALUES
(1, 'Arnaud', 'a@gmail.com', '123');

CREATE TABLE `aeroports` (
  `ID_Aeroport` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Localisation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `aeroports` (`ID_Aeroport`, `Nom`, `Localisation`) VALUES
(1, 'Aéroport de Paris-Charles de Gaulle (CDG)', 'FRANCE'),
(2, 'Aéroport de Paris-Orly (ORY)', 'FRANCE'),
(3, 'Aéroport de Lyon-Saint Exupéry (LYS)', 'FRANCE'),
(4, 'Aéroport de Nice-Côte dAzur (NCE)', 'FRANCE'),
(5, 'Aéroport de Toulouse-Blagnac (TLS)', 'FRANCE'),
(6, 'Aéroport de Marseille-Provence (MRS)', 'FRANCE'),
(7, 'Aéroport de Bordeaux-Mérignac (BOD)', 'FRANCE'),
(8, 'Aéroport de Nantes Atlantique (NTE)', 'FRANCE'),
(9, 'Aéroport de Strasbourg-Entzheim (SXB)', 'FRANCE'),
(10, 'Aéroport de Genève (GVA)', 'SUISSE'),
(11, 'Aéroport de Amsterdam-Schiphol (AMS)', 'Pays-Bas'),
(12, 'Aéroport de New York-JFK (JFK)', 'États-Unis'),
(13, 'Aéroport de Los Angeles International (LAX)', 'États-Unis'),
(14, 'Aéroport de Londres-Heathrow (LHR)', 'Royaume-Uni'),
(15, 'Aéroport de Tokyo-Narita (NRT)', 'Japon');

CREATE TABLE `avions` (
  `ID_Avion` int(11) NOT NULL,
  `Modele` varchar(255) DEFAULT NULL,
  `NombrePlaces` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `avions` (`ID_Avion`, `Modele`, `NombrePlaces`) VALUES
(1, 'Airbus A380-1000 \"SkyGiant\"', 600),
(2, 'Boeing 797 \"DreamBird\"', 550),
(3, 'Embraer E999 \"SkyCruiser\"', 200),
(4, 'Bombardier B1000 \"StarJet\"', 300),
(5, 'Cessna C888 \"SkyHawk\"', 10),
(6, 'Gulfstream G7000 \"StarLux\"', 20),
(7, 'Airbus A550 \"SkyWing\"', 400),
(8, 'Boeing 899X \"SuperEagle\"', 500),
(9, 'Lockheed Martin L1000 \"SkyMaster\"', 450),
(10, 'Bombardier BD1000 \"DreamStar\"', 350);

CREATE TABLE `membresequipage` (
  `ID_MembreEquipage` int(11) NOT NULL,
  `ID_Personne` int(11) DEFAULT NULL,
  `Role` varchar(255) DEFAULT NULL,
  `DateEmbauche` date DEFAULT NULL,
  `ID_Vol` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `membresequipage` (`ID_MembreEquipage`, `ID_Personne`, `Role`, `DateEmbauche`, `ID_Vol`) VALUES
(6, 11, 'Pilote', '2023-01-15', 2),
(7, 12, 'Copilote', '2023-01-20', 2),
(8, 13, 'Hôtesse de lair', '2023-02-10', 2),
(9, 14, 'Steward', '2023-02-10', 2),
(10, 15, 'Steward', '2023-03-05', 2);

CREATE TABLE `paiements` (
  `ID_Paiement` int(11) NOT NULL,
  `ID_Reservation` int(11) DEFAULT NULL,
  `MontantPaye` int(11) DEFAULT NULL,
  `DatePaiement` date DEFAULT NULL,
  `StatutPaiement` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `paiements` (`ID_Paiement`, `ID_Reservation`, `MontantPaye`, `DatePaiement`, `StatutPaiement`) VALUES
(1, 7, 45, '2023-01-15', 'Payé');

CREATE TABLE `passagers` (
  `ID_Passager` int(11) NOT NULL,
  `ID_Personne` int(11) DEFAULT NULL,
  `NumPasseport` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(16, 35, 'AQ987654'),
(17, 36, 'FG652378'),
(18, 37, 'AZ123456'),
(19, 38, 'KZ120987'),
(20, 39, 'HG123456'),
(21, 40, 'G2R23342'),
(22, 41, 'G2R35342'),
(23, 42, 'AA623456'),
(24, 43, 'AB127367'),
(25, 44, 'AA623456'),
(26, 45, 'AQ987654'),
(27, 46, 'FG352378'),
(28, 47, 'AZ623456'),
(29, 48, 'KZ220987'),
(30, 49, 'AG123456');

CREATE TABLE `personne` (
  `ID_Personne` int(11) NOT NULL,
  `Nom` varchar(255) DEFAULT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `DateNaissance` date DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `personne` (`ID_Personne`, `Nom`, `Prenom`, `DateNaissance`, `adresse`, `email`, `telephone`) VALUES
(1, 'Dupont', 'Jean', '1990-01-01', '123 Rue Example, Ville, Pays', 'jean.dupont@example.com', '0612345678'),
(4, 'Leroy', 'Isabelle', '1978-03-20', '321 Rue Demo, Ville, Pays', 'isabelle.leroy@example.com', '0612345680'),
(5, 'Roux', 'Antoine', '1989-11-05', '654 Avenue Exemple, Ville, Pays', 'antoine.roux@example.com', '0712345681'),
(6, 'Fournier', 'Marie', '1983-07-12', '987 Boulevard Test, Ville, Pays', 'marie.fournier@example.com', '0612345682'),
(7, 'Moreau', 'Thomas', '1980-09-18', '741 Rue Échantillon, Ville, Pays', 'thomas.moreau@example.com', '0712345683'),
(8, 'Girard', 'Laura', '1975-12-25', '159 Avenue Démo, Ville, Pays', 'laura.girard@example.com', '0612345684'),
(9, 'Bonnet', 'Julien', '1992-08-08', '852 Boulevard Exemple, Ville, Pays', 'julien.bonnet@example.com', '0712345685'),
(10, 'Lambert', 'Camille', '1987-04-14', '369 Rue Test, Ville, Pays', 'camille.lambert@example.com', '0612345686'),
(11, 'Rousseau', 'Nicolas', '1981-06-30', '258 Avenue Échantillon, Ville, Pays', 'nicolas.rousseau@example.com', '0712345687'),
(12, 'Clement', 'Manon', '1979-02-28', '147 Boulevard Démo, Ville, Pays', 'manon.clement@example.com', '0612345688'),
(13, 'Guerin', 'Alexandre', '1984-10-10', '963 Rue Exemple, Ville, Pays', 'alexandre.guerin@example.com', '0712345689'),
(14, 'Garnier', 'Chloé', '1986-12-03', '852 Avenue Test, Ville, Pays', 'chloe.garnier@example.com', '0612345690'),
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
(30, 'zerrze', 'zerzerzer', '1990-01-01', '', 'adf@gmail.com', '0678453267'),
(31, 'zerrze', 'zerzerzer', '1990-01-01', '', 'adf@gmail.com', '0678453267'),
(32, 'dfdf', 'dfdf', '1990-01-01', '', 'a@gmail.com', '0656786543'),
(33, 'dsdsq', 'dfdf', '1990-01-01', '', 'a@gmail.com', '0627778912'),
(34, 'sdf', 'dfsdf', '1990-01-01', '', 'a@gmail.com', '0625841345'),
(35, 'Raveloson', 'Arnaud', '1990-01-01', '', 'arnaud@gmail.com', '0611737253'),
(36, 'Raveloson', 'Axel', '2003-01-01', '47 rue des Lilas, Paris', 'axel@gmail.com', '0625673254'),
(37, 'Jinwoo', 'Sung', '1997-09-01', '12 rue du coco, Paris, France', 'a@gmail.com', '0754678721'),
(38, 'Zoldick', 'Kirua', '2012-07-12', '12 rue du chateau, Paris, France', 'zoldickkirua@gmail.com', '0643215612'),
(39, 'zachary', 'charles', '1990-01-01', '65 rue des petites, Paris', 'f@gmail.com', '0654122222'),
(40, 'zdfisd', 'sdfsdf', '1981-01-01', '23 rue de deter', 'gf@gmail.com', '01272228721'),
(41, 'Bristol', 'Joe', '1990-01-01', '21 rue armin, Mantes', 'ff@gmail.com', '0782828762'),
(42, 'Aze', 'rty', '1990-01-01', '12 rue jolie', 'a@gmail.com', '0657432512'),
(43, 'dz', 'ds', '1990-01-01', '12 rue des chiots', 'a@gmail.com', '0657452356'),
(44, 'blabla', 'car', '1990-01-01', '57 rue des ptis', 'a@gmail.com', '0167676767'),
(45, 'df', 'dfds', '1990-01-01', '12 rue acacia', 'zaaze', '9008089'),
(46, 'Lin', 'Franck', '1990-01-01', '12 rue des chinois', 'linfranck@gmail.com', '0621271827'),
(47, 'hgg', 'ghjg', '1990-01-01', '23 rue des ptits', 'a@mail.com', '0654372887'),
(48, 'sfsdf', 'CHinois de la cailler', '1990-01-01', '12 rue des cer', 'a@gmail.com', '0765465478'),
(49, 'dsqdsq', 'suddd', '1990-01-01', 'ssdsdsq', 'sqdsqdsq', '9070700'),
(50, 'zdfsdf', 'sdfsdf', '1990-01-01', 'ezerezrze', 'rzerzer', 'erzerzerze'),
(51, 'zdfsdf', 'sdfsdf', '1990-01-01', '12 rue de', 'a@gmail.com', '0675767577'),
(52, 'zeze', 'zaezae', '1990-01-01', '12 rue des cocotiers', 'A@gmail.com', '0615267823');

CREATE TABLE `reservations` (
  `ID_Reservation` int(11) NOT NULL,
  `ID_Passager` int(11) DEFAULT NULL,
  `ID_Vol` int(11) DEFAULT NULL,
  `DateReservation` date DEFAULT NULL,
  `SiegeAttribue` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `reservations` (`ID_Reservation`, `ID_Passager`, `ID_Vol`, `DateReservation`, `SiegeAttribue`) VALUES
(7, 1, 2, '2024-03-07', 'A1'),
(10, 10, 3, '2024-03-05', 'K3'),
(11, 17, 4, '2024-03-07', 'J6');


CREATE TABLE `vols` (
  `ID_Vol` int(11) NOT NULL,
  `DateDepart` datetime DEFAULT NULL,
  `DateArrivee` datetime DEFAULT NULL,
  `ID_AeroportDepart` int(11) DEFAULT NULL,
  `ID_AeroportArrivee` int(11) DEFAULT NULL,
  `ID_Avion` int(11) DEFAULT NULL,
  `Statut` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `vols` (`ID_Vol`, `DateDepart`, `DateArrivee`, `ID_AeroportDepart`, `ID_AeroportArrivee`, `ID_Avion`, `Statut`) VALUES
(1, '2023-01-01 08:00:00', '2023-01-01 10:30:00', 1, 2, 1, 'Programmé'),
(2, '2023-01-01 13:00:00', '2023-01-01 15:30:00', 2, 1, 2, 'Programmé'),
(3, '2023-01-02 08:00:00', '2023-01-02 10:30:00', 3, 4, 3, 'Programmé'),
(4, '2023-01-02 13:00:00', '2023-01-02 15:30:00', 4, 3, 4, 'Programmé'),
(5, '2023-01-03 08:00:00', '2023-01-03 10:30:00', 5, 6, 5, 'Programmé'),
(6, '2023-01-03 13:00:00', '2023-01-03 15:30:00', 6, 5, 6, 'Programmé'),
(7, '2023-01-04 08:00:00', '2023-01-04 10:30:00', 7, 8, 7, 'Programmé'),
(8, '2023-01-04 13:00:00', '2023-01-04 15:30:00', 8, 7, 8, 'Programmé'),
(9, '2023-01-05 08:00:00', '2023-01-05 10:30:00', 9, 10, 9, 'Programmé'),
(10, '2023-01-05 13:00:00', '2023-01-05 15:30:00', 10, 9, 10, 'Programmé');

ALTER TABLE `admin`
  ADD PRIMARY KEY (`ID_Admin`);

ALTER TABLE `aeroports`
  ADD PRIMARY KEY (`ID_Aeroport`);


ALTER TABLE `avions`
  ADD PRIMARY KEY (`ID_Avion`);


ALTER TABLE `membresequipage`
  ADD PRIMARY KEY (`ID_MembreEquipage`),
  ADD KEY `ID_Personne` (`ID_Personne`),
  ADD KEY `fk_id_vol` (`ID_Vol`);


ALTER TABLE `paiements`
  ADD PRIMARY KEY (`ID_Paiement`),
  ADD KEY `ID_Reservation` (`ID_Reservation`);


ALTER TABLE `passagers`
  ADD PRIMARY KEY (`ID_Passager`),
  ADD KEY `ID_Personne` (`ID_Personne`);


ALTER TABLE `personne`
  ADD PRIMARY KEY (`ID_Personne`);


ALTER TABLE `reservations`
  ADD PRIMARY KEY (`ID_Reservation`),
  ADD KEY `ID_Passager` (`ID_Passager`),
  ADD KEY `ID_Vol` (`ID_Vol`);


ALTER TABLE `vols`
  ADD PRIMARY KEY (`ID_Vol`),
  ADD KEY `AeroportDepart` (`AeroportDepart`),
  ADD KEY `AeroportArrivee` (`AeroportArrivee`),
  ADD KEY `Avion` (`Avion`);


ALTER TABLE `admin`
  MODIFY `ID_Admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


ALTER TABLE `aeroports`
  MODIFY `ID_Aeroport` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;


ALTER TABLE `avions`
  MODIFY `ID_Avion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;


ALTER TABLE `membresequipage`
  MODIFY `ID_MembreEquipage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;


ALTER TABLE `paiements`
  MODIFY `ID_Paiement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;


ALTER TABLE `passagers`
  MODIFY `ID_Passager` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;


ALTER TABLE `personne`
  MODIFY `ID_Personne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;


ALTER TABLE `reservations`
  MODIFY `ID_Reservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;


ALTER TABLE `vols`
  MODIFY `ID_Vol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;


ALTER TABLE `membresequipage`
  ADD CONSTRAINT `fk_id_vol` FOREIGN KEY (`ID_Vol`) REFERENCES `vols` (`ID_Vol`),
  ADD CONSTRAINT `membresequipage_ibfk_1` FOREIGN KEY (`ID_Personne`) REFERENCES `personne` (`ID_Personne`);


ALTER TABLE `paiements`
  ADD CONSTRAINT `paiements_ibfk_1` FOREIGN KEY (`ID_Reservation`) REFERENCES `reservations` (`ID_Reservation`);


ALTER TABLE `passagers`
  ADD CONSTRAINT `passagers_ibfk_1` FOREIGN KEY (`ID_Personne`) REFERENCES `personne` (`ID_Personne`);


ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`ID_Passager`) REFERENCES `passagers` (`ID_Passager`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`ID_Vol`) REFERENCES `vols` (`ID_Vol`);


ALTER TABLE `vols`
  ADD CONSTRAINT `vols_ibfk_1` FOREIGN KEY (`AeroportDepart`) REFERENCES `aeroports` (`ID_Aeroport`),
  ADD CONSTRAINT `vols_ibfk_2` FOREIGN KEY (`AeroportArrivee`) REFERENCES `aeroports` (`ID_Aeroport`),
  ADD CONSTRAINT `vols_ibfk_3` FOREIGN KEY (`Avion`) REFERENCES `avions` (`ID_Avion`);
COMMIT;
--
-- Structure de la vue `vue_vols`
--

create view vue_vols as (
select v.ID_Vol, v.NumeroVol,  v.DateDepart, v.DateArrivee, v.HeureDepart, v.HeureArrivee, a.Nom as AeroportDepart, b.Nom as AeroportArrivee
, v.Avion from vols v, aeroports a , aeroports b 
where v.AeroportDepart = a.ID_Aeroport
and v.AeroportArrivee = b.ID_Aeroport
); 