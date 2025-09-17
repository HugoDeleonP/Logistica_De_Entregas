-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS logistica_entrega;
USE logistica_entrega;

-- ======================================
-- Tabela: cliente
-- ======================================
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    cpf_cnpj VARCHAR(20) NOT NULL,
    endereco VARCHAR(60) NOT NULL,
    cidade VARCHAR(60) NOT NULL,
    estado VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

-- ======================================
-- Tabela: motorista
-- ======================================

CREATE TABLE motorista (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    cnh VARCHAR(20) NOT NULL,
    veiculo VARCHAR(60) NOT NULL,
    cidade_base VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

-- ======================================
-- Tabela: pedido
-- ======================================
DROP TABLE IF EXISTS pedido;

CREATE TABLE pedido (
    id INT NOT NULL AUTO_INCREMENT,
    cliente_id INT NOT NULL,
    data_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    volume_m3 DOUBLE(8 , 2 ) NOT NULL,
    peso_kg DOUBLE(8 , 2 ) NOT NULL,
    status_pedido ENUM('PENDENTE', 'ENTREGUE', 'CANCELADO') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id)
        REFERENCES cliente (id)
);

-- ======================================
-- Tabela: entrega
-- ======================================
DROP TABLE IF EXISTS entrega;

CREATE TABLE entrega (
    id INT NOT NULL AUTO_INCREMENT,
    pedido_id INT NOT NULL,
    motorista_id INT NOT NULL,
    data_saida DATETIME NOT NULL,
    data_entrega DATETIME NULL,
    status_entrega ENUM('EM_ROTA', 'ENTREGUE', 'ATRASADA') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (pedido_id)
        REFERENCES pedido (id),
    FOREIGN KEY (motorista_id)
        REFERENCES motorista (id)
);

-- ======================================
-- Tabela: historicoEntrega
-- ======================================
DROP TABLE IF EXISTS historicoEntrega;

CREATE TABLE historicoEntrega (
    id INT NOT NULL AUTO_INCREMENT,
    entrega_id INT NOT NULL,
    data_evento DATE NOT NULL,
    descricao TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (entrega_id)
        REFERENCES entrega (id)
);

SELECT * FROM pedido;
select * from entrega;
select * from historicoEntrega;

SELECT entrega.id, entrega.pedido_id, motorista.nome, data_saida, data_entrega, status_entrega
FROM entrega
INNER JOIN motorista ON entrega.motorista_id = motorista.id;

SELECT pedido.id, cliente.nome, pedido.data_pedido, pedido.volume_m3, pedido.peso_kg, pedido.status_pedido 
from pedido
INNER JOIN cliente on pedido.cliente_id = cliente.id;

select pedido.id as pedido_id,
                cliente.id as cliente_id,
                cliente.cpf_cnpj as cliente_cpf_cnpj,
                cliente.nome as cliente_nome,
                pedido.data_pedido,
                pedido.volume_m3,
                pedido.peso_kg,
                pedido.status_pedido
                from pedido
                LEFT JOIN cliente ON pedido.cliente_id = cliente.id
                WHERE cliente.cpf_cnpj like '%18%';
                
                
describe cliente;
describe pedido;
describe entrega;
describe historicoEntrega;
                
-- Total de Entregas por Motorista
select motorista.nome as nome_motorista, count(entrega.id) as quantidade_entregas
from entrega
LEFT JOIN motorista ON entrega.motorista_id = motorista.id
GROUP BY motorista.nome;

-- Clientes com Maior Volume Entregue
select cliente.nome as nome_cliente, max(pedido.volume_m3) as maior_volume
FROM pedido
LEFT JOIN cliente ON pedido.cliente_id = cliente.id
GROUP BY nome_cliente; 

-- Pedidos Pendentes por Estado
select cliente.estado as estado, count(pedido.id) as quantidade_pedidos
FROM pedido
LEFT JOIN cliente ON pedido.cliente_id = cliente.id
GROUP BY cliente.estado;

-- Entregas Atrasadas por Cidade
select cliente.cidade, count(entrega.id) as quantidade_entrega
FROM entrega
LEFT JOIN pedido ON entrega.pedido_id = pedido.id
	LEFT JOIN cliente ON pedido.cliente_id = cliente.id
GROUP BY cliente.cidade;