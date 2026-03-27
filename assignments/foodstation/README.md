# Даалгавар 2: FoodStation (Хоолны станц)

**Оноо:** 30 | **Түвшин:** Дунд+

## Зорилго

Сансрын станцын хоолны газрыг загварчилсан `FoodStation` класс бичнэ үү. Цэс удирдах, захиалга авах, орлого тооцох боломжтой.

## Талбарууд (Fields) — бүгд `private`

| Талбар | Төрөл | Анхны утга | Тайлбар |
|--------|--------|------------|---------|
| `ner` | `String` | constructor-оос | Станцын нэр |
| `menu` | `ArrayList<String>` | `new ArrayList<>()` | Хоолны нэрсийн жагсаалт |
| `uneNuud` | `ArrayList<Integer>` | `new ArrayList<>()` | Үнүүдийн жагсаалт |
| `niitOrlogo` | `int` | `0` | Нийт орлого |

**Чухал:** `menu` болон `uneNuud` нь зэрэгцээ (parallel) ArrayList — ижил индексээр хоолны нэр ба үнийг хадгална.

## Constructor

```java
public FoodStation(String ner)
```

- `ner` талбарт утга олгоно
- `menu` болон `uneNuud`-ийг шинэ `ArrayList`-ээр үүсгэнэ

## Methods

### 1. `tsesNemeh(String hool, int une)` → `void`

Цэсэнд шинэ хоол нэмнэ.

- `menu`-д `hool` нэмнэ
- `uneNuud`-д `une` нэмнэ

**Жишээ:**
```java
FoodStation station = new FoodStation("Galaxy Cafe");
station.tsesNemeh("Бууз", 3500);
station.tsesNemeh("Хуушуур", 2500);
```

### 2. `zahialga(String hool)` → `String`

Хоол захиалах.

- `menu.indexOf(hool)` ашиглан хоол хайна
- Хэрэв олдохгүй бол (`indexOf` нь -1) → `"❌ Цэсэнд байхгүй"` буцаана
- Олдвол тухайн хоолны үнийг `niitOrlogo`-д нэмж → `"✅ hool бэлтгэж байна"` буцаана (hool = хоолны нэр)

**Жишээ:**
```java
station.zahialga("Бууз");      // "✅ Бууз бэлтгэж байна" (орлого: 3500)
station.zahialga("Пицца");     // "❌ Цэсэнд байхгүй"
station.zahialga("Хуушуур");   // "✅ Хуушуур бэлтгэж байна" (орлого: 6000)
```

### 3. `hamgiinHvnstei()` → `String`

Хамгийн үнэтэй хоолны нэрийг буцаана.

- `uneNuud` дотроос хамгийн их утгыг олно
- Тухайн индексийн `menu`-ийн элементийг буцаана
- Хэрэв цэс хоосон бол → `"Цэс хоосон"` буцаана

**Жишээ:**
```java
station.hamgiinHvnstei();  // "Бууз" (3500 > 2500)
```

### 4. `toString()` → `String`

**Формат:**
```
🍜 [нэр] | Цэс: X хоол | Орлого: Y₮
```

**Жишээ:**
```
🍜 Galaxy Cafe | Цэс: 2 хоол | Орлого: 6000₮
```

## Тест ажиллуулах

```bash
cd java-oop-exam
bash scripts/run_tests.sh foodstation
```

## Шалгуур

- [ ] Бүх талбар `private` эсэх
- [ ] Constructor зөв ажиллах (ArrayList үүсгэсэн)
- [ ] `tsesNemeh` — цэсэнд зөв нэмэх
- [ ] `zahialga` — олдох + олдохгүй нөхцөл
- [ ] `hamgiinHvnstei` — зөв хоол олох + хоосон цэс
- [ ] `toString()` — формат яг таарах
