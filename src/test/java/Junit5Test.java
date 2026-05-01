import org.example.Programs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class Junit5Test {



    @BeforeEach
    public void beforeTest() {
        System.out.println("Тест начат");
    }

    @DisplayName("Вычислить факториал числа")
    @ParameterizedTest
    @ValueSource(ints = {7, 11})
    public void testOne(int n) {
        assertEquals(5040, Programs.factorial(n));
    }

    @DisplayName("Найти площадь треугольника")
    @ParameterizedTest
    @CsvFileSource(resources = "/source.csv")
    public void testTwo(int base, int height) {
        assertEquals(77, Programs.areaTriangle(base, height));
    }

    @DisplayName("Арифметические действия с двумя целыми числами")
    @ParameterizedTest
    @CsvFileSource(resources = "/source.csv")
    public void testThree(int a, int b) {
        assertAll(
                () -> assertEquals(40, Programs.calculate(a, b, '+')),
                () -> assertEquals(24, Programs.calculate(a, b, '-')),
                () -> assertEquals(256, Programs.calculate(a, b, '*'))


        );
    }

    @DisplayName("Сравнение двух целых чисел.")
    @ParameterizedTest
    @CsvFileSource(resources = "/source.csv")
    public void testFour(int a, int b) {
        assertEquals("Числа равны", Programs.compare(a, b));
    }
}
