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
        if(list.size()==0)
            logger.info("No rule name was found!");
        else
            logger.info(list.size()+" rule name was found!");
        return list;
    }

    public RuleName add(RuleName ruleName)
    {
        if (ruleNameRepository.existsByName(ruleName.getName()))
        {
            logger.error("ERROR: this account is already used.");
            return null;
        }
        RuleName ruleName1=ruleNameRepository.save(ruleName);
        if (ruleName1!=null)
            logger.info("Rule name added successfully!");
        return ruleName1;
    }

    public boolean deleteRuleName(Integer id) {

        boolean isDeleted = false;

        if (!(ruleNameRepository.existsById(id))) {
            logger.error("Unknown rule with id : {}", id);
            return isDeleted;
        }
        else
        {
            ruleNameRepository.deleteById(id);
            logger.info("Rule deleted successfully!");
            isDeleted = true;
        }
        return isDeleted;
    }

    public RuleName getRuleName(Integer id) {

        if (ruleNameRepository.existsById(id))
        {
            logger.info("RuleName found successfully!");
            return ruleNameRepository.findById(id).get();
        }
        else
        {
            logger.info("RuleName not found!");
            return null;
        }

    }

    public RuleName update(RuleName ruleName, Integer id) {
        if (ruleNameRepository.existsById(id))
        {
            ruleName.setId(id);
            RuleName ruleName1=ruleNameRepository.save(ruleName);
            if (ruleName1!=null)
                logger.info("RuleName updated successfully!");
            else
                logger.info("RuleName Not updated!");
            return ruleName1;
        }
        else
            logger.info("RuleName Not found!");
        return null;
    }
}
