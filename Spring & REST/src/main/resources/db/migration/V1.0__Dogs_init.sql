CREATE TABLE IF NOT EXISTS DOGS
(
    ID          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL CHECK (length(name) > 0),
    dateOfBirth DATE,
    height      INTEGER      NOT NULL CHECK (height > 0),
    weight      INTEGER      NOT NULL CHECK (weight > 0)
)