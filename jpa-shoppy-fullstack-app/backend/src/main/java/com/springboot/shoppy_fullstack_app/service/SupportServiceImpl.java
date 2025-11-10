package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.SupportDto;
import com.springboot.shoppy_fullstack_app.entity.Support;
import com.springboot.shoppy_fullstack_app.jpa_repository.JpaSupportRepository;
import com.springboot.shoppy_fullstack_app.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportServiceImpl implements SupportService{

    private SupportRepository supportRepository;
    private JpaSupportRepository jpaSupportRepository;

    @Autowired
    public SupportServiceImpl(SupportRepository supportRepository, JpaSupportRepository jpaSupportRepository) {
        this.supportRepository = supportRepository;
        this.jpaSupportRepository = jpaSupportRepository;
    }

    @Override
    public List<SupportDto> findAll(SupportDto supportDto) {
        List<Support> list = null;
        if(supportDto.getStype().equals("all")) {
            list = jpaSupportRepository.findAll();
        } else {
            list = jpaSupportRepository.findByType(supportDto.getStype());
        }
        List<SupportDto> resultList = new ArrayList<>();
        list.forEach(entity -> resultList.add(new SupportDto(entity)));
        return resultList;
    }
}
