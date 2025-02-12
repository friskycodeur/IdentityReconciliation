package bitespeed.identify_reconcillation.controller;

import bitespeed.identify_reconcillation.dao.ContactRequest;
import bitespeed.identify_reconcillation.dao.ContactResponse;
import bitespeed.identify_reconcillation.entity.Contact;
import bitespeed.identify_reconcillation.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping(value = "/identify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactResponse> identifyContacts(@RequestBody ContactRequest request){
        if(request == null || request.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one of email or phoneNumber is required.");
            //throw new IllegalArgumentException("At least one of email or phoneNumber is required.");
        }
        ContactResponse response =  contactService.identifyConsolidatedContacts(request.getEmail(), request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> fetchContacts(){
        return contactService.fetchContacts();
    }

}
