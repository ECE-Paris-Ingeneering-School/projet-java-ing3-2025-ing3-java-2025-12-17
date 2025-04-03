DROP DATABASE IF EXISTS projet_java;
CREATE DATABASE projet_java;
USE projet_java;

CREATE TABLE Client(
                       IdClient INT,
                       NomUtilisateur VARCHAR(50),
                       MotDePasse VARCHAR(50),
                       Age INT,
                       Tarif VARCHAR(50),
                       PRIMARY KEY(IdClient)
);

CREATE TABLE Attraction(
                           IdAttraction VARCHAR(50),
                           Nom VARCHAR(50),
                           PrixComplet DOUBLE,
                           PrixHabitué DOUBLE,
                           PrixJeune DOUBLE,
                           PrixSenior DOUBLE,
                           PRIMARY KEY(IdAttraction)
);

CREATE TABLE reservation(
                            IdReservation INT,
                            DateReservation DATETIME,
                            NombreBillets INT,
                            #A discuter
                            PrixUnBillet DOUBLE,

                            IdClient INT NOT NULL,
                            IdAttraction VARCHAR(50) NOT NULL,
                            PRIMARY KEY(IdReservation),
                            FOREIGN KEY(IdClient) REFERENCES Client(IdClient),
                            FOREIGN KEY(IdAttraction) REFERENCES Attraction(IdAttraction)
);