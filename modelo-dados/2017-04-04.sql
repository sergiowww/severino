insert into `severino`.`organizacao` (`id_organizacao`, `nome`)VALUES (1, 'PRT08');
insert into `severino`.`local` (`id_local`, `nome`, `id_organizacao`) VALUES (1, 'Sede', 1);

/*usuario*/
insert into `severino`.`usuario`
(`id_usuario`,
`nome`,
`id_local`)
SELECT `usuario`.`id_usuario`,
    `usuario`.`nome`,
    1 as ID_LOCAL
from `severino_new`.`usuario`;


/*visitante*/
insert into `severino`.`visitante`
(`id_visitante`,
`nome`,
`documento`,
`orgao_emissor`,
`uf`,
`profissao`,
`telefone`,
`telefone_alternativo`,
`email`)
SELECT `visitante`.`id_visitante`,
    `visitante`.`nome`,
    `visitante`.`documento`,
    `visitante`.`orgao_emissor`,
    `visitante`.`uf`,
    `visitante`.`profissao`,
    `visitante`.`telefone`,
    `visitante`.`telefone_alternativo`,
    `visitante`.`email`
from `severino_new`.`visitante`;

/*endereco*/
insert into `severino`.`endereco`
(`id_visitante`,
`cep`,
`municipio`,
`bairro`,
`complemento`,
`numero`,
`uf`,
`referencia`,
`logradouro`)
SELECT `endereco`.`id_visitante`,
    `endereco`.`cep`,
    `endereco`.`municipio`,
    `endereco`.`bairro`,
    `endereco`.`complemento`,
    `endereco`.`numero`,
    `endereco`.`uf`,
    `endereco`.`referencia`,
    `endereco`.`logradouro`
from `severino_new`.`endereco`;


/*motorista*/
insert into `severino`.`motorista`
(`id_motorista`,
`nome`,
`tipo`,
`matricula`,
`id_local`)
SELECT `motorista`.`id_motorista`,
    `motorista`.`nome`,
    `motorista`.`tipo`,
    `motorista`.`matricula`,
    1 as ID_LOCAL
from `severino_new`.`motorista`;

/*veiculo*/
insert into `severino`.`veiculo`
(`placa`,
`marca`,
`modelo`,
`cor`,
`viatura_mp`,
`id_motorista`,
`id_local`,
`ativo`)
SELECT `veiculo`.`placa`,
    `veiculo`.`marca`,
    `veiculo`.`modelo`,
    `veiculo`.`cor`,
    `veiculo`.`viatura_mp`,
    `veiculo`.`id_motorista`,
    1 as ID_LOCAL,
    1 as ATIVO
from `severino_new`.`veiculo`;

/*setor*/
insert into `severino`.`setor`
(`id_setor`,
`nome`,
`andar`,
`sala`,
`id_local`)
SELECT `setor`.`id_setor`,
    `setor`.`nome`,
    `setor`.`andar`,
    `setor`.`sala`,
    1 as ID_LOCAL
from `severino_new`.`setor`;

/*empresa*/
insert into `severino`.`empresa`
(`id_empresa`,
`nome`)
SELECT `empresa`.`id_empresa`,
    `empresa`.`nome`
from `severino_new`.`empresa`;

/*controle_motorista*/
insert into `severino`.`controle_motorista`
(`id_controle_motorista`,
`id_motorista`,
`data_hora`,
`fluxo`,
`id_local`)
SELECT `controle_motorista`.`id_controle_motorista`,
    `controle_motorista`.`id_motorista`,
    `controle_motorista`.`data_hora`,
    `controle_motorista`.`fluxo`,
    1 as ID_LOCAL
from `severino_new`.`controle_motorista`;


/*viagem*/
insert into `severino`.`viagem`
(`id_viagem`,
`anotacao`,
`data_hora_cadastro`,
`id_motorista`,
`placa`,
`id_usuario`,
`id_controle_saida`,
`id_controle_retorno`,
`id_local`)
SELECT `viagem`.`id_viagem`,
    `viagem`.`anotacao`,
    `viagem`.`data_hora_cadastro`,
    `viagem`.`id_motorista`,
    `viagem`.`placa`,
    `viagem`.`id_usuario`,
    `viagem`.`id_controle_saida`,
    `viagem`.`id_controle_retorno`,
    1 as ID_LOCAL
from `severino_new`.`viagem`;

/*visita*/
insert into `severino`.`visita`
(`id_visita`,
`entrada`,
`saida`,
`nome_procurado`,
`setor_procurado`,
`id_visitante`,
`id_setor`,
`id_usuario`,
`id_empresa`,
`data_hora_cadastro`,
`id_local`)
SELECT `visita`.`id_visita`,
    `visita`.`entrada`,
    `visita`.`saida`,
    `visita`.`nome_procurado`,
    `visita`.`setor_procurado`,
    `visita`.`id_visitante`,
    `visita`.`id_setor`,
    `visita`.`id_usuario`,
    `visita`.`id_empresa`,
    `visita`.`data_hora_cadastro`,
    1 as ID_LOCAL
from `severino_new`.`visita`;

/*acesso_garagem*/
insert into `severino`.`acesso_garagem`
(`id_acesso_garagem`,
`entrada`,
`saida`,
`data_hora_cadastro`,
`anotacao`,
`id_motorista`,
`id_visita`,
`placa`,
`id_usuario`,
`id_local`)
SELECT `acesso_garagem`.`id_acesso_garagem`,
    `acesso_garagem`.`entrada`,
    `acesso_garagem`.`saida`,
    `acesso_garagem`.`data_hora_cadastro`,
    `acesso_garagem`.`anotacao`,
    `acesso_garagem`.`id_motorista`,
    `acesso_garagem`.`id_visita`,
    `acesso_garagem`.`placa`,
    `acesso_garagem`.`id_usuario`,
    1 as ID_LOCAL
from `severino_new`.`acesso_garagem`;


/*passageiro*/
insert into `severino`.`passageiro`
(`nome`,
`matricula`,
`id_viagem`)
SELECT `passageiro`.`nome`,
    `passageiro`.`matricula`,
    `passageiro`.`id_viagem`
from `severino_new`.`passageiro`;