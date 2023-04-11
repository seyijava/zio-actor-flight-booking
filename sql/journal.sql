create table if not exists journal_zio
(
    persistence_id varchar not null,
    sequence_number serial not null,
    message bytea,
    constraint journal_zio_pk
        primary key (persistence_id, sequence_number)
);

16277acc-5152-4edc-87ee-5a8cbc353473