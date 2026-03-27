# 🚀 Java OOP Шалгалт — Сансрын Станц

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![JUnit](https://img.shields.io/badge/JUnit-5-green?logo=junit5)
![AI Detection](https://img.shields.io/badge/AI%20Detection-Enabled-red)
![License](https://img.shields.io/badge/License-MIT-blue)

Сансрын станцын сэдэвтэй Java OOP шалгалт. 3 даалгавар, нийт **90 оноо**.

## 📋 Даалгаврууд

| # | Даалгавар | Оноо | Түвшин | Файл |
|---|-----------|------|--------|------|
| 1 | **SpaceShip** — Сансрын хөлөг | 25 | Дунд | `assignments/spaceship/` |
| 2 | **FoodStation** — Хоолны станц | 30 | Дунд+ | `assignments/foodstation/` |
| 3 | **CommSystem** — Холбооны систем | 35 | Хүнд | `assignments/commsystem/` |

## 🏗️ Хавтасны бүтэц

```
java-oop-exam/
├── README.md                          # Энэ файл
├── .gitignore
├── assignments/
│   ├── spaceship/
│   │   ├── SpaceShip.java             # ← Та энд код бичнэ
│   │   └── README.md                  # Даалгаврын заавар
│   ├── foodstation/
│   │   ├── FoodStation.java           # ← Та энд код бичнэ
│   │   └── README.md
│   └── commsystem/
│       ├── CommSystem.java            # ← Та энд код бичнэ
│       └── README.md
├── tests/
│   ├── SpaceShipTest.java             # JUnit 5 тестүүд
│   ├── FoodStationTest.java
│   └── CommSystemTest.java
├── scripts/
│   ├── run_tests.sh                   # Тест ажиллуулах скрипт
│   └── ai_detector.py                 # AI илрүүлэгч
└── .github/workflows/exam.yml         # GitHub Actions автомат шалгагч
```

## 🚀 Эхлэх заавар

### Алхам 1: Fork хийх
Энэ repo-г өөрийн GitHub account руу **Fork** хийнэ.

### Алхам 2: Clone хийх
```bash
git clone https://github.com/<таны-username>/java-oop-exam.git
cd java-oop-exam
```

### Алхам 3: Branch үүсгэх
```bash
git checkout -b exam/миний-нэр
```

### Алхам 4: Код бичих
`assignments/` хавтас дахь `.java` файлуудын `TODO` коммент дээр код бичнэ. Даалгавар тус бүрийн `README.md` файлыг анхааралтай уншина уу.

### Алхам 5: Локал тест ажиллуулах
```bash
# Бүх даалгавар
bash scripts/run_tests.sh

# Тодорхой даалгавар
bash scripts/run_tests.sh spaceship
bash scripts/run_tests.sh foodstation
bash scripts/run_tests.sh commsystem
```

### Алхам 6: Commit & Push
```bash
git add assignments/
git commit -m "Шалгалтын хариулт"
git push origin exam/миний-нэр
```

### Алхам 7: Pull Request үүсгэх
GitHub дээр `main` branch руу **Pull Request** үүсгэнэ. Автомат шалгагч ажиллана.

## 📊 Оноо тооцох систем

| Шалгуур | Тайлбар |
|---------|---------|
| **Компайл** | Код алдаагүй компайл болох |
| **JUnit тест** | Бүх тест амжилттай болох |
| **AI Detection** | AI ашигласан эсэх шалгах |

### AI Detection оноо
| Оноо | Түвшин | Тайлбар |
|------|--------|---------|
| 0-19 | ✅ LOW | AI шинж тэмдэг бага |
| 20-39 | ⚠️ MEDIUM | Зарим AI шинж тэмдэг |
| 40+ | 🚨 HIGH | AI ашигласан магадлал өндөр |

AI detector нь дараах 11 шалгуураар кодыг шинжилнэ:
1. Javadoc хэт их хэрэглэсэн эсэх
2. `@param`/`@return` tag-ууд
3. Коммент/код харьцаа
4. AI-д түгээмэл хэллэгүүд
5. Мэдэгдэж буй AI коммент загвар
6. Indent загвар
7. Мөрийн уртын жигд байдал
8. Exception throw хэрэглэсэн эсэх
9. Хоосон мөрийн загвар
10. Method нэрийн жигд байдал
11. Ашиглагдаагүй import

## ⚠️ Дүрэм

1. **Тест файлуудыг өөрчлөхгүй** — `tests/` хавтас дахь файлуудыг хөндөхгүй
2. **Зөвхөн `.java` файлуудад код бичнэ** — `assignments/` хавтас дахь starter файлуудад
3. **AI ашиглахгүй** — ChatGPT, Copilot, Claude зэрэг AI хэрэгсэл хэрэглэхгүй
4. **Өөрийн branch дээр ажиллана**
5. **Deadline-аа баримтална**

## 🛠️ Шаардлага

- **Java 17+** (`java -version`)
- **Python 3.11+** (`python3 --version`) — AI detector-д
- **Bash** — тест скриптэд
- **curl** — JUnit jar автомат татахад

## 📞 Асуулт байвал

Багшдаа хандана уу. Амжилт хүсье! 🎓
