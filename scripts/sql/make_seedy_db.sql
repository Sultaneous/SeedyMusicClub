-- Database Schema and Population Script
-- Karim Sultan, 2017-11-21

-- Run this script via MySQL Workbench (File | Run SQL Script...) OR
-- you can also run it from MySQL shell (>script [path to script]

-- This script will:
-- 1. Create a database called "seedy_db"
-- 2. Create the four major tables (cds, accounts, orders, orderitems)
-- 3. Populate the cds table with a catalog of cds

-- Note that each CD has properties, cover art and a 15 sec music sample.


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema seedy_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `seedy_db` DEFAULT CHARACTER SET utf8 ;
USE `seedy_db` ;

-- -----------------------------------------------------
-- Drop Tables
-- PLEASE do not change the order below.  It has to be 
-- dropped in this order to adhere to foreign key constraints.
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seedy_db`.`orderitems` ;
DROP TABLE IF EXISTS `seedy_db`.`orders` ;
DROP TABLE IF EXISTS `seedy_db`.`accounts` ;
DROP TABLE IF EXISTS `seedy_db`.`cds` ;


-- -----------------------------------------------------
-- Table `seedy_db`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seedy_db`.`accounts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `country` VARCHAR(255) NULL DEFAULT NULL,
  `date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `firstName` VARCHAR(255) NULL DEFAULT NULL,
  `lastName` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  `postalCode` VARCHAR(255) NULL DEFAULT NULL,
  `province` VARCHAR(255) NULL DEFAULT NULL,
  `street` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  UNIQUE (username),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seedy_db`.`cds`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seedy_db`.`cds` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `band` VARCHAR(255) NULL DEFAULT NULL,
  `cover` VARCHAR(255) NULL DEFAULT NULL,
  `date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `genre` VARCHAR(255) NULL DEFAULT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT(11) NOT NULL,
  `sample` VARCHAR(255) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seedy_db`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seedy_db`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `accountId` INT(11) NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  `date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seedy_db`.`orderitems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seedy_db`.`orderitems` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cdid` INT(11) NOT NULL,
  `orderid` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_m5lkunghh06tt5g98d6madcw3`
    FOREIGN KEY (`orderid`)
    REFERENCES `seedy_db`.`orders` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK_m5lkunghh06tt5g98d6madcw3` ON `seedy_db`.`orderitems` (`orderid` ASC);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Now we insert a couple of dummy records for tables
-- -----------------------------------------------------

-- 3 Accounts
INSERT INTO `seedy_db`.`accounts` (`city`, `country`, `firstName`, `lastName`, `password`, `phone`, 
`postalCode`, `province`, `street`, `username`, `email`) VALUES ('Ottawa', 'Canada', 'admin', 'istrator', 'admin!', 
'613-555-5555', 'K1N 6N5', 'Ontario', '55 Laurier Avenue West', 'admin', 'admin@seedymusic.club');

INSERT INTO `seedy_db`.`accounts` (`city`, `country`, `firstName`, `lastName`, `password`, `phone`, 
`postalCode`, `province`, `street`, `username`, `email`) VALUES ('Ottawa', 'Canada', 'Simple', 'Simon', 
'password', '613-862-2229', 'A1B 2C3', 'Ontario', '314 Pi Street', 'simplesimon', 'simplesimon@thepieman.ca');

INSERT INTO `seedy_db`.`accounts` (`city`, `country`, `firstName`, `lastName`, `password`, `phone`, 
`postalCode`, `province`, `street`, `username`, `email`) VALUES ('Beijing', 'China', 'Ali', 'Baba', 'aAS3Jf4', 
'172-6482-1443', '100000', 'Hebei', '8 Cyber Road', 'alibaba', 'ali_babs7@kaching.cn');

-- 2 Orders
INSERT INTO `seedy_db`.`orders` (`accountId`, `status`) VALUES ('2', 'shipped');
INSERT INTO `seedy_db`.`orders` (`accountId`, `status`) VALUES ('3', 'open');

-- With 4 OrderItems in first and 7 in second 
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('22', '1');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('38', '1');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('99', '1');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('102', '1');

INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('7', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('49', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('51', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('64', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('127', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('136', '2');
INSERT INTO `seedy_db`.`orderitems` (`cdid`, `orderid`) VALUES ('144', '2');

-- -----------------------------------------------------
-- The following commands insert the CD catalog data.
-- Do not edit these lines!
-- They are created automatically by the Genesis tool.
-- -----------------------------------------------------

-- [START] Genesis Data --

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Finding His Hood",
   "Pilot of Summer",
   "hiphop",
   "cover_gwovuw.jpg",
   "sample_gwovuw.mp3",
   "50",
   "11.99",
   "2017-10-24 17:31:42"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Dark Smile",
   "Juice",
   "blues",
   "cover_phssvz.jpg",
   "sample_phssvz.mp3",
   "48",
   "15.99",
   "2017-10-24 17:31:50"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Bittersweet Fires",
   "Switch of Boogie",
   "rock",
   "cover_mfimpm.jpg",
   "sample_mfimpm.mp3",
   "22",
   "15.99",
   "2017-10-24 17:31:59"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I'm Addicted To You",
   "Smooth Mystery",
   "hiphop",
   "cover_xbgcic.jpg",
   "sample_xbgcic.mp3",
   "24",
   "17.99",
   "2017-10-24 17:32:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, We've Only Just Begun",
   "Brass",
   "country",
   "cover_vfolbz.jpg",
   "sample_vfolbz.mp3",
   "29",
   "17.99",
   "2017-10-24 17:32:16"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Hates I'm A Lone Wolf",
   "Millennium Beats",
   "country",
   "cover_lcmqha.jpg",
   "sample_lcmqha.mp3",
   "18",
   "13.99",
   "2017-10-24 17:32:25"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Hates She Cannot Go On",
   "Game of Action",
   "blues",
   "cover_kxvxxz.jpg",
   "sample_kxvxxz.mp3",
   "33",
   "19.99",
   "2017-10-24 17:32:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Happy Delights",
   "Pink Pretender",
   "rnb",
   "cover_nfelnq.jpg",
   "sample_nfelnq.mp3",
   "45",
   "7.99",
   "2017-10-24 17:32:40"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Frenzy Of Your Heart",
   "Initiative of Hustle",
   "latin",
   "cover_emyajn.jpg",
   "sample_emyajn.mp3",
   "22",
   "7.99",
   "2017-10-24 17:32:49"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Without You",
   "Daydream",
   "rock",
   "cover_pupnto.jpg",
   "sample_pupnto.mp3",
   "19",
   "18.99",
   "2017-10-24 17:32:57"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Love I'm On Fire",
   "Order of Twilight",
   "jazz",
   "cover_egxsio.jpg",
   "sample_egxsio.mp3",
   "45",
   "17.99",
   "2017-10-24 17:33:05"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Said You're Over Me",
   "Menace",
   "country",
   "cover_bsgfub.jpg",
   "sample_bsgfub.mp3",
   "34",
   "16.99",
   "2017-10-24 17:33:13"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Black Paradise",
   "Maroon Cavalry",
   "rock",
   "cover_frpolc.jpg",
   "sample_frpolc.mp3",
   "14",
   "11.99",
   "2017-10-24 17:33:20"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lost Smiles",
   "Destiny",
   "rock",
   "cover_fihzpc.jpg",
   "sample_fihzpc.mp3",
   "49",
   "9.99",
   "2017-10-24 17:33:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I Got A Feeling",
   "Mindless Angels",
   "latin",
   "cover_jmyegk.jpg",
   "sample_jmyegk.mp3",
   "46",
   "8.99",
   "2017-10-24 17:33:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He's Charming",
   "Spectacle of Hysteria",
   "jazz",
   "cover_bilqvj.jpg",
   "sample_bilqvj.mp3",
   "40",
   "7.99",
   "2017-10-24 17:33:44"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Thinking Of My Ways",
   "Angels of Plasma",
   "rock",
   "cover_suudvr.jpg",
   "sample_suudvr.mp3",
   "13",
   "16.99",
   "2017-10-24 17:33:54"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Whisper My Heart",
   "Stereo Games",
   "country",
   "cover_kihwyl.jpg",
   "sample_kihwyl.mp3",
   "36",
   "14.99",
   "2017-10-24 17:34:02"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Memories Of My Beginning",
   "Drums of the Year",
   "jazz",
   "cover_hxnqjz.jpg",
   "sample_hxnqjz.mp3",
   "33",
   "6.99",
   "2017-10-24 17:34:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I Need To Be Alone",
   "Impulse of Jade",
   "rock",
   "cover_ukdvpi.jpg",
   "sample_ukdvpi.mp3",
   "49",
   "11.99",
   "2017-10-24 17:34:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "For My World",
   "Crunch",
   "rock",
   "cover_hgurmg.jpg",
   "sample_hgurmg.mp3",
   "13",
   "5.99",
   "2017-10-24 17:34:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Kiss His Fire",
   "Scrap",
   "electronic",
   "cover_aiwgvt.jpg",
   "sample_aiwgvt.mp3",
   "26",
   "14.99",
   "2017-10-24 17:34:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I'll Take You On A Journey",
   "Network",
   "rock",
   "cover_aopbip.jpg",
   "sample_aopbip.mp3",
   "27",
   "7.99",
   "2017-10-24 17:34:45"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I Think I Like You",
   "Malady",
   "country",
   "cover_guyhgb.jpg",
   "sample_guyhgb.mp3",
   "50",
   "4.99",
   "2017-10-24 17:34:55"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Altered Vibes",
   "Astronauts of Focus",
   "hiphop",
   "cover_fozwfm.jpg",
   "sample_fozwfm.mp3",
   "42",
   "17.99",
   "2017-10-24 17:35:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Sweetie, Come With Me",
   "Figures of the Void",
   "country",
   "cover_loikmh.jpg",
   "sample_loikmh.mp3",
   "13",
   "18.99",
   "2017-10-24 17:35:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Sweetie, I'm Coming Home",
   "Prize of Felicity",
   "country",
   "cover_bdxzvr.jpg",
   "sample_bdxzvr.mp3",
   "11",
   "9.99",
   "2017-10-24 17:35:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Best Story",
   "Nerve",
   "country",
   "cover_cvpzba.jpg",
   "sample_cvpzba.mp3",
   "27",
   "5.99",
   "2017-10-24 17:35:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Beastly Stranger",
   "Serenity",
   "rock",
   "cover_xakgoc.jpg",
   "sample_xakgoc.mp3",
   "25",
   "19.99",
   "2017-10-24 17:35:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Dearest Miracles",
   "Passenger of the Groove",
   "jazz",
   "cover_flhdrt.jpg",
   "sample_flhdrt.mp3",
   "36",
   "7.99",
   "2017-10-24 17:35:42"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think She's Family",
   "Minister",
   "rock",
   "cover_tszqnb.jpg",
   "sample_tszqnb.mp3",
   "12",
   "9.99",
   "2017-10-24 17:35:50"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Sunset For A Moment",
   "Ghost of Summer",
   "rock",
   "cover_xromuf.jpg",
   "sample_xromuf.mp3",
   "13",
   "15.99",
   "2017-10-24 17:35:58"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Talk For A Lost Soul",
   "Sunset of Riddles",
   "rock",
   "cover_ajqnbx.jpg",
   "sample_ajqnbx.mp3",
   "48",
   "11.99",
   "2017-10-24 17:36:06"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Remember His Place",
   "Trance of Today",
   "country",
   "cover_arvqzh.jpg",
   "sample_arvqzh.mp3",
   "37",
   "5.99",
   "2017-10-24 17:36:14"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Stormy Soul",
   "System of Soul",
   "hiphop",
   "cover_bayite.jpg",
   "sample_bayite.mp3",
   "16",
   "4.99",
   "2017-10-24 17:36:22"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Loves She's All Alone",
   "Unnamed Zombies",
   "rnb",
   "cover_nkoepo.jpg",
   "sample_nkoepo.mp3",
   "22",
   "11.99",
   "2017-10-24 17:36:29"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Memories Of My Money",
   "Attitude of Danger",
   "hiphop",
   "cover_xjcyng.jpg",
   "sample_xjcyng.mp3",
   "39",
   "13.99",
   "2017-10-24 17:36:37"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Thinks I'm Tainted",
   "Bravado",
   "world",
   "cover_qbhqlv.jpg",
   "sample_qbhqlv.mp3",
   "18",
   "9.99",
   "2017-10-24 17:36:45"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "For His Machine",
   "Rapture",
   "electronic",
   "cover_xtxguo.jpg",
   "sample_xtxguo.mp3",
   "35",
   "12.99",
   "2017-10-24 17:36:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Hope She's Bad For You",
   "Liberty",
   "hiphop",
   "cover_nznzlr.jpg",
   "sample_nznzlr.mp3",
   "47",
   "6.99",
   "2017-10-24 17:37:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Walking Moves",
   "Zombies",
   "electronic",
   "cover_qkzxmp.jpg",
   "sample_qkzxmp.mp3",
   "20",
   "9.99",
   "2017-10-24 17:37:07"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Wait For Your Money",
   "Bittersweet",
   "hiphop",
   "cover_ibswlf.jpg",
   "sample_ibswlf.mp3",
   "37",
   "10.99",
   "2017-10-24 17:37:14"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Rhythm Of My Childhood",
   "Voyage",
   "jazz",
   "cover_kcroxo.jpg",
   "sample_kcroxo.mp3",
   "26",
   "6.99",
   "2017-10-24 17:37:22"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Darling, I Don't Know You At All",
   "Children of Shuffle",
   "rnb",
   "cover_yxzbja.jpg",
   "sample_yxzbja.mp3",
   "42",
   "8.99",
   "2017-10-24 17:37:30"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Said You Rock My World",
   "Explosion of Protest",
   "rock",
   "cover_vuckxu.jpg",
   "sample_vuckxu.mp3",
   "16",
   "6.99",
   "2017-10-24 17:37:37"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Thinks I Need You",
   "Race of Terror",
   "electronic",
   "cover_bsezzq.jpg",
   "sample_bsezzq.mp3",
   "36",
   "19.99",
   "2017-10-24 17:37:44"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think I'm Done",
   "Animal Artillery",
   "rock",
   "cover_tavacq.jpg",
   "sample_tavacq.mp3",
   "34",
   "14.99",
   "2017-10-24 17:37:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Enjoy The Show",
   "Timber",
   "hiphop",
   "cover_juyqhu.jpg",
   "sample_juyqhu.mp3",
   "42",
   "8.99",
   "2017-10-24 17:38:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Loves I'm In Love",
   "Mutant of Sound",
   "rock",
   "cover_xqjrlz.jpg",
   "sample_xqjrlz.mp3",
   "10",
   "13.99",
   "2017-10-24 17:38:07"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Babe, Hold Me",
   "Ambush Fellow",
   "country",
   "cover_tlmjju.jpg",
   "sample_tlmjju.mp3",
   "35",
   "10.99",
   "2017-10-24 17:38:15"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Moments Of My Past",
   "Innocence of Prejudice",
   "rock",
   "cover_fprsqj.jpg",
   "sample_fprsqj.mp3",
   "32",
   "17.99",
   "2017-10-24 17:38:23"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Hates She's My Sister",
   "Whip of Honor",
   "hiphop",
   "cover_ekeueh.jpg",
   "sample_ekeueh.mp3",
   "46",
   "14.99",
   "2017-10-24 17:38:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Frozen Life",
   "Figment of Sugar",
   "blues",
   "cover_wsfzun.jpg",
   "sample_wsfzun.mp3",
   "20",
   "18.99",
   "2017-10-24 17:38:40"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Thoughts Of His Voice",
   "Cycle",
   "rnb",
   "cover_cucbqw.jpg",
   "sample_cucbqw.mp3",
   "12",
   "14.99",
   "2017-10-24 17:38:47"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Losin' His Soul",
   "Equal Vibrations",
   "hiphop",
   "cover_xuzfdx.jpg",
   "sample_xuzfdx.mp3",
   "31",
   "13.99",
   "2017-10-24 17:38:56"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Medal For Yesterday",
   "Embrace of the Sun",
   "country",
   "cover_vjhtqv.jpg",
   "sample_vjhtqv.mp3",
   "34",
   "5.99",
   "2017-10-24 17:39:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Said He Did It",
   "Cure of Gloom",
   "hiphop",
   "cover_aorgpx.jpg",
   "sample_aorgpx.mp3",
   "50",
   "15.99",
   "2017-10-24 17:39:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Storm Choices",
   "Riot",
   "country",
   "cover_dmbzcb.jpg",
   "sample_dmbzcb.mp3",
   "24",
   "13.99",
   "2017-10-24 17:39:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Thinks She Loves Me",
   "Temper",
   "rock",
   "cover_zxegde.jpg",
   "sample_zxegde.mp3",
   "22",
   "8.99",
   "2017-10-24 17:39:28"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lost Things",
   "State of Wrath",
   "hiphop",
   "cover_msavwx.jpg",
   "sample_msavwx.mp3",
   "33",
   "18.99",
   "2017-10-24 17:39:36"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Dear Life",
   "Abacus",
   "hiphop",
   "cover_zmcifs.jpg",
   "sample_zmcifs.mp3",
   "37",
   "17.99",
   "2017-10-24 17:39:44"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Crazy Game",
   "Tourists of Rhythm",
   "electronic",
   "cover_ksuxlw.jpg",
   "sample_ksuxlw.mp3",
   "12",
   "9.99",
   "2017-10-24 17:39:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Home Of Your Girl",
   "Sisterhood of Spice",
   "country",
   "cover_slyrjf.jpg",
   "sample_slyrjf.mp3",
   "28",
   "6.99",
   "2017-10-24 17:40:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Stop Crazies",
   "Exiles",
   "blues",
   "cover_bndodj.jpg",
   "sample_bndodj.mp3",
   "43",
   "15.99",
   "2017-10-24 17:40:09"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I'm On The Wild Side",
   "Blaze of Jeopardy",
   "rock",
   "cover_sailik.jpg",
   "sample_sailik.mp3",
   "13",
   "14.99",
   "2017-10-24 17:40:17"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Love I'm Awesome",
   "Dreams of Influence",
   "electronic",
   "cover_lmezmt.jpg",
   "sample_lmezmt.mp3",
   "45",
   "10.99",
   "2017-10-24 17:40:25"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Rock 'N Roll Whispers",
   "Solitary Force",
   "rock",
   "cover_bfcuuz.jpg",
   "sample_bfcuuz.mp3",
   "18",
   "8.99",
   "2017-10-24 17:40:34"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "A Mic Of A Chance",
   "Barrage",
   "hiphop",
   "cover_pjekwc.jpg",
   "sample_pjekwc.mp3",
   "33",
   "6.99",
   "2017-10-24 17:40:42"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Won't Stay Tonight",
   "Messenger",
   "country",
   "cover_jxzhhb.jpg",
   "sample_jxzhhb.mp3",
   "44",
   "11.99",
   "2017-10-24 17:40:50"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I Got A Feeling",
   "Mercury",
   "rock",
   "cover_fudrez.jpg",
   "sample_fudrez.mp3",
   "36",
   "11.99",
   "2017-10-24 17:41:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Silence For Everything",
   "Karma",
   "rock",
   "cover_srqnhw.jpg",
   "sample_srqnhw.mp3",
   "32",
   "8.99",
   "2017-10-24 17:41:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Let's Go Wild",
   "Blaze of Kings",
   "rock",
   "cover_aawqlg.jpg",
   "sample_aawqlg.mp3",
   "50",
   "18.99",
   "2017-10-24 17:41:16"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lightning Promises",
   "Jam",
   "electronic",
   "cover_jbkdcg.jpg",
   "sample_jbkdcg.mp3",
   "18",
   "11.99",
   "2017-10-24 17:41:24"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Blissful Dreams",
   "Salvo",
   "jazz",
   "cover_oxesrf.jpg",
   "sample_oxesrf.mp3",
   "50",
   "15.99",
   "2017-10-24 17:41:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She said We Can't Stay",
   "Flight of Jubilation",
   "blues",
   "cover_zmcptn.jpg",
   "sample_zmcptn.mp3",
   "23",
   "4.99",
   "2017-10-24 17:41:40"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Got The Moves",
   "Bloody Faith",
   "latin",
   "cover_supfkz.jpg",
   "sample_supfkz.mp3",
   "27",
   "19.99",
   "2017-10-24 17:41:48"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Speed Of Her Ignorance",
   "Guest",
   "rnb",
   "cover_yeqaoy.jpg",
   "sample_yeqaoy.mp3",
   "15",
   "9.99",
   "2017-10-24 17:41:56"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Can't Sleep",
   "Bad World",
   "blues",
   "cover_zhznfk.jpg",
   "sample_zhznfk.mp3",
   "15",
   "8.99",
   "2017-10-24 17:42:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Inventions Of My Moment",
   "Peril",
   "jazz",
   "cover_kuizos.jpg",
   "sample_kuizos.mp3",
   "47",
   "7.99",
   "2017-10-24 17:42:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Decisions Of His Imagination",
   "Pajama Anarchy",
   "rnb",
   "cover_hkcrqp.jpg",
   "sample_hkcrqp.mp3",
   "48",
   "9.99",
   "2017-10-24 17:42:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Money For Your Time",
   "Animus",
   "rock",
   "cover_enyxon.jpg",
   "sample_enyxon.mp3",
   "40",
   "9.99",
   "2017-10-24 17:42:28"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Flowers For Her Rodeo",
   "Melody Hearts",
   "country",
   "cover_vcdqce.jpg",
   "sample_vcdqce.mp3",
   "26",
   "10.99",
   "2017-10-24 17:42:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Rebel Promises",
   "Avenue of Freaks",
   "hiphop",
   "cover_pqcjie.jpg",
   "sample_pqcjie.mp3",
   "11",
   "14.99",
   "2017-10-24 17:42:43"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Babe, Enjoy The Silence",
   "Error",
   "hiphop",
   "cover_khessa.jpg",
   "sample_khessa.mp3",
   "17",
   "17.99",
   "2017-10-24 17:42:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Nightlife Miracle",
   "Contest of Soul",
   "hiphop",
   "cover_spcoqf.jpg",
   "sample_spcoqf.mp3",
   "29",
   "19.99",
   "2017-10-24 17:43:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Knows We Love To Rock",
   "Heroic Operators",
   "rock",
   "cover_hrtrrf.jpg",
   "sample_hrtrrf.mp3",
   "24",
   "5.99",
   "2017-10-24 17:43:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Heat Of His Dreams",
   "Echoing Charm",
   "rock",
   "cover_plaajx.jpg",
   "sample_plaajx.mp3",
   "16",
   "10.99",
   "2017-10-24 17:43:16"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Know I Know",
   "Tranquility",
   "country",
   "cover_gvqeto.jpg",
   "sample_gvqeto.mp3",
   "17",
   "11.99",
   "2017-10-24 17:43:24"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Way Of The Streets",
   "Avenue",
   "rnb",
   "cover_adztqz.jpg",
   "sample_adztqz.mp3",
   "23",
   "10.99",
   "2017-10-24 17:43:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Give It All You Got",
   "Dust of Change",
   "electronic",
   "cover_iulgeq.jpg",
   "sample_iulgeq.mp3",
   "19",
   "7.99",
   "2017-10-24 17:43:40"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Leave it, Let Me Be",
   "Unity",
   "blues",
   "cover_kleztc.jpg",
   "sample_kleztc.mp3",
   "31",
   "6.99",
   "2017-10-24 17:43:49"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Love For My Time",
   "Flood of Oblivion",
   "country",
   "cover_lafadv.jpg",
   "sample_lafadv.mp3",
   "46",
   "8.99",
   "2017-10-24 17:43:58"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Dreams Of My Heart",
   "Blaze",
   "country",
   "cover_otrczj.jpg",
   "sample_otrczj.mp3",
   "27",
   "13.99",
   "2017-10-24 17:44:07"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Loves You",
   "Replacement of the Stage",
   "rnb",
   "cover_fkjhaa.jpg",
   "sample_fkjhaa.mp3",
   "20",
   "14.99",
   "2017-10-24 17:44:15"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, I'm Walking Away",
   "Magic",
   "country",
   "cover_ttfogl.jpg",
   "sample_ttfogl.mp3",
   "40",
   "11.99",
   "2017-10-24 17:44:23"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lose My Cash",
   "Sunset of Jupiter",
   "hiphop",
   "cover_kfyjud.jpg",
   "sample_kfyjud.mp3",
   "30",
   "9.99",
   "2017-10-24 17:44:31"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Night Angel",
   "Symbol of Possibilities",
   "country",
   "cover_hnkwfa.jpg",
   "sample_hnkwfa.mp3",
   "29",
   "14.99",
   "2017-10-24 17:44:39"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Lost His Mind",
   "Mystery of Ecstasy",
   "hiphop",
   "cover_bcrxls.jpg",
   "sample_bcrxls.mp3",
   "37",
   "6.99",
   "2017-10-24 17:44:47"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, Come Back",
   "Privilege of Noise",
   "electronic",
   "cover_kbcero.jpg",
   "sample_kbcero.mp3",
   "45",
   "12.99",
   "2017-10-24 17:44:55"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Hope He's The Devil",
   "Bug of Pleasure",
   "rock",
   "cover_vhfjuw.jpg",
   "sample_vhfjuw.mp3",
   "24",
   "13.99",
   "2017-10-24 17:45:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Heroic Beauty",
   "Gore",
   "rock",
   "cover_xznopz.jpg",
   "sample_xznopz.mp3",
   "47",
   "6.99",
   "2017-10-24 17:45:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Brave Chances",
   "Hero of Sparks",
   "blues",
   "cover_gsfsau.jpg",
   "sample_gsfsau.mp3",
   "13",
   "12.99",
   "2017-10-24 17:45:20"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Think Of My Hero",
   "Solitary Antics",
   "electronic",
   "cover_cksrpj.jpg",
   "sample_cksrpj.mp3",
   "21",
   "19.99",
   "2017-10-24 17:45:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Said He's A Player",
   "Ashes of Rhythm",
   "latin",
   "cover_nhnevi.jpg",
   "sample_nhnevi.mp3",
   "28",
   "8.99",
   "2017-10-24 17:45:36"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Journey Of A Lover",
   "Passion",
   "rnb",
   "cover_obzbth.jpg",
   "sample_obzbth.mp3",
   "43",
   "6.99",
   "2017-10-24 17:45:45"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Angelic Jungle",
   "Beach Experience",
   "rock",
   "cover_szrfck.jpg",
   "sample_szrfck.mp3",
   "36",
   "18.99",
   "2017-10-24 17:45:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Moments Of My Shadows",
   "Axis",
   "jazz",
   "cover_zfrksk.jpg",
   "sample_zfrksk.mp3",
   "17",
   "19.99",
   "2017-10-24 17:46:01"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Flying Pride",
   "Volley",
   "country",
   "cover_gplpir.jpg",
   "sample_gplpir.mp3",
   "50",
   "18.99",
   "2017-10-24 17:46:17"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He's From Out Of Town",
   "Phase",
   "rock",
   "cover_oobcoc.jpg",
   "sample_oobcoc.mp3",
   "47",
   "10.99",
   "2017-10-24 17:46:24"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Sunset For Desire",
   "Simple World",
   "latin",
   "cover_nngodi.jpg",
   "sample_nngodi.mp3",
   "13",
   "9.99",
   "2017-10-24 17:46:33"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Hot Vibes",
   "Cure of Freedom",
   "rock",
   "cover_neilvs.jpg",
   "sample_neilvs.mp3",
   "23",
   "16.99",
   "2017-10-24 17:46:42"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I said He Hates You",
   "Avenue of Heaven",
   "blues",
   "cover_ooufdw.jpg",
   "sample_ooufdw.mp3",
   "18",
   "14.99",
   "2017-10-24 17:46:49"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Matter For Yourself",
   "Sapphire",
   "electronic",
   "cover_hstyen.jpg",
   "sample_hstyen.mp3",
   "10",
   "6.99",
   "2017-10-24 17:46:57"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Feel The Rhythm",
   "Question",
   "latin",
   "cover_uizbdc.jpg",
   "sample_uizbdc.mp3",
   "29",
   "13.99",
   "2017-10-24 17:47:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Twisted Joys",
   "Syndicate of Youth",
   "rock",
   "cover_uymvvc.jpg",
   "sample_uymvvc.mp3",
   "25",
   "5.99",
   "2017-10-24 17:47:15"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Know I'm Part Of The Hood",
   "Volley",
   "hiphop",
   "cover_vtfrbu.jpg",
   "sample_vtfrbu.mp3",
   "27",
   "10.99",
   "2017-10-24 17:47:23"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Living Of My Life",
   "Meaning of Magnetism",
   "rock",
   "cover_kohste.jpg",
   "sample_kohste.mp3",
   "44",
   "6.99",
   "2017-10-24 17:47:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Fire Of My Tears",
   "Theory of Ash",
   "rock",
   "cover_qwasbn.jpg",
   "sample_qwasbn.mp3",
   "48",
   "14.99",
   "2017-10-24 17:47:41"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Trapped By My Voice",
   "Slice",
   "hiphop",
   "cover_glpypn.jpg",
   "sample_glpypn.mp3",
   "21",
   "9.99",
   "2017-10-24 17:47:49"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lost Money",
   "All Out Alliance",
   "blues",
   "cover_ixibkf.jpg",
   "sample_ixibkf.mp3",
   "19",
   "18.99",
   "2017-10-24 17:47:56"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Knows I'm All For You",
   "Astronauts of Hazard",
   "latin",
   "cover_tcpvxd.jpg",
   "sample_tcpvxd.mp3",
   "41",
   "14.99",
   "2017-10-24 17:48:05"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Months Of Yesterday",
   "Screaming Riot",
   "rnb",
   "cover_teebkx.jpg",
   "sample_teebkx.mp3",
   "29",
   "6.99",
   "2017-10-24 17:48:13"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Sound Of My Ignorance",
   "Cold Bards",
   "rock",
   "cover_eguois.jpg",
   "sample_eguois.mp3",
   "17",
   "5.99",
   "2017-10-24 17:48:22"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Know I Got Passion",
   "Inferno",
   "latin",
   "cover_uufkwa.jpg",
   "sample_uufkwa.mp3",
   "47",
   "8.99",
   "2017-10-24 17:48:30"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Heard I'm In Love",
   "Sequel",
   "latin",
   "cover_ivmsvs.jpg",
   "sample_ivmsvs.mp3",
   "20",
   "14.99",
   "2017-10-24 17:48:37"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Rebel Chance",
   "Droid of Sin",
   "hiphop",
   "cover_zoagev.jpg",
   "sample_zoagev.mp3",
   "26",
   "18.99",
   "2017-10-24 17:48:45"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Crazy Things",
   "Bittersweet Accident",
   "hiphop",
   "cover_asemks.jpg",
   "sample_asemks.mp3",
   "25",
   "15.99",
   "2017-10-24 17:48:53"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Seeing His Way",
   "School of Valentine",
   "country",
   "cover_ofhfzh.jpg",
   "sample_ofhfzh.mp3",
   "45",
   "17.99",
   "2017-10-24 17:49:01"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Hates You",
   "Merit",
   "blues",
   "cover_abnjvn.jpg",
   "sample_abnjvn.mp3",
   "36",
   "4.99",
   "2017-10-24 17:49:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Smooth Resurrection",
   "Charm of Menace",
   "hiphop",
   "cover_suncnq.jpg",
   "sample_suncnq.mp3",
   "50",
   "13.99",
   "2017-10-24 17:49:16"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Need Of Nowhere",
   "Adorable Smash",
   "country",
   "cover_auacic.jpg",
   "sample_auacic.mp3",
   "47",
   "12.99",
   "2017-10-24 17:49:24"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Forget His Thunder",
   "Ace",
   "blues",
   "cover_isnrqy.jpg",
   "sample_isnrqy.mp3",
   "43",
   "4.99",
   "2017-10-24 17:49:33"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Said He's Into you",
   "Kiss of the Inferno",
   "rock",
   "cover_ihdcxg.jpg",
   "sample_ihdcxg.mp3",
   "37",
   "5.99",
   "2017-10-24 17:49:41"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Thinks She's Good For You",
   "Nemo",
   "rock",
   "cover_fwatqn.jpg",
   "sample_fwatqn.mp3",
   "27",
   "5.99",
   "2017-10-24 17:49:49"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Hopes I Am Troubled",
   "Velvet Romance",
   "country",
   "cover_ujpyml.jpg",
   "sample_ujpyml.mp3",
   "47",
   "15.99",
   "2017-10-24 17:49:57"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Loves He's Family",
   "Spoof",
   "rock",
   "cover_zqcrsw.jpg",
   "sample_zqcrsw.mp3",
   "23",
   "14.99",
   "2017-10-24 17:50:04"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Wild Chance",
   "Wish of Struggle",
   "latin",
   "cover_prokhh.jpg",
   "sample_prokhh.mp3",
   "28",
   "17.99",
   "2017-10-24 17:50:12"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Loves She's No Good",
   "Bliss",
   "hiphop",
   "cover_mlqfzi.jpg",
   "sample_mlqfzi.mp3",
   "17",
   "6.99",
   "2017-10-24 17:50:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Twisted Moves",
   "Caricature",
   "jazz",
   "cover_hcxrxn.jpg",
   "sample_hcxrxn.mp3",
   "21",
   "9.99",
   "2017-10-24 17:50:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Loved By My Moods",
   "Daughters of Sugar",
   "jazz",
   "cover_aiuqaw.jpg",
   "sample_aiuqaw.mp3",
   "47",
   "12.99",
   "2017-10-24 17:50:34"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Babe, Whatever",
   "Times of Power",
   "hiphop",
   "cover_ilcrff.jpg",
   "sample_ilcrff.mp3",
   "18",
   "17.99",
   "2017-10-24 17:50:42"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Whispers Of Your World",
   "Call of Fiction",
   "rnb",
   "cover_vokdrz.jpg",
   "sample_vokdrz.mp3",
   "19",
   "19.99",
   "2017-10-24 17:50:50"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Said She's Crazy",
   "Pure Guests",
   "hiphop",
   "cover_dbmvhm.jpg",
   "sample_dbmvhm.mp3",
   "17",
   "10.99",
   "2017-10-24 17:50:57"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Loves He's A Dancer",
   "Epilogue of Daydreams",
   "electronic",
   "cover_iaenfa.jpg",
   "sample_iaenfa.mp3",
   "49",
   "14.99",
   "2017-10-24 17:51:05"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Home Of My Ways",
   "Noble Awakening",
   "electronic",
   "cover_nyhbjr.jpg",
   "sample_nyhbjr.mp3",
   "46",
   "11.99",
   "2017-10-24 17:51:13"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, Let's Get A Move On",
   "Overdose",
   "rock",
   "cover_libayy.jpg",
   "sample_libayy.mp3",
   "40",
   "16.99",
   "2017-10-24 17:51:22"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think I'm Tainted",
   "Void",
   "rock",
   "cover_oxyyma.jpg",
   "sample_oxyyma.mp3",
   "42",
   "13.99",
   "2017-10-24 17:51:30"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, I'm Falling In Love",
   "Phoenix",
   "rock",
   "cover_vfuzbb.jpg",
   "sample_vfuzbb.mp3",
   "23",
   "7.99",
   "2017-10-24 17:51:37"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Heard I'm Coming To You",
   "Engine of Fury",
   "electronic",
   "cover_dkombu.jpg",
   "sample_dkombu.mp3",
   "33",
   "14.99",
   "2017-10-24 17:51:48"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Thinks We Will Dance Tonight",
   "Privilege of Diamonds",
   "electronic",
   "cover_pyxrdx.jpg",
   "sample_pyxrdx.mp3",
   "21",
   "6.99",
   "2017-10-24 17:51:55"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Smooth Lyrics",
   "Right of Death",
   "hiphop",
   "cover_nhkqhq.jpg",
   "sample_nhkqhq.mp3",
   "36",
   "13.99",
   "2017-10-24 17:52:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Rhythm Of My Mind",
   "Garden of Madness",
   "rock",
   "cover_ypjadb.jpg",
   "sample_ypjadb.mp3",
   "12",
   "13.99",
   "2017-10-24 17:52:10"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, You Knock Me Off My Feet",
   "Freak of Nature",
   "rock",
   "cover_zxsvli.jpg",
   "sample_zxsvli.mp3",
   "37",
   "17.99",
   "2017-10-24 17:52:18"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think We're No Good For Each Other",
   "Silver",
   "rnb",
   "cover_uqrnoo.jpg",
   "sample_uqrnoo.mp3",
   "40",
   "18.99",
   "2017-10-24 17:52:26"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Hope I Need To Ride",
   "Grace of Malice",
   "country",
   "cover_fayyat.jpg",
   "sample_fayyat.mp3",
   "41",
   "15.99",
   "2017-10-24 17:52:34"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Hopes He Misses You",
   "Burst of Daydreams",
   "blues",
   "cover_vxyuvj.jpg",
   "sample_vxyuvj.mp3",
   "14",
   "12.99",
   "2017-10-24 17:52:43"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Moment For Joy",
   "Parade of Relaxation",
   "rock",
   "cover_zjuhyp.jpg",
   "sample_zjuhyp.mp3",
   "32",
   "17.99",
   "2017-10-24 17:52:50"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Bitter Love",
   "Pirate of Brilliance",
   "rock",
   "cover_egqmfh.jpg",
   "sample_egqmfh.mp3",
   "30",
   "7.99",
   "2017-10-24 17:52:58"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Loves He's Got His Eye On You",
   "Menace",
   "rock",
   "cover_ennyqg.jpg",
   "sample_ennyqg.mp3",
   "18",
   "19.99",
   "2017-10-24 17:53:07"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Strange Soul",
   "Blissful Blaze",
   "jazz",
   "cover_iftmln.jpg",
   "sample_iftmln.mp3",
   "30",
   "17.99",
   "2017-10-24 17:53:15"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Mind Of Eternity",
   "March of Frenzy",
   "rock",
   "cover_cyliaf.jpg",
   "sample_cyliaf.mp3",
   "26",
   "5.99",
   "2017-10-24 17:53:22"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Heard She's Rock 'N Roll",
   "Parade of Obscurity",
   "rock",
   "cover_ahzazn.jpg",
   "sample_ahzazn.mp3",
   "43",
   "5.99",
   "2017-10-24 17:53:31"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She's Mysterious",
   "Turbulence",
   "rock",
   "cover_zrvfeg.jpg",
   "sample_zrvfeg.mp3",
   "43",
   "14.99",
   "2017-10-24 17:53:39"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, You And I",
   "Hazard",
   "latin",
   "cover_nktsgq.jpg",
   "sample_nktsgq.mp3",
   "19",
   "5.99",
   "2017-10-24 17:53:48"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Honey, Let's End It",
   "Temper",
   "blues",
   "cover_tptiku.jpg",
   "sample_tptiku.mp3",
   "16",
   "15.99",
   "2017-10-24 17:53:55"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think He Got It Going",
   "Oblivious Airwaves",
   "jazz",
   "cover_itttde.jpg",
   "sample_itttde.mp3",
   "45",
   "8.99",
   "2017-10-24 17:54:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Said I am Trouble",
   "Riders of Genesis",
   "country",
   "cover_krvxax.jpg",
   "sample_krvxax.mp3",
   "31",
   "5.99",
   "2017-10-24 17:54:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Baby, You're My Favorite Thing",
   "Void",
   "jazz",
   "cover_gbbdmk.jpg",
   "sample_gbbdmk.mp3",
   "47",
   "13.99",
   "2017-10-24 17:54:20"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Said You Love Me",
   "Blossoms of Myth",
   "rock",
   "cover_iedkax.jpg",
   "sample_iedkax.mp3",
   "45",
   "12.99",
   "2017-10-24 17:54:28"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Hate My Pride",
   "Twisted Revolution",
   "blues",
   "cover_qqytmp.jpg",
   "sample_qqytmp.mp3",
   "20",
   "13.99",
   "2017-10-24 17:54:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Lost Frenzy",
   "Law",
   "rock",
   "cover_umyswl.jpg",
   "sample_umyswl.mp3",
   "48",
   "7.99",
   "2017-10-24 17:54:44"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "You're My Number One",
   "Obsidian",
   "country",
   "cover_cmjuuv.jpg",
   "sample_cmjuuv.mp3",
   "14",
   "18.99",
   "2017-10-24 17:54:52"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Heard He's Crazy",
   "Passion of Envy",
   "country",
   "cover_ylgiki.jpg",
   "sample_ylgiki.mp3",
   "18",
   "5.99",
   "2017-10-24 17:55:00"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Goodbye Soul",
   "Merit",
   "country",
   "cover_uhcinc.jpg",
   "sample_uhcinc.mp3",
   "43",
   "13.99",
   "2017-10-24 17:55:08"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Hopes She's Devoted",
   "Rival",
   "jazz",
   "cover_jttsuc.jpg",
   "sample_jttsuc.mp3",
   "49",
   "19.99",
   "2017-10-24 17:55:16"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "End it, Come On",
   "KO Broadcast",
   "blues",
   "cover_yzfgit.jpg",
   "sample_yzfgit.mp3",
   "38",
   "9.99",
   "2017-10-24 17:55:24"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Shining Flower",
   "Riot",
   "country",
   "cover_qvfzaa.jpg",
   "sample_qvfzaa.mp3",
   "19",
   "5.99",
   "2017-10-24 17:55:32"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Moments Of Your Lust",
   "Vanished Shout",
   "rnb",
   "cover_bwbrpy.jpg",
   "sample_bwbrpy.mp3",
   "43",
   "13.99",
   "2017-10-24 17:55:40"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Smooth Life",
   "Dragons",
   "jazz",
   "cover_xozhva.jpg",
   "sample_xozhva.mp3",
   "33",
   "7.99",
   "2017-10-24 17:55:48"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Think He's Sophisticated",
   "Guess",
   "jazz",
   "cover_gcvlhm.jpg",
   "sample_gcvlhm.mp3",
   "43",
   "12.99",
   "2017-10-24 17:55:56"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "The Girl Of His Moments",
   "Fever",
   "rnb",
   "cover_yzvpka.jpg",
   "sample_yzvpka.mp3",
   "50",
   "17.99",
   "2017-10-24 17:56:03"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "We Will Dance Tonight",
   "Unique Struggle",
   "electronic",
   "cover_cvimin.jpg",
   "sample_cvimin.mp3",
   "32",
   "19.99",
   "2017-10-24 17:56:11"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Said I'm The Devil",
   "Virtuoso",
   "rock",
   "cover_jfbrki.jpg",
   "sample_jfbrki.mp3",
   "18",
   "16.99",
   "2017-10-24 17:56:20"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Hopes He Cares",
   "Passage of Hustle",
   "country",
   "cover_irxdbi.jpg",
   "sample_irxdbi.mp3",
   "15",
   "9.99",
   "2017-10-24 17:56:33"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Thinks You Rock My World",
   "Cosmic Legion",
   "rock",
   "cover_kjpmfk.jpg",
   "sample_kjpmfk.mp3",
   "39",
   "5.99",
   "2017-10-24 17:56:41"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Good Chances",
   "Simple Band",
   "country",
   "cover_wrpfdn.jpg",
   "sample_wrpfdn.mp3",
   "16",
   "16.99",
   "2017-10-24 17:56:51"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Said He Left You",
   "Wireless Intern",
   "rnb",
   "cover_hkspox.jpg",
   "sample_hkspox.mp3",
   "26",
   "4.99",
   "2017-10-24 17:56:58"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "She Hopes I Miss You",
   "Fever of Comfort",
   "electronic",
   "cover_yrjhdf.jpg",
   "sample_yrjhdf.mp3",
   "29",
   "18.99",
   "2017-10-24 17:57:05"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Figments Of My Feelings",
   "Parody",
   "jazz",
   "cover_chteop.jpg",
   "sample_chteop.mp3",
   "40",
   "19.99",
   "2017-10-24 17:57:13"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Love He's A Player",
   "Library of the Groove",
   "latin",
   "cover_ddscie.jpg",
   "sample_ddscie.mp3",
   "19",
   "9.99",
   "2017-10-24 17:57:21"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Don't Need Her Heart",
   "Times of Truth",
   "electronic",
   "cover_esufej.jpg",
   "sample_esufej.mp3",
   "18",
   "8.99",
   "2017-10-24 17:57:29"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Know I'm Going To Get You",
   "Temper",
   "electronic",
   "cover_vihoyf.jpg",
   "sample_vihoyf.mp3",
   "50",
   "12.99",
   "2017-10-24 17:57:38"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Darling, Remember The Good Times",
   "Flight of Victory",
   "electronic",
   "cover_nsdrip.jpg",
   "sample_nsdrip.mp3",
   "16",
   "13.99",
   "2017-10-24 17:57:46"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Hates She's Crazy",
   "Blaze of Twilight",
   "hiphop",
   "cover_bniwuh.jpg",
   "sample_bniwuh.mp3",
   "36",
   "14.99",
   "2017-10-24 17:57:53"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Chains Of My Moments",
   "Guild of Earth",
   "rock",
   "cover_sopleb.jpg",
   "sample_sopleb.mp3",
   "35",
   "17.99",
   "2017-10-24 17:58:01"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "I Said He's In Love",
   "Static",
   "rock",
   "cover_lqhdlt.jpg",
   "sample_lqhdlt.mp3",
   "25",
   "4.99",
   "2017-10-24 17:58:10"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Memories Of Her Glass Heart",
   "Attic Critters",
   "rock",
   "cover_frcxgn.jpg",
   "sample_frcxgn.mp3",
   "32",
   "6.99",
   "2017-10-24 17:58:19"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Darling, Feel The Rhythm Of My Beat",
   "Surrender of Slander",
   "rnb",
   "cover_vtnyvg.jpg",
   "sample_vtnyvg.mp3",
   "43",
   "17.99",
   "2017-10-24 17:58:27"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "He Said You're In Love",
   "Ashes of Dust",
   "jazz",
   "cover_eapuym.jpg",
   "sample_eapuym.mp3",
   "18",
   "17.99",
   "2017-10-24 17:58:35"
);

INSERT INTO cds
(
   title, band, genre, cover, sample, quantity, price, date
)
VALUES
(
   "Luck Of Endless Summers",
   "Low Haven",
   "electronic",
   "cover_htqbqe.jpg",
   "sample_htqbqe.mp3",
   "18",
   "6.99",
   "2017-10-24 17:58:44"
);

