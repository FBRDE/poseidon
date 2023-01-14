package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService {
    private static final Logger logger = LogManager.getLogger("CurveService");
    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> getCurvePointList() {

        List<CurvePoint> list=curvePointRepository.findAll();
        if(list.size()==0)
            logger.info("No curve point was found");
        else
            logger.info(list.size()+" curve point was found");
        return list;
    }

    public CurvePoint add(CurvePoint curvePoint) {

        if (curvePointRepository.existsByCurveId(curvePoint.getCurveId()))
        {
            logger.error("ERROR: this id is already used.");
            return null;
        }
        CurvePoint curvePoint1=curvePointRepository.save(curvePoint);
        if(curvePoint1!=null)
            logger.info("Curve point added successfully!");
        return curvePoint1;
    }
    public boolean deleteCurvePoint(Integer id) {
        boolean isDeleted = false;

        if (!(curvePointRepository.existsById(id))) {
            logger.error("Unknown curvePoint with id : {}", id);
            return isDeleted;
        }
        else
        {
            curvePointRepository.deleteById(id);
            logger.info("CurvePoint deleted successfully!");
            isDeleted = true;
        }
        return isDeleted;

    }

    public CurvePoint getCurvePointById(Integer id) {
        if (curvePointRepository.existsById(id))
        {
            logger.info("CurvePoint found successfully!");
            return curvePointRepository.findById(id).get();
        }
        else
        {
            logger.info("CurvePoint not found!");
            return null;
        }
    }

    public CurvePoint update(CurvePoint curvePoint,Integer id) {

        curvePoint.setId(id);
        CurvePoint curvePoint1=curvePointRepository.save(curvePoint);
        if(curvePoint1!=null)
            logger.info("Curve point updated successfully!");
        return  curvePoint1;

    }
}
