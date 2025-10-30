Ocorre um Deadlock.

Todas as Threads entram em estado WAITING, esperando uma notificaçãoem
seus proprios monitores. Como não há mais nenhuma thread em execução pra chamar o Notify(), elas ficarão bloqueadas
indefinidamente, e o programa não terinará

**_ Filosofo F4 está esperando por 8.601
Filosofo F2 terminou de jantar!
Filosofo F3 está jantando pela 10a vez e por 4.172 segundos
_** Filosofo F2 está esperando por 7.546
Filosofo F5 terminou de jantar!
**_ Filosofo F5 está esperando por 8.415
Filosofo F1 está jantando pela 10a vez e por 3.023 segundos
Filosofo F3 terminou de jantar!
_** Filosofo F3 está esperando por 6.95
Filosofo F4 está jantando pela 10a vez e por 3.152 segundos
Filosofo F1 terminou de jantar!
**_ Filosofo F1 está esperando por 5.579
Filosofo F4 terminou de jantar!
_** Filosofo F4 está esperando por 8.227

Neste ponto, todas as 5 threads estão no estado de espera.
Não há nenhuma thread ativa para chamar notify(), o programa parou completamente e não terminará
