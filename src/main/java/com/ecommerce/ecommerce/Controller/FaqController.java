package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.Faq;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.FaqService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/faq")
public class FaqController {
    @Autowired
    FaqService faqService;

    @GetMapping("/all")
    public ResponseEntity<List<Faq>> allFaqs() {
        List<Faq> response = faqService.getAllFaqs();
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @RequestMapping(value = "/createFaq")
    private ResponseEntity<Faq> create(@RequestBody Faq user) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(faqService.create(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Faq user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(faqService.update(id, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(faqService.findById(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            faqService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
