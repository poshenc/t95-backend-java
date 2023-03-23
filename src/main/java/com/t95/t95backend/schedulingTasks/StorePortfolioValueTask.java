package com.t95.t95backend.schedulingTasks;

import com.t95.t95backend.entity.Portfolio;
import com.t95.t95backend.entity.PortfolioHistory;
import com.t95.t95backend.returnBean.ReturnPosition;
import com.t95.t95backend.service.PortfolioHistoryService;
import com.t95.t95backend.service.PortfolioService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@EnableScheduling
@Component
public class StorePortfolioValueTask {

    private final PortfolioService portfolioService;
    private final PortfolioHistoryService portfolioHistoryService;

    public StorePortfolioValueTask(PortfolioService portfolioService, PortfolioHistoryService portfolioHistoryService) {
        this.portfolioService = portfolioService;
        this.portfolioHistoryService = portfolioHistoryService;
    }


//    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron="01 0 0 * * *")
    public void storePortfolioValueAtMidnight() {

        //1. get all portfolios (portfolio_id + user_id + cash)
        List<Portfolio> portfolios = portfolioService.getPortfolios();

        for (Portfolio portfolio : portfolios) {
            double portfolioSum = 0;
            portfolioSum += portfolio.getCash();

            //2. get a list of position and its (quantity=200, price=191.15)
            List<ReturnPosition> positionAndPrice = portfolioService.getPortfolioPositionAndPrice(portfolio.getId());
            for (ReturnPosition returnPosition : positionAndPrice) {
                //3. calculate value (price * quantity)
                double positionValue = Double.parseDouble(returnPosition.getPrice()) * returnPosition.getQuantity();
                portfolioSum += positionValue;
            };

            //4. store each portfolio value to db
            PortfolioHistory portfolioHistory = new PortfolioHistory(LocalDate.now().minusDays(1L), portfolioSum, portfolio.getId(), portfolio.getUserId());
            portfolioHistoryService.savePortfolioHistory(portfolioHistory);
        };
    }
}
