# Lekce 04 — dekompilační protokol

Vzor z lekce byl přeložen Kotlinem 2.0.21 pro JVM 17. Výsledné třídy a bytekód
jsem zkontroloval pomocí `javap -p -c`. Počty řádků v Javě jsou odhady.

| Konstrukce | Podpis v JVM třídě | Řádky v Javě |
|---|---|---:|
| `data class Zapis` | `Zapis copy(String, double, String)`, `String component1()`, `double component2()`, `String component3()` | 30–40 |
| `object Prevody` | `public static final Prevody INSTANCE`, `public static final double ABSOLUTNI_NULA`, `double naFahrenheita(double)` | 7 |
| Extension pro `Double` | `static double zaokrouhlenoNaDesetiny(double)` | 3 |
| Callback | `static double prumer(List<Zapis>, Function1<? super Zapis, Double>)` | 10 |
| Korutina | `static Object nactiPoZdrzeni(List<Zapis>, Continuation<? super List<Zapis>>)` | 20–30 |

1. `Zapis` má 11 metod a dva konstruktory. Vznikly tři gettery, `component1–3`,
   `copy`, `copy$default`, `toString`, `hashCode` a `equals`. Žádná z těchto
   metod ve zdroji není přímo deklarovaná.
2. Syntetický konstruktor má navíc masku typu `int` a parametr
   `DefaultConstructorMarker`. Maska určuje, kdy se místo třetího argumentu
   použije výchozí prázdný řetězec.
3. První argument extension je primitivní `double`. Funkce je v JVM statická.
   Výběr závisí na typu při překladu a nepoužívá virtuální volání na příjemci.
4. Druhý argument `prumer` má typ `kotlin.jvm.functions.Function1`.
   Výpis bytekódu ukazuje `Function1.invoke` a převod výsledku na číslo.
5. Přibyl parametr `Continuation` a výsledný typ je `Object`. Funkce může
   vrátit seznam nebo značku `COROUTINE_SUSPENDED`. `tableswitch` rozlišuje
   první vstup a pokračování po `delay`.

[Zadani lekce 04](https://www.michalmiklas.cz/vyuka/mobilni-technologie/lekce-04-jazyky.html#cviceni)
