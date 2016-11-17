INSERT INTO usuario (id_usuario,nome) VALUES ('c.marcos.porfirio','Marcos Jose Santos Porfirio');
INSERT INTO motorista (id_motorista, nome, tipo, matricula) VALUES (14, 'Cintia Nazare Pantoja Leao', 0, '705-6');
INSERT INTO motorista (id_motorista, nome, tipo, matricula) VALUES (15, 'Faustino Bartolomeu Alves Pimenta', 0, '565-7');
INSERT INTO motorista (id_motorista, nome, tipo, matricula) VALUES (17, 'Carla Afonso de Novoa Melo', 0, '863-X');
INSERT INTO veiculo (placa,marca,modelo,cor,viatura_mp,id_motorista) VALUES ('JVW9192','citroen','c3','vermelho',0,15);
INSERT INTO veiculo (placa,marca,modelo,cor,viatura_mp,id_motorista) VALUES ('OSY2505','hyundai','ix35','prata',0,17);
INSERT INTO veiculo (placa,marca,modelo,cor,viatura_mp,id_motorista) VALUES ('QDI3612','honda','hrv','grafite',0,14);
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (54,'2016-11-14 08:58:00','2016-11-14 12:25:03','2016-11-14 08:58:35',NULL,14,NULL,'QDI3612','c.marcos.porfirio');
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (64,'2016-11-14 13:06:00','2016-11-14 15:29:54','2016-11-14 13:06:07',NULL,14,NULL,'QDI3612','c.marcos.porfirio');
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (48,'2016-11-14 08:25:00','2016-11-14 13:38:27','2016-11-14 08:25:27',NULL,17,NULL,'OSY2505','c.marcos.porfirio');
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (69,'2016-11-16 08:07:00',NULL,'2016-11-16 08:07:45',NULL,17,NULL,'OSY2505','c.marcos.porfirio');
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (55,'2016-11-14 08:58:00','2016-11-14 10:57:43','2016-11-14 08:58:49',NULL,15,NULL,'JVW9192','c.marcos.porfirio');
INSERT INTO acesso_garagem (id_acesso_garagem,entrada,saida,data_hora_cadastro,anotacao,id_motorista,id_visita,placa,id_usuario) VALUES (75,'2016-11-16 08:54:30',NULL,'2016-11-16 08:54:30',NULL,15,NULL,'JVW9192','c.marcos.porfirio');