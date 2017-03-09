-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ces
-- -----------------------------------------------------
-- CES - Controle de Entrada e Saída de Visitantes

-- -----------------------------------------------------
-- Schema ces
--
-- CES - Controle de Entrada e Saída de Visitantes
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ces` DEFAULT CHARACTER SET latin1 ;
USE `ces` ;

-- -----------------------------------------------------
-- Table `ces`.`motorista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`motorista` (
  `id_motorista` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `tipo` TINYINT NOT NULL COMMENT 'Indica se é técnico de transporte, servidor ou procurador',
  `matricula` CHAR(9) NOT NULL,
  PRIMARY KEY (`id_motorista`))
ENGINE = InnoDB
COMMENT = 'Motorista ou técnico de transporte do mpt';


-- -----------------------------------------------------
-- Table `ces`.`veiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`veiculo` (
  `placa` CHAR(7) NOT NULL,
  `marca` VARCHAR(30) NOT NULL,
  `modelo` VARCHAR(30) NOT NULL,
  `cor` VARCHAR(10) NOT NULL,
  `viatura_mp` TINYINT(1) NOT NULL DEFAULT 0,
  `id_motorista` INT NULL,
  PRIMARY KEY (`placa`),
  INDEX `fk_veiculo_motorista_idx` (`id_motorista` ASC),
  CONSTRAINT `fk_veiculo_motorista`
    FOREIGN KEY (`id_motorista`)
    REFERENCES `ces`.`motorista` (`id_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Veículo que adentrou a garagem';


-- -----------------------------------------------------
-- Table `ces`.`acesso_garagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`acesso_garagem` (
  `id_acesso_garagem` INT NOT NULL AUTO_INCREMENT,
  `entrada` DATETIME NOT NULL,
  `saida` DATETIME NULL,
  `data_hora_cadastro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `anotacao` VARCHAR(400) NULL,
  `id_motorista` INT NULL,
  `id_visita` INT NULL,
  `placa` CHAR(7) NOT NULL,
  `id_usuario` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_acesso_garagem`),
  INDEX `fk_acesso_garagem_motorista_idx` (`id_motorista` ASC),
  INDEX `fk_acesso_garagem_visita_idx` (`id_visita` ASC),
  INDEX `fk_acesso_garagem_veiculo_idx` (`placa` ASC),
  INDEX `fk_acesso_garagem_usuario_idx` (`id_usuario` ASC),
  CONSTRAINT `fk_acesso_garagem_motorista`
    FOREIGN KEY (`id_motorista`)
    REFERENCES `ces`.`motorista` (`id_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acesso_garagem_visita`
    FOREIGN KEY (`id_visita`)
    REFERENCES `ces`.`visita` (`id_visita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acesso_garagem_veiculo`
    FOREIGN KEY (`placa`)
    REFERENCES `ces`.`veiculo` (`placa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acesso_garagem_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `ces`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Servidores, membros ou visitantes que acessam a garagem com seus veículos';


-- -----------------------------------------------------
-- Table `ces`.`controle_motorista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`controle_motorista` (
  `id_controle_motorista` INT NOT NULL AUTO_INCREMENT,
  `id_motorista` INT NOT NULL,
  `data_hora` DATETIME NOT NULL,
  `fluxo` TINYINT NOT NULL COMMENT 'Indica se o foi uma entrada ou saída do motorista',
  INDEX `fk_controle_motorista_motorista_idx` (`id_motorista` ASC),
  PRIMARY KEY (`id_controle_motorista`),
  CONSTRAINT `fk_controle_motorista_motorista`
    FOREIGN KEY (`id_motorista`)
    REFERENCES `ces`.`motorista` (`id_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Controle de ponto do motorista';


-- -----------------------------------------------------
-- Table `ces`.`viagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`viagem` (
  `id_viagem` INT NOT NULL AUTO_INCREMENT,
  `anotacao` VARCHAR(400) NULL,
  `data_hora_cadastro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_motorista` INT NOT NULL,
  `placa` CHAR(7) NULL,
  `id_usuario` VARCHAR(20) NOT NULL,
  `id_controle_saida` INT NOT NULL,
  `id_controle_retorno` INT NULL,
  PRIMARY KEY (`id_viagem`),
  INDEX `fk_viagem_motorista_idx` (`id_motorista` ASC),
  INDEX `fk_viagem_veiculo_idx` (`placa` ASC),
  INDEX `fk_viagem_usuario_idx` (`id_usuario` ASC),
  INDEX `fk_viagem_controle_motorista1_idx` (`id_controle_saida` ASC),
  INDEX `fk_viagem_controle_motorista2_idx` (`id_controle_retorno` ASC),
  CONSTRAINT `fk_viagem_motorista`
    FOREIGN KEY (`id_motorista`)
    REFERENCES `ces`.`motorista` (`id_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_viagem_veiculo`
    FOREIGN KEY (`placa`)
    REFERENCES `ces`.`veiculo` (`placa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_viagem_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `ces`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_viagem_controle_motorista1`
    FOREIGN KEY (`id_controle_saida`)
    REFERENCES `ces`.`controle_motorista` (`id_controle_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_viagem_controle_motorista2`
    FOREIGN KEY (`id_controle_retorno`)
    REFERENCES `ces`.`controle_motorista` (`id_controle_motorista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `ces`.`passageiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ces`.`passageiro` (
  `nome` VARCHAR(200) NOT NULL,
  `matricula` VARCHAR(9) NULL,
  `id_viagem` INT NOT NULL,
  PRIMARY KEY (`nome`, `id_viagem`),
  INDEX `fk_passageiro_viagem_idx` (`id_viagem` ASC),
  CONSTRAINT `fk_passageiro_viagem`
    FOREIGN KEY (`id_viagem`)
    REFERENCES `ces`.`viagem` (`id_viagem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
