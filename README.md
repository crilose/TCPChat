<b>Corso di Tecnologie e progettazione di sistemi informatici e di telecomunicazioni </b>


<b>Classi 5AInf, 5BInf, 5CInf a.s. 2017/2018</b>

 
<b>Relazione progetto “TCPChat”</b>

<b>Studente: Cristiano Ceccarelli</b>
<b>Docente/i: Monica Ciuchetti, Paolo Testi</b>


<b>Obiettivo del progetto</b>

L’obiettivo del progetto è realizzare un applicativo di chat in Java sfruttando la comunicazione TCP e i Socket, che permetta di far comunicare un client e un server tramite alcuni comandi speciali e semplici messaggi di testo. L’applicativo utilizzerà lo standard output per mostrare la comunicazione.

<b>Architettura dell’applicazione</b>

L’applicativo prevede la presenza di un eseguibile di tipo server ed un eseguibile di tipo client, posti o sulla stessa macchina o su macchine diverse purché all’interno della propria LAN o su internet utilizzando un apposito port forwarding. Il programma è scritto in Java, si compone di 6 classi di cui due eseguibili (una per client e una per server), due oggetti (Client e Server) effettivamente istanziati per la gestione dei socket e dello scambio di messaggi, una classe per i messaggi inviati ed una classe thread di ascolto dello stream ricevuto.

<b>Attori</b>

Il sistema è strutturato e si compone di due principali attori:

-Utente server: avvia l’applicativo server, sceglie la porta su cui comunicare e si mette in ascolto, in attesa di un client che inizi con lui la comunicazione testuale

-Utente client: avvia l’applicativo client, sceglie la porta su cui comunicare, l’indirizzo ip e prova a collegarsi al server, qualora trovasse un server in ascolto avvia la comunicazione testuale

<b>Funzionalità del programma</b>

Il programma permette a entrambi gli utenti di specificare all’inizio della comunicazione un nome utente che intendono utilizzare, successivamente la comunicazione testuale ha inizio. Oltre i semplici messaggi di chat alcuni comandi speciali, utilizzabili ponendo una barra (/) prima del comando stesso, permetteranno di fare alcune operazioni particolari: <br>

/username: il comando permetterà di specificare nuovamente un nome utente in modo da poterlo cambiare<br>
/end: il comando permetterà di disconnettersi dall’applicazione<br>
/online: il comando permetterà di rimettersi in ascolto della comunicazione e di comunicare il proprio stato di disponibilità a ricevere ed inviare messaggi<br>
/offline: il comando permetterà di interrompere l’ascolto della comunicazione in modo da non ricevere più messaggi dell’interlocutore<br>
/smile: il comando permetterà di inviare all’interlocutore una emoticon sorridente<br>
/like: il comando permetterà di inviare all’interlocutore una emoticon di gradimento<br><br>
/sendfile: il comando permetterà, specificando di seguito un nome di file, di inviare il file all’interlocutore<br>
/echo: il comando permetterà di ripetere l’ultimo messaggio ricevuto dall’interlocutore ed inviarlo allo stesso<br>

Altri comandi potranno essere successivamente aggiunti in espansione al progetto principale che prevede questi essenziali comandi speciali.

<b>Casi d’uso</b>

I casi d'uso possono essere consultati nella documentazione completa:
https://drive.google.com/open?id=1CrWQo9V2-x4gzgEFxo4nQESWm3L8qWClHz3PTPm1RUM



<b>Vincoli e Tecnologie usate</b>

Oltre alla dovuta scelta di utilizzare Java e l’ambiente NetBeans, vengono prese alcune scelte progettuali ritenute necessarie ai fini dell’applicazione:<br>

Un oggetto thread di ascolto della comunicazione si rende necessario per evitare l’effetto “walkie-talkie” e dunque la comunicazione asincrona<br>
Vengono utilizzati i Berkley Sockets per Java per realizzare la connessione TCP<br>
Vengono utilizzati lo STDIN e lo STDOUT per l’invio e la ricezione delle stringhe, in quanto non si prevede ancora l’implementazione di un’interfaccia grafica particolareggiata<br>

<b>Diagramma delle classi</b>

Il diagramma delle classi può essere consultato nella documentazione completa:
https://drive.google.com/open?id=1CrWQo9V2-x4gzgEFxo4nQESWm3L8qWClHz3PTPm1RUM



<b>Commento del codice</b>

Il commento del codice è interamente realizzato con lo strumento Javadoc per la documentazione automatica. Aprendo i sorgenti questo aspetto è facilmente riscontrabile.
I commenti sono in fase di completamento e potrebbero esserci ancora metodi e classi non commentati.

<b>Test dell’applicazione</b>

L’applicazione viene testata dopo l’applicazione di qualsiasi modifica per verificare il funzionamento corretto di tutti gli aspetti implementati e per consolidare il funzionamento già verificato di quelli precedentemente risolti.
Il testing avviene per mezzo di una prova manuale di funzionamento dove si avviano un server ed un client, si mettono in comunicazione e si provano ad inviare dei messaggi di testo normali o dei comandi che andranno a far intervenire gli appositi metodi in fase di implementazione. 
Una volta concluso il test con successo o con fallimento si torna sul codice per aggiungere altre funzionalità nel primo caso o per modificare e risolvere le problematiche nel secondo caso.

<b>Licenza d’uso</b>

Il codice è libero per ogni utilizzo in ogni contesto, data la sua natura didattica.
Potete prenderlo e copiarlo, scaricarlo, modificarlo, ricaricarlo, venderlo, regalarlo, affittarlo e tutte le altre operazioni che vi vengono in mente.

