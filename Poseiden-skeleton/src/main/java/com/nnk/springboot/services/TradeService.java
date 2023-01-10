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

    public void add(Trade trade) {
        Trade trade1=tradeRepository.save(trade);
        if(trade1!=null)
        logger.info("Trade added successfully");
         }

    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }

    public Trade getTrade(Integer id) {
        return  tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
    }

}
