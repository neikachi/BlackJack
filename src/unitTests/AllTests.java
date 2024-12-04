package unitTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CardTest.class, DealerTest.class, DeckCollectionTest.class, DeckTest.class, PlayerTest.class,
		UserTest.class })
public class AllTests {

}
