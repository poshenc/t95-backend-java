package com.t95.t95backend.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class StockControllerIntTest {
	
	private static String SPECIFIC_STOCK_URL = "/api/stocks/TSLA";
	
	private static String GET_ALL_STOCK_URL = "/api/stocks";
	
	private static String GET_STOCKS_IN_WATCHLIST_URL = "/api/stocks/watchlists/1";
	
	@Autowired
	private TestRestTemplate template;
	
	@Test
	void findStockBySymbol_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_STOCK_URL, String.class);
		String expectedResponse = "{\"id\":5,\"name\":\"Telsa Inc\",\"symbol\":\"TSLA\",\"price\":\"283.70\",\"movementPoints\":\"+9.28\",\"movementPercentage\":\"+3.38%\"}";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);		
		
//		System.out.println(responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());
	}
	
	
	@Test
	void getStocksList_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GET_ALL_STOCK_URL, String.class);
		String expectedResponse = "[{\"name\": \"Dow Jones\"},{\"name\": \"NASDAQ\"},{\"name\": \"S&P 500\"},{\"name\": \"Telsa Inc\"},{\"name\": \"Apple\"},{\"name\": \"Nvidia Corporation\"},{\"name\": \"台積電\"},{\"name\": \"聯華\"},{\"name\": \"聯發科\"},{\"name\": \"Etherum\"},{\"name\": \"Soloran\"},{\"name\": \"Bitcoin\"},{\"name\": \"USD/TWD\"}]";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);		
	}	
	
	@Test
	void getWatchedStocksByWatchlistId_basicSenario() throws JSONException {
		ResponseEntity<String> responseEntity = template.getForEntity(GET_STOCKS_IN_WATCHLIST_URL, String.class);
		String expectedResponse = "[{\"name\":\"Dow Jones\"},{\"name\":\"NASDAQ\"},{\"name\":\"S&P 500\"}]";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);		
	}
	
	@Test
	void addNewWatchedStock_basicSenario() { 
		String SPECIFIC_WATCHED_STOCK_URL = "/api/stocks/watchlists/1?stockId=8";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		
		//POST
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_WATCHED_STOCK_URL, HttpMethod.POST, httpEntity, String.class);
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("\"Success added to watchlist.\"", responseEntity.getBody());	
		
		template.delete(SPECIFIC_WATCHED_STOCK_URL);
	}
	

}
