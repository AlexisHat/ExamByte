create table if not exists quiz(
    quiz_id serial primary key,
    quiz_name varchar(200),
    start_time timestamp,
    end_time timestamp
);

create table if not exists question(
    question_id serial primary key,
    quiz integer references quiz(quiz_id),
    quiz_key integer,
    points double precision,
    titel varchar(250),
    task text,
    type varchar(30),
    options text,
    correct_option_index text,
    muster_loesung_for_text_question text
)