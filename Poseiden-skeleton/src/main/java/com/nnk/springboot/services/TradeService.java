package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private static final Logger logger = LogManager.getLogger("TradeService");
    @Autowired
    private TradeRepository tradeRepository;
    public List<Trade> getTradeList() {
        List<Trade> list=tradeRepository.findAll();
        if(list.size()<=0)
            logger.info("No trade was found!");
        else
            logger.info(list.size()+" trade was found!");
        return list;
    }

    public Trade add(Trade trade) {
        if (tradeRepository.existsByAccount(trade.getAccount()))
        {
            logger.error("ERROR: this account is already used.");
            return null;
        }
        Trade trade1=tradeRepository.save(trade);
        if(trade1!=null)
        logger.info("Trade added successfully");
        return trade1;
         }

    public boolean deleteTrade(Integer id) {

        boolean isDeleted = false;

        if (!(tradeRepository.existsById(id))) {
            logger.error("Unknown trade with id : {}", id);
            return isDeleted;
        }
        else
        {
            tradeRepository.deleteById(id);
            logger.info("Trade deleted successfully!");
            isDeleted = true;
        }
        return isDeleted;
    }

    public Trade getTrade(Integer id) {
        if (tradeRepository.existsById(id))
        {
            logger.info("Trade found successfully!");
            return tradeRepository.findById(id).get();
        }
        else
        {
            logger.info("Trade not found!");
            return null;
        }
    }

    public Trade update(Trade trade, Integer id) {

        trade.setTradeId(id);
        Trade updatedTrade=tradeRepository.save(trade);
        if (updatedTrade!=null)
            logger.info("BidList updated successfully!");
        return updatedTrade;
    }
}
