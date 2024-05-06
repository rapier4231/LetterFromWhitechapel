-- 생성자 Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   위치:        2024-05-02 10:47:40 KST
--   사이트:      Oracle Database 12c
--   유형:      Oracle Database 12c



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE


-- drop table playroom cascade constraints purge;
drop table gameversion cascade constraints purge; 
drop table node cascade constraints purge; 
drop table nodedirection cascade constraints purge; 
drop table player cascade constraints purge; 
drop table playroom cascade constraints purge; 
drop table playroomaction cascade constraints purge; 
drop table playwait cascade constraints purge; 


CREATE TABLE gameversion (
    game_version_id   NUMBER(7) generated as identity NOT NULL,
    game_version      VARCHAR2(10) NOT NULL,
    game_field_row    NUMBER(2) NOT NULL,
    game_field_col    NUMBER(2) NOT NULL,
    jack_move_count   NUMBER(1) NOT NULL,
    police_move_count NUMBER(1) NOT NULL,
    last_turn_number  NUMBER(3) NOT NULL,
    turn_limit_time_s NUMBER NOT NULL,
    create_time       DATE NOT NULL,
    current_indicator VARCHAR2(1) NOT NULL
);

create index gameversion_game_version_id_idx
on gameversion(game_version_id);

ALTER TABLE gameversion ADD CONSTRAINT gameversion_pk PRIMARY KEY ( game_version_id );

CREATE TABLE node (
    node_id               NUMBER(7) generated as identity NOT NULL,
    node_name             VARCHAR2(3) NOT NULL,
    node_state            VARCHAR2(1) NOT NULL,
    playroom_play_room_id NUMBER(7) NOT NULL,
    playroom_role         VARCHAR2(1) NOT NULL
);
create index node_node_id_idx
on node(node_id);
ALTER TABLE node ADD CONSTRAINT node_pk PRIMARY KEY ( node_id );

CREATE TABLE nodedirection (
    node_direction_id NUMBER(7) generated as identity NOT NULL,
    left_top          VARCHAR2(1) NOT NULL,
    top               VARCHAR2(1) NOT NULL,
    right_top         VARCHAR2(1) NOT NULL,
    left              VARCHAR2(1) NOT NULL,
    right             VARCHAR2(1) NOT NULL,
    left_bottom       VARCHAR2(1) NOT NULL,
    bottom            VARCHAR2(1) NOT NULL,
    right_bottom      VARCHAR2(1) NOT NULL,
    node_node_id      NUMBER(7) NOT NULL
);

CREATE UNIQUE INDEX nodedirection__idx ON
    nodedirection (
        node_node_id
    ASC );
    
create index nodedirection_node_direction_id_idx
on nodedirection(node_direction_id);

ALTER TABLE nodedirection ADD CONSTRAINT nodedirection_pk PRIMARY KEY ( node_direction_id );

CREATE TABLE player (
    player_id   NUMBER(7) generated as identity NOT NULL,
    nick_name   VARCHAR2(6) NOT NULL,
    create_time DATE NOT NULL,
    score       NUMBER(5) NOT NULL
);

create index player_player_id_idx
on player(player_id);

ALTER TABLE player ADD CONSTRAINT player_pk PRIMARY KEY ( player_id );



CREATE TABLE playroom (
    play_room_id                NUMBER(7) generated as identity NOT NULL,
    role                        VARCHAR2(1) NOT NULL,
    gameversion_game_version_id NUMBER(7) NOT NULL,
    player_player_id            NUMBER(7) NOT NULL
);

create index playr_play_room_id_idx
on playroom(play_room_id,role);

ALTER TABLE playroom ADD CONSTRAINT playroom_pk PRIMARY KEY (play_room_id, role);

CREATE TABLE playroomaction (
    play_room_action_id   NUMBER(7) generated as identity NOT NULL,
    game_turn             NUMBER(3) NOT NULL,
    node_node_id          NUMBER(7) NOT NULL,
    player_score          NUMBER(1) NOT NULL,
    create_time           DATE NOT NULL,
    playroom_play_room_id NUMBER(7) NOT NULL,
    playroom_role         VARCHAR2(1) NOT NULL
);
create index playroomaction_play_room_action_id_idx
on playroomaction(play_room_action_id);

ALTER TABLE playroomaction ADD CONSTRAINT playroomaction_pk PRIMARY KEY ( play_room_action_id );

CREATE TABLE playwait (
    play_wait_id     NUMBER(7) generated as identity NOT NULL,
    state            VARCHAR2(1) NOT NULL,
    create_time      DATE NOT NULL,
    update_time      DATE NOT NULL,
    player_player_id NUMBER(7) NOT NULL
);

CREATE UNIQUE INDEX playwait__idx ON
    playwait (
        player_player_id
    ASC );

create index playwait_play_wait_id_idx
on playwait(play_wait_id);

ALTER TABLE playwait ADD CONSTRAINT playwait_pk PRIMARY KEY ( play_wait_id );

ALTER TABLE node
    ADD CONSTRAINT node_playroom_fk FOREIGN KEY ( playroom_play_room_id,
                                                  playroom_role )
        REFERENCES playroom ( play_room_id,
                              role );

ALTER TABLE nodedirection
    ADD CONSTRAINT nodedirection_node_fk FOREIGN KEY ( node_node_id )
        REFERENCES node ( node_id );

ALTER TABLE playroom
    ADD CONSTRAINT playroom_gameversion_fk FOREIGN KEY ( gameversion_game_version_id )
        REFERENCES gameversion ( game_version_id );

ALTER TABLE playroom
    ADD CONSTRAINT playroom_player_fk FOREIGN KEY ( player_player_id )
        REFERENCES player ( player_id );

ALTER TABLE playroomaction
    ADD CONSTRAINT playroomaction_node_fk FOREIGN KEY ( node_node_id )
        REFERENCES node ( node_id );

ALTER TABLE playroomaction
    ADD CONSTRAINT playroomaction_playroom_fk FOREIGN KEY ( playroom_play_room_id,
                                                            playroom_role )
        REFERENCES playroom ( play_room_id,
                              role );

ALTER TABLE playwait
    ADD CONSTRAINT playwait_player_fk FOREIGN KEY ( player_player_id )
        REFERENCES player ( player_id );



-- Oracle SQL Developer Data Modeler 요약 보고서: 
-- 
-- CREATE TABLE                             7
-- CREATE INDEX                             2
-- ALTER TABLE                             14
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- TSDP POLICY                              0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
