create table if not exists quiz_attempt(
    id serial primary key,
    quiz_id integer references quiz(quiz_id),
    student_id integer references student(id)
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