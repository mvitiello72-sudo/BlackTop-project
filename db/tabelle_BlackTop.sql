DROP DATABASE if exists BlackTop;
CREATE DATABASE BlackTop;
USE BlackTop;

CREATE TABLE utente(
	id_utente int PRIMARY KEY AUTO_INCREMENT,
    email varchar(255) NOT NULL UNIQUE,
    nome varchar(20) NOT NULL,
    cognome varchar(20) NOT NULL,
    password varchar(255) NOT NULL,
    ruolo enum('USER','ADMIN') NOT NULL DEFAULT('USER'),
    cellulare varchar(50)
);

CREATE TABLE indirizzo(
	id_indirizzo int PRIMARY KEY AUTO_INCREMENT,
    via_numciv varchar(255),
    paese varchar(100),
    citta varchar(100),
    provincia varchar(100),
    codice_postale varchar(10),
	predefinito BOOLEAN NOT NULL DEFAULT FALSE,
	
	fk_utente int NOT NULL,
	
	FOREIGN KEY(fk_utente) REFERENCES utente(id_utente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE metodo_pagamento(
	id_metodo int PRIMARY KEY AUTO_INCREMENT,
    tipo varchar(50) NOT NULL,
    numero_carta varchar(20),
    intestatario varchar(100),
    scadenza date,
    cvv varchar(10),
	predefinito BOOLEAN NOT NULL DEFAULT FALSE,
	
    fk_utente int NOT NULL,

    FOREIGN KEY (fk_utente) REFERENCES utente(id_utente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE ordine(
	id_ordine int PRIMARY KEY AUTO_INCREMENT,
    data_ordine date NOT NULL,
    stato enum('IN_PREPARAZIONE','SPEDITO','CONSEGNATO','RIMBORSATO') DEFAULT 'IN_PREPARAZIONE',
    totale decimal (10,2) NOT NULL,
	
	fk_utente int NOT NULL,
	fk_indirizzo int NOT NULL,
	
    FOREIGN KEY(fk_indirizzo) REFERENCES indirizzo(id_indirizzo) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(fk_utente) REFERENCES utente(id_utente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE fattura(
    id_fattura int PRIMARY KEY AUTO_INCREMENT,
    data_emissione date NOT NULL,
    totale_fattura decimal(10,2) NOT NULL,

    fk_ordine INT NOT NULL UNIQUE,

    FOREIGN KEY (fk_ordine) REFERENCES ordine(id_ordine) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE prodotto(
    id_prodotto int PRIMARY KEY AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    squadra varchar(100),
    materiale varchar(255),
    descrizione varchar(255),
    prezzo decimal(10,2) NOT NULL,
    stock int DEFAULT 0,
    taglia enum('XS','S','M','L','XL','XXL') NOT NULL,
    attivo boolean DEFAULT TRUE,
    sconto INT CHECK (sconto BETWEEN 0 AND 100),
	categoria enum('icon','attuali') NOT NULL
);

CREATE TABLE immagine(
	id_immagine int PRIMARY KEY AUTO_INCREMENT,
	percorso_immagine varchar(255),
	
    fk_prodotto int,
    
    FOREIGN KEY(fk_prodotto) REFERENCES prodotto(id_prodotto) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE dettagliOrdine(
	fk_ordine int,
   	fk_prodotto int,
    quantita int DEFAULT 1,
    prezzo_snapshot decimal(10,2),
   	
   	PRIMARY KEY(fk_ordine,fk_prodotto),
   	
    FOREIGN KEY(fk_ordine) REFERENCES ordine(id_ordine) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(fk_prodotto) REFERENCES prodotto(id_prodotto) ON UPDATE cascade ON DELETE restrict
);

CREATE TABLE recensione (
    fk_utente int NOT NULL,
    fk_prodotto int NOT NULL,
    voto int CHECK (voto BETWEEN 1 AND 5),
    commento varchar(500),
    data_recensione date NOT NULL,
	
	PRIMARY KEY(fk_utente,fk_prodotto),

    FOREIGN KEY(fk_utente) REFERENCES utente(id_utente) ON DELETE CASCADE,
    FOREIGN KEY(fk_prodotto) REFERENCES prodotto(id_prodotto) ON DELETE CASCADE
);

