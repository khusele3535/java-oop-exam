import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;

@DisplayName("Даалгавар 1: SpaceShip")
public class SpaceShipTest {

    private SpaceShip ship;

    @BeforeEach
    void setUp() {
        ship = new SpaceShip("Falcon");
    }

    // ==================== Бүтэц шалгах ====================

    @Test
    @DisplayName("SpaceShip класс үүссэн эсэх")
    void classExists() {
        assertNotNull(ship);
    }

    @Test
    @DisplayName("Constructor нэг String параметртэй")
    void constructorParams() throws Exception {
        Constructor<SpaceShip> c = SpaceShip.class.getConstructor(String.class);
        assertNotNull(c);
    }

    @Test
    @DisplayName("Бүх талбар private эсэх")
    void allFieldsPrivate() {
        for (Field f : SpaceShip.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                f.getName() + " талбар private биш байна!");
        }
    }

    // ==================== Анхны утгууд ====================

    @Test
    @DisplayName("ner талбарын анхны утга зөв")
    void initialNer() throws Exception {
        Field f = SpaceShip.class.getDeclaredField("ner");
        f.setAccessible(true);
        assertEquals("Falcon", f.get(ship));
    }

    @Test
    @DisplayName("tulsh анхны утга 100")
    void initialTulsh() throws Exception {
        Field f = SpaceShip.class.getDeclaredField("tulsh");
        f.setAccessible(true);
        assertEquals(100, f.getInt(ship));
    }

    @Test
    @DisplayName("zorchigch анхны утга 0")
    void initialZorchigch() throws Exception {
        Field f = SpaceShip.class.getDeclaredField("zorchigch");
        f.setAccessible(true);
        assertEquals(0, f.getInt(ship));
    }

    @Test
    @DisplayName("km анхны утга 0.0")
    void initialKm() throws Exception {
        Field f = SpaceShip.class.getDeclaredField("km");
        f.setAccessible(true);
        assertEquals(0.0, f.getDouble(ship), 0.001);
    }

    // ==================== zorchigchAvah ====================

    @Test
    @DisplayName("zorchigchAvah: хэвийн нөхцөл")
    void zorchigchAvahNormal() {
        String result = ship.zorchigchAvah(5);
        assertEquals("5 зорчигч нэмэгдлээ", result);
    }

    @Test
    @DisplayName("zorchigchAvah: яг 10 хүрэх")
    void zorchigchAvahExact10() {
        ship.zorchigchAvah(5);
        String result = ship.zorchigchAvah(5);
        assertEquals("5 зорчигч нэмэгдлээ", result);
    }

    @Test
    @DisplayName("zorchigchAvah: 10-аас хэтрэх")
    void zorchigchAvahOverCapacity() {
        ship.zorchigchAvah(5);
        String result = ship.zorchigchAvah(6);
        assertEquals("Багтаамж хэтэрлээ!", result);
    }

    @Test
    @DisplayName("zorchigchAvah: хэтэрсний дараа зорчигч нэмэгдээгүй")
    void zorchigchAvahOverCapacityNoChange() throws Exception {
        ship.zorchigchAvah(8);
        ship.zorchigchAvah(5); // хэтэрнэ
        Field f = SpaceShip.class.getDeclaredField("zorchigch");
        f.setAccessible(true);
        assertEquals(8, f.getInt(ship));
    }

    // ==================== nisleg ====================

    @Test
    @DisplayName("nisleg: хэвийн нөхцөл")
    void nislegNormal() {
        String result = ship.nisleg(100);
        assertTrue(result.contains("100.0"));
        assertTrue(result.contains("Нислэг амжилттай"));
    }

    @Test
    @DisplayName("nisleg: түлш зөв хасагдсан")
    void nislegFuelDecrease() throws Exception {
        ship.nisleg(100); // 100*0.5 = 50 түлш зарцуулна
        Field f = SpaceShip.class.getDeclaredField("tulsh");
        f.setAccessible(true);
        assertEquals(50, f.getInt(ship));
    }

    @Test
    @DisplayName("nisleg: km зөв нэмэгдсэн")
    void nislegKmIncrease() throws Exception {
        ship.nisleg(100);
        Field f = SpaceShip.class.getDeclaredField("km");
        f.setAccessible(true);
        assertEquals(100.0, f.getDouble(ship), 0.001);
    }

    @Test
    @DisplayName("nisleg: түлш дутуу")
    void nislegNotEnoughFuel() {
        ship.nisleg(100); // tulsh=50
        String result = ship.nisleg(200); // 200*0.5=100 > 50
        assertEquals("Түлш дутуу!", result);
    }

    @Test
    @DisplayName("nisleg: түлш дутуу үед km өөрчлөгдөхгүй")
    void nislegNotEnoughFuelNoKmChange() throws Exception {
        ship.nisleg(100); // km=100
        ship.nisleg(200); // түлш дутуу
        Field f = SpaceShip.class.getDeclaredField("km");
        f.setAccessible(true);
        assertEquals(100.0, f.getDouble(ship), 0.001);
    }

    // ==================== tulshTsenegleh ====================

    @Test
    @DisplayName("tulshTsenegleh: хэвийн нөхцөл")
    void tulshTseneglehNormal() throws Exception {
        ship.nisleg(100); // tulsh=50
        ship.tulshTsenegleh(30); // tulsh=80
        Field f = SpaceShip.class.getDeclaredField("tulsh");
        f.setAccessible(true);
        assertEquals(80, f.getInt(ship));
    }

    @Test
    @DisplayName("tulshTsenegleh: 100-аас хэтрэхгүй")
    void tulshTseneglehCap() throws Exception {
        ship.tulshTsenegleh(50); // 100+50 → 100
        Field f = SpaceShip.class.getDeclaredField("tulsh");
        f.setAccessible(true);
        assertEquals(100, f.getInt(ship));
    }

    // ==================== toString ====================

    @Test
    @DisplayName("toString: анхны формат")
    void toStringInitial() {
        String result = ship.toString();
        assertEquals("🚀 Falcon | Түлш: 100% | Зорчигч: 0 | Нийт: 0.0 км", result);
    }

    @Test
    @DisplayName("toString: утгууд өөрчлөгдсөний дараа")
    void toStringAfterChanges() {
        ship.zorchigchAvah(3);
        ship.nisleg(50); // tulsh=75, km=50
        String result = ship.toString();
        assertEquals("🚀 Falcon | Түлш: 75% | Зорчигч: 3 | Нийт: 50.0 км", result);
    }
}
