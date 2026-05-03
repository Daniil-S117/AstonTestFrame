import org.example.Programs;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestNGFactorial {
    Programs programs = new Programs();

    @BeforeMethod
    public void beforeTest() {
        System.out.println("Тест начат: "  + this.getClass().getName());
    }

    @Test()
    @Parameters({"a", "b"})
    public void testFactorialOne(@Optional("7") int a, @Optional("5040") int b) {
        Assert.assertEquals(programs.factorial(a), b);
    }

    @Test()
    @Parameters({"x", "y"})
    public void testFactorialTwo(@Optional("11") int x, @Optional("3991680") int y) {
        Assert.assertEquals(programs.factorial(x), y);
    }
}
