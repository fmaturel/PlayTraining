

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest extends TestCase {

    private static class Personne {
        private String prenom;

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
    }

    @Mock
    Personne personne;

    @Test
    public void testMockExemple() {

        when(personne.getPrenom()).thenReturn("François");

        System.out.println(personne.getPrenom()); // affiche "François"

        personne.setPrenom("Albert");

        System.out.println(personne.getPrenom()); // affiche "François"
    }
}