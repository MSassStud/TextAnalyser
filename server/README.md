# TextAnalyser

## Emotion
### Sentimentanalyse
- https://fortext.net/routinen/methoden/sentimentanalyse
- https://wortschatz.uni-leipzig.de/de/download

## Schlüsselwörter / Keywords

### Schlüsselwörter (statistische Verfahren)

Idee: Häufigkeitsanalyse  
Funktion:
Trenne Text in einzelne Wörter und bewerte diese nach Häufigkeit (häufig = hohe Bewertung).
Entferne Wörter ohne besondere Bedeutung (a, and, the, ...).  
Problem: häufig vorkommende Wörter können inhaltlich uninteressant sein

Idee: Anwendung von TFIDF auf einzelne Nachrichten  
Funktion:
TFIDF bewertet die Wörter eines Dokuments aus einer Sammlung von Dokumenten.
Wörter eines Dokuments werden hoch bewertet, wenn sie
a) häufig im Dokument und
b) selten in allen anderen Dokumenten der Sammlung vorkommen.
Für die Anwendung wird die Nachricht in mehrere Abschnitte (= einzelne Dokumente) aufgeteilt und für jeden Abschnitt eine Bewertung durchgeführt.
Im Ergebnis erhält man die für den jeweiligen Abschnitt besonderen Wörter.  
Problem: bei kurzen Texten/Abschnitten ungenau

### Emojis zu Text

Idee: Stelle Textinhalt mit Emojis dar  
Funktion: Identifiziere Nomen im Text.
Für die Identifizierung werden Methoden des Natural Language Processing angewandt.
Suche zu jedem Nomen passend annotierte Emojis im Unicode-Standard.
Für die Suche wird ein Index von Schlüsselwort zu Emoji verwendet.
Personennamen werden durch ein spezielles Emoji repräsentiert.  
Erweiterungen:
Erweiterung der annotierten Schlüsselwörter um Synonyme oder weitere zum Emoji passende Wörter.
Berücksichtigung von Kontextinformationen, wie Adjektiven oder in der Nähe des Nomens vorkommenden Wörtern.

### Kategorisierung durch Nutzer

Idee: Nutzer können Wortgruppen vorgeben, die für eine bestimmte Kategorie stehen  
Funktion:
Nutzer geben je Kategorie eine Liste von Wörtern an und wählen ein Symbol (ein oder mehrere Emojis).
Der Text wird in einzelne Wörter zerlegt.
Die Symbole der passenden Kategorien können neben der Nachricht angezeigt werden, um den Benutzer auf interessante Inhalte hinzuweisen.

## Verlauf
- 11.11 Erstes Brainstorming
- 18.11 Ergebnisse vom Vortermin als Mindmap erstellt
- 24.11 Start zusammenarbeit mit VoiceUp
- 27.11 Erste mögliche Ziele besprochen
-- Themengebiet Entertainment (Speech-to-text, gif, animation etc. für abhörer)
-- Prototyp transcription von sprache zu text mit ausgabe eines GIFs
-- Integration in VoiceUp Backend
- Hinweis von Voiceup, dass transcription schon auf dem gerät passieren wird
- Einstellung der suche nach einer transkriptions api
- Mögliche Analyseablaufe per diagramm erstellt
- Erstellung eines demo Backends: REST-Api mit endpunkt erstellt
- Einbinden der Stanford-Lib
-- Stanford-Lib: Nomen und Namen aus text extrahiert
-- Stanford-Lib: sentiment aus text extrahiert
- Nomen und Namen in smilys übersetzt
- Nomen und Namen Gewichtung nach Häufigeit
- häufigstes Nomen zu giphy api
- Erstellung klick prototype v1
- Qualitativer Test mit wenigen usern des Klick prototype
- Erstellung Ionic App mit Vue Framework
- Lokale entwicklung der App
- 

---

Gliederung für Abgabe

---
#Titel
Projektdaten: Von - bis, akteure, evtl. pressetext (ersten absatz)

##Inhaltsverzeichnis

##Abstakt
In diesem Projekt ging es darum den Einsazt von Sprachnachrichten zu optimieren und mögliche Anwendungsgebiete zu finden. Mit einem StartUp zusammen, welches einen rein Sprachnachrichten basierten Chat aufbaut haben wir innerhalb 2 Semester die Anwendungsmöglichkeiten für diesen Zweck beleuchtet. Die Ausgangsfrage war immer: "Wie kann man dem Empfänger das Empfangen einer Sprachnachricht möglichst komfortabel gestalten.".

##Thema
Sprachnachrichten sind zum Versenden sehr einfach und praktikabel. Um schnell eine Nachricht asynchron an einen Empfänger zu senden benötigt es in gängigen Applikationen meist nur einen Klick. Die Nachricht wird aufgenommen und dem Empfänger zugestellt in kürzester Zeit. Der Empfänger bekommt als Interface einen Zeitstrahl der die Nachricht darstellt und einen Start sowie Pause Button präsentiert. Vor- und Zurückspulen ist in vielen Fällen auch möglich. Diese Grundfunktionalitäten bieten derzeit viele Hersteller an. Mehr als sich die Nachrichten so oft anzuhören wie man möchte geht dann im Normalfall nicht. Ein Chat mit vielen Sprachnachrichten ist nicht durchsuchbar, filterbar, scanbar oder optisch nachvollziehbar. An disem Punkt haben wir uns weiterführende Funktionalitäten gesucht. Beispielsweise könnte man dem Empfänger einen Hinweis zum Inhalt präsentieren in vielerlei Form. Automatisch generierte Bilderkollagen, passende GIFs aber auch Suchfunktionen, Verlinkungen oder Kalendereinträge könnte das Empfangen einer Sprachnachricht angenemer machen.

###Umfeld / Einordnung
In welchem gebiet stochern wird da herum, was sind player am markt wen könnte es interessieren
####Projektpartner VoiceUP
Beschreibung des Projektpartners und warum interesse an der arbeit besteht, was können sie mit einbringen
###Ähnliche Projekte
Relevante projekte, allgemein vllt was kann man heute schon SpeechToText machen.

##Projektverlauf
Wie ist es gastartet, was waren die ersten Ideen
###Exploration
In welche richtungen wurde insgesamt gedacht wo hat man potentiale gesehen, wo wurde man hingeschaut und kurz durchdacht.
###Themenfindung
Welche bereiche wurden nun weiter ins auge gefasst
###Entwicklug
Wie hat man sich den Themenbereichen genähert, was hat man produziert
###Tests
Welcher Stand wurde Probanden gezeigt und was haben sie dazu gesagt
###Zwischenergebnisse
Welche wichtigen Iterationen gab es. Erste Techideen, Klickdummy

##Ergebnisse
Was kam grob am ende raus,
###Prototyp
Was kann das Produkt nun? Was kann man erahnen? 
###Workflows
Wie genau sollte man die App / Api benutzen 
###Techstack
Wie ist die Technik aufgebaut, womit wurde gearbeitet, was muss man haben um das auszuprobieren
###Ergebnisse für VoiceUP

##Ausblick / Weiterführende Ideen
in welchen bereichen könnte man warum weiter probieren, Kommentare aus den Interviews mit rein nehmen.
Welche Themen sind zu wenig beleuchtet worden, Wo kann es einen Wert für die Gesellschaft bieten, evtl. arbeit erleichtern.
Gibt es bestimmte gruppen die besonders davon provitieren könnten


##Literaturverzeichnis

##Abbildungsverzeichnis

##Tabellenverzeichnis

##Eidesstattliche Erklärung

##Anhang





































