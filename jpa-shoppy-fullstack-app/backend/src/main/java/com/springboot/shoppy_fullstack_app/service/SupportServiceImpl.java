package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.PageResponseDto;
import com.springboot.shoppy_fullstack_app.dto.SupportDto;
import com.springboot.shoppy_fullstack_app.entity.Support;
import com.springboot.shoppy_fullstack_app.jpa_repository.JpaSupportRepository;
import com.springboot.shoppy_fullstack_app.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PageResponseDto<SupportDto> findSearchAll(SupportDto support){
        int currentPage = support.getCurrentPage()-1;
        int pageSize = support.getPageSize();
        String type = support.getType();
        String keyword = "%"+support.getKeyword()+"%";
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Support> list = jpaSupportRepository.search(type,keyword,pageable);

        List<SupportDto> resultList = new ArrayList<>();
        //entity => Dto, rowNumber 추가
        int offset = pageSize * (currentPage);

        for (int i=0; i<list.getContent().size(); i++){
            SupportDto dto = new SupportDto(list.getContent().get(i));
            dto.setRowNumber(offset + i + 1);
            resultList.add(dto);
        }

        return new PageResponseDto<>(
                resultList,
                list.getTotalElements(),
                list.getTotalPages(),
                list.getNumber()
        );
    }

    @Override
    public PageResponseDto<SupportDto> findAll(SupportDto supportDto) {
        int currentPage = supportDto.getCurrentPage()-1;
        int pageSize = supportDto.getPageSize();
        String stype = supportDto.getStype();
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Support> list = null;
        if(supportDto.getStype().equals("all")) {
            list = jpaSupportRepository.findAll(pageable);
        } else {
            list = jpaSupportRepository.findByType(stype, pageable);
        }
        List<SupportDto> resultList = new ArrayList<>();
        //entity => Dto, rowNumber 추가
        int offset = pageSize * (currentPage);
        for (int i=0; i<list.getContent().size(); i++){
            SupportDto dto = new SupportDto(list.getContent().get(i));
            dto.setRowNumber(offset + i + 1);
            resultList.add(dto);
        }

        return new PageResponseDto<>(
                resultList,
                list.getTotalElements(),
                list.getTotalPages(),
                list.getNumber()
        );
    }
}
