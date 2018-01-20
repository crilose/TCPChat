# TCPChat
Sulla base del programma fornito per la comunicazione via socket TCP ho implementato un basilare meccanismo di chat tra client e server.
Questa repository serve non per realizzare la completa applicazione ma solo per tenere traccia temporaneamente del progresso raggiunto senza modificare la repository originale, cosa che sarà fatta seguendo le indicazioni del compito a tempo debito.

# Come funziona?
Molto semplicemente, sfruttando la normale comunicazione tra client e server con le due classi Java, ho inserito un ciclo while infinito durante il quale entrambi possono inviare una riga di messaggio all'altro. L'ascolto dei messaggi della controparte è invece affidato ad un thread istanziato da ciascuno dei due che semplicemente ascolta lo stream proveniente e lo stampa a schermo. La necessità del thread è data dal fatto che se inserito l'ascolto nel flow principale dove avviene anche l'input, mentre il programma attende la nostra stringa non riuscirà a fare altro. Con un thread che è sempre in esecuzione parallela possiamo invece stampare la stringa che ci arriva mentre stiamo ancora scrivendo o dobbiamo ancora iniziare a scrivere il nostro messaggio di risposta.
