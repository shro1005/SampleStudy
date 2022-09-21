create table if not exists search_word
(search_word varchar(500),
search_cnt bigint default 0,
rgt_dtm timestamp without time zone,
udt_dtm timestamp without time zone,
primary key (search_word));