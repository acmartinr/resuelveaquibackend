package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Color;
import com.ecommerce.ecommerce.Repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public Color create(Color color){return colorRepository.save(color); }

    public List<Color> getAllColors(){
        return colorRepository.findAll();
    }

    public Color update(Long id, Color color){
        Optional<Color> entity=colorRepository.findById(id);
        Color p=entity.get();
        p=colorRepository.save(color);
        return p;
    }

    public void delete(Long id){
        colorRepository.deleteById(id);
    }

    public Optional<Color> findByID(Long id){
        return colorRepository.findById(id);
    }
}


