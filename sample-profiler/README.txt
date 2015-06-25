<h1> Jadex Beispiel für generisches logging </h1>
gemessen werden Übertragungszeit und Übertragungsgröße





<h2> Was passiert im Beispiel? </h2>

Der SampleAgent übergibt ein Objekt an den SampleService zur Modifikation.
Der SampleService verändert das Objekt und schiebt es zurück an den SampleAgent.

<h2> Was sagt das Log? </h2>

TIMESTEAM ; THREADID ; CLASS; CUSTOM MESSAGE
1435232847814;1;FLog;beginning log                           // start des Logfiles
1435232847830;41;SampleAgent;sende String an Service    // Agent überträgt den String
1435232847831;25;SampleService;before modify            // String ist am Service angekommen
1435232847858;25;SampleService;before modify size: 88   // größe des Objektes vor der Veränderung
1435232847859;25;SampleService;after modify             // Service hat das Objekt verändert
1435232847859;25;SampleService;after modify size: 216   // größe nach der Veränderung
1435232847860;25;SampleAgent;bekomme String von Service // String ist wieder zurück beim Agenten




<h2> Beachten </h2>
Runtime Configurations:
Um Objektgrößen zu messen muss als VM-Argument folgendes eintragen werden:
-javaagent:libs/object-explorer.jar
