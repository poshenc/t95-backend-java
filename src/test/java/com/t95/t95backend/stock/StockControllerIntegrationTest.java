package com.t95.t95backend.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.security.auth.message.AuthException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class StockControllerIntegrationTest {
	
	private static String SPECIFIC_STOCK_URL = "/api/stocks/TSLA";
	
	private static String GET_ALL_STOCK_URL = "/api/stocks";
	
	private static String GET_STOCKS_IN_WATCHLIST_URL = "/api/stocks/watchlists/1";
	
	private HttpEntity<String> httpEntity;
			
	@MockBean
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private TestRestTemplate template;
	
	@BeforeEach
	public void jwtTokenSetup() throws AuthException {				
		//mock JWT
		ReturnUserInfo userInfo = new ReturnUserInfo("johnson", 1l);
		when(jwtTokenUtils.getJwtInfo(anyString())).thenReturn(userInfo);		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "mockedJWTtocken");
		
		httpEntity = new HttpEntity<String>(headers);
    }	
	
	
	@Test
	public void findStockBySymbol_basicScenario() throws JSONException, Exception {
		
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_STOCK_URL, HttpMethod.GET, httpEntity, String.class);

		String expectedResponse = "{\"id\":5,\"name\":\"Telsa Inc\",\"symbol\":\"TSLA\",\"price\":\"283.70\",\"movementPoints\":\"+9.28\",\"movementPercentage\":\"+3.38%\"}";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
//		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}
	
	
	@Test
	public void getStocksList_basicScenario() throws JSONException, Exception {
		
		ResponseEntity<String> responseEntity = template.exchange(GET_ALL_STOCK_URL, HttpMethod.GET, httpEntity, String.class);
		
		String expectedResponse = "[{\"name\": \"Dow Jones\"},{\"name\": \"NASDAQ\"},{\"name\": \"S&P 500\"},{\"name\": \"Telsa Inc\"},{\"name\": \"Apple\"},{\"name\": \"Nvidia Corporation\"},{\"name\": \"台積電\"},{\"name\": \"聯華\"},{\"name\": \"聯發科\"},{\"name\": \"Etherum\"},{\"name\": \"Soloran\"},{\"name\": \"Bitcoin\"},{\"name\": \"USD/TWD\"}]";
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
//		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}	
	
	@Test
	public void getWatchedStocksByWatchlistId_basicSenario() throws JSONException, Exception {		
		//POST
		ResponseEntity<String> responseEntity = template.exchange(GET_STOCKS_IN_WATCHLIST_URL, HttpMethod.GET, httpEntity, String.class);

		String expectedResponse = "[{\"name\":\"Dow Jones\"},{\"name\":\"NASDAQ\"},{\"name\":\"S&P 500\"}]";		
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));	
//		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);		
	}
	
	@Test
	public void addNewWatchedStock_basicSenario() throws Exception { 		
		//POST
		String SPECIFIC_WATCHED_STOCK_URL = "/api/stocks/watchlists/2?stockId=1";
		ResponseEntity<String> responseEntity = template.exchange(SPECIFIC_WATCHED_STOCK_URL, HttpMethod.POST, httpEntity, String.class);
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("\"Success added to watchlist.\"", responseEntity.getBody());	
		
		//tear down
		template.exchange(SPECIFIC_WATCHED_STOCK_URL, HttpMethod.DELETE, httpEntity, String.class);
	}
	

}
