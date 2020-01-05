-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 05, 2020 at 12:13 PM
-- Server version: 5.7.27
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbpenjualan`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbbarang`
--

CREATE TABLE `tbbarang` (
  `IdBarang` int(11) NOT NULL,
  `NamaBarang` varchar(100) NOT NULL,
  `HargaBarang` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbbarang`
--

INSERT INTO `tbbarang` (`IdBarang`, `NamaBarang`, `HargaBarang`) VALUES
(1, 'Sabun', 5000),
(3, 'Shampo', 20000),
(4, 'Pepsodent', 3000),
(5, 'Kinder Joy', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `tbdetailpenjualan`
--

CREATE TABLE `tbdetailpenjualan` (
  `IdDetailPenjualan` int(11) NOT NULL,
  `IdPenjualan` int(11) NOT NULL,
  `IdBarang` int(11) NOT NULL,
  `JumlahBarang` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbdetailpenjualan`
--

INSERT INTO `tbdetailpenjualan` (`IdDetailPenjualan`, `IdPenjualan`, `IdBarang`, `JumlahBarang`) VALUES
(37, 39, 1, 3),
(38, 39, 4, 2),
(39, 40, 1, 2),
(40, 40, 3, 3),
(41, 41, 3, 1),
(42, 41, 3, 4),
(43, 41, 1, 0),
(45, 42, 1, 2),
(46, 43, 3, 2),
(47, 43, 1, 3),
(48, 43, 4, 10);

-- --------------------------------------------------------

--
-- Table structure for table `tbpenjualan`
--

CREATE TABLE `tbpenjualan` (
  `IdPenjualan` int(11) NOT NULL,
  `IdUser` int(11) NOT NULL,
  `StatusPenjualan` enum('TRUE','FALSE') NOT NULL,
  `TanggalTransaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbpenjualan`
--

INSERT INTO `tbpenjualan` (`IdPenjualan`, `IdUser`, `StatusPenjualan`, `TanggalTransaksi`) VALUES
(39, 1, 'TRUE', '2019-12-24'),
(40, 1, 'TRUE', '2019-12-24'),
(41, 1, 'TRUE', '2019-12-24'),
(42, 1, 'TRUE', '2019-12-24'),
(43, 1, 'TRUE', '2020-01-04');

-- --------------------------------------------------------

--
-- Table structure for table `tbuser`
--

CREATE TABLE `tbuser` (
  `IdUser` int(11) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` tinytext NOT NULL,
  `NamaUser` varchar(100) NOT NULL,
  `AlamatUser` text NOT NULL,
  `NoTelpUser` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbuser`
--

INSERT INTO `tbuser` (`IdUser`, `Username`, `Password`, `NamaUser`, `AlamatUser`, `NoTelpUser`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Anthony Lee', 'Denpasar', '087760303967'),
(3, 'mamat', '651797b2026d502f76cb2ad0111293ae', 'adads', '3asdasd', '12312');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbbarang`
--
ALTER TABLE `tbbarang`
  ADD PRIMARY KEY (`IdBarang`);

--
-- Indexes for table `tbdetailpenjualan`
--
ALTER TABLE `tbdetailpenjualan`
  ADD PRIMARY KEY (`IdDetailPenjualan`);

--
-- Indexes for table `tbpenjualan`
--
ALTER TABLE `tbpenjualan`
  ADD PRIMARY KEY (`IdPenjualan`);

--
-- Indexes for table `tbuser`
--
ALTER TABLE `tbuser`
  ADD PRIMARY KEY (`IdUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbbarang`
--
ALTER TABLE `tbbarang`
  MODIFY `IdBarang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbdetailpenjualan`
--
ALTER TABLE `tbdetailpenjualan`
  MODIFY `IdDetailPenjualan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `tbpenjualan`
--
ALTER TABLE `tbpenjualan`
  MODIFY `IdPenjualan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `tbuser`
--
ALTER TABLE `tbuser`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
