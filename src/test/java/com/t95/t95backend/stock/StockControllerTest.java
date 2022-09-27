package com.t95.t95backend.stock;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.message.AuthException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.t95.t95backend.controller.StockController;
import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.StockService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

//Test stock controller
@WebMvcTest(controllers =  StockController.class)
@MockBeans({ @MockBean(StockService.class), @MockBean(JwtTokenUtils.class) })
public class StockControllerTest {
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private MockMvc mockMvc;	
	
	private static String SPECIFIC_STOCK_URL = "http://localhost:8086/api/stocks/TSLA";
	
	private static String WATCHLIST_GEN_URL = "http://localhost:8086/api/stocks/watchlists";
	
	//Mock data
	private ReturnUserInfo userInfo = new ReturnUserInfo("johnson", 1l);
	
	@BeforeEach
	public void jwtTokenSetup() throws AuthException {				
		when(jwtTokenUtils.getJwtInfo(anyString())).thenReturn(userInfo);
    }	
	
	//404 scenario
	@Test
	public void findStockBySymbol_404Scenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
				.get(SPECIFIC_STOCK_URL)
				.header("Authorization", "mockJwtToken")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals("404 NOT_FOUND", mvcResult.getResponse().getContentAsString());
	}
	
	//basic get stock by symbol scenario
	@Test
	public void findStockBySymbol_basicScenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
				.get(SPECIFIC_STOCK_URL)
				.header("Authorization", "mockJwtToken")
				.accept(MediaType.APPLICATION_JSON);
		
		//Mock data
		Optional<Stock> stock = Optional.of(new Stock((long) 5, "Telsa Inc", "TSLA", "283.70", "+9.28", "+3.38%"));
		
		//Stub
		when(stockService.findStockBySymbol("TSLA")).thenReturn(stock);
		
		//Fire the request
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Expected response
		String expectedResponse = "{\"id\":5,\"name\":\"Telsa Inc\",\"symbol\":\"TSLA\",\"price\":\"283.70\",\"movementPoints\":\"+9.28\",\"movementPercentage\":\"+3.38%\"}";

		//Assert
		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
	}
	

	//********* stocks current price and movements in watchLists

	//get stocks current price and movements in a watchLists
	@Test
	public void getWatchedStocksByWatchlistId_basicScenario() throws Exception {
		Long watchlistId = 1l;
        	
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
				.get(WATCHLIST_GEN_URL+ "/" + watchlistId)
				.header("Authorization", "mockJwtToken")
				.accept(MediaType.APPLICATION_JSON);
		
		//Mock data		
		List<Stock> watchedStocks = new ArrayList<Stock>();
		Stock stock1 = new Stock(1l, "Telsa Inc", "TSLA", "283.70", "+9.28", "+3.38%");
		Stock stock2 = new Stock(2l, "Apple", "APPL", "155.96", "-14.2", "-3.12%");
		watchedStocks.add(stock1);
		watchedStocks.add(stock2);
		
		//Stub
		when(stockService.getWatchedStockByWatchlistId(anyLong())).thenReturn(watchedStocks);
		
		//Fire the request
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Expected response
		String expectedResponse = "[{\"name\":\"Telsa Inc\"},{\"name\":\"Apple\"}]";
		
		//Assert
		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);			
	}
	
	//add stock to watchList	
	@Test
	public void addNewWatchedStock_basicScenario() throws Exception {
		Long variableWatchlistId = 1l;
		String paramStockId = "8";
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post(WATCHLIST_GEN_URL+"/"+variableWatchlistId)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "mockJwtToken")
				.queryParam("stockId", paramStockId);
		
		//Stub
		when(stockService.findWatchedPair(anyLong(), anyLong())).thenReturn(false);		
		when(stockService.addNewWatchedStock(anyLong(), anyLong())).thenReturn(1);
		
		//Fire the request
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Assert 201, success created
		assertEquals(201, mvcResult.getResponse().getStatus());		
	}
	

	
	//remove a stock from watchList
	@Test
	public void deleteWatchedStock_basicScenario() throws Exception {
		Long variableWatchlistId = 1l;
		String paramStockId = "8";
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.delete(WATCHLIST_GEN_URL+"/"+variableWatchlistId)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "mockJwtToken")
				.queryParam("stockId", paramStockId);
		
		//Stub
		when(stockService.findWatchedPair(anyLong(), anyLong())).thenReturn(true);	
		
		//Fire the request
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Assert
		ArgumentCaptor<Long> watchlistIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);		
		ArgumentCaptor<Long> stockIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);	
		verify(stockService).deleteWatchedStock(watchlistIdArgumentCaptor.capture(), stockIdArgumentCaptor.capture());
		assertEquals(1, watchlistIdArgumentCaptor.getValue().longValue());
		assertEquals(8, stockIdArgumentCaptor.getValue().longValue());	
		assertEquals(204, mvcResult.getResponse().getStatus());		
	}
	
	

}
