import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

@DisplayName("Даалгавар 2: FoodStation")
public class FoodStationTest {

    private FoodStation station;

    @BeforeEach
    void setUp() {
        station = new FoodStation("Galaxy Cafe");
    }

    // ==================== Бүтэц шалгах ====================

    @Test
    @DisplayName("FoodStation класс үүссэн эсэх")
    void classExists() {
        assertNotNull(station);
    }

    @Test
    @DisplayName("Constructor нэг String параметртэй")
    void constructorParams() throws Exception {
        Constructor<FoodStation> c = FoodStation.class.getConstructor(String.class);
        assertNotNull(c);
    }

    @Test
    @DisplayName("Бүх талбар private эсэх")
    void allFieldsPrivate() {
        for (Field f : FoodStation.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()),
                f.getName() + " талбар private биш байна!");
        }
    }

    // ==================== Анхны утгууд ====================

    @Test
    @DisplayName("ner талбарын анхны утга зөв")
    void initialNer() throws Exception {
        Field f = FoodStation.class.getDeclaredField("ner");
        f.setAccessible(true);
        assertEquals("Galaxy Cafe", f.get(station));
    }

    @Test
    @DisplayName("menu анхны утга хоосон ArrayList")
    void initialMenu() throws Exception {
        Field f = FoodStation.class.getDeclaredField("menu");
        f.setAccessible(true);
        Object menu = f.get(station);
        assertNotNull(menu, "menu нь null байна! new ArrayList<>() ашиглана уу");
        assertTrue(menu instanceof ArrayList, "menu нь ArrayList биш байна!");
        assertEquals(0, ((ArrayList<?>) menu).size());
    }

    @Test
    @DisplayName("uneNuud анхны утга хоосон ArrayList")
    void initialUneNuud() throws Exception {
        Field f = FoodStation.class.getDeclaredField("uneNuud");
        f.setAccessible(true);
        Object uneNuud = f.get(station);
        assertNotNull(uneNuud, "uneNuud нь null байна!");
        assertTrue(uneNuud instanceof ArrayList);
        assertEquals(0, ((ArrayList<?>) uneNuud).size());
    }

    @Test
    @DisplayName("niitOrlogo анхны утга 0")
    void initialOrlogo() throws Exception {
        Field f = FoodStation.class.getDeclaredField("niitOrlogo");
        f.setAccessible(true);
        assertEquals(0, f.getInt(station));
    }

    // ==================== tsesNemeh ====================

    @Test
    @DisplayName("tsesNemeh: нэг хоол нэмэх")
    void tsesNemehOne() throws Exception {
        station.tsesNemeh("Бууз", 3500);
        Field menuField = FoodStation.class.getDeclaredField("menu");
        menuField.setAccessible(true);
        ArrayList<?> menu = (ArrayList<?>) menuField.get(station);
        assertEquals(1, menu.size());
        assertEquals("Бууз", menu.get(0));

        Field uneField = FoodStation.class.getDeclaredField("uneNuud");
        uneField.setAccessible(true);
        ArrayList<?> uneNuud = (ArrayList<?>) uneField.get(station);
        assertEquals(3500, uneNuud.get(0));
    }

    @Test
    @DisplayName("tsesNemeh: олон хоол нэмэх")
    void tsesNemehMultiple() throws Exception {
        station.tsesNemeh("Бууз", 3500);
        station.tsesNemeh("Хуушуур", 2500);
        station.tsesNemeh("Цуйван", 4000);

        Field menuField = FoodStation.class.getDeclaredField("menu");
        menuField.setAccessible(true);
        ArrayList<?> menu = (ArrayList<?>) menuField.get(station);
        assertEquals(3, menu.size());
    }

    // ==================== zahialga ====================

    @Test
    @DisplayName("zahialga: олдох нөхцөл")
    void zahialgaFound() {
        station.tsesNemeh("Бууз", 3500);
        String result = station.zahialga("Бууз");
        assertEquals("✅ Бууз бэлтгэж байна", result);
    }

    @Test
    @DisplayName("zahialga: олдохгүй нөхцөл")
    void zahialgaNotFound() {
        station.tsesNemeh("Бууз", 3500);
        String result = station.zahialga("Пицца");
        assertEquals("❌ Цэсэнд байхгүй", result);
    }

    @Test
    @DisplayName("zahialga: орлого зөв нэмэгдсэн")
    void zahialgaRevenueAdded() throws Exception {
        station.tsesNemeh("Бууз", 3500);
        station.tsesNemeh("Хуушуур", 2500);
        station.zahialga("Бууз");
        station.zahialga("Хуушуур");

        Field f = FoodStation.class.getDeclaredField("niitOrlogo");
        f.setAccessible(true);
        assertEquals(6000, f.getInt(station));
    }

    @Test
    @DisplayName("zahialga: олдохгүй үед орлого нэмэгдэхгүй")
    void zahialgaNotFoundNoRevenue() throws Exception {
        station.tsesNemeh("Бууз", 3500);
        station.zahialga("Пицца");

        Field f = FoodStation.class.getDeclaredField("niitOrlogo");
        f.setAccessible(true);
        assertEquals(0, f.getInt(station));
    }

    @Test
    @DisplayName("zahialga: хоосон цэснээс захиалах")
    void zahialgaEmptyMenu() {
        String result = station.zahialga("Бууз");
        assertEquals("❌ Цэсэнд байхгүй", result);
    }

    // ==================== hamgiinHvnstei ====================

    @Test
    @DisplayName("hamgiinHvnstei: хэвийн нөхцөл")
    void hamgiinHvnsteiNormal() {
        station.tsesNemeh("Бууз", 3500);
        station.tsesNemeh("Хуушуур", 2500);
        station.tsesNemeh("Стейк", 15000);
        assertEquals("Стейк", station.hamgiinHvnstei());
    }

    @Test
    @DisplayName("hamgiinHvnstei: нэг хоолтой цэс")
    void hamgiinHvnsteiSingle() {
        station.tsesNemeh("Бууз", 3500);
        assertEquals("Бууз", station.hamgiinHvnstei());
    }

    @Test
    @DisplayName("hamgiinHvnstei: хоосон цэс")
    void hamgiinHvnsteiEmpty() {
        assertEquals("Цэс хоосон", station.hamgiinHvnstei());
    }

    // ==================== toString ====================

    @Test
    @DisplayName("toString: анхны формат")
    void toStringInitial() {
        String result = station.toString();
        assertEquals("🍜 Galaxy Cafe | Цэс: 0 хоол | Орлого: 0₮", result);
    }

    @Test
    @DisplayName("toString: утгууд өөрчлөгдсөний дараа")
    void toStringAfterChanges() {
        station.tsesNemeh("Бууз", 3500);
        station.tsesNemeh("Хуушуур", 2500);
        station.zahialga("Бууз");
        String result = station.toString();
        assertEquals("🍜 Galaxy Cafe | Цэс: 2 хоол | Орлого: 3500₮", result);
    }
}
