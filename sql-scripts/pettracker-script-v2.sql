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
SELECT*FROM food_stock;
SELECT*FROM allergen;
SELECT*FROM food_allergen;
SELECT*FROM food_eaten;
SELECT*FROM pet;
SELECT*FROM pet_allergen;
SELECT*FROM weight_history;

-- -----------------------------------------------------
-- TEST DATA: Insert statements
-- -----------------------------------------------------

-- PET --
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Gab', 'dog', 'male', 3);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Chai', 'cat', 'female', 2);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Ash', 'dog', 'male', 4);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Coby', 'bird', 'female', 1);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Jacob', 'dog', 'male', 2);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Edward', 'bird', 'female', 5);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Bella', 'cat', 'female', 3);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Bogart', 'bird', 'male', 1);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('FPJ', 'dog', 'female', 4);
INSERT INTO pet (pet_name, species, gender, age) VALUES ('Coco Martin', 'bird', 'female', 3);


-- FOOD --
INSERT INTO food_stock 
            (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
            VALUES ('Royal Canin Shih Tzu Kibble', '2027-11-10', '2025-11-05', 800, 400, 5000);
INSERT INTO food_stock 
            (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
            VALUES ('Raw Chicken Breast', '2025-11-20', '2025-11-05', 598, 1200, 1000);
INSERT INTO food_stock 
            (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
            VALUES ('Pedigree Adult Beef & Liver Kibble', '2027-09-15', '2025-10-30', 1250, 350, 2000);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Cesar Wet Food Chicken Flavor', '2026-03-20', '2025-11-01', 55, 110, 150);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Boiled Pumpkin (Frozen)', '2025-12-05', '2025-11-04', 80, 26, 600);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Raw Ground Beef', '2025-11-18', '2025-11-05', 420, 250, 1200);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Royal Canin Sensitive Digestion Wet Pouch', '2026-04-18', '2025-11-06', 75, 90, 200);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Steamed Carrots (Frozen)', '2026-01-10', '2025-11-02', 60, 41, 500);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Chicken Liver (Raw)', '2025-11-14', '2025-11-05', 150, 135, 700);
INSERT INTO food_stock 
    (food_name, date_expiry, date_bought, cost, caloric_count, stock_qty)
	VALUES ('Vitality Lamb & Beef Kibble', '2028-02-12', '2025-10-27', 1899, 380, 10000);


INSERT INTO food_eaten (pet_id, food_id, date_time, serving_size) VALUES (?, ?, ?, ?);
-- ALLERGEN --
INSERT INTO allergen (description) VALUES ('dairy');
INSERT INTO allergen (description) VALUES ('chicken');
INSERT INTO allergen (description) VALUES ('nuts');
INSERT INTO allergen (description) VALUES ('shellfish');
INSERT INTO allergen (description) VALUES ('beef');
INSERT INTO allergen (description) VALUES ('wheat');
INSERT INTO allergen (description) VALUES ('soy');
INSERT INTO allergen (description) VALUES ('fish');
INSERT INTO allergen (description) VALUES ('corn');
INSERT INTO allergen (description) VALUES ('egg');

INSERT INTO food_allergen (food_id, allergen_id) VALUES (1, 6);  -- Royal Canin Shih Tzu Kibble, wheat
INSERT INTO food_allergen (food_id, allergen_id) VALUES (1, 9);  -- Royal Canin, corn

INSERT INTO food_allergen (food_id, allergen_id) VALUES (2, 2);  -- Raw Chicken Breast, chicken

INSERT INTO food_allergen (food_id, allergen_id) VALUES (3, 6);  -- Pedigree Kibble, wheat
INSERT INTO food_allergen (food_id, allergen_id) VALUES (3, 5);  -- Pedigree, beef

INSERT INTO food_allergen (food_id, allergen_id) VALUES (4, 2);  -- Cesar Wet Food , chicken
INSERT INTO food_allergen (food_id, allergen_id) VALUES (4, 10); -- Cesar, egg

INSERT INTO food_allergen (food_id, allergen_id) VALUES (6, 5);  -- Raw Ground Beef. beef

INSERT INTO food_allergen (food_id, allergen_id) VALUES (7, 2);  -- RC Sensitive Wet, chicken

INSERT INTO food_allergen (food_id, allergen_id) VALUES (10, 5); -- Vitality Lamb & Beef Kibble, beef

-- Gab (dog) – allergic to chicken and beef
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (1, 2);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (1, 5);

-- Chai (cat) – allergic to fish
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (2, 8);

-- Ash (dog) – allergic to wheat and soy
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (3, 6);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (3, 7);

-- Coby (bird) – allergic to egg
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (4, 10);

-- Jacob (dog) – allergic to dairy, beef, and chicken
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (5, 1);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (5, 5);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (5, 2);

-- Edward (bird) – poor bird allergic to corn
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (6, 9);

-- Bella (cat) – allergic to dairy and egg
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (7, 1);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (7, 10);

-- Bogart (bird) – allergic to nuts
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (8, 3);

-- FPJ (dog) – allergic to soy and wheat
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (9, 7);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (9, 6);

-- Coco Martin (bird) – allergic to corn and dairy
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (10, 9);
INSERT INTO pet_allergy (pet_id, allergen_id) VALUES (10, 1);

-- WEIGHT -- 
INSERT INTO weight_history (pet_name, species, gender, age) VALUES (?, ?, ?, ?)
















