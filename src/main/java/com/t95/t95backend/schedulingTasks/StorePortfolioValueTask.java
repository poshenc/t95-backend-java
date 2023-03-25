package com.t95.t95backend.schedulingTasks;

import com.t95.t95backend.entity.Portfolio;
import com.t95.t95backend.entity.PortfolioHistory;
import com.t95.t95backend.returnBean.ReturnPosition;
import com.t95.t95backend.service.PortfolioHistoryService;
import com.t95.t95backend.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EnableScheduling
@Component
public class StorePortfolioValueTask {

    private static final Logger logger = LoggerFactory.getLogger(RetrieveYahooFinance.class);

    private final PortfolioService portfolioService;
    private final PortfolioHistoryService portfolioHistoryService;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    public StorePortfolioValueTask(PortfolioService portfolioService, PortfolioHistoryService portfolioHistoryService) {
        this.portfolioService = portfolioService;
        this.portfolioHistoryService = portfolioHistoryService;
    }


//    @EventListener(ApplicationReadyEvent.class) //fire task on app init
    @Scheduled(cron="0 5 1 * * ?") //fire task at 1:05am TPE time every day
    public void storePortfolioValueAtMidnight() {
        if(activeProfile.equals("prod")) {
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

            logger.info("Successfully store all portfolios value at midnight. Time: " + LocalTime.now());
        }
    }

}
