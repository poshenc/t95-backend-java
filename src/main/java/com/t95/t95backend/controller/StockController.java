package com.t95.t95backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.t95.t95backend.EventListener.WebSocketSessions;
import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.StockService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@EnableScheduling
@RestController
@RequestMapping(path = "api/stocks")
public class StockController {

    private StockService stockService;
    private JwtTokenUtils jwtTokenUtils;
    private SimpMessagingTemplate template;
    private final Logger LOG = LoggerFactory.getLogger(DefaultHandshakeHandler.class);
    private WebSocketSessions webSocketSessions;

    @Autowired
    public StockController(StockService stockService, JwtTokenUtils jwtTokenUtils, SimpMessagingTemplate template, WebSocketSessions webSocketSessions) {
        this.stockService = stockService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.template = template;
        this.webSocketSessions = webSocketSessions;
    }

    //get all stocks names and id
    @GetMapping
    public ResponseEntity getStocksList(@RequestHeader("Authorization") String authorization) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		List<Map> list = stockService.getStocksList();
    		return ResponseEntity.status(HttpStatus.OK).body(list);
    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }

    //get a stock current price and movements
    @GetMapping(path = "{symbol}")
    public ResponseEntity findStockBySymbol(@RequestHeader("Authorization") String authorization, @PathVariable("symbol") String symbol) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		Optional<Stock> stock = stockService.findStockBySymbol(symbol);
        	
        	if(stock.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    		return ResponseEntity.status(HttpStatus.OK).body(stock);
    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }   
    
  //get key index prices and movements
    @GetMapping(path = "/keyIndices")
    public ResponseEntity getKeyIndices(@RequestHeader("Authorization") String authorization) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		String[] mainSymbols = {"DOW J", "IXIC", "GSPC", "USDTWD", "TSLA", "APPL", "2330", "BTC", "ETH"};
        	List<Stock> data = new ArrayList<Stock>();   		
    		for(String symbol: mainSymbols) {
    			Optional<Stock> stock = stockService.findStockBySymbol(symbol);
    			if(stock.isPresent()) data.add(stock.get());    			
    		}
        	
    		return ResponseEntity.status(HttpStatus.OK).body(data);
    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }   
    
//    @MessageMapping("/message")
//	@SendTo("/topic/messages")
//	public SocketResponseMessage getMessage(final Message message) throws InterruptedException {
//		Thread.sleep(1000);
//		template.convertAndSend("/topic/messages", "Hello");
//		return new SocketResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
//	}
    
    //send key index prices and movements to all users
    @Scheduled(fixedRate = 60000) //10min = 600000
    public void priceUpdate() throws InterruptedException {
    	String[] mainSymbols = {"DOW J", "IXIC", "GSPC", "USDTWD", "TSLA", "APPL", "2330", "BTC", "ETH"};
    	List<Stock> data = new ArrayList<Stock>();   		
		for(String symbol: mainSymbols) {
			Optional<Stock> stock = stockService.findStockBySymbol(symbol);
			if(stock.isPresent()) data.add(stock.get());    			
		}
        Thread.sleep(1000);
        this.template.convertAndSend("/topic/globalIndex", data);
    }
    
  //send private
//    @Scheduled(fixedRate = 10000)
//    public void privatePriceUpdate() throws InterruptedException {
//        Thread.sleep(1000);
////        webSocketSessions.getSessionIdsByUser(user).forEach(sessionId -> template.convertAndSendToUser(sessionId, USER_TOPIC, message));
//    }
    
}
