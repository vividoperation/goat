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
        'http://goat.millercoding.com:5000',
        'http://goat.millercoding.com:8081/newgame'
    );
