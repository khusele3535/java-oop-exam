# Даалгавар 3: CommSystem (Холбооны систем)

**Оноо:** 35 | **Түвшин:** Хүнд

## Зорилго

Сансрын станцын холбооны системийг загварчилсан `CommSystem` класс бичнэ үү. Мессеж илгээх, хүлээн авах, сигнал удирдах, лог хадгалах боломжтой.

## Талбарууд (Fields) — бүгд `private`

| Талбар | Төрөл | Анхны утга | Тайлбар |
|--------|--------|------------|---------|
| `stationNer` | `String` | constructor-оос | Станцын нэр |
| `log` | `ArrayList<String>` | `new ArrayList<>()` | Мессежийн лог |
| `signalHvch` | `int` | `100` | Сигналын хүч (0-100) |
| `offline` | `boolean` | `false` | Офлайн эсэх |

## Constructor

```java
public CommSystem(String stationNer)
```

- `stationNer` талбарт утга олгоно
- `log`-ийг шинэ `ArrayList`-ээр үүсгэнэ

## Methods

### 1. `ilgeeh(String hvleenAvagch, String mesg)` → `String`

Мессеж илгээнэ.

- Хэрэв `offline == true` бол → `"📡 Офлайн!"` буцаана (юу ч хийхгүй)
- `signalHvch -= 5` (илгээх бүрд сигнал буурна)
- Хэрэв `signalHvch < 10` болвол → автоматаар `offline = true` (гэхдээ энэ мессежийг илгээнэ)
- `log`-д `"→ hvleenAvagch: mesg"` нэмнэ
- `"Илгээлээ: hvleenAvagch"` буцаана

**Жишээ:**
```java
CommSystem comm = new CommSystem("ISS");
comm.ilgeeh("Mars", "Сайн уу");
// log: ["→ Mars: Сайн уу"], signal: 95, return: "Илгээлээ: Mars"

// Signal 10 үед илгээвэл:
// signal 10-5=5, 5<10 тул offline=true болно
// Гэхдээ энэ мессеж log-д орно, "Илгээлээ: ..." буцаана
// Дараагийн илгээлт "📡 Офлайн!" болно
```

### 2. `hvleenAvah(String ilgeegch, String mesg)` → `void`

Мессеж хүлээн авна.

- `log`-д `"← ilgeegch: mesg"` нэмнэ

**Жишээ:**
```java
comm.hvleenAvah("Earth", "Мэдээ ирлээ");
// log: [..., "← Earth: Мэдээ ирлээ"]
```

### 3. `signalSergemjuuleh()` → `void`

Сигнал сэргээнэ.

- `signalHvch = 100`
- `offline = false`

### 4. `logHarah(int n)` → `String`

Сүүлийн `n` мессежийг буцаана.

- `StringBuilder` ашиглана
- Мөр бүрийг `\n` (шинэ мөр)-ээр тусгаарлана
- Хэрэв `n > log.size()` бол бүх логийг буцаана
- Сүүлийн мессежийн ард `\n` байхгүй

**Жишээ:**
```java
// log: ["→ Mars: Сайн уу", "← Earth: Мэдээ", "→ Moon: Hello"]
comm.logHarah(2);
// "← Earth: Мэдээ\n→ Moon: Hello"

comm.logHarah(100);
// "→ Mars: Сайн уу\n← Earth: Мэдээ\n→ Moon: Hello" (бүгд)
```

### 5. `toString()` → `String`

**Формат:**
```
📡 [нэр] [ONLINE/OFFLINE] Signal: X% | Лог: Y мессеж
```

- `offline == false` бол `ONLINE`, `true` бол `OFFLINE`

**Жишээ:**
```
📡 ISS [ONLINE] Signal: 95% | Лог: 1 мессеж
📡 ISS [OFFLINE] Signal: 5% | Лог: 19 мессеж
```

## Тест ажиллуулах

```bash
cd java-oop-exam
bash scripts/run_tests.sh commsystem
```

## Шалгуур

- [ ] Бүх талбар `private` эсэх
- [ ] Constructor зөв ажиллах (ArrayList үүсгэсэн)
- [ ] `ilgeeh` — хэвийн + офлайн + auto-offline нөхцөл
- [ ] `hvleenAvah` — лог-д зөв нэмэх
- [ ] `signalSergemjuuleh` — утгууд сэргээх
- [ ] `logHarah` — сүүлийн n + хэтрэх нөхцөл
- [ ] `toString()` — формат + ONLINE/OFFLINE
