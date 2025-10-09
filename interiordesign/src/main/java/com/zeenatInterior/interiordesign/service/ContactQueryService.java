// ContactQueryService.java
package com.zeenatInterior.interiordesign.service;

import com.zeenatInterior.interiordesign.entity.ContactQuery;
import com.zeenatInterior.interiordesign.repository.ContactQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactQueryService {

    @Autowired
    private ContactQueryRepository contactQueryRepository;

    public List<ContactQuery> getAllQueries() {
        return contactQueryRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteQuery(Long id) {
        contactQueryRepository.deleteById(id);
    }

    public ContactQuery saveQuery(ContactQuery query) {
        return contactQueryRepository.save(query);
    }
}