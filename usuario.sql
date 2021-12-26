use Usuario;

Create table Usuario(
    UsuarioID int not null primary key auto_increment,
    UsuarioNome varchar(100) not null,
    UsuarioCPF varchar(14) not null unique,
    UsuarioDataNasc date not null,
    UsuarioSexo char(1) not null,
    UsuarioAtivo boolean not null    
);

SELECT * FROM Usuario;