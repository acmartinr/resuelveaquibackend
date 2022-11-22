package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Faq;
import com.ecommerce.ecommerce.Repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Faq create(Faq faq) {
        return faqRepository.save(faq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Faq update(Long id, Faq faq) {
        faq.setId(id);
        return faqRepository.save(faq);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        faqRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Faq> findById(Long id) {
      return faqRepository.findById(id);
    }


    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }
}
