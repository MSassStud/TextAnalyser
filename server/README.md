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