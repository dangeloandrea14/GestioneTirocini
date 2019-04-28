-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Tirocinio
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Tirocinio
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Tirocinio` DEFAULT CHARACTER SET utf8 ;
USE `Tirocinio` ;

-- -----------------------------------------------------
-- Table `Tirocinio`.`Azienda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tirocinio`.`Azienda` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Sede` VARCHAR(45) NOT NULL,
  `IVA` VARCHAR(11) NOT NULL,
  `ForoCompetenza` VARCHAR(45) NOT NULL,
  `NomeResponsabile` VARCHAR(45) NOT NULL,
  `CognomeResponsabile` VARCHAR(45) NOT NULL,
  `TelefonoResponsabile` VARCHAR(13) NULL,
  `emailResponsabile` VARCHAR(45) NOT NULL,
  `NomeCognomeLegale` VARCHAR(55) NULL,
  `Voto` INT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `PathDocumento` VARCHAR(45) NULL DEFAULT 'Non ancora convenzionata.',
  `Convenzionata` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `IVA_UNIQUE` (`IVA` ASC) VISIBLE,
  UNIQUE INDEX `Nome_UNIQUE` (`Nome` ASC) VISIBLE,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  UNIQUE INDEX `PathDocumento_UNIQUE` (`PathDocumento` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Tirocinio`.`Studente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tirocinio`.`Studente` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `DataNascita` DATE NOT NULL,
  `LuogoNascita` VARCHAR(45) NOT NULL,
  `CF` VARCHAR(16) NOT NULL,
  `Handicap` TINYINT NOT NULL DEFAULT 0,
  `Email` VARCHAR(45) NOT NULL,
  `Ruolo` INT NULL,
  `Residenza` VARCHAR(45) NOT NULL,
  `CorsoLaurea` VARCHAR(45) NOT NULL,
  `NumeroCFU` INT NOT NULL,
  `Telefono` VARCHAR(14) NOT NULL,
  `Diploma` VARCHAR(45) NULL,
  `Laurea` VARCHAR(45) NULL,
  `Specializzazione` VARCHAR(45) NULL,
  `Password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Tirocinio`.`Offerta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tirocinio`.`Offerta` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `IDAzienda` INT NOT NULL,
  `Luogo` VARCHAR(45) NOT NULL,
  `Orari` VARCHAR(20) NULL,
  `Mesi/Ore` VARCHAR(45) NOT NULL,
  `Obiettivi` MEDIUMTEXT NOT NULL,
  `Modalità` VARCHAR(45) NOT NULL,
  `RimborsoSpese` VARCHAR(45) NULL DEFAULT 'Non è offerto alcun rimborso spese.',
  `Attiva` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  INDEX `AziendaHAOfferta_idx` (`IDAzienda` ASC) VISIBLE,
  CONSTRAINT `AziendaHAOfferta`
    FOREIGN KEY (`IDAzienda`)
    REFERENCES `Tirocinio`.`Azienda` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Tirocinio`.`Tirocinio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tirocinio`.`Tirocinio` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `IDAzienda` INT NOT NULL,
  `IDStudente` INT NOT NULL,
  `Inizio` DATE NOT NULL,
  `Fine` DATE NOT NULL,
  `SettoreInserimento` VARCHAR(45) NULL,
  `TempoDiAccesso` VARCHAR(45) NULL,
  `NumeroOre` VARCHAR(45) NULL,
  `TutoreUniversitario` VARCHAR(45) NULL,
  `TutoreAziendale` VARCHAR(45) NULL,
  `Attivo` TINYINT NOT NULL DEFAULT 1,
  `PathDocumento` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  INDEX `StudenteHATirocinio_idx` (`IDStudente` ASC) VISIBLE,
  INDEX `AziendaHATirocinio_idx` (`IDAzienda` ASC) VISIBLE,
  CONSTRAINT `StudenteHATirocinio`
    FOREIGN KEY (`IDStudente`)
    REFERENCES `Tirocinio`.`Studente` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `AziendaHATirocinio`
    FOREIGN KEY (`IDAzienda`)
    REFERENCES `Tirocinio`.`Azienda` (`ID`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Tirocinio`.`Valutazione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tirocinio`.`Valutazione` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Stelle` INT NOT NULL,
  `Commento` MEDIUMTEXT NULL,
  `IDStudente` INT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE,
  INDEX `StudenteHARecensione_idx` (`IDStudente` ASC) VISIBLE,
  CONSTRAINT `StudenteHARecensione`
    FOREIGN KEY (`IDStudente`)
    REFERENCES `Tirocinio`.`Studente` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
