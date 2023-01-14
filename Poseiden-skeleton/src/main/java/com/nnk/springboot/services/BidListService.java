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
        if(list.size() == 0)
            logger.info("No bidList was found!");
        else
            logger.info(list.size()+" bidList was found!");
        return list;
    }

    public boolean deleteBid(Integer id) {

        boolean isDeleted = false;

        if (!(bidListRepository.existsById(id))) {
            logger.error("Unknown bid with id : {}", id);
            return isDeleted;
        }
        else
        {
            bidListRepository.deleteById(id);
            logger.info("Bid deleted successfully!");
            isDeleted = true;
        }
        return isDeleted;

    }

    public BidList add(BidList bid) {

        if (bidListRepository.existsByAccount(bid.getAccount()))
        {
            logger.error("ERROR: this account is already used.");
            return null;
        }
        BidList addedBidList=bidListRepository.save(bid);
        if(addedBidList!=null)
            logger.info("Bid added successfully!");
        return addedBidList;
    }
    public BidList getBidById(Integer id) {
        if (bidListRepository.existsById(id))
        {
            logger.info("Bid found successfully!");
            return bidListRepository.findById(id).get();
        }
        else
        {
            logger.info("Bid not found!");
            return null;
        }

    }

    public BidList update(BidList bidList, Integer id) {
        if (bidListRepository.existsById(id))
        {
            bidList.setBidListId(id);
            BidList bidList1=bidListRepository.save(bidList);
            if (bidList1!=null)
                logger.info("BidList updated successfully!");
            else
                logger.info("BidList Not updated!");
            return bidList1;
        }
        else
            logger.info("BidList Not found!");
        return null;
    }
}
