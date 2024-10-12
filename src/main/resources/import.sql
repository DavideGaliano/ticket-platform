-- Ruoli
INSERT INTO ruoli (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ruoli (id, name) VALUES (2, 'ROLE_OPERATORE');

-- Utenti
INSERT INTO utenti (id, nome, email, username, password, stato_disponibilita) VALUES (1, 'Gino', 'gino@libero.it', 'admin', '$2a$12$dq8.4hIVAMCpYJU9SlmHwOCJwUPDVAlYd3eaSAfuZZ8CswydC29QC', true); -- admin123
INSERT INTO utenti (id, nome, email, username, password, stato_disponibilita) VALUES (2, 'Pino', 'pino@tiscali.it', 'operator', '$2a$12$JhG8syLUbtpIviRExrcugeArh93lFGZr1dEIo4H/9hMNOE9iSfUnu', true); -- operator123


-- Associazioni Utenti-Ruoli
INSERT INTO utenti_ruoli (id_utente, id_ruolo) VALUES (1, 1);
INSERT INTO utenti_ruoli (id_utente, id_ruolo) VALUES (2, 2);

--Tickets
INSERT INTO tickets (id, titolo, descrizione, data_creazione, stato, id_operatore) VALUES (1, 'Colpa di Allegri', 'La juve non segna', NOW(), 'Da fare', 2);
INSERT INTO tickets (id, titolo, descrizione, data_creazione, stato, id_operatore) VALUES (2, 'Pareggio Italia', 'Brutta partita contro il Belgio', NOW(), 'Da fare', 2);

--Note
INSERT INTO note (id, testo, data_creazione, id_autore, id_ticket) VALUES (1, 'Sono una nota', NOW(), 1, 1);
INSERT INTO note (id, testo, data_creazione, id_autore, id_ticket) VALUES (2, 'Sono un altra nota', NOW(), 1, 1);
INSERT INTO note (id, testo, data_creazione, id_autore, id_ticket) VALUES (3, 'Sono una nota per un altro ticket', NOW(), 1, 2);