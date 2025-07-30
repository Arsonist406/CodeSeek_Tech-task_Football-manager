-- Insert sample teams
INSERT INTO teams (name, transfer_fee, account) VALUES
		                                                ('Red Dragons', 5.5, 1000000.00),
		                                                ('Blue Falcons', 7.2, 2500000.00),
		                                                ('Green Tigers', 3.8, 1500000.00),
		                                                ('Golden Eagles', 6.0, 3000000.00);

-- Insert sample players (50 players distributed among 4 teams)
-- Team 1: Red Dragons (12 players)
INSERT INTO players (name, surname, birth_date, experience_in_mouths, team_id) VALUES
		                                                                               ('John', 'Smith', '1995-03-15', 24, 1),
		                                                                               ('Michael', 'Johnson', '1992-07-22', 36, 1),
		                                                                               ('David', 'Williams', '1998-11-05', 12, 1),
		                                                                               ('James', 'Brown', '1994-09-30', 30, 1),
		                                                                               ('Robert', 'Jones', '1997-05-18', 18, 1),
		                                                                               ('William', 'Garcia', '1993-12-10', 33, 1),
		                                                                               ('Richard', 'Miller', '1996-08-25', 21, 1),
		                                                                               ('Joseph', 'Davis', '1991-04-12', 42, 1),
		                                                                               ('Thomas', 'Rodriguez', '1999-02-28', 9, 1),
		                                                                               ('Charles', 'Martinez', '1990-06-08', 48, 1),
		                                                                               ('Christopher', 'Hernandez', '1995-10-17', 27, 1),
		                                                                               ('Daniel', 'Lopez', '1997-07-03', 15, 1);

-- Team 2: Blue Falcons (13 players)
INSERT INTO players (name, surname, birth_date, experience_in_mouths, team_id) VALUES
		                                                                               ('Matthew', 'Gonzalez', '1994-01-20', 33, 2),
		                                                                               ('Anthony', 'Wilson', '1996-09-14', 21, 2),
		                                                                               ('Mark', 'Anderson', '1993-05-07', 36, 2),
		                                                                               ('Donald', 'Thomas', '1998-03-25', 12, 2),
		                                                                               ('Steven', 'Taylor', '1992-11-11', 39, 2),
		                                                                               ('Paul', 'Moore', '1995-07-30', 24, 2),
		                                                                               ('Andrew', 'Jackson', '1997-04-05', 18, 2),
		                                                                               ('Joshua', 'Martin', '1991-08-19', 42, 2),
		                                                                               ('Kevin', 'Lee', '1999-12-03', 9, 2),
		                                                                               ('Brian', 'Perez', '1994-02-28', 33, 2),
		                                                                               ('George', 'Thompson', '1996-10-15', 21, 2),
		                                                                               ('Timothy', 'White', '1993-06-22', 36, 2),
		                                                                               ('Ronald', 'Harris', '1997-01-08', 18, 2);

-- Team 3: Green Tigers (12 players)
INSERT INTO players (name, surname, birth_date, experience_in_mouths, team_id) VALUES
		                                                                               ('Edward', 'Sanchez', '1995-08-12', 27, 3),
		                                                                               ('Jason', 'Clark', '1992-04-30', 39, 3),
		                                                                               ('Jeffrey', 'Ramirez', '1998-10-17', 12, 3),
		                                                                               ('Ryan', 'Lewis', '1994-07-25', 33, 3),
		                                                                               ('Jacob', 'Robinson', '1997-03-08', 18, 3),
		                                                                               ('Gary', 'Walker', '1993-11-14', 36, 3),
		                                                                               ('Nicholas', 'Hall', '1996-05-21', 24, 3),
		                                                                               ('Eric', 'Young', '1991-09-03', 45, 3),
		                                                                               ('Stephen', 'Allen', '1999-01-26', 9, 3),
		                                                                               ('Jonathan', 'King', '1995-12-07', 27, 3),
		                                                                               ('Larry', 'Wright', '1997-02-19', 1, 3),
		                                                                               ('Frank', 'Scott', '1994-06-11', 33, 3);

-- Team 4: Golden Eagles (13 players)
INSERT INTO players (name, surname, birth_date, experience_in_mouths, team_id) VALUES
		                                                                               ('Scott', 'Torres', '1993-03-28', 36, 4),
		                                                                               ('Brandon', 'Nguyen', '1996-11-09', 21, 4),
		                                                                               ('Benjamin', 'Hill', '1992-07-16', 39, 4),
		                                                                               ('Samuel', 'Flores', '1998-04-23', 12, 4),
		                                                                               ('Gregory', 'Baker', '1995-01-07', 30, 4),
		                                                                               ('Alexander', 'Adams', '1997-08-14', 0, 4),
		                                                                               ('Patrick', 'Nelson', '1994-05-31', 33, 4),
		                                                                               ('Raymond', 'Carter', '1991-10-12', 42, 4),
		                                                                               ('Jack', 'Mitchell', '1999-09-05', 9, 4),
		                                                                               ('Dennis', 'Roberts', '1996-12-20', 21, 4),
		                                                                               ('Jerry', 'Turner', '1993-02-15', 36, 4),
		                                                                               ('Tyler', 'Phillips', '1997-07-28', 18, 4),
		                                                                               ('Aaron', 'Campbell', '1994-04-03', 33, 4);