create table if not exists quiz(
   quiz_id integer primary key,
   quiz_name varchar(200),
   ergebnis_ausgabe timestamp
);

create table if not exists question(
    question_id integer primary key,
    quiz_key integer,
      points double precision,
                                       titel varchar(250),
    task text,
                                       type varchar(30),
    options text,
                                       correct_option_index text,
    muster_loesung_for_text_question text
);

create table if not exists quiz_attempt(
    id serial primary key,
    quiz_id integer references quiz(quiz_id),
    student_id integer
);

create table if not exists answer(
    answer_id serial primary key,
    quiz_attempt integer references quiz_attempt(id),
    quiz_attempt_key integer,
    frage_id integer references question(question_id),
    type varchar(30),
    selected_options text,
    text_answer text,
    submitted_at timestamp
);