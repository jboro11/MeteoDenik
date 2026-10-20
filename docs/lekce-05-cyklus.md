# Lekce 05 — životní cyklus

Použij commit s názvem `Lekce 05: Logcat a detail mesta`.
Filtr Logcatu je `tag:MeteoCyklus`. Před každým scénářem vyčisti výpis.

| Scénář | Předpoklad | Skutečné pořadí z Logcatu | Vysvětlení rozdílu |
|---|---|---|---|
| Start z ikony | onCreate → onStart → onResume | Čeká na měření | |
| Otočení | Nová instance, zapsat hodnotu uloženého stavu | Čeká na měření | |
| Home | onPause → onStop | Čeká na měření | |
| Návrat | onRestart → onStart → onResume, pokud instance přežila | Čeká na měření | |
| Zpět na MainActivity | Android může přesunout hlavní aktivitu na pozadí | Čeká na měření | |
| `adb shell am kill cz.utb.meteodenik` na pozadí a návrat | Nový proces a nové onCreate, zapsat uložený stav a skutečná volání | Čeká na měření | |

Pro 5.2 navíc klepni postupně na všechna tři města. Ověř jejich názvy, teploty,
návrat a pořadí callbacků obou aktivit.
