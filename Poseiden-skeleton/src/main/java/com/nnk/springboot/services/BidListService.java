package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {
    private static final Logger logger = LogManager.getLogger("BidListService");
    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getBidList() {
        List<BidList> list=bidListRepository.findAll();
        if(list.size()<=0)
            logger.info("No bidList was found!");
        else
            logger.info(list.size()+" bidList was found!");
        return list;
    }

    public void deleteBid(Integer id) {
        bidListRepository.deleteById(id);
    }

    public void add(BidList bid) {
        BidList bidList=bidListRepository.save(bid);
        if(bidList!=null)
            logger.info("Bid added successfully!");

    }

    public BidList getBid(Integer id) {
        return  bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
    }
}
