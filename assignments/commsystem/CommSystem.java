import java.util.ArrayList;

public class CommSystem {

    // TODO: private талбаруудыг зарлана уу
    // - stationNer (String)
    // - log (ArrayList<String>) — мессежүүдийн лог
    // - signalHvch (int, анхны утга 100)
    // - offline (boolean, анхны утга false)

    // TODO: Constructor бичнэ үү
    // CommSystem(String stationNer)
    // - log-ийг шинэ ArrayList-ээр үүсгэнэ

    // TODO: ilgeeh(String hvleenAvagch, String mesg) method бичнэ үү
    // - Хэрэв offline бол "📡 Офлайн!" буцаана
    // - signalHvch -= 5
    // - Хэрэв signalHvch < 10 бол автоматаар offline = true болгоно
    // - log-д "→ hvleenAvagch: mesg" нэмнэ
    // - "Илгээлээ: hvleenAvagch" буцаана

    // TODO: hvleenAvah(String ilgeegch, String mesg) method бичнэ үү
    // - log-д "← ilgeegch: mesg" нэмнэ

    // TODO: signalSergemjuuleh() method бичнэ үү
    // - signalHvch = 100
    // - offline = false

    // TODO: logHarah(int n) method бичнэ үү
    // - Сүүлийн n мессежийг буцаана
    // - StringBuilder ашиглана
    // - Мөр бүрийг шинэ мөрөөр тусгаарлана (\n)
    // - Хэрэв n > log.size() бол бүх лог-ийг буцаана

    // TODO: toString() method бичнэ үү
    // Формат: "📡 [нэр] [ONLINE/OFFLINE] Signal: X% | Лог: Y мессеж"

}
