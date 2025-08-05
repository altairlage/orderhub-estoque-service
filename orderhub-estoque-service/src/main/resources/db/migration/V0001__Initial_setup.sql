create table estoque (
    id_produto bigint primary key,
    quantidade_disponivel integer not null,
    criado_em timestamp not null,
    atualizado_em timestamp
);

insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (1, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (2, 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (3, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (4, 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (5, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (7, 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (8, 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (9, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into estoque (id_produto, quantidade_disponivel, criado_em, atualizado_em) values (10, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);