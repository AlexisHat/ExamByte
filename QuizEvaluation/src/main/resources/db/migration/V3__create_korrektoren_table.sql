create table if not exists korrektor(
    id serial primary key,
    github_id varchar(255)
);

alter table postgres.public.answer
add column korrektor integer references korrektor(id);