package com.sky.service.impl;

import com.sky.mapper.StatusMapper;
import com.sky.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusMapper statusMapper;

    @Override
    public void startOrStop(Integer status, Long id) {

    }
}
