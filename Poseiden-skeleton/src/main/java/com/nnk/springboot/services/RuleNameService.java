package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    private static final Logger logger = LogManager.getLogger("RuleNameService");
    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> getRuleNameList() {
        List<RuleName> list=ruleNameRepository.findAll();
        if(list.size()<=0)
            logger.info("No rule name was found!");
        else
            logger.info(list.size()+" rule name was found!");
        return list;
    }

    public void add(RuleName ruleName)
    {

        RuleName ruleName1=ruleNameRepository.save(ruleName);
        if (ruleName1!=null)
            logger.info("Rule name added successfully!");
    }

    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }

    public RuleName getRuleName(Integer id) {
        return  ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rule Name Id:" + id));
    }

}
