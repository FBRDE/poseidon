package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurveService {
    private static final Logger logger = LogManager.getLogger("CurveService");
    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> getCurvePointList() {

        List<CurvePoint> list=curvePointRepository.findAll();
        if(list.size()<=0)
            logger.info("No curve point was found");
        else
            logger.info(list.size()+" curve point was found");
        return list;
    }

    public void add(CurvePoint curvePoint) {

        CurvePoint curvePoint1=curvePointRepository.save(curvePoint);
    if(curvePoint1!=null)
        logger.info("Curve point added successfully!");

    }

    public void deleteCurvePoint(Integer id) {

        curvePointRepository.deleteById(id);
    }

    public CurvePoint getCurvePoint(Integer id) {

        return  curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
    }
}
