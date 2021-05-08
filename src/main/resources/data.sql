INSERT INTO `days_of_week` VALUES
(1,'mon'),
(2,'tue'),
(3,'wed'),
(4,'thu'),
(5,'fri'),
(6,'sat'),
(7,'sun');

INSERT INTO `ZONE` VALUES
('z1', 'zone 1'),
('z2', 'zone 2');

INSERT INTO `CAPPING` VALUES
(1, 100, 500, 'z1','z1'),
(2, 120, 600, 'z1','z2'),
(3, 120, 600, 'z2','z1'),
(4, 80, 400, 'z2','z2');

INSERT INTO `RATE` VALUES
(1, 25, 30, 'z1', 'z1'),
(2, 30, 35, 'z1', 'z2'),
(3, 30, 35, 'z2', 'z1'),
(4, 20, 25, 'z2', 'z2');
