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
Seit einigen Jahren sind Textchats als Nachvolger von SMS ein fester Bestandteil im Alltag vieler Menschen. Als alternative eines synchronen Telefonats sind Sprachnachrichten inzwischen in vielen Chats integriert. Am Markt gibt es diverse Chatprogramme und fast jeder mit einem Smartphone setzt auf mindestens eines der Programme. Die prominentesten Softwarelösungen sind WhatsApp, Facebook Messanger, WeChat, QQ, Telegram und Snapchat. [ https://www.messengerpeople.com/de/weltweite-nutzer-statistik-fuer-whatsapp-wechat-und-andere-messenger/ ]. Bis auf die Grundfunktionalitäten einer Sprachnachricht bieten sie keine Komfortfunktionen beim Empfangen dieser Art von Nachrichten an. 

####Projektpartner Feinfone GmbH
Während des Projektes hat uns ein StartUp aus Berlin begleitet. Es arbeitet selbst an einer rein Sprachchatbasierten Lösung zur Kommunikation. Im regelmäßigem Austausch haben wir immer wieder Input bekommen. Feinfone hat erkannt, dass Sprachnachrichten versenden ein gern genutzes Feature ist und es eine Lücke gibt beim Empfangen diser Nachrichten. Es gibt viele Möglichkeiten ihren Sprachchat an den Markt zu bringen. Während des Verlaufs des Projektes ist die Basis beim StartUp gleich geblieben der Focus und die Branche haben sich allerdings in der Zeit geändert. Daraus lässt sich vermuten, dass das Einsatzgebiet der Technologie sehr variable ist und die grundsätzlichen Herausforderungen in verschiedenen Gebieten sich stark ähneln.

###Ähnliche Kontexte
Die Nutzung von Sprachnachrichten oder Sprachkomandos sind keine Seltenheit. Im Bereich der Bedienung von Geräten z.B. im Umfeld des Smart Home werden Sprachkomandos in Text übersetzt und als eingabe zur Steuerung genutzt. Diktierfuntionen unterstützen beim aufschreiben von Texten und Notizen. Sie unterstützen in dem Sinne, dass man sein gesprochenes Wort nicht per Hand abtippen muss. Manche Plattformen bieten inzwischen automatisch generierte Untertietel an. Dabei wird ebenfalls Sprache zu Text umgewandelt und einem Punkt in der Zeitachse zugeordnet.
Sowohl Google, als auch Amazon als bekannteste Vertreter haben inzwischen ihre Produkte zum Umwandeln der Sprache zu text am Markt plaziert. Über eine API kann jeder seine Sprache in Texte umwandeln lassen. Diese Umwandlungen werden mit tranierten KI-Modellen verwirklicht. Prnzipiel ist das Umwandeln in Text meist der erste Schritt um mit einer Sprachnachticht automatisiert weiter arbeiten zu können.

##Projektverlauf
Zu begin sammelten wir in welchen Bereichen man die Analyse von Sprache in Bezug auf Sprachnachrichten verwenden könnte. Wir identifizierten mehrere Bereiche für die man Prototypen erstellen könnte um den Anwerdern eines Sprachtchats ein besseres Anwendererlebnis zu generieren. Die größten Potentiale fanden wir in den Bereichen Markierungen, Unterhaltung und Struktur. Im November starteten wir direkt mit einem wöchentlichem Austausch mit den Verantwortlichen von Feinfone in dem es darum ging den Fortschritt des Projektes zu besprechen und Ideen miteinander auszutauschen. Im ständigen Austausch erhielten wir Einblicke von unserem Projektpartner und konnten unsere Ideen mit ihrer Marktkompetenz validieren. Da unser Projekt über 2 Fachsemeter verlief hatten wir mit Ende des ersten Semesters die Hauptsächliche Exploration der Themen abgeschlossen und begannen im zweitem Semester einen Prototypen zu erstellen.

###Exploration

Hier Bild von Mindmap einfügen.

Unsere Exproration fand größten Teils in der ersten Projekthälfte statt und befasste sich mit dem Thema Unterhaltung und Markierungen. Da die Grundidee des Projektes auf Analyse der Stimmung eines Textes beruhte, setzten wir als erstes mit der Sentimentanalyse auseinader. In der Sentimentanalyse werden Wörter den Werten positiv, neutral oder negativ zugeordnet. Anhand dieser Zuordnung und der Häufigkeit der verwendeten Wörter eines Wertes, wird ermittelt wie die Haltung des Senders eines Textes ist. Auf einer 5 Werte Skala von sehr positiv bis sehr negativ wird der Sentiment einer Nachricht eingeordnet. Diese Analyse findet ihre Anwendung häufig in kombination mit maschinellem Lernen.

Ein weiterer großer Anteil unserer Exploration war das Zuordnen von GIFs und Emojis zu einem Text. Wir versprachen uns davon dem Empfänger vor dem Abhören schon einen groben Eindruck zu vermitteln was sich in der Sprachnachricht befindet. Wenn der Sender z.B. über ein Haus spricht, dann soll das Emoji für ein Haus an der passenden Stelle auf der Zeitleiste der Nachricht hinzugefügt werden. Ebenso soll das Wort, welches am häufigsten Verwendung findet, als GIF präsentiert werden. Zwischenzeitlich hatten wir die Idee verfolgt, dass der Sender vor dem Versenden auswählen könnte, ob er ein GIF, ein Bild oder eine Wordcloud der Nachricht hinzugefügen möchte. In einem Test unseres Klickdummy wurde die Wordcloud allerdings als unnötig empfunden. Der Sinn so eine zu versenden, wenn man die Möglichkeit auf ein Bild oder ein GIF hatte, erschloss sich den Testern nicht. Technisch sind Emojis auf jedem Smartphone vorhanden und können angezeigt werden. GIFs werden von vielen Produkten über die API von der Plattform Giphy [ https://giphy.com/ ] geladen. Eine ähnliche Plattform wie Giphy ist Unsplash [ https://unsplash.com/ ] auf der Bilder über eine API zur Verfügung gestellt werden. Eine Wordcloud könnte man über eine Javascript Bibliothek [ https://www.npmjs.com/package/wordcloud ] realisieren.

Im Bereich der Struktur hatten wir Überlegungen angestellt die hauptsächlich der komfortablen Bedienung zu Gute kommen sollte. Die Möglichkeit schnell mit nur einem Klick ein in der Sprachnachricht erwähnten Termin anzulegen viel dabei am häufigsten. Wenn diese Funktionalität vorhanden wäre könnte man sie erweitern um Telefonnummer und Webseiten. Aber auch das direkte Ansprechen eines anderen Teilnehmers in einem Gruppenchat durchdachten wir grob. Wenn der Sender in einem Gruppenchat einen Teilnehmer dieser Gruppe namentlich nennt, könnte im Chat ein klassiches @Name an die Nachricht gehangen werden damit man leicht den Chatverlauf optisch scanbar macht.

In den Bereichen Services, Pain Points, Search und Different Media hatten wir keine Nennenswerten vorstöße gemacht. 

###Themenfindung
Es zeichnete sich schon während der Exploration ab, dass wir uns im Bereich Unterhaltung und Markierung für den Empfänger weiter vertiefen wollten. Die Schwierigkeit aus einer Sprachnachricht automatisch Informationen zu extrahieren und diese mit optisch ansprechenden Informationen anzureichern fanden wir als besonders spannen. Auch in den wöchentlichen Austauschrunden fand dieses Thema den meisten anklang. Das Ziel war nun einen Prototypen zu entwickeln der zu einer Sprachnachricht einen Unterhaltungswert liefert. 

###Entwicklug
Wie hat man sich den Themenbereichen genähert, was hat man produziert

Rahmenbedingungen
- Endnutzer kommunizieren über eine Smartphone-App
- Kommunikation findet zwischen jeweils zwei Nutzern statt (keine Gruppen)
- Unabhängige Entwicklung, d.h. keine Nutzung von exisitierenden Systemen von Feinfone
- Englische Sprache

- Ein Nutzer muss identifizierbar sein, um ihm gezielt Nachrichten senden zu können.

Architektur
chat app --> server [Benutzerverwaltung, Nachrichtenverlauf, Nachrichtenverarbeitung]

Benutzerverwaltung
Wahl eines Namens, zur Zuordnung von Nachrichten zum Sender
Speicherung von Konfiguration zu einem Nutzer (nur Topics)
Registrierung, Authentifizierung usw. sind für den Prototypen nicht notwendig.

Nachrichtenverlauf
Chats funktionieren in der Regel asynchron.
Ein Benutzer kann einem anderen Nachrichten senden, auch wenn dieser gerade nicht erreichbar ist.
Der Nachrichtenverlauf zwischen zwei Benutzern wird als fortlaufende Unterhaltung verwaltet.
Die Zuordnung von Sender und Empfänger erfolgt über die Namen der Teilnehmer.

Nachrichtenverarbeitung
Nachrichten werden auf ihrem Inhalt basierend mit weiteren Informationen angereichert.

text --> analyser a -----------------> }
     |               |                 } result
     |-> analyser b --> analyser c --> }

Substantive
Artikel direkt vor dem Substantiv => nicht immer der Fall.
Groß- und Kleinschreibung => im Englischen und bei transkibierten Texten nicht gegeben.
Entscheidung an konkretem Wort => es gibt Wörter, die je nach Kontext Substantiv, Adjektiv oder Verb sein können
Der folgende Satz verdeutlicht das Problem:
A ripe orange is orange.
Der Artikel steht vor dem Adjektiv, Groß- und Kleinschreibung bietet keine Orientierung und das Wort "orange" kommt sowohl als Adjektiv als auch als Subjektiv vor.
Die inhaltliche Interpretation von Text ist nicht trivial und Gegenstand der Forschung.
Im Prototypen greifen wir daher für die Klassifizierung auf eine Bibliothek aus dem Bereich des Natural Language Processing zurück.
Das Ergebnis der Klassifizierung beinhaltet normale Subjektiv, erkannte Eigennamen (Personen, Organisationen) und aufgelöste indirekte Nennungen (Pronomen).
Es enthält das Subjektiv und die ursprüngliche Textstelle.

Emojis

Erwähnte Themen / Mentioned Topics

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
###Ergebnisse für Feinfone

##Ausblick / Weiterführende Ideen
in welchen bereichen könnte man warum weiter probieren, Kommentare aus den Interviews mit rein nehmen.
Welche Themen sind zu wenig beleuchtet worden, Wo kann es einen Wert für die Gesellschaft bieten, evtl. arbeit erleichtern.
Gibt es bestimmte gruppen die besonders davon provitieren könnten

##Literaturverzeichnis

##Abbildungsverzeichnis

##Tabellenverzeichnis

##Anhang





































