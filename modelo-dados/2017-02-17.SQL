ALTER TABLE `ces`.`visitante` 
ADD COLUMN `telefone_alternativo` CHAR(11) NULL AFTER `telefone`,
ADD COLUMN `email` VARCHAR(45) NULL AFTER `telefone_alternativo`;


CREATE TABLE IF NOT EXISTS `ces`.`endereco` (
  `id_visitante` INT NOT NULL,
  `cep` CHAR(8) NULL,
  `municipio` VARCHAR(80) NOT NULL,
  `bairro` VARCHAR(80) NOT NULL,
  `complemento` VARCHAR(50) NULL,
  `numero` VARCHAR(10) NULL,
  `uf` CHAR(2) NOT NULL,
  `referencia` VARCHAR(30) NULL,
  `logradouro` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id_visitante`),
  CONSTRAINT `fk_endereco_visitante`
    FOREIGN KEY (`id_visitante`)
    REFERENCES `ces`.`visitante` (`id_visitante`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;