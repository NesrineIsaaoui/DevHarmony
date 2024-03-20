-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2024 at 02:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eduwave`
--

-- --------------------------------------------------------

--
-- Table structure for table `avis`
--

CREATE TABLE `avis` (
  `id` int(11) NOT NULL,
  `cours_id` int(11) NOT NULL,
  `etoiles` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `avis`
--

INSERT INTO `avis` (`id`, `cours_id`, `etoiles`) VALUES
(41, 35, 5),
(42, 35, 3),
(43, 35, 1),
(45, 36, 2),
(46, 36, 3),
(47, 38, 3),
(48, 37, 2),
(49, 37, 5),
(50, 37, 3),
(51, 37, 4),
(52, 37, 4),
(53, 39, 3),
(54, 39, 5),
(55, 39, 4),
(56, 37, 4),
(57, 37, 5),
(58, 35, 4),
(59, 35, 4),
(60, 35, 4),
(61, 36, 3),
(62, 40, 3),
(63, 37, 4),
(64, 38, 1);

-- --------------------------------------------------------

--
-- Table structure for table `categoriecodepromo`
--

CREATE TABLE `categoriecodepromo` (
  `id` int(100) NOT NULL,
  `code` varchar(200) NOT NULL,
  `value` float NOT NULL,
  `nb_users` int(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categoriecodepromo`
--

INSERT INTO `categoriecodepromo` (`id`, `code`, `value`, `nb_users`) VALUES
(12, 'aaz', 0.2, 1),
(13, '1az', 0.2, 1),
(16, '12z', 0.4, 5),
(20, 'hello123', 0.1, 5),
(23, 'hi000', 0.6, 5);

-- --------------------------------------------------------

--
-- Table structure for table `commentaire`
--

CREATE TABLE `commentaire` (
  `id` int(11) NOT NULL,
  `contenu` text NOT NULL,
  `date_commentaire` timestamp NOT NULL DEFAULT current_timestamp(),
  `publication_id` int(11) DEFAULT NULL,
  `utilisateur_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `id` int(11) NOT NULL,
  `coursName` varchar(50) NOT NULL,
  `coursDescription` varchar(255) NOT NULL,
  `coursImage` varchar(255) NOT NULL,
  `coursPrix` int(11) NOT NULL,
  `idCategory` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cours`
--

INSERT INTO `cours` (`id`, `coursName`, `coursDescription`, `coursImage`, `coursPrix`, `idCategory`) VALUES
(35, 'francais ', 'Cours validation', 'C:\\Users\\razan\\OneDrive\\Desktop\\integration\\DevHarmony\\src\\main\\resources\\418677530_360055920329676_2489057018682850579_n.png', 123, 36),
(36, 'englais', 'hello ', 'C:\\Users\\LENOVO\\Downloads\\cours-en-ligne.png', 230, 37),
(37, 'allemand', 'danke shon', 'C:\\Users\\LENOVO\\Downloads\\livre.png', 255, 37),
(38, 'turkish', 'cok guzel', 'C:\\Users\\LENOVO\\Downloads\\croissance.png', 260, 37),
(39, 'turkish', 'cok guzel', 'C:\\Users\\LENOVO\\Downloads\\croissance.png', 260, 36),
(40, 'valid', 'valid', 'C:\\Users\\razan\\Downloads\\418677530_360055920329676_2489057018682850579_n.png', 123, 37);

-- --------------------------------------------------------

--
-- Table structure for table `courscategory`
--

CREATE TABLE `courscategory` (
  `id` int(11) NOT NULL,
  `categoryName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courscategory`
--

INSERT INTO `courscategory` (`id`, `categoryName`) VALUES
(36, 'phy'),
(37, 'svt');

-- --------------------------------------------------------

--
-- Table structure for table `publication`
--

CREATE TABLE `publication` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `contenu` text NOT NULL,
  `date_publication` timestamp NOT NULL DEFAULT current_timestamp(),
  `utilisateur_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `id_user` int(10) NOT NULL,
  `id_cours` int(11) DEFAULT NULL,
  `resStatus` tinyint(1) NOT NULL,
  `date_reservation` datetime NOT NULL,
  `id_codepromo` int(100) NOT NULL,
  `prixd` float NOT NULL,
  `paidStatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`id`, `id_user`, `id_cours`, `resStatus`, `date_reservation`, `id_codepromo`, `prixd`, `paidStatus`) VALUES
(31, 52, 35, 1, '2024-03-04 23:43:38', 16, 73.8, 1),
(32, 52, 35, 1, '2024-03-05 00:16:15', 16, 73.8, 1),
(33, 52, 36, 1, '2024-03-05 00:21:53', 16, 138, 1),
(34, 52, 36, 1, '2024-03-05 00:42:28', 16, 138, 1),
(35, 52, 35, 1, '2024-03-05 01:41:25', 16, 73.8, 1),
(37, 52, 38, 1, '2024-03-05 03:29:31', 16, 156, 1),
(38, 52, 37, 1, '2024-03-05 03:33:36', 16, 153, 1),
(39, 52, 35, 1, '2024-03-05 03:50:44', 16, 73.8, 1),
(42, 52, 35, 1, '2024-03-05 09:14:23', 12, 98.4, 1),
(43, 52, 35, 0, '2024-03-05 09:18:24', 12, 98.4, 0),
(44, 52, 35, 1, '2024-03-05 09:38:20', 12, 98.4, 1),
(47, 52, 38, 0, '2024-03-20 13:39:21', 12, 208, 0),
(48, 52, 37, 1, '2024-03-20 13:50:15', 12, 204, 0),
(49, 52, 35, 0, '2024-03-20 14:00:13', 12, 98.4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id_role` int(10) NOT NULL,
  `nom_role` varchar(15) NOT NULL,
  `id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) NOT NULL,
  `nom` varchar(15) NOT NULL,
  `prenom` varchar(15) NOT NULL,
  `role` varchar(15) NOT NULL,
  `age` int(10) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `num_tel` int(8) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `resetcode` int(20) DEFAULT NULL,
  `confirmcode` varchar(25) DEFAULT NULL,
  `statuscode` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `role`, `age`, `image`, `num_tel`, `email`, `mdp`, `status`, `resetcode`, `confirmcode`, `statuscode`) VALUES
(49, 'nour', 'riahi', 'Enseignant', 0, 'lilia.jpg', 0, 'nourriahi@gmail.com', '$2a$10$nNpv1Bhov.ie3.lv6eKC6.H2ufU44.XKGAg0s.YzgxoBhP0fgXt5W', 'Desactive', 0, '0', NULL),
(50, 'test', 'test', 'Enseignant', NULL, NULL, NULL, 'dabyain@gmail.com', '$2a$10$tzkEjtIC0hfEHZYtiXPQv.4stI97mfDZVHcltpg0juYXhtzAVJpKy', 'Desactive', NULL, 'verified', NULL),
(52, 'razane', 'salem', 'Parent', NULL, NULL, NULL, 'Razane.Salem@esprit.tn', '$2a$10$D/JpGzdFcJMSuPqEyQPnp.DaG3uwmbyyJj.cyHWehKYxxFfMDrwui', 'Active', NULL, 'verified', NULL),
(53, 'aziz', 'laabidi', 'Eleve', NULL, NULL, NULL, 'mohamedazizlabidi25@gmail.com', '$2a$10$Z7bfxnchsqGwzgaq0Pb1GumuChcEICdZcSp1xmb/Qd3OQEYRPyZGC', 'Active', NULL, 'verified', NULL),
(54, 'razane', 'salem', 'Enseignant', NULL, NULL, NULL, 'lifeasrazane@gmail.com', '$2a$10$9uYaQApk9j5sD6xVz0oixOTo6bAoYhwbPVhl8SCIw7HdRNaaevfii', 'Active', NULL, 'verified', NULL),
(56, 'lilia', 'jemai', 'Enseignant', NULL, NULL, NULL, 'liliajemai00@gmail.com', '$2a$10$q2bUsCQOOtnkEp2UScNGAO7pVqY6/FhYzYr8/0z.t.esLO8J9Q9dO', 'Active', NULL, 'verified', NULL),
(57, 'lilia', 'jemai', 'Eleve', 0, NULL, 27438527, 'lilia.jemai@esprit.tn', '$2a$10$wZXaY1Bwh5YNdkH4P4VvselN9GbmnJahgBUXxKcrTOZYfU4qFJSyW', 'Active', 0, NULL, 155984),
(58, 'issaoui', 'nerine', 'Eleve', NULL, NULL, NULL, 'nesrine.aissaoui@esprit.tn', '$2a$10$0kdLd8BwIPhKYhfk0UvWWe..Hg.q9PzzE2QvBKzLsMOqbIo8rorIS', 'Active', NULL, NULL, NULL),
(59, 'issaoui', 'nesrine', 'Eleve', NULL, NULL, NULL, 'nesrineissaoui05@gmail.com', '$2a$10$sEVCX9X68shWdcS/APbcc.wuBVgBbhJLEa0rlsx9SZABuV6gbFFWW', 'Active', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_avis` (`cours_id`);

--
-- Indexes for table `categoriecodepromo`
--
ALTER TABLE `categoriecodepromo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `commentaire_ibfk_2` (`publication_id`),
  ADD KEY `utilisateur_id` (`utilisateur_id`);

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_category` (`idCategory`);

--
-- Indexes for table `courscategory`
--
ALTER TABLE `courscategory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `publication`
--
ALTER TABLE `publication`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utilisateur_id` (`utilisateur_id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_codepromo` (`id_codepromo`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_cours` (`id_cours`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`nom_role`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `avis`
--
ALTER TABLE `avis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT for table `categoriecodepromo`
--
ALTER TABLE `categoriecodepromo`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `cours`
--
ALTER TABLE `cours`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `courscategory`
--
ALTER TABLE `courscategory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `publication`
--
ALTER TABLE `publication`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `fk_avis` FOREIGN KEY (`cours_id`) REFERENCES `cours` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `commentaire_ibfk_1` FOREIGN KEY (`publication_id`) REFERENCES `publication` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `commentaire_ibfk_2` FOREIGN KEY (`utilisateur_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_category` FOREIGN KEY (`idCategory`) REFERENCES `courscategory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `publication_ibfk_1` FOREIGN KEY (`utilisateur_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id_codepromo`) REFERENCES `categoriecodepromo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_cours`) REFERENCES `cours` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `role_ibfk_1` FOREIGN KEY (`nom_role`) REFERENCES `user` (`role`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
