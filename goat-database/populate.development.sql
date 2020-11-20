-- Create one example game
INSERT INTO Games
    (
        name,
        startGameURL,
        createGameURL
    )
VALUES
    (
        'testgame',
        'http://localhost:5000',
        'http://testgame-backend:8081/newgame'
    );
