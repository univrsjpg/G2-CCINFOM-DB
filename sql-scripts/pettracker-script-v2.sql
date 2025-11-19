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
  `date_bought` date NOT NULL,
  `date_expiry` date NOT NULL,
  `cost` double NOT NULL,
  `caloric_count` double NOT NULL,
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


USE pettracker;

-- -----------------------------------------------------
-- TEST DATA: Insert statements
-- -----------------------------------------------------

-- PET --
INSERT INTO pet (pet_name, species, gender, age) VALUES
	('Gab', 'dog', 'male', 3),
    ('Chai', 'cat', 'female', 2),
    ('Ash', 'dog', 'male', 4),
    ('Coby', 'bird', 'female', 1),
    ('Jacob', 'dog', 'male', 2),
    ('Edward', 'bird', 'female', 5),
    ('Bella', 'cat', 'female', 3),
    ('Bogart', 'bird', 'male', 1),
    ('FPJ', 'dog', 'female', 4),
    ('Coco Martin', 'bird', 'female', 3);


-- FOOD --
INSERT INTO food_stock 
            (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty) VALUES 
	('Royal Canin Shih Tzu Kibble', '2027-11-10', '2025-11-05', 800, 400, 5000),
    ('Raw Chicken Breast', '2025-11-20', '2025-11-05', 598, 1200, 1000),
    ('Pedigree Adult Beef & Liver Kibble', '2027-09-15', '2025-10-30', 1250, 350, 2000),
    ('Cesar Wet Food Chicken Flavor', '2026-03-20', '2025-11-01', 55, 110, 150),
    ('Boiled Pumpkin (Frozen)', '2025-12-05', '2025-11-04', 80, 26, 600),
    ('Raw Ground Beef', '2025-11-18', '2025-11-05', 420, 250, 1200),
    ('Royal Canin Sensitive Digestion Wet Pouch', '2026-04-18', '2025-11-06', 75, 90, 200),
    ('Steamed Carrots (Frozen)', '2026-01-10', '2025-11-02', 60, 41, 500),
    ('Chicken Liver (Raw)', '2025-11-14', '2025-11-05', 150, 135, 700),
    ('Vitality Lamb & Beef Kibble', '2028-02-12', '2025-10-27', 1899, 380, 10000);

-- FOOD EATEN --
INSERT INTO food_eaten (pet_id, food_id, date_time, serving_size) VALUES
	(1, 3, '2008-11-11 13:27:45', 5),
    (2, 3, '2003-01-07 08:10:51', 4),
    (3, 4, '2005-01-22 00:22:41', 3),
    (2, 2, '2008-03-30 14:23:42', 2),
    (1, 2, '2008-02-20 16:58:31', 1),
    (1, 5, '2014-12-10 02:59:55', 1),
    (2, 6, '2020-08-26 01:53:32', 2),
    (3, 7, '2013-06-19 16:45:11', 3),
    (2, 8, '2009-07-13 04:52:32', 4),
    (4, 9, '2012-04-16 09:21:13', 5);

-- ALLERGEN --
INSERT INTO allergen (description) VALUES
	('dairy'),
    ('chicken'),
    ('nuts'),
    ('shellfish'),
    ('beef'),
    ('wheat'),
    ('soy'),
    ('fish'),
    ('corn'),
    ('egg');

INSERT INTO food_allergen (food_id, allergen_id) VALUES
	(1, 6),  -- Royal Canin Shih Tzu Kibble, wheat
	(1, 9),  -- Royal Canin, corn

	(2, 2),  -- Raw Chicken Breast, chicken

	(3, 6),  -- Pedigree Kibble, wheat
	(3, 5),  -- Pedigree, beef

	(4, 2),  -- Cesar Wet Food , chicken
	(4, 10), -- Cesar, egg

	(6, 5),  -- Raw Ground Beef. beef

	(7, 2),  -- RC Sensitive Wet, chicken

	(10, 5); -- Vitality Lamb & Beef Kibble, beef

-- Gab (dog) – allergic to chicken and beef
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(1, 2),
    (1, 5);

-- Chai (cat) – allergic to fish
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (2, 8);

-- Ash (dog) – allergic to wheat and soy
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(3, 6),
    (3, 7);

-- Coby (bird) – allergic to egg
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (4, 10);

-- Jacob (dog) – allergic to dairy, beef, and chicken
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(5, 1),
    (5, 5),
    (5, 2);

-- Edward (bird) – poor bird allergic to corn
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (6, 9);

-- Bella (cat) – allergic to dairy and egg
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(7, 1),
    (7, 10);

-- Bogart (bird) – allergic to nuts
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (8, 3);

-- FPJ (dog) – allergic to soy and wheat
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(9, 7),
    (9, 6);

-- Coco Martin (bird) – allergic to corn and dairy
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES
	(10, 9),
    (10, 1);

-- WEIGHT -- 
INSERT INTO weight_history (pet_id, curr_weight, `date`) VALUES
	(1, 40, '2025-05-04'),
    (2, 98, '2025-08-06'),
    (3, 192, '2006-5-09'),
    (4, 56, '2001-10-03'),
    (5, 126, '2025-07-30'),
    (1, 12, '2027-09-04'),
    (2, 65, '2029-09-06'),
    (3, 45, '2009-6-09'),
    (4, 200, '2012-11-03'),
    (5, 94, '2022-08-30');