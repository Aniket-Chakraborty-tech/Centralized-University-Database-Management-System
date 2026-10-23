-- Centralized University Database
-- MySQL 8.x

DROP DATABASE IF EXISTS university_db;
CREATE DATABASE university_db;
USE university_db;

CREATE TABLE department (
    dept_id     INT AUTO_INCREMENT PRIMARY KEY,
    dept_name   VARCHAR(100) NOT NULL UNIQUE,
    building    VARCHAR(50)
);

CREATE TABLE faculty (
    faculty_id  INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(120) NOT NULL UNIQUE,
    designation VARCHAR(50),
    dept_id     INT NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE student (
    student_id  INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(120) NOT NULL UNIQUE,
    dept_id     INT NOT NULL,
    year_of_study TINYINT NOT NULL CHECK (year_of_study BETWEEN 1 AND 6),
    admitted_on DATE NOT NULL DEFAULT (CURRENT_DATE),
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE course (
    course_id   INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(10) NOT NULL UNIQUE,
    title       VARCHAR(120) NOT NULL,
    credits     TINYINT NOT NULL CHECK (credits > 0),
    dept_id     INT NOT NULL,
    faculty_id  INT,
    FOREIGN KEY (dept_id) REFERENCES department(dept_id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE SET NULL
);

-- many-to-many between students and courses
CREATE TABLE enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id    INT NOT NULL,
    course_id     INT NOT NULL,
    semester      VARCHAR(10) NOT NULL,      -- e.g. 2026-ODD
    grade         CHAR(2) NULL,              -- NULL until results are out
    UNIQUE KEY uq_enroll (student_id, course_id, semester),
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id)  REFERENCES course(course_id)  ON DELETE CASCADE
);

-- research cell
CREATE TABLE research_project (
    project_id  INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    lead_faculty_id INT NOT NULL,
    status      ENUM('PROPOSED','ACTIVE','COMPLETED') NOT NULL DEFAULT 'PROPOSED',
    started_on  DATE,
    FOREIGN KEY (lead_faculty_id) REFERENCES faculty(faculty_id)
);

CREATE TABLE project_member (
    project_id  INT NOT NULL,
    student_id  INT NOT NULL,
    role        VARCHAR(50) DEFAULT 'Research Assistant',
    PRIMARY KEY (project_id, student_id),
    FOREIGN KEY (project_id) REFERENCES research_project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE
);

CREATE INDEX idx_student_dept ON student(dept_id);
CREATE INDEX idx_enroll_student ON enrollment(student_id);
