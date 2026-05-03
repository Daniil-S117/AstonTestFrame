import org.example.Programs;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestNGCalculate {
    Programs programs = new Programs();

    @BeforeMethod
    public void beforeTest() {
        System.out.println("Тест начат: " + this.getClass().getName());
    }

    @DataProvider
    public Object[][] sourceData() {
        return new Object[][]{
                {32, 8, '+', 25},
                {11, 14, '-', 3},
                {15, 15, '*', 225},
                {7, 5, '/', 1},
        };
    }

    @Test(dataProvider = "sourceData")
    public void testCalcSum(int a, int b, char l, int r) {
        Assert.assertEquals(programs.calculate(b, a, l), r);
    }

    @Test(dataProvider = "sourceData")
    public void testCalcSub(int a, int b, char l, int r) {
        Assert.assertEquals(programs.calculate(b, a, l), r);
    }

    @Test(dataProvider = "sourceData")
    public void testCalcMult(int a, int b, char l, int r) {
        Assert.assertEquals(programs.calculate(b, a, l), r);
    }

    @Test(dataProvider = "sourceData")
    public void testCalcDiv(int a, int b, char l, int r) {
        Assert.assertEquals(programs.calculate(b, a, l), r);
    }
}
