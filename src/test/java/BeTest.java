import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.not;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BeTest {

    Methods page = new Methods();

    @Test(dataProvider = "getData")
    public void searchCarsByCriteria(String category, String bodyStyle, String marka, String model, String yearsFrom,
            String yearsTo, String gearbox, String raceFrom, String raceTo) throws InterruptedException {
        HttpResponse response;
        int count;
//        формируем критерии запроса
        StringBuilder params = new StringBuilder();
        params.append("&category_id=" + category);
        params.append("&bodystyle[0]=" + bodyStyle);
        params.append("&marka_id[0]=" + marka);
        params.append("&model_id[0]=" + model);
        params.append("&s_yers[1]=" + yearsFrom);
        params.append("&po_yers[1]=" + yearsTo);
        params.append("&gearbox[1]=" + gearbox);
        params.append("&raceFrom=" + raceFrom);
        params.append("&raceTo=" + raceTo);
//        делаем запрос, получаем ответ
        response = page.createRequest(params.toString());
        StatusLine statusLine = response.getStatusLine();
//        проверяем, что запрос был успешный, если ассерт не пройдет, то выведет сообщение, которое пришло нам в ответе на запрос
        assertEquals(statusLine.getStatusCode(), 200, statusLine.getReasonPhrase());
//        получаем count и проверяем, что он больше нуля, то есть, что поиск дал результат
        count = page.checkCountsOfResult(response);
        assertTrue(count >= 0, "Search returned no results, possibly incorrect search criteria");

//        Колбэк запроса, время ответа и count выводятся в консоль

    }

    @Test(dataProvider = "getData")
    public void searchCarsByCriteria1(String category, String bodyStyle, String marka, String model, String yearsFrom,
            String yearsTo, String gearbox, String raceFrom, String raceTo) {
        HttpResponse response;
        int count;
//        формируем критерии запроса
        StringBuilder params = new StringBuilder();
        params.append("&category_id=" + category);
        params.append("&bodystyle[0]=" + bodyStyle);
        params.append("&marka_id[0]=" + marka);
        params.append("&model_id[0]=" + model);
        params.append("&s_yers[1]=" + yearsFrom);
        params.append("&po_yers[1]=" + yearsTo);
        params.append("&gearbox[1]=" + gearbox);
        params.append("&raceFrom=" + raceFrom);
        params.append("&raceTo=" + raceTo);
        String url =
                "https://developers.ria.com/auto/search?" + "api_key=MUcjo3x6iWRA5sRPQZFzkEAFTXnT2qKPELpO8lxh" + params
                        .toString();
//        делаем запрос, получаем ответ
        System.out.println(url);
        vdfvd();
        vdfvdd();
        given()
                .filter(new AllureRestAssured())
                .get(url)
                .then()
                .assertThat()
                .body("result.search_result.count", not(0));
    }

    @Step
    public void vdfvd() {
        System.out.println("ghbdf");
    }

    @Attachment
    public String vdfvdd() {
        return "ghbdf";
    }

    @DataProvider()
    public Iterator<Object[]> getData() {
        return Stream.of(
                asList("1", "3", "47", "6", "2017", "2017", "2", "1", "1"),
                asList("1", "3", "79", "698", "2017", "2017", "1", "1", "1"),
                asList("1", "3", "28", "265", "2017", "2017", "1", "1", "1")
        ).map(List::toArray).iterator();


    }
}



