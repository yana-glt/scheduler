SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

CREATE SCHEMA IF NOT EXISTS `school_schedule` DEFAULT CHARACTER SET utf8 ;
USE `school_schedule` ;

CREATE TABLE IF NOT EXISTS `school_schedule`.`students_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `school_schedule`.`teacher` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `school_schedule`.`subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `teacher_id` INT NOT NULL,
  PRIMARY KEY (`id`, `teacher_id`),
  INDEX `fk_subject_teacher_idx` (`teacher_id` ASC),
  CONSTRAINT `fk_subject_teacher`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `school_schedule`.`teacher` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `school_schedule`.`students_group_has_subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subject_id` INT NOT NULL,
  `students_group_id` INT NOT NULL,
  `amount_of_hours` INT NULL,
  PRIMARY KEY (`id`, `subject_id`, `students_group_id`),
  INDEX `fk_subject_has_student_group_student_group1_idx` (`students_group_id` ASC),
  INDEX `fk_subject_has_student_group_subject1_idx` (`subject_id` ASC),
  CONSTRAINT `fk_subject_has_student_group_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `school_schedule`.`subject` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_subject_has_student_group_student_group1`
    FOREIGN KEY (`students_group_id`)
    REFERENCES `school_schedule`.`students_group` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;