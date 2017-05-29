package com.ecommerce.controller;

import com.ecommerce.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ImageController {
    @Autowired
    public ImageDao imageDao;
//TODO handle conversion errors
    @RequestMapping(value="/admin/deleteImage/{id}",method= RequestMethod.POST)
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id){
        imageDao.removeImageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
