package com.example.mpgexchange.service;

import com.example.mpgexchange.util.MPGUtil;
import com.example.mpgexchange.util.ModelMPG;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCache {

    public List<ModelMPG> getFuelEconomyDataForToyota(Integer startYear, Integer endYear, String model) {
        List<ModelMPG> allData = new ArrayList<>();

        //set years if only one is set
        if (startYear == null) {
            startYear = endYear;
        }
        if (endYear == null) {
            endYear = startYear;
        }
        //loop through all years and pull MPG
        for (int year = startYear; year <= endYear; year++) {
            List<ModelMPG> models = MPGUtil.getFuelEconomyData("Toyota", year);
            for (ModelMPG modelMPG : models) {
                if (model == null || modelMPG.getModelName().toLowerCase().contains(model.toLowerCase())) {
                    allData.add(modelMPG);
                }
            }
        }

        return allData;
    }
}
