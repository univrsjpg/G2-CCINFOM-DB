-- ============================================================================
-- DATABASE CREATION
-- ============================================================================
DROP SCHEMA IF EXISTS `pettracker` ; -- upd nalang schema name if evers
CREATE DATABASE IF NOT EXISTS `pettracker`;
USE `pettracker`;


-- ============================================================================
-- TABLE DEFINITIONS
-- ============================================================================

-- -----------------------------------------------------
-- TABLE: pettracker.pet
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pet` (
  `pet_id` int NOT NULL AUTO_INCREMENT,
  `pet_name` varchar(255) NOT NULL,
  `species` varchar(255) NOT NULL,
  `gender` enum('Male', 'Female') NOT NULL,
  `age` varchar(255) NOT NULL,
  `weight` double,
  PRIMARY KEY (`pet_id`)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.weight_history
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weight_history` (
  `weight_id` int NOT NULL AUTO_INCREMENT,
  `pet_id` int NOT NULL,
  `curr_weight` double NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`weight_id`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.food_stock
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_stock` (
  `food_id` int NOT NULL AUTO_INCREMENT,
  `food_name` varchar(255) NOT NULL,
  `date_expiry` date NOT NULL,
  `cost` double NOT NULL,
  `caloric_count` double NOT NULL,
  `date_bought` date NOT NULL,
  `stock_qty` double NOT NULL,
  PRIMARY KEY (`food_id`)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.food_eaten
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_eaten` (
  `pet_id` int NOT NULL,
  `food_id` int NOT NULL,
  `date_time` datetime NOT NULL,
  `serving_size` double NOT NULL,
  PRIMARY KEY (`pet_id`, `food_id`, `date_time`),
  FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  FOREIGN KEY (`food_id`) REFERENCES `food_stock` (`food_id`)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.allergen
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `allergen` (
	`allergen_id` int NOT NULL AUTO_INCREMENT,
    `description` varchar(400),
    PRIMARY KEY (`allergen_id`)
)
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.pet_allergy
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pet_allergy` (
	`allergen_id` int NOT NULL,
    `pet_id` int NOT NULL,
    PRIMARY KEY (`allergen_id`, `pet_id`),
    FOREIGN KEY (`allergen_id`) REFERENCES `allergen` (`allergen_id`),
    FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`)
)
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;

-- -----------------------------------------------------
-- TABLE: pettracker.food_allergen
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_allergen` (
	`allergen_id` int NOT NULL,
    `food_id` int NOT NULL,
    PRIMARY KEY (`allergen_id`, `food_id`),
    FOREIGN KEY (`allergen_id`) REFERENCES `allergen` (`allergen_id`),
    FOREIGN KEY (`food_id`) REFERENCES `food_stock` (`food_id`)
)
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4;















