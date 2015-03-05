import junit.framework.TestCase;
import models.Report;
import org.junit.*;

public class JUnitTest extends TestCase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Code exécuté avant l'exécution du premier test (et de la méthode @Before)
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Code exécuté après l'exécution de tous les tests
    }

    @Before
    public void setUp() throws Exception {
        // Code exécuté avant chaque test
    }

    @After
    public void tearDown() throws Exception {
        // Code exécuté après chaque test
    }

    @Test
    public void test() {
        Report r = new Report("test");
        r.content = "Ceci est un test".getBytes();

        assertEquals(null, r.getChunk(Integer.MIN_VALUE));
        assertEquals(null, r.getChunk(-1));
        assertEquals("Ceci", r.getChunk(0));
        assertEquals("est", r.getChunk(1));
        assertEquals("un", r.getChunk(2));
        assertEquals("test", r.getChunk(3));
        assertEquals(null, r.getChunk(4));
        assertEquals(null, r.getChunk(Integer.MAX_VALUE));
    }
}