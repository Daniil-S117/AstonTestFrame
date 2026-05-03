import org.example.Programs;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestNGAreaTriangle {
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
    public void testAreaTriangle(int a, int b) {
        Assert.assertEquals(programs.areaTriangle(b, a), 77);
    }

}
