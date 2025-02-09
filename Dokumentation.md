## Einführung und Ziele
### Grundlegende Anforderungen
- Exambyte ist eine Webanwendung, die den Dozenten erlaubt den Übungsbetrieb vollständig Online zu verwalten.
- Es erlaubt den Studierenden diese Tests zu bearbeiten und Feedback zu erhalten
- Es erlaubt Korrektoren die Bewertung der von den Studierenden bearbeiteten Tests.

### Qualitätsziele
- Barrierefreie und Responsive Webinterface
- Erweiterbarkeit für neue Fragetypen
- Wartbarkeit der Anwendung

### Stakeholder

- Entwickler: Sollen möglichst leicht das System deployen und warten können, sowie um neue Features erweitern können.
- Dozenten: Wollen ihre Kurse online verwalten können, das beinhaltet sowohl die Erstellung von Tests als auch die Verwaltung der Mitglieder und deren Zulassungsstatus.
- Korrektoren: Wollen die ihnen Zugeordneten Abgaben bewerten.
- Studierende wollen die Tests online schnell und einfach bearbeiten.

## Randbedingungen
### Technisch
- Java Spring Webanwendung
- Bootstrap und Thymeleaf für HTML
- Postgres Datenbank

### Organisatorisch
- Entwicklung in einem kleinen Team über Git
- Testing mit JUnit
- Zeitraum: 3 Monate
- Sprache: Deutsch

## Kontextabgrenzung

### Dozenten & Organisatoren (Benutzer)
Jeder Kurs wird von Lehrkräften betreut, diese sollen die Inhalte verwalten und die Tests erstellen. Zudem soll diese Gruppe auch die Zulassungen der Studierenden verwalten, sowie das Recht haben individuell die Ergebnisse der Studierenden anzupassen.

### Studierende (Benutzer)
Studierende bearbeiten die Tests und sollen in der Lage sein, ihre eigenen Ergebnisse und ihren Zulassungsstatus einzusehen.

### Korrektoren (Benutzer)
Bewerten die Abgaben im QuizEvaluation System, die ihnen zugeordnet wurden.

### ExamByte (Hauptsystem)
- Bietet die geforderten Funktionen für Organisatoren und Studierende an.
- Leitet die Abgaben an das QuizEvaluations System weiter.

### QuizEvaluation (Subsystem)
- Verteilt die Abgaben auf die Korrektoren.
- Korrektoren können die Bewertungen dort einreichen.
- Stellt die Bewertungen für ExamByte bereit.

### Postgres-DB (Fremdsystem)
- Speichert Bewertungen, Tests sowie Abgaben persistent.

## Lösungsstrategie

### Prinzipien
- Barrierefreie und Responsive Webanwendung wird durch HTML und Bootstrap umgesetzt.
- Erweiterbarkeit wird mit der Onion-Architektur umgesetzt.
- Wartbarkeit wird mit Unit-Tests umgesetzt.

### Aufbau
Exambyte und seine Funktionen kann in die folgenden Teile zerlegt werden
- Erstellen und Konfigurieren von Tests und dessen Parametern
- Erstellen einer Abgabe zu einem Test
- Erstellen einer Bewertung zu einer Abgabe
- Bestimmung des Zulassungsstatus mithilfe aller Abgaben

## Laufzeitsicht
Der Zeitliche Ablauf einer Woche sieht wie folgt aus:
1. Der Dozent erstellt einen Test und legt das Startdatum fest.
2. Die Studierenden haben nach Beginn des Startdatums eine Woche Zeit ihre Abgaben einzureichen.
3. Nach der Bearbeitungszeit werden die Abgaben auf die Korrektoren verteilt.
4. Wenn die Korrektur abgeschlossen ist, dann können die Studierenden die Ergebnisse einsehen.

## Querschnittliche Konzepte
- Die Nutzer der Anwendung werden über Github verwaltet. Der Zugriff auf die Ressourcen wird mit O-Auth gesteuert über die Github-ID.
- Die Benutzeroberfläche wird mit dem Frontend der Webanwendung umgesetzt.

## Entwurfsentscheidungen
- Die Anwendung wurde in zwei SCS unterteilt. Zum einen ExamByte, welches die Seite der Anwendung darstellt, die die Interaktionen von Dozenten und Studierenden ermöglicht. Zum anderen QuizEvaluation, die die Interaktionen zwischen Dozenten und Korrektoren ermöglicht.
