/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80400
 Source Host           : localhost:3306
 Source Schema         : test_manage_system

 Target Server Type    : MySQL
 Target Server Version : 80400
 File Encoding         : 65001

 Date: 07/07/2024 23:43:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `ClassID` int NOT NULL AUTO_INCREMENT,
  `ClassName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ClassID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '高一1班');
INSERT INTO `class` VALUES (2, '高一2班');
INSERT INTO `class` VALUES (3, '高一3班');
INSERT INTO `class` VALUES (4, '高一4班');
INSERT INTO `class` VALUES (5, '高一5班');
INSERT INTO `class` VALUES (6, '高一6班');
INSERT INTO `class` VALUES (7, '高一7班');
INSERT INTO `class` VALUES (8, '高一8班');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `CourseID` int NOT NULL AUTO_INCREMENT,
  `CourseName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Priority` int UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`CourseID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '语文', 6);
INSERT INTO `course` VALUES (2, '数学', 6);
INSERT INTO `course` VALUES (3, '英语', 6);
INSERT INTO `course` VALUES (4, '物理', 3);
INSERT INTO `course` VALUES (5, '生物', 3);
INSERT INTO `course` VALUES (6, '化学', 3);
INSERT INTO `course` VALUES (7, '体育', 2);
INSERT INTO `course` VALUES (8, '艺术', 1);

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `ScheduleID` int NOT NULL AUTO_INCREMENT,
  `ClassID` int NULL DEFAULT NULL,
  `CourseID` int NULL DEFAULT NULL,
  `TeacherID` int NULL DEFAULT NULL,
  `DayOfWeek` int NULL DEFAULT NULL,
  `TimeSlotID` int NULL DEFAULT NULL,
  PRIMARY KEY (`ScheduleID`) USING BTREE,
  INDEX `schedule_ibfk_1`(`ClassID` ASC) USING BTREE,
  INDEX `schedule_ibfk_2`(`CourseID` ASC) USING BTREE,
  INDEX `schedule_ibfk_3`(`TeacherID` ASC) USING BTREE,
  INDEX `schedule_ibfk_4`(`TimeSlotID` ASC) USING BTREE,
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`ClassID`) REFERENCES `class` (`ClassID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`TeacherID`) REFERENCES `teacher` (`TeacherID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_4` FOREIGN KEY (`TimeSlotID`) REFERENCES `timeslot` (`TimeSlotID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `StudentID` int NOT NULL AUTO_INCREMENT,
  `StudentName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ClassID` int NULL DEFAULT NULL,
  PRIMARY KEY (`StudentID`) USING BTREE,
  INDEX `student_ibfk_2`(`ClassID` ASC) USING BTREE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`ClassID`) REFERENCES `class` (`ClassID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'Student 1-1', 1);
INSERT INTO `student` VALUES (2, 'Student 1-2', 1);
INSERT INTO `student` VALUES (3, 'Student 1-3', 1);
INSERT INTO `student` VALUES (4, 'Student 1-4', 1);
INSERT INTO `student` VALUES (5, 'Student 1-5', 1);
INSERT INTO `student` VALUES (6, 'Student 1-6', 1);
INSERT INTO `student` VALUES (7, 'Student 1-7', 1);
INSERT INTO `student` VALUES (8, 'Student 1-8', 1);
INSERT INTO `student` VALUES (9, 'Student 1-9', 1);
INSERT INTO `student` VALUES (10, 'Student 1-10', 1);
INSERT INTO `student` VALUES (11, 'Student 1-11', 1);
INSERT INTO `student` VALUES (12, 'Student 1-12', 1);
INSERT INTO `student` VALUES (13, 'Student 1-13', 1);
INSERT INTO `student` VALUES (14, 'Student 1-14', 1);
INSERT INTO `student` VALUES (15, 'Student 1-15', 1);
INSERT INTO `student` VALUES (16, 'Student 1-16', 1);
INSERT INTO `student` VALUES (17, 'Student 1-17', 1);
INSERT INTO `student` VALUES (18, 'Student 1-18', 1);
INSERT INTO `student` VALUES (19, 'Student 1-19', 1);
INSERT INTO `student` VALUES (20, 'Student 1-20', 1);
INSERT INTO `student` VALUES (21, 'Student 2-1', 2);
INSERT INTO `student` VALUES (22, 'Student 2-2', 2);
INSERT INTO `student` VALUES (23, 'Student 2-3', 2);
INSERT INTO `student` VALUES (24, 'Student 2-4', 2);
INSERT INTO `student` VALUES (25, 'Student 2-5', 2);
INSERT INTO `student` VALUES (26, 'Student 2-6', 2);
INSERT INTO `student` VALUES (27, 'Student 2-7', 2);
INSERT INTO `student` VALUES (28, 'Student 2-8', 2);
INSERT INTO `student` VALUES (29, 'Student 2-9', 2);
INSERT INTO `student` VALUES (30, 'Student 2-10', 2);
INSERT INTO `student` VALUES (31, 'Student 2-11', 2);
INSERT INTO `student` VALUES (32, 'Student 2-12', 2);
INSERT INTO `student` VALUES (33, 'Student 2-13', 2);
INSERT INTO `student` VALUES (34, 'Student 2-14', 2);
INSERT INTO `student` VALUES (35, 'Student 2-15', 2);
INSERT INTO `student` VALUES (36, 'Student 2-16', 2);
INSERT INTO `student` VALUES (37, 'Student 2-17', 2);
INSERT INTO `student` VALUES (38, 'Student 2-18', 2);
INSERT INTO `student` VALUES (39, 'Student 2-19', 2);
INSERT INTO `student` VALUES (40, 'Student 2-20', 2);
INSERT INTO `student` VALUES (41, 'Student 3-1', 3);
INSERT INTO `student` VALUES (42, 'Student 3-2', 3);
INSERT INTO `student` VALUES (43, 'Student 3-3', 3);
INSERT INTO `student` VALUES (44, 'Student 3-4', 3);
INSERT INTO `student` VALUES (45, 'Student 3-5', 3);
INSERT INTO `student` VALUES (46, 'Student 3-6', 3);
INSERT INTO `student` VALUES (47, 'Student 3-7', 3);
INSERT INTO `student` VALUES (48, 'Student 3-8', 3);
INSERT INTO `student` VALUES (49, 'Student 3-9', 3);
INSERT INTO `student` VALUES (50, 'Student 3-10', 3);
INSERT INTO `student` VALUES (51, 'Student 3-11', 3);
INSERT INTO `student` VALUES (52, 'Student 3-12', 3);
INSERT INTO `student` VALUES (53, 'Student 3-13', 3);
INSERT INTO `student` VALUES (54, 'Student 3-14', 3);
INSERT INTO `student` VALUES (55, 'Student 3-15', 3);
INSERT INTO `student` VALUES (56, 'Student 3-16', 3);
INSERT INTO `student` VALUES (57, 'Student 3-17', 3);
INSERT INTO `student` VALUES (58, 'Student 3-18', 3);
INSERT INTO `student` VALUES (59, 'Student 3-19', 3);
INSERT INTO `student` VALUES (60, 'Student 3-20', 3);
INSERT INTO `student` VALUES (61, 'Student 4-1', 4);
INSERT INTO `student` VALUES (62, 'Student 4-2', 4);
INSERT INTO `student` VALUES (63, 'Student 4-3', 4);
INSERT INTO `student` VALUES (64, 'Student 4-4', 4);
INSERT INTO `student` VALUES (65, 'Student 4-5', 4);
INSERT INTO `student` VALUES (66, 'Student 4-6', 4);
INSERT INTO `student` VALUES (67, 'Student 4-7', 4);
INSERT INTO `student` VALUES (68, 'Student 4-8', 4);
INSERT INTO `student` VALUES (69, 'Student 4-9', 4);
INSERT INTO `student` VALUES (70, 'Student 4-10', 4);
INSERT INTO `student` VALUES (71, 'Student 4-11', 4);
INSERT INTO `student` VALUES (72, 'Student 4-12', 4);
INSERT INTO `student` VALUES (73, 'Student 4-13', 4);
INSERT INTO `student` VALUES (74, 'Student 4-14', 4);
INSERT INTO `student` VALUES (75, 'Student 4-15', 4);
INSERT INTO `student` VALUES (76, 'Student 4-16', 4);
INSERT INTO `student` VALUES (77, 'Student 4-17', 4);
INSERT INTO `student` VALUES (78, 'Student 4-18', 4);
INSERT INTO `student` VALUES (79, 'Student 4-19', 4);
INSERT INTO `student` VALUES (80, 'Student 4-20', 4);
INSERT INTO `student` VALUES (81, 'Student 5-1', 5);
INSERT INTO `student` VALUES (82, 'Student 5-2', 5);
INSERT INTO `student` VALUES (83, 'Student 5-3', 5);
INSERT INTO `student` VALUES (84, 'Student 5-4', 5);
INSERT INTO `student` VALUES (85, 'Student 5-5', 5);
INSERT INTO `student` VALUES (86, 'Student 5-6', 5);
INSERT INTO `student` VALUES (87, 'Student 5-7', 5);
INSERT INTO `student` VALUES (88, 'Student 5-8', 5);
INSERT INTO `student` VALUES (89, 'Student 5-9', 5);
INSERT INTO `student` VALUES (90, 'Student 5-10', 5);
INSERT INTO `student` VALUES (91, 'Student 5-11', 5);
INSERT INTO `student` VALUES (92, 'Student 5-12', 5);
INSERT INTO `student` VALUES (93, 'Student 5-13', 5);
INSERT INTO `student` VALUES (94, 'Student 5-14', 5);
INSERT INTO `student` VALUES (95, 'Student 5-15', 5);
INSERT INTO `student` VALUES (96, 'Student 5-16', 5);
INSERT INTO `student` VALUES (97, 'Student 5-17', 5);
INSERT INTO `student` VALUES (98, 'Student 5-18', 5);
INSERT INTO `student` VALUES (99, 'Student 5-19', 5);
INSERT INTO `student` VALUES (100, 'Student 5-20', 5);
INSERT INTO `student` VALUES (101, 'Student 6-1', 6);
INSERT INTO `student` VALUES (102, 'Student 6-2', 6);
INSERT INTO `student` VALUES (103, 'Student 6-3', 6);
INSERT INTO `student` VALUES (104, 'Student 6-4', 6);
INSERT INTO `student` VALUES (105, 'Student 6-5', 6);
INSERT INTO `student` VALUES (106, 'Student 6-6', 6);
INSERT INTO `student` VALUES (107, 'Student 6-7', 6);
INSERT INTO `student` VALUES (108, 'Student 6-8', 6);
INSERT INTO `student` VALUES (109, 'Student 6-9', 6);
INSERT INTO `student` VALUES (110, 'Student 6-10', 6);
INSERT INTO `student` VALUES (111, 'Student 6-11', 6);
INSERT INTO `student` VALUES (112, 'Student 6-12', 6);
INSERT INTO `student` VALUES (113, 'Student 6-13', 6);
INSERT INTO `student` VALUES (114, 'Student 6-14', 6);
INSERT INTO `student` VALUES (115, 'Student 6-15', 6);
INSERT INTO `student` VALUES (116, 'Student 6-16', 6);
INSERT INTO `student` VALUES (117, 'Student 6-17', 6);
INSERT INTO `student` VALUES (118, 'Student 6-18', 6);
INSERT INTO `student` VALUES (119, 'Student 6-19', 6);
INSERT INTO `student` VALUES (120, 'Student 6-20', 6);
INSERT INTO `student` VALUES (121, 'Student 7-1', 7);
INSERT INTO `student` VALUES (122, 'Student 7-2', 7);
INSERT INTO `student` VALUES (123, 'Student 7-3', 7);
INSERT INTO `student` VALUES (124, 'Student 7-4', 7);
INSERT INTO `student` VALUES (125, 'Student 7-5', 7);
INSERT INTO `student` VALUES (126, 'Student 7-6', 7);
INSERT INTO `student` VALUES (127, 'Student 7-7', 7);
INSERT INTO `student` VALUES (128, 'Student 7-8', 7);
INSERT INTO `student` VALUES (129, 'Student 7-9', 7);
INSERT INTO `student` VALUES (130, 'Student 7-10', 7);
INSERT INTO `student` VALUES (131, 'Student 7-11', 7);
INSERT INTO `student` VALUES (132, 'Student 7-12', 7);
INSERT INTO `student` VALUES (133, 'Student 7-13', 7);
INSERT INTO `student` VALUES (134, 'Student 7-14', 7);
INSERT INTO `student` VALUES (135, 'Student 7-15', 7);
INSERT INTO `student` VALUES (136, 'Student 7-16', 7);
INSERT INTO `student` VALUES (137, 'Student 7-17', 7);
INSERT INTO `student` VALUES (138, 'Student 7-18', 7);
INSERT INTO `student` VALUES (139, 'Student 7-19', 7);
INSERT INTO `student` VALUES (140, 'Student 7-20', 7);
INSERT INTO `student` VALUES (141, 'Student 8-1', 8);
INSERT INTO `student` VALUES (142, 'Student 8-2', 8);
INSERT INTO `student` VALUES (143, 'Student 8-3', 8);
INSERT INTO `student` VALUES (144, 'Student 8-4', 8);
INSERT INTO `student` VALUES (145, 'Student 8-5', 8);
INSERT INTO `student` VALUES (146, 'Student 8-6', 8);
INSERT INTO `student` VALUES (147, 'Student 8-7', 8);
INSERT INTO `student` VALUES (148, 'Student 8-8', 8);
INSERT INTO `student` VALUES (149, 'Student 8-9', 8);
INSERT INTO `student` VALUES (150, 'Student 8-10', 8);
INSERT INTO `student` VALUES (151, 'Student 8-11', 8);
INSERT INTO `student` VALUES (152, 'Student 8-12', 8);
INSERT INTO `student` VALUES (153, 'Student 8-13', 8);
INSERT INTO `student` VALUES (154, 'Student 8-14', 8);
INSERT INTO `student` VALUES (155, 'Student 8-15', 8);
INSERT INTO `student` VALUES (156, 'Student 8-16', 8);
INSERT INTO `student` VALUES (157, 'Student 8-17', 8);
INSERT INTO `student` VALUES (158, 'Student 8-18', 8);
INSERT INTO `student` VALUES (159, 'Student 8-19', 8);
INSERT INTO `student` VALUES (160, 'Student 8-20', 8);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `TeacherID` int NOT NULL AUTO_INCREMENT,
  `TeacherName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`TeacherID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 308 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 'Teacher A');
INSERT INTO `teacher` VALUES (2, 'Teacher B');
INSERT INTO `teacher` VALUES (3, 'Teacher C');
INSERT INTO `teacher` VALUES (4, 'Teacher D');
INSERT INTO `teacher` VALUES (5, 'Teacher E');
INSERT INTO `teacher` VALUES (6, 'Teacher F');
INSERT INTO `teacher` VALUES (7, 'Teacher G');
INSERT INTO `teacher` VALUES (8, 'Teacher H');

-- ----------------------------
-- Table structure for timeslot
-- ----------------------------
DROP TABLE IF EXISTS `timeslot`;
CREATE TABLE `timeslot`  (
  `TimeSlotID` int NOT NULL AUTO_INCREMENT,
  `StartTime` time NOT NULL,
  `EndTime` time NOT NULL,
  PRIMARY KEY (`TimeSlotID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timeslot
-- ----------------------------
INSERT INTO `timeslot` VALUES (1, '08:00:00', '09:30:00');
INSERT INTO `timeslot` VALUES (2, '10:00:00', '11:30:00');
INSERT INTO `timeslot` VALUES (3, '13:00:00', '14:30:00');
INSERT INTO `timeslot` VALUES (4, '15:00:00', '16:30:00');
INSERT INTO `timeslot` VALUES (5, '18:00:00', '19:30:00');
INSERT INTO `timeslot` VALUES (6, '20:00:00', '21:30:00');

-- ----------------------------
-- Procedure structure for CheckTeacherSchedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `CheckTeacherSchedule`;
delimiter ;;
CREATE PROCEDURE `CheckTeacherSchedule`(IN inputTeacherID INT, IN inputTimeSlotID INT, IN inputDayOfWeek INT,
                                      OUT hasClass BOOLEAN)
BEGIN
    DECLARE classCount INT; -- 用于存储查询结果的课程数量

    -- 查询匹配的课程数量
    SELECT COUNT(*)
    INTO classCount
    FROM Schedule
    WHERE TeacherID = inputTeacherID
      AND TimeSlotID = inputTimeSlotID
      AND DayOfWeek = inputDayOfWeek;

    -- 判断是否有课
    IF classCount > 0 THEN
        SET hasClass = TRUE;
    ELSE
        SET hasClass = FALSE;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GenerateClassSchedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `GenerateClassSchedule`;
delimiter ;;
CREATE PROCEDURE `GenerateClassSchedule`(IN classId INT)
BEGIN
    SELECT ts.TimeSlotID, ts.StartTime, ts.EndTime, s.DayOfWeek, c.CourseName, t.TeacherName
    FROM Schedule s
             JOIN Course c ON s.CourseID = c.CourseID
             JOIN Teacher t ON s.TeacherID = t.TeacherID
             JOIN TimeSlot ts ON s.TimeSlotID = ts.TimeSlotID
    WHERE s.ClassID = classId
    ORDER BY s.DayOfWeek, s.TimeSlotID;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for GenerateTeacherSchedule
-- ----------------------------
DROP PROCEDURE IF EXISTS `GenerateTeacherSchedule`;
delimiter ;;
CREATE PROCEDURE `GenerateTeacherSchedule`(IN teacherId INT)
BEGIN
    SELECT ts.TimeSlotID, s.DayOfWeek, ts.StartTime, ts.EndTime, cl.ClassName, c.CourseName
    FROM Schedule s
             JOIN Course c ON s.CourseID = c.CourseID
             JOIN Class cl ON s.ClassID = cl.ClassID
             JOIN TimeSlot ts ON s.TimeSlotID = ts.TimeSlotID
    WHERE s.TeacherID = teacherId
    ORDER BY s.DayOfWeek, s.TimeSlotID;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for ShowAllCreateTableStatements
-- ----------------------------
DROP PROCEDURE IF EXISTS `ShowAllCreateTableStatements`;
delimiter ;;
CREATE PROCEDURE `ShowAllCreateTableStatements`()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE table_name VARCHAR(64);
    DECLARE cur CURSOR FOR 
        SELECT table_name 
        FROM information_schema.tables 
        WHERE table_schema = DATABASE();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO table_name;
        IF done THEN
            LEAVE read_loop;
        END IF;
        SET @query = CONCAT('SHOW CREATE TABLE ', table_name);
        PREPARE stmt FROM @query;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END LOOP;
    CLOSE cur;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
