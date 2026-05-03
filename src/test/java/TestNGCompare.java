import org.example.Programs;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestNGCompare {
    Programs programs = new Programs();

    @BeforeMethod
    public void beforeTest() {
        System.out.println("Тест начат: "  + this.getClass().getName());
    }

    @DataProvider
     public Object[][] sourceData(){
        return new Object[][] {
                {32, 8},
                {11, 14},
                {15, 15},
                {7, 5}
        };
    }

    @Test(dataProvider = "sourceData")
    public void testCompare(int a, int b) {
        Assert.assertEquals(programs.compare(a, b), "Числа равны");
    }
}
