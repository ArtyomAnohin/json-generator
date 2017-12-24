import groovy.json.JsonBuilder
import it.test.Base
import org.testng.annotations.BeforeTest
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class JsonTest extends Base {

    @BeforeTest
    void setUp() {
        person = fakePerson()
    }

    @Test(dataProvider = "removeData")
    void removeData(def removeData, Integer statusCode) {
        def fakePerson = person.remover(removeData)
        println new JsonBuilder(fakePerson).toPrettyString()
        println "Statuscode: $statusCode"
    }

    @Test(dataProvider = "fakeData")
    void fakeData(def fakeData, Integer statusCode) {
        def fakePerson = person.faker(fakeData)
        println new JsonBuilder(fakePerson).toPrettyString()
        println "Statuscode: $statusCode"
    }

    @DataProvider(name = "removeData")
    public Object[][] removeData() {
        [
                [["firstName"], 200],
                [["address.city"], 200],
                [["creditCards.number"], 200],
                [["skills.Administrative"],200]
        ]
    }
    @DataProvider(name = "fakeData")
    public Object[][] fakeData() {
        [
                [["firstName=1"], 200],
                [["address.city=true"], 200],
                [["creditCards.cvv=AAAAA"], 200],
                [["skills.Administrative"],200]
        ]
    }
}
