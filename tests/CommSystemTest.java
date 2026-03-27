import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

@DisplayName("Даалгавар 3: CommSystem")
public class CommSystemTest {

    private CommSystem comm;

    @BeforeEach
    void setUp() {
        comm = new CommSystem("ISS");
    }

    // ==================== Бүтэц шалгах ====================

    @Test
    @DisplayName("CommSystem класс үүссэн эсэх")
    void classExists() {
        assertNotNull(comm);
    }

    @Test
    @DisplayName("Constructor нэг String параметртэй")
    void constructorParams() throws Exception {
        Constructor<CommSystem> c = CommSystem.class.getConstructor(String.class);
        assertNotNull(c);
    }

    @Test
    @DisplayName("Бүх талбар private эсэх")
    void allFieldsPrivate() {
        for (Field f : CommSystem.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                f.getName() + " талбар private биш байна!");
        }
    }

    // ==================== Анхны утгууд ====================

    @Test
    @DisplayName("stationNer талбарын анхны утга зөв")
    void initialStationNer() throws Exception {
        Field f = CommSystem.class.getDeclaredField("stationNer");
        f.setAccessible(true);
        assertEquals("ISS", f.get(comm));
    }

    @Test
    @DisplayName("log анхны утга хоосон ArrayList")
    void initialLog() throws Exception {
        Field f = CommSystem.class.getDeclaredField("log");
        f.setAccessible(true);
        Object log = f.get(comm);
        assertNotNull(log, "log нь null байна! new ArrayList<>() ашиглана уу");
        assertTrue(log instanceof ArrayList);
        assertEquals(0, ((ArrayList<?>) log).size());
    }

    @Test
    @DisplayName("signalHvch анхны утга 100")
    void initialSignal() throws Exception {
        Field f = CommSystem.class.getDeclaredField("signalHvch");
        f.setAccessible(true);
        assertEquals(100, f.getInt(comm));
    }

    @Test
    @DisplayName("offline анхны утга false")
    void initialOffline() throws Exception {
        Field f = CommSystem.class.getDeclaredField("offline");
        f.setAccessible(true);
        assertFalse(f.getBoolean(comm));
    }

    // ==================== ilgeeh ====================

    @Test
    @DisplayName("ilgeeh: хэвийн нөхцөл")
    void ilgeehNormal() {
        String result = comm.ilgeeh("Mars", "Сайн уу");
        assertEquals("Илгээлээ: Mars", result);
    }

    @Test
    @DisplayName("ilgeeh: signal буурах")
    void ilgeehSignalDecrease() throws Exception {
        comm.ilgeeh("Mars", "Сайн уу");
        Field f = CommSystem.class.getDeclaredField("signalHvch");
        f.setAccessible(true);
        assertEquals(95, f.getInt(comm));
    }

    @Test
    @DisplayName("ilgeeh: log-д зөв нэмэгдсэн")
    void ilgeehLogAdded() throws Exception {
        comm.ilgeeh("Mars", "Сайн уу");
        Field f = CommSystem.class.getDeclaredField("log");
        f.setAccessible(true);
        ArrayList<?> log = (ArrayList<?>) f.get(comm);
        assertEquals(1, log.size());
        assertEquals("→ Mars: Сайн уу", log.get(0));
    }

    @Test
    @DisplayName("ilgeeh: офлайн үед")
    void ilgeehWhileOffline() throws Exception {
        // Офлайн болгох
        Field offlineField = CommSystem.class.getDeclaredField("offline");
        offlineField.setAccessible(true);
        offlineField.setBoolean(comm, true);

        String result = comm.ilgeeh("Mars", "Сайн уу");
        assertEquals("📡 Офлайн!", result);
    }

    @Test
    @DisplayName("ilgeeh: офлайн үед log нэмэгдэхгүй")
    void ilgeehOfflineNoLog() throws Exception {
        Field offlineField = CommSystem.class.getDeclaredField("offline");
        offlineField.setAccessible(true);
        offlineField.setBoolean(comm, true);

        comm.ilgeeh("Mars", "test");

        Field logField = CommSystem.class.getDeclaredField("log");
        logField.setAccessible(true);
        ArrayList<?> log = (ArrayList<?>) logField.get(comm);
        assertEquals(0, log.size());
    }

    @Test
    @DisplayName("ilgeeh: auto-offline (signal < 10)")
    void ilgeehAutoOffline() throws Exception {
        // Signal-ийг 10 болгох
        Field sigField = CommSystem.class.getDeclaredField("signalHvch");
        sigField.setAccessible(true);
        sigField.setInt(comm, 10);

        String result = comm.ilgeeh("Mars", "test"); // 10-5=5, 5<10 → offline
        assertEquals("Илгээлээ: Mars", result); // энэ мессеж илгээгдэнэ

        Field offlineField = CommSystem.class.getDeclaredField("offline");
        offlineField.setAccessible(true);
        assertTrue(offlineField.getBoolean(comm), "signal<10 үед offline=true болох ёстой");

        // Дараагийн илгээлт
        String result2 = comm.ilgeeh("Earth", "hello");
        assertEquals("📡 Офлайн!", result2);
    }

    @Test
    @DisplayName("ilgeeh: олон удаа илгээх (signal буурах)")
    void ilgeehMultiple() throws Exception {
        for (int i = 0; i < 5; i++) {
            comm.ilgeeh("Target" + i, "msg" + i);
        }
        Field f = CommSystem.class.getDeclaredField("signalHvch");
        f.setAccessible(true);
        assertEquals(75, f.getInt(comm)); // 100 - 5*5 = 75
    }

    // ==================== hvleenAvah ====================

    @Test
    @DisplayName("hvleenAvah: log-д зөв нэмэгдсэн")
    void hvleenAvahLog() throws Exception {
        comm.hvleenAvah("Earth", "Мэдээ ирлээ");
        Field f = CommSystem.class.getDeclaredField("log");
        f.setAccessible(true);
        ArrayList<?> log = (ArrayList<?>) f.get(comm);
        assertEquals(1, log.size());
        assertEquals("← Earth: Мэдээ ирлээ", log.get(0));
    }

    @Test
    @DisplayName("hvleenAvah: олон мессеж")
    void hvleenAvahMultiple() throws Exception {
        comm.hvleenAvah("Earth", "msg1");
        comm.hvleenAvah("Mars", "msg2");
        Field f = CommSystem.class.getDeclaredField("log");
        f.setAccessible(true);
        ArrayList<?> log = (ArrayList<?>) f.get(comm);
        assertEquals(2, log.size());
    }

    // ==================== signalSergemjuuleh ====================

    @Test
    @DisplayName("signalSergemjuuleh: signal 100 болох")
    void signalSergemjuulehSignal() throws Exception {
        comm.ilgeeh("Mars", "test"); // signal=95
        comm.signalSergemjuuleh();
        Field f = CommSystem.class.getDeclaredField("signalHvch");
        f.setAccessible(true);
        assertEquals(100, f.getInt(comm));
    }

    @Test
    @DisplayName("signalSergemjuuleh: offline false болох")
    void signalSergemjuulehOffline() throws Exception {
        Field offlineField = CommSystem.class.getDeclaredField("offline");
        offlineField.setAccessible(true);
        offlineField.setBoolean(comm, true);

        comm.signalSergemjuuleh();
        assertFalse(offlineField.getBoolean(comm));
    }

    // ==================== logHarah ====================

    @Test
    @DisplayName("logHarah: сүүлийн n мессеж")
    void logHarahLastN() {
        comm.ilgeeh("Mars", "msg1");
        comm.ilgeeh("Moon", "msg2");
        comm.ilgeeh("Earth", "msg3");

        String result = comm.logHarah(2);
        assertEquals("→ Moon: msg2\n→ Earth: msg3", result);
    }

    @Test
    @DisplayName("logHarah: n > log.size() бол бүгд буцаах")
    void logHarahOverSize() {
        comm.ilgeeh("Mars", "msg1");
        comm.hvleenAvah("Earth", "msg2");

        String result = comm.logHarah(100);
        assertEquals("→ Mars: msg1\n← Earth: msg2", result);
    }

    @Test
    @DisplayName("logHarah: нэг мессеж")
    void logHarahSingle() {
        comm.ilgeeh("Mars", "hello");
        String result = comm.logHarah(1);
        assertEquals("→ Mars: hello", result);
    }

    @Test
    @DisplayName("logHarah: хоосон лог")
    void logHarahEmpty() {
        String result = comm.logHarah(5);
        assertEquals("", result);
    }

    // ==================== toString ====================

    @Test
    @DisplayName("toString: анхны формат (ONLINE)")
    void toStringInitial() {
        String result = comm.toString();
        assertEquals("📡 ISS [ONLINE] Signal: 100% | Лог: 0 мессеж", result);
    }

    @Test
    @DisplayName("toString: OFFLINE формат")
    void toStringOffline() throws Exception {
        Field offlineField = CommSystem.class.getDeclaredField("offline");
        offlineField.setAccessible(true);
        offlineField.setBoolean(comm, true);

        Field sigField = CommSystem.class.getDeclaredField("signalHvch");
        sigField.setAccessible(true);
        sigField.setInt(comm, 5);

        String result = comm.toString();
        assertEquals("📡 ISS [OFFLINE] Signal: 5% | Лог: 0 мессеж", result);
    }

    @Test
    @DisplayName("toString: мессеж тоо зөв")
    void toStringMessageCount() {
        comm.ilgeeh("Mars", "msg1");
        comm.hvleenAvah("Earth", "msg2");
        comm.ilgeeh("Moon", "msg3");

        String result = comm.toString();
        assertTrue(result.contains("Лог: 3 мессеж"));
    }
}
