CREATE TABLE teams (
		                   id BIGSERIAL PRIMARY KEY,
		                   name VARCHAR(255) NOT NULL,
		                   transfer_fee DOUBLE PRECISION NOT NULL,
		                   account DOUBLE PRECISION NOT NULL
);

CREATE TABLE players (
		                     id BIGSERIAL PRIMARY KEY,
		                     name VARCHAR(255) NOT NULL,
		                     surname VARCHAR(255) NOT NULL,
		                     birth_date DATE NOT NULL,
		                     experience_in_mouths INTEGER NOT NULL,
		                     team_id BIGINT,
		                     CONSTRAINT fk_players_team FOREIGN KEY (team_id)
				                     REFERENCES teams (id)
				                     ON DELETE SET NULL
);
