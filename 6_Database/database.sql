DROP DATABASE progettogiochi;
CREATE DATABASE progettogiochi;
USE progettogiochi;

CREATE TABLE utente (
    username VARCHAR(50),
    password VARCHAR(100),
    commenti VARCHAR(200),
    PRIMARY KEY(username)
);

CREATE TABLE gioco (
    titolo VARCHAR(50) PRIMARY KEY
);

CREATE TABLE lista (
    id INT AUTO_INCREMENT,
    nome VARCHAR(20),
    utente_username VARCHAR(50),
    PRIMARY KEY(id),
    FOREIGN KEY (utente_username) REFERENCES utente(username)
);

CREATE TABLE contiene (
    lista_id INT,
    gioco_titolo VARCHAR(50),
    PRIMARY KEY (lista_id, gioco_titolo),
    FOREIGN KEY (lista_id) REFERENCES lista(id),
    FOREIGN KEY (gioco_titolo) REFERENCES gioco(titolo)
);
INSERT INTO gioco(titolo) VALUES("Oriva");
INSERT INTO gioco(titolo) VALUES("Prova1445");
INSERT INTO gioco(titolo) VALUES("Prova1323");
INSERT INTO gioco(titolo) VALUES("Prova123");
INSERT INTO gioco(titolo) VALUES("Prova23");
INSERT INTO gioco(titolo) VALUES("Prova4");