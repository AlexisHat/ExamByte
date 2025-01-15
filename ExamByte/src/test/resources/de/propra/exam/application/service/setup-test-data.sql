DELETE FROM quiz_attempt;
DELETE FROM answer;
DELETE FROM question;
DELETE FROM quiz;
DELETE FROM student;




INSERT INTO student (id,github_id, name) VALUES (1,'123456789' ,'Test Student');

INSERT INTO quiz (quiz_id, quiz_name, start_time, end_time)
VALUES (1, 'Test Quiz', '2024-12-26 10:00:00', '2024-12-30 12:00:00');

INSERT INTO question (question_id, quiz, quiz_key, points, titel, task, type, options, correct_option_index, muster_loesung_for_text_question)
VALUES
    (1, 1, 1, 10.0, 'Wer das ließt ist toll', 'ich bin eine frage?', 'MULTIPLE_CHOICE', '["1", "2", "3", "4"]', '1', NULL),
    (2, 1, 2, 20.0, 'text frage', 'aufgabenstellung', 'TEXT', NULL, NULL, 'muserloesung');
