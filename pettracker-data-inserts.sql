-- ============================================================================
-- PETTRACKER - MySQL Data Insert
-- ============================================================================
-- Original Database: pettracker
-- Description: Database schema to track pets and their food intake, their food
-- 				stock, their weight, and allergies
-- Version: MySQL 5.7+
-- Note: Run pettracker-structure.sql first to create the database schema
-- ============================================================================

-- Set MySQL session variables
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

-- Character set configuration
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Use the northwind database
USE `pettracker`;

-- ============================================================================
-- DISABLE FOREIGN KEY CHECKS FOR DATA LOADING
-- ============================================================================
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================================
-- TABLE DATA INSERTS
-- ============================================================================

-- Insert data for table `continents`
INSERT INTO `pet` (`pet_name`, `species`, `gender`, `age`, `weight`) VALUES
  ('Penny', 'Cat', 'Female', 5, null),
  ('Dooey', 'Dog', 'Male', 2, 9),
  ('Buddy', 'Cat', 'Female', 9, 8),
  ('Linus', 'Bird', 'Male', 1, null),
  ('Molly', 'Dog', 'Female', 6, 13),
  ('Bo', 'Bird', 'Female', 4, 0.45),
  ('Bridget', 'Dog', 'Female', 13, null),
  ('Bernard', 'Cat', 'Male', 12, 11),
  ('Mippy', 'Rabbit', 'Female', 8, 2),
  ('Waldo', 'Dog', 'Male', 9, 12);

-- ============================================================================
-- RE-ENABLE FOREIGN KEY CHECKS
-- ============================================================================
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- FINALIZE TRANSACTION
-- ============================================================================

COMMIT;

-- Restore MySQL session variables
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
