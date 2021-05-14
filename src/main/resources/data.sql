INSERT INTO `days_of_week` VALUES
(1,'mon'),
(2,'tue'),
(3,'wed'),
(4,'thu'),
(5,'fri'),
(6,'sat'),
(7,'sun');

INSERT INTO `ZONE` VALUES
(1, 'zone 1'),
(2, 'zone 2');

INSERT INTO `CAPPING` VALUES
(1, 100, 500, 1,1),
(2, 120, 600, 2,1),
(3, 120, 600, 1,2),
(4, 80, 400, 2,2);

INSERT INTO `RATE` VALUES
(1, 25, 30, 1, 1),
(2, 30, 35, 1, 2),
(3, 30, 35, 2, 1),
(4, 20, 25, 2, 2);

INSERT INTO `RIDE_RULES` VALUES
(1,'17:00:00', TRUE, '20:00:00', NULL, NULL),
(2,'07:00:00', TRUE, '10:30:00', NULL, NULL),
(3,'09:00:00', TRUE, '11:00:00', NULL, NULL),
(4,'18:00:00', TRUE, '22:00:00', NULL, NULL),
(5,'17:00:00', FALSE, '20:00:00', 2, 1),
(6,'18:00:00', FALSE, '22:00:00', 1, 1);

INSERT INTO `RIDE_RULES_DAYS` VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(2,1),
(2,2),
(2,3),
(2,4),
(2,5),
(3,6),
(3,7),
(4,6),
(4,7),
(5,1),
(5,2),
(5,3),
(5,4),
(5,5),
(6,6),
(6,7);


INSERT INTO `TIGER_CARD` VALUES
(1000, now(), 'Yusuf Ali');