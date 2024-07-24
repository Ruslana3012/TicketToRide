INSERT INTO users (name, password, amount)
VALUES ('John Doe',
        '$2a$12$/gn1jbwCe/nbV1um.HVAxuxlu9VCv300s9Ww.kMzqTEhJCXFCCgkK',
        30);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Bristol', 'Birgmngham', 3);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Bristol', 'Swindon', 2);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Birgmngham', 'Swindon', 4);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Birgmngham', 'Coventry', 1);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Coventry', 'Northhampton', 2);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Northhampton', 'London', 2);

INSERT INTO routes(departure, arrival, segments)
VALUES ('London', 'Reading', 1);

INSERT INTO routes(departure, arrival, segments)
VALUES ('Reading', 'Swindon', 4);