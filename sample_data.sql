USE university_db;

INSERT INTO department (dept_name, building) VALUES
 ('Computer Science', 'Block A'),
 ('Electronics', 'Block B'),
 ('Mechanical', 'Block C');

INSERT INTO faculty (full_name, email, designation, dept_id) VALUES
 ('Dr. Ritu Sen',    'ritu.sen@uni.edu',   'Professor', 1),
 ('Dr. Amit Ghosh',  'amit.ghosh@uni.edu', 'Associate Professor', 1),
 ('Dr. Neha Das',    'neha.das@uni.edu',   'Assistant Professor', 2);

INSERT INTO student (full_name, email, dept_id, year_of_study) VALUES
 ('Aniket Roy',    'aniket.roy@uni.edu',  1, 3),
 ('Priya Mondal',  'priya.m@uni.edu',     1, 2),
 ('Rahul Bose',    'rahul.b@uni.edu',     2, 4);

INSERT INTO course (course_code, title, credits, dept_id, faculty_id) VALUES
 ('CS301', 'Database Management Systems', 4, 1, 1),
 ('CS302', 'Operating Systems',            4, 1, 2),
 ('EC201', 'Digital Electronics',          3, 2, 3);

INSERT INTO enrollment (student_id, course_id, semester, grade) VALUES
 (1, 1, '2026-ODD', NULL),
 (1, 2, '2026-ODD', NULL),
 (2, 1, '2026-ODD', NULL),
 (3, 3, '2026-ODD', 'A');

INSERT INTO research_project (title, lead_faculty_id, status, started_on) VALUES
 ('Semantic Search for Academic Archives', 1, 'ACTIVE', '2026-08-01');

INSERT INTO project_member (project_id, student_id, role) VALUES (1, 1, 'Research Assistant');
