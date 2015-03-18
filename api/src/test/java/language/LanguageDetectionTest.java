package language;

import junit.framework.TestCase;
import me.champeau.ld.UberLanguageDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LanguageDetectionTest extends TestCase{
	
	private UberLanguageDetector det = UberLanguageDetector.getInstance();
	
	@Test
	public void shouldRecognizeGermanLanguage() {
		String lang = det.detectLang("Hallo ich bin ein deutscher Text mit einem coolen Anglizismus");
		assertEquals("de", lang);
	}
	
	@Test
	public void shouldRecognizeEnglishLanguage() {
		String lang = det.detectLang("My child went to kindergarten.");
		assertEquals("en", lang);
	}
}
