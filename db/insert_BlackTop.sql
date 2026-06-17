USE blacktop;

-- ==========================================
-- 1. UTENTI (Password cifrate in SHA-512)
-- ==========================================
INSERT INTO utente (email, nome, cognome, password, ruolo, cellulare) VALUES
('admin@blacktop.com', 'Alessandro', 'Admin', SHA2('Adminsito1', 512), 'ADMIN', '331122334'),
('marco.rossi@email.com', 'Marco', 'Rossi', SHA2('Marcorossi1', 512), 'USER', '3475566778');

-- ==========================================
-- 2. INDIRIZZI
-- ==========================================
INSERT INTO indirizzo (via_numciv, paese, citta, provincia, codice_postale, predefinito, fk_utente) VALUES
('Via Monte Napoleone 12', 'Italia', 'Milano', 'MI', '20121', TRUE, 1),              -- id_indirizzo: 1 (Admin)
('Via Roma 45', 'Italia', 'Torino', 'TO', '10121', TRUE, 2),                          -- id_indirizzo: 2 (Marco)
('Corso Vittorio Emanuele II 89', 'Italia', 'Torino', 'TO', '10128', FALSE, 2);       -- id_indirizzo: 3 (Marco)

-- ==========================================
-- 3. METODI DI PAGAMENTO
-- ==========================================
INSERT INTO metodo_pagamento (tipo, numero_carta, intestatario, scadenza, cvv, predefinito, fk_utente) VALUES
('Visa', '4532111122223333', 'Marco Rossi', '2028-12-31', '123', TRUE, 2);           -- id_metodo: 1 (Utente 2)

-- ==========================================
-- 4. PRODOTTI (Con varianti di taglia - Rimossa colonna sconto)
-- ==========================================
INSERT INTO prodotto (id_prodotto, nome, squadra, materiale, descrizione, prezzo, stock, taglia, attivo, categoria) VALUES
-- 1. Chicago Bulls Home (M, L, XL) - Prezzo: 89.99
(1, 'Canotta Chicago Bulls Home', 'Chicago Bulls', '100% Poliestere', 'Canotta ufficiale dei Chicago Bulls versione attuale', 89.99, 50, 'M', TRUE, 'attuali'),
(19, 'Canotta Chicago Bulls Home', 'Chicago Bulls', '100% Poliestere', 'Canotta ufficiale dei Chicago Bulls versione attuale', 89.99, 30, 'L', TRUE, 'attuali'),
(20, 'Canotta Chicago Bulls Home', 'Chicago Bulls', '100% Poliestere', 'Canotta ufficiale dei Chicago Bulls versione attuale', 89.99, 20, 'XL', TRUE, 'attuali'),

-- 2. Chicago Bulls Away (L, M, XL) - Prezzo: 89.99
(2, 'Canotta Chicago Bulls Away', 'Chicago Bulls', '100% Poliestere', 'Seconda canotta dei Chicago Bulls versione attuale', 89.99, 35, 'L', TRUE, 'attuali'),
(21, 'Canotta Chicago Bulls Away', 'Chicago Bulls', '100% Poliestere', 'Seconda canotta dei Chicago Bulls versione attuale', 89.99, 40, 'M', TRUE, 'attuali'),
(22, 'Canotta Chicago Bulls Away', 'Chicago Bulls', '100% Poliestere', 'Seconda canotta dei Chicago Bulls versione attuale', 89.99, 15, 'XL', TRUE, 'attuali'),

-- 3. Golden State Warriors (L, M, S) - Prezzo: 89.99
(3, 'Canotta Golden State Warriors', 'Golden State Warriors', '100% Poliestere', 'Canotta ufficiale Golden State versione attuale', 89.99, 40, 'L', TRUE, 'attuali'),
(23, 'Canotta Golden State Warriors', 'Golden State Warriors', '100% Poliestere', 'Canotta ufficiale Golden State versione attuale', 89.99, 50, 'M', TRUE, 'attuali'),
(24, 'Canotta Golden State Warriors', 'Golden State Warriors', '100% Poliestere', 'Canotta ufficiale Golden State versione attuale', 89.99, 15, 'S', TRUE, 'attuali'),

-- 4. Stephen Curry Icon (M, L, XL) - Prezzo: 110.00
(4, 'Canotta Stephen Curry Icon', 'Golden State Warriors', 'Cotone/Poliestere', 'Canotta celebrativa Icon Edition di Stephen Curry', 110.00, 20, 'M', TRUE, 'icon'),
(25, 'Canotta Stephen Curry Icon', 'Golden State Warriors', 'Cotone/Poliestere', 'Canotta celebrativa Icon Edition di Stephen Curry', 110.00, 25, 'L', TRUE, 'icon'),
(26, 'Canotta Stephen Curry Icon', 'Golden State Warriors', 'Cotone/Poliestere', 'Canotta celebrativa Icon Edition di Stephen Curry', 110.00, 10, 'XL', TRUE, 'icon'),

-- 5. LA Lakers Home (XL, L, M) - Prezzo: 89.99
(5, 'Canotta LA Lakers Home', 'Los Angeles Lakers', '100% Poliestere', 'Canotta ufficiale Los Angeles Lakers attuale giallo/viola', 89.99, 60, 'XL', TRUE, 'attuali'),
(27, 'Canotta LA Lakers Home', 'Los Angeles Lakers', '100% Poliestere', 'Canotta ufficiale Los Angeles Lakers attuale giallo/viola', 89.99, 45, 'L', TRUE, 'attuali'),
(28, 'Canotta LA Lakers Home', 'Los Angeles Lakers', '100% Poliestere', 'Canotta ufficiale Los Angeles Lakers attuale giallo/viola', 89.99, 40, 'M', TRUE, 'attuali'),

-- 6. LA Lakers Away (M, L, S) - Prezzo: 89.99
(6, 'Canotta LA Lakers Away', 'Los Angeles Lakers', '100% Poliestere', 'Seconda canotta Los Angeles Lakers attuale', 89.99, 45, 'M', TRUE, 'attuali'),
(29, 'Canotta LA Lakers Away', 'Los Angeles Lakers', '100% Poliestere', 'Seconda canotta Los Angeles Lakers attuale', 89.99, 30, 'L', TRUE, 'attuali'),
(30, 'Canotta LA Lakers Away', 'Los Angeles Lakers', '100% Poliestere', 'Seconda canotta Los Angeles Lakers attuale', 89.99, 20, 'S', TRUE, 'attuali'),

-- 7. Kobe Bryant Icon (L, M, XL) - Prezzo: 120.00
(7, 'Canotta Kobe Bryant Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Kobe Bryant #24', 120.00, 15, 'L', TRUE, 'icon'),
(31, 'Canotta Kobe Bryant Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Kobe Bryant #24', 120.00, 18, 'M', TRUE, 'icon'),
(32, 'Canotta Kobe Bryant Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Kobe Bryant #24', 120.00, 12, 'XL', TRUE, 'icon'),

-- 8. Oklahoma City Thunder (S, M, L) - Prezzo: 84.99
(8, 'Canotta Oklahoma City Thunder', 'Oklahoma City Thunder', '100% Poliestere', 'Canotta ufficiale OKC Thunder versione attuale', 84.99, 25, 'S', TRUE, 'attuali'),
(33, 'Canotta Oklahoma City Thunder', 'Oklahoma City Thunder', '100% Poliestere', 'Canotta ufficiale OKC Thunder versione attuale', 84.99, 30, 'M', TRUE, 'attuali'),
(34, 'Canotta Oklahoma City Thunder', 'Oklahoma City Thunder', '100% Poliestere', 'Canotta ufficiale OKC Thunder versione attuale', 84.99, 20, 'L', TRUE, 'attuali'),

-- 9. Milwaukee Bucks (XL, L, M) - Prezzo: 89.99
(9, 'Canotta Milwaukee Bucks', 'Milwaukee Bucks', '100% Poliestere', 'Canotta ufficiale Giannis Antetokounmpo Bucks attuale', 89.99, 30, 'XL', TRUE, 'attuali'),
(35, 'Canotta Milwaukee Bucks', 'Milwaukee Bucks', '100% Poliestere', 'Canotta ufficiale Giannis Antetokounmpo Bucks attuale', 89.99, 35, 'L', TRUE, 'attuali'),
(36, 'Canotta Milwaukee Bucks', 'Milwaukee Bucks', '100% Poliestere', 'Canotta ufficiale Giannis Antetokounmpo Bucks attuale', 89.99, 25, 'M', TRUE, 'attuali'),

-- 10. Miami Heat (M, L, XL) - Prezzo: 89.99
(10, 'Canotta Miami Heat', 'Miami Heat', '100% Poliestere', 'Canotta ufficiale Miami Heat versione attuale', 89.99, 28, 'M', TRUE, 'attuali'),
(37, 'Canotta Miami Heat', 'Miami Heat', '100% Poliestere', 'Canotta ufficiale Miami Heat versione attuale', 89.99, 35, 'L', TRUE, 'attuali'),
(38, 'Canotta Miami Heat', 'Miami Heat', '100% Poliestere', 'Canotta ufficiale Miami Heat versione attuale', 89.99, 15, 'XL', TRUE, 'attuali'),

-- 11. Cleveland Cavaliers (L, M, S) - Prezzo: 84.99
(11, 'Canotta Cleveland Cavaliers', 'Cleveland Cavaliers', '100% Poliestere', 'Canotta ufficiale Cleveland Cavs versione attuale', 84.99, 20, 'L', TRUE, 'attuali'),
(39, 'Canotta Cleveland Cavaliers', 'Cleveland Cavaliers', '100% Poliestere', 'Canotta ufficiale Cleveland Cavs versione attuale', 84.99, 25, 'M', TRUE, 'attuali'),
(40, 'Canotta Cleveland Cavaliers', 'Cleveland Cavaliers', '100% Poliestere', 'Canotta ufficiale Cleveland Cavs versione attuale', 84.99, 10, 'S', TRUE, 'attuali'),

-- 12. Dallas Mavericks (M, L, XL) - Prezzo: 89.99
(12, 'Canotta Dallas Mavericks', 'Dallas Mavericks', '100% Poliestere', 'Canotta ufficiale Luka Doncic Dallas attuale', 89.99, 42, 'M', TRUE, 'attuali'),
(41, 'Canotta Dallas Mavericks', 'Dallas Mavericks', '100% Poliestere', 'Canotta ufficiale Luka Doncic Dallas attuale', 89.99, 38, 'L', TRUE, 'attuali'),
(42, 'Canotta Dallas Mavericks', 'Dallas Mavericks', '100% Poliestere', 'Canotta ufficiale Luka Doncic Dallas attuale', 89.99, 20, 'XL', TRUE, 'attuali'),

-- 13. New York Knicks (L, M, XL) - Prezzo: 84.99
(13, 'Canotta New York Knicks', 'New York Knicks', '100% Poliestere', 'Canotta ufficiale New York Knicks versione attuale', 84.99, 33, 'L', TRUE, 'attuali'),
(43, 'Canotta New York Knicks', 'New York Knicks', '100% Poliestere', 'Canotta ufficiale New York Knicks versione attuale', 84.99, 40, 'M', TRUE, 'attuali'),
(44, 'Canotta New York Knicks', 'New York Knicks', '100% Poliestere', 'Canotta ufficiale New York Knicks versione attuale', 84.99, 15, 'XL', TRUE, 'attuali'),

-- 14. Denver Nuggets (XL, L, M) - Prezzo: 89.99
(14, 'Canotta Denver Nuggets', 'Denver Nuggets', '100% Poliestere', 'Canotta ufficiale Nikola Jokic Denver attuale', 89.99, 25, 'XL', TRUE, 'attuali'),
(45, 'Canotta Denver Nuggets', 'Denver Nuggets', '100% Poliestere', 'Canotta ufficiale Nikola Jokic Denver attuale', 89.99, 30, 'L', TRUE, 'attuali'),
(46, 'Canotta Denver Nuggets', 'Denver Nuggets', '100% Poliestere', 'Canotta ufficiale Nikola Jokic Denver attuale', 89.99, 20, 'M', TRUE, 'attuali'),

-- 15. Dwyane Wade Icon (M, L, XL) - Prezzo: 110.00
(15, 'Canotta Dwyane Wade Icon', 'Miami Heat', 'Mesh di alta qualità', 'Canotta storica Icon Edition Dwyane Wade Miami Heat', 110.00, 12, 'M', TRUE, 'icon'),
(47, 'Canotta Dwyane Wade Icon', 'Miami Heat', 'Mesh di alta qualità', 'Canotta storica Icon Edition Dwyane Wade Miami Heat', 110.00, 15, 'L', TRUE, 'icon'),
(48, 'Canotta Dwyane Wade Icon', 'Miami Heat', 'Mesh di alta qualità', 'Canotta storica Icon Edition Dwyane Wade Miami Heat', 110.00, 8, 'XL', TRUE, 'icon'),

-- 16. Michael Jordan Icon (L, M, XL) - Prezzo: 130.00
(16, 'Canotta Michael Jordan Icon', 'Chicago Bulls', 'Mesh di alta qualità', 'Canotta storica leggendaria Icon Edition Michael Jordan #23', 130.00, 10, 'L', TRUE, 'icon'),
(49, 'Canotta Michael Jordan Icon', 'Chicago Bulls', 'Mesh di alta qualità', 'Canotta storica leggendaria Icon Edition Michael Jordan #23', 130.00, 12, 'M', TRUE, 'icon'),
(50, 'Canotta Michael Jordan Icon', 'Chicago Bulls', 'Mesh di alta qualità', 'Canotta storica leggendaria Icon Edition Michael Jordan #23', 130.00, 8, 'XL', TRUE, 'icon'),

-- 17. Shaquille O Neal Icon (XXL, XL, L) - Prezzo: 120.00
(17, 'Canotta Shaquille O Neal Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Shaquille O Neal Los Angeles', 120.00, 8, 'XXL', TRUE, 'icon'),
(51, 'Canotta Shaquille O Neal Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Shaquille O Neal Los Angeles', 120.00, 10, 'XL', TRUE, 'icon'),
(52, 'Canotta Shaquille O Neal Icon', 'Los Angeles Lakers', 'Mesh di alta qualità', 'Canotta storica Icon Edition Shaquille O Neal Los Angeles', 120.00, 10, 'L', TRUE, 'icon'),

-- 18. LeBron James Icon (XL, L, M) - Prezzo: 115.00
(18, 'Canotta LeBron James Icon', 'Cleveland Cavaliers', 'Cotone/Poliestere', 'Canotta storica Icon Edition LeBron James dei primi anni', 115.00, 18, 'XL', TRUE, 'icon'),
(53, 'Canotta LeBron James Icon', 'Cleveland Cavaliers', 'Cotone/Poliestere', 'Canotta storica Icon Edition LeBron James dei primi anni', 115.00, 20, 'L', TRUE, 'icon'),
(54, 'Canotta LeBron James Icon', 'Cleveland Cavaliers', 'Cotone/Poliestere', 'Canotta storica Icon Edition LeBron James dei primi anni', 115.00, 15, 'M', TRUE, 'icon');


-- ==========================================
-- 5. IMMAGINI
-- ==========================================
INSERT INTO immagine (percorso_immagine, fk_prodotto) VALUES
-- Immagini per Chicago Bulls Home (ID: 1, 19, 20)
('/img/prodotti/chicago_bulls_attuale_1.jpg', 1),
('/img/prodotti/chicago_bulls_attuale_2.jpg', 1),
('/img/prodotti/chicago_bulls_attuale_1.jpg', 19),
('/img/prodotti/chicago_bulls_attuale_2.jpg', 19),
('/img/prodotti/chicago_bulls_attuale_1.jpg', 20),
('/img/prodotti/chicago_bulls_attuale_2.jpg', 20),

-- Immagini per Chicago Bulls Away (ID: 2, 21, 22)
('/img/prodotti/chicago_bulls_attuale2_1.jpg', 2),
('/img/prodotti/chicago_bulls_attuale2_2.jpg', 2),
('/img/prodotti/chicago_bulls_attuale2_3.jpg', 2),
('/img/prodotti/chicago_bulls_attuale2_1.jpg', 21),
('/img/prodotti/chicago_bulls_attuale2_2.jpg', 21),
('/img/prodotti/chicago_bulls_attuale2_3.jpg', 21),
('/img/prodotti/chicago_bulls_attuale2_1.jpg', 22),
('/img/prodotti/chicago_bulls_attuale2_2.jpg', 22),
('/img/prodotti/chicago_bulls_attuale2_3.jpg', 22),

-- Immagini per Golden State Warriors (ID: 3, 23, 24)
('/img/prodotti/golden_state_attuale_1.jpg', 3),
('/img/prodotti/golden_state_attuale_2.jpg', 3),
('/img/prodotti/golden_state_attuale_3.jpg', 3),
('/img/prodotti/golden_state_attuale_1.jpg', 23),
('/img/prodotti/golden_state_attuale_2.jpg', 23),
('/img/prodotti/golden_state_attuale_3.jpg', 23),
('/img/prodotti/golden_state_attuale_1.jpg', 24),
('/img/prodotti/golden_state_attuale_2.jpg', 24),
('/img/prodotti/golden_state_attuale_3.jpg', 24),

-- Immagini per Stephen Curry Icon (ID: 4, 25, 26)
('/img/prodotti/icon_curry_1.jpg', 4),
('/img/prodotti/icon_curry_2.jpg', 4),
('/img/prodotti/icon_curry_1.jpg', 25),
('/img/prodotti/icon_curry_2.jpg', 25),
('/img/prodotti/icon_curry_1.jpg', 26),
('/img/prodotti/icon_curry_2.jpg', 26),

-- Immagini per LA Lakers Home (ID: 5, 27, 28)
('/img/prodotti/lakers_attuale_1.jpg', 5),
('/img/prodotti/lakers_attuale_2.jpg', 5),
('/img/prodotti/lakers_attuale_1.jpg', 27),
('/img/prodotti/lakers_attuale_2.jpg', 27),
('/img/prodotti/lakers_attuale_1.jpg', 28),
('/img/prodotti/lakers_attuale_2.jpg', 28),

-- Immagini per LA Lakers Away (ID: 6, 29, 30)
('/img/prodotti/lakers_attuale2_1.jpg', 6),
('/img/prodotti/lakers_attuale2_.jpg', 6),
('/img/prodotti/lakers_attuale2_1.jpg', 29),
('/img/prodotti/lakers_attuale2_.jpg', 29),
('/img/prodotti/lakers_attuale2_1.jpg', 30),
('/img/prodotti/lakers_attuale2_.jpg', 30),

-- Immagini per Kobe Bryant Icon (ID: 7, 31, 32)
('/img/prodotti/icon_kobe_1.jpg', 7),
('/img/prodotti/icon_kobe_2.jpg', 7),
('/img/prodotti/icon_kobe_1.jpg', 31),
('/img/prodotti/icon_kobe_2.jpg', 31),
('/img/prodotti/icon_kobe_1.jpg', 32),
('/img/prodotti/icon_kobe_2.jpg', 32),

-- Immagini per Oklahoma City Thunder (ID: 8, 33, 34)
('/img/prodotti/thunder_attuale_1.jpg', 8),
('/img/prodotti/thunder_attuale_2.jpg', 8),
('/img/prodotti/thunder_attuale_1.jpg', 33),
('/img/prodotti/thunder_attuale_2.jpg', 33),
('/img/prodotti/thunder_attuale_1.jpg', 34),
('/img/prodotti/thunder_attuale_2.jpg', 34),

-- Immagini per Milwaukee Bucks (ID: 9, 35, 36)
('/img/prodotti/bucks_attuale_1.jpg', 9),
('/img/prodotti/bucks_attuale_2.jpg', 9),
('/img/prodotti/bucks_attuale_1.jpg', 35),
('/img/prodotti/bucks_attuale_2.jpg', 35),
('/img/prodotti/bucks_attuale_1.jpg', 36),
('/img/prodotti/bucks_attuale_2.jpg', 36),

-- Immagini per Miami Heat (ID: 10, 37, 38)
('/img/prodotti/miami_attuale_1.jpg', 10),
('/img/prodotti/miami_attuale_2.jpg', 10),
('/img/prodotti/miami_attuale_1.jpg', 37),
('/img/prodotti/miami_attuale_2.jpg', 37),
('/img/prodotti/miami_attuale_1.jpg', 38),
('/img/prodotti/miami_attuale_2.jpg', 38),

-- Immagini per Cleveland Cavaliers (ID: 11, 39, 40)
('/img/prodotti/cleveland_attuale_1.jpg', 11),
('/img/prodotti/cleveland_attuale_2.jpg', 11),
('/img/prodotti/cleveland_attuale_1.jpg', 39),
('/img/prodotti/cleveland_attuale_2.jpg', 39),
('/img/prodotti/cleveland_attuale_1.jpg', 40),
('/img/prodotti/cleveland_attuale_2.jpg', 40),

-- Immagini per Dallas Mavericks (ID: 12, 41, 42)
('/img/prodotti/dallas_attuale_1.jpg', 12),
('/img/prodotti/dallas_attuale_2.jpg', 12),
('/img/prodotti/dallas_attuale_1.jpg', 41),
('/img/prodotti/dallas_attuale_2.jpg', 41),
('/img/prodotti/dallas_attuale_1.jpg', 42),
('/img/prodotti/dallas_attuale_2.jpg', 42),

-- Immagini per New York Knicks (ID: 13, 43, 44)
('/img/prodotti/ny_attuale_1.jpg', 13),
('/img/prodotti/ny_attuale_2.jpg', 13),
('/img/prodotti/ny_attuale_1.jpg', 43),
('/img/prodotti/ny_attuale_2.jpg', 43),
('/img/prodotti/ny_attuale_1.jpg', 44),
('/img/prodotti/ny_attuale_2.jpg', 44),

-- Immagini per Denver Nuggets (ID: 14, 45, 46)
('/img/prodotti/denver_attuale_1.jpg', 14),
('/img/prodotti/denver_attuale_2.jpg', 14),
('/img/prodotti/denver_attuale_1.jpg', 45),
('/img/prodotti/denver_attuale_2.jpg', 45),
('/img/prodotti/denver_attuale_1.jpg', 46),
('/img/prodotti/denver_attuale_2.jpg', 46),

-- Immagini per Dwyane Wade Icon (ID: 15, 47, 48)
('/img/prodotti/icon_wade_1.jpg', 15),
('/img/prodotti/icon_wade_2.jpg', 15),
('/img/prodotti/icon_wade_3.jpg', 15),
('/img/prodotti/icon_wade_1.jpg', 47),
('/img/prodotti/icon_wade_2.jpg', 47),
('/img/prodotti/icon_wade_3.jpg', 47),
('/img/prodotti/icon_wade_1.jpg', 48),
('/img/prodotti/icon_wade_2.jpg', 48),
('/img/prodotti/icon_wade_3.jpg', 48),

-- Immagini per Michael Jordan Icon (ID: 16, 49, 50)
('/img/prodotti/icon_jordan_1.jpg', 16),
('/img/prodotti/icon_jordan_1.jpg', 49),
('/img/prodotti/icon_jordan_1.jpg', 50),

-- Immagini per Shaquille O Neal Icon (ID: 17, 51, 52)
('/img/prodotti/icon_shaquille_1.jpg', 17),
('/img/prodotti/icon_shaquille_1.jpg', 51),
('/img/prodotti/icon_shaquille_1.jpg', 52),

-- Immagini per LeBron James Icon (ID: 18, 53, 54)
('/img/prodotti/icon_lebron_1.jpg', 18),
('/img/prodotti/icon_lebron_2.jpg', 18),
('/img/prodotti/icon_lebron_1.jpg', 53),
('/img/prodotti/icon_lebron_2.jpg', 53),
('/img/prodotti/icon_lebron_1.jpg', 54),
('/img/prodotti/icon_lebron_2.jpg', 54);


-- ==========================================
-- 6. ORDINI (Ricalcolati a prezzo pieno senza sconti)
-- ==========================================
INSERT INTO ordine (data_ordine, stato, totale, fk_utente, fk_indirizzo, fk_pagamento) VALUES
('2026-05-15', 'CONSEGNATO', 179.98, 2, 2, 1),   -- id_ordine: 1 (Prod 3 + Prod 5 = 89.99 + 89.99)
('2026-06-01', 'SPEDITO', 89.99, 2, 3, 1),       -- id_ordine: 2 (Prod 6 = 89.99)
('2026-06-05', 'CONSEGNATO', 110.00, 2, 2, 1),   -- id_ordine: 3 (Prod 4 = 110.00)
('2026-06-10', 'SPEDITO', 89.99, 2, 2, 1),       -- id_ordine: 4 (Prod 1 = 89.99)
('2026-06-11', 'IN_PREPARAZIONE', 130.00, 2, 3, 1), -- id_ordine: 5 (Prod 16 = 130.00)
('2026-06-12', 'IN_PREPARAZIONE', 199.99, 2, 2, 1); -- id_ordine: 6 (Prod 2 + Prod 4 = 89.99 + 110.00)

-- ==========================================
-- 7. DETTAGLI ORDINE (Snapshot aggiornati a prezzo pieno)
-- ==========================================
INSERT INTO dettagliOrdine (fk_ordine, fk_prodotto, quantita, prezzo_snapshot) VALUES
-- Dettagli Ordine 1 (Totale: 89.99 + 89.99 = 179.98)
(1, 3, 1, 89.99),    -- Canotta Golden State Warriors (Prezzo pieno)
(1, 5, 1, 89.99),    -- Canotta LA Lakers Home (89.99)

-- Dettagli Ordine 2 (Totale: 89.99)
(2, 6, 1, 89.99),    -- Canotta LA Lakers Away (Prezzo pieno)

-- Dettagli Ordine 3 (Totale: 110.00)
(3, 4, 1, 110.00),   -- Canotta Stephen Curry Icon (110.00)

-- Dettagli Ordine 4 (Totale: 89.99)
(4, 1, 1, 89.99),    -- Canotta Chicago Bulls Home (89.99)

-- Dettagli Ordine 5 (Totale: 130.00)
(5, 16, 1, 130.00),  -- Canotta Michael Jordan Icon (130.00)

-- Dettagli Ordine 6 (Totale: 89.99 + 110.00 = 199.99)
(6, 2, 1, 89.99),    -- Canotta Chicago Bulls Away (89.99)
(6, 4, 1, 110.00);   -- Canotta Stephen Curry Icon (110.00)

-- ==========================================
-- 8. FATTURE (Allineate al centesimo con i nuovi totali)
-- ==========================================
INSERT INTO fattura (data_emissione, totale_fattura, fk_ordine) VALUES
('2026-05-15', 179.98, 1),
('2026-06-02', 89.99, 2),
('2026-06-05', 110.00, 3),
('2026-06-10', 89.99, 4),
('2026-06-11', 130.00, 5),
('2026-06-12', 199.99, 6);