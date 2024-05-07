CREATE TABLE IF NOT EXISTS profile (
    id UUID PRIMARY KEY,
    gender VARCHAR(10),
    nickname VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    email VARCHAR(100),
    university VARCHAR(100),
    major VARCHAR(100),
    phone_number VARCHAR(20),
    date_of_admission DATE
);
