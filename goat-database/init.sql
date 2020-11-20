-- User information table
CREATE TABLE IF NOT EXISTS Users
(
    username VARCHAR(80) NOT NULL,
    password VARCHAR(80) NOT NULL,
    email VARCHAR(80) NOT NULL,
    phone BIGINT NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY(username)
);

-- Game information table
CREATE TABLE IF NOT EXISTS Games
(
    name VARCHAR(80) NOT NULL,
    startGameURL VARCHAR(80) NOT NULL,
    createGameURL VARCHAR(80) NOT NULL,
    PRIMARY KEY(name)
);

-- Game Records table
CREATE TABLE IF NOT EXISTS GameRecords
(
    gameId INT GENERATED ALWAYS AS IDENTITY,
    gameName VARCHAR(80) NOT NULL,
    winner VARCHAR(80),
    tournamentId INT,
	round INT,
	seed1 INT,
	seed2 INT,
    PRIMARY KEY(gameId),
    CONSTRAINT fkWinner
        FOREIGN KEY(winner)
	        REFERENCES Users(username),
    CONSTRAINT fkGameName
        FOREIGN KEY(gameName)
	        REFERENCES Games(name)
);

-- Token mappings table
CREATE TABLE IF NOT EXISTS TokenMappings
(
    token INT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(80) NOT NULL,
    gameId INT NOT NULL,
    PRIMARY KEY(token),
    CONSTRAINT fkUsername
        FOREIGN KEY(username)
	        REFERENCES Users(username),
    CONSTRAINT fkGameId
        FOREIGN KEY(gameId)
	        REFERENCES GameRecords(gameId)
);

-- Game Players table tracking participants in a game record
CREATE TABLE IF NOT EXISTS GamePlayers
(
    gameId INT,
    username VARCHAR(80),
    PRIMARY KEY(gameId, username),
    CONSTRAINT fkGameId
        FOREIGN KEY(gameId)
	        REFERENCES GameRecords(gameId),
    CONSTRAINT fkUsername
        FOREIGN KEY(username)
	        REFERENCES Users(username)
);

-- Tournaments table tracks tournament information
CREATE TABLE IF NOT EXISTS Tournaments
(
    tournamentId INT GENERATED ALWAYS AS IDENTITY,
    maxPlayers INT NOT NULL,
    currentPlayers INT NOT NULL,
    tournamentName VARCHAR(80) NOT NULL,
    gameName VARCHAR(80) NOT NULL,
    PRIMARY KEY(tournamentId),
    CONSTRAINT fkGameName
        FOREIGN KEY(gameName)
	        REFERENCES Games(name)
);

-- Seeding table maps a player to a tournament
CREATE TABLE IF NOT EXISTS Seedings
(
    seedingId INT GENERATED ALWAYS AS IDENTITY,
    tournamentId INT NOT NULL,
    seed INT NOT NULL DEFAULT 0,
    username VARCHAR(80) NOT NULL,
    round INT NOT NULL,
    PRIMARY KEY(seedingId),
    CONSTRAINT fkTournamentId
        FOREIGN KEY(tournamentId)
	        REFERENCES Tournaments(tournamentId),
    CONSTRAINT fkUsername
        FOREIGN KEY(username)
	        REFERENCES Users(username)
)