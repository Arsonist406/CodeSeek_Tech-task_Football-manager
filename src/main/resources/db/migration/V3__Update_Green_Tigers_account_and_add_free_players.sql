-- Update Green Tigers account to 0
UPDATE teams
SET account = 0
WHERE name = 'Green Tigers';

-- Add players without team
INSERT INTO players (name, surname, birth_date, experience_in_mouths, team_id) VALUES
		                                                                               ('Eric', 'King', '1993-03-28', 31, null),
		                                                                               ('Jason', 'Sanchez', '1996-11-09', 15, null),
		                                                                               ('Campbell', 'Hill', '1996-07-28', 13, null),
		                                                                               ('Aaron', 'Walker', '1998-04-23', 27, null);