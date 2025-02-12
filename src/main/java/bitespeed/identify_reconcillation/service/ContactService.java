package bitespeed.identify_reconcillation.service;

import bitespeed.identify_reconcillation.dao.ContactResponse;
import bitespeed.identify_reconcillation.entity.Contact;
import bitespeed.identify_reconcillation.enums.ContactEnum;
import bitespeed.identify_reconcillation.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public ContactResponse identifyConsolidatedContacts(String email, String number){

        List<Contact> matchingContacts = contactRepository.findAllByEmailOrPhoneNumber(email, number);
        boolean exactMatch = true;

        if(matchingContacts.isEmpty()){
            return formatResponse(createPrimaryContact(email, number));
        }


        Contact primaryContact = matchingContacts.stream().filter(c -> c.getLinkPrecedence() == ContactEnum.PRIMARY).
                min(Comparator.comparing(Contact::getCreatedAt)).orElse(matchingContacts.get(0));

        if(!primaryContact.getEmail().equals(email) || !primaryContact.getPhoneNumber().equals(number)){
            exactMatch = false;
        }

        List<Contact> secondaryContacts = matchingContacts.stream().
                filter(c -> c.getId() != primaryContact.getId()  && c.getLinkPrecedence() == ContactEnum.SECONDARY).
                collect(Collectors.toList());


        for(Contact contact: matchingContacts){
            if(contact.getId() != primaryContact.getId() && contact.getLinkPrecedence() == ContactEnum.PRIMARY){
                contact.setLinkedContact(primaryContact);
                contact.setLinkPrecedence(ContactEnum.SECONDARY);
                contactRepository.save(contact);
                secondaryContacts.add(contact);
            }
        }

        if(!exactMatch){
            secondaryContacts.add(createSecondaryContact(email, number, primaryContact));
        }

        return formatResponse(primaryContact);
    }

    public Contact createPrimaryContact(String email, String phoneNumber){
        Contact newContact = new Contact(phoneNumber, email, ContactEnum.PRIMARY, null, LocalDateTime.now());
        contactRepository.save(newContact);
        return newContact;
    }

    public Contact createSecondaryContact(String email, String phoneNumber, Contact linkedContact){
        Contact newContact = new Contact(phoneNumber, email, ContactEnum.SECONDARY, linkedContact, LocalDateTime.now());
        contactRepository.save(newContact);
        return newContact;
    }

    public ContactResponse formatResponse(Contact primaryContact){
        List<Contact> linkedContacts = contactRepository.findAllByEmailOrPhoneNumber(primaryContact.getEmail(), primaryContact.getPhoneNumber());

        Set<String> emails = new LinkedHashSet<>();
        Set<String> phoneNumbers = new LinkedHashSet<>();
        List<Integer> secondaryContactIds = new ArrayList<>();

        emails.add(primaryContact.getEmail());
        phoneNumbers.add(primaryContact.getPhoneNumber());

        for(Contact contact: linkedContacts){
            if (contact.getEmail() != null && !contact.getEmail().equals(primaryContact.getEmail())) {
                emails.add(contact.getEmail());
            }
            if (contact.getPhoneNumber() != null && !contact.getPhoneNumber().equals(primaryContact.getPhoneNumber())) {
                phoneNumbers.add(contact.getPhoneNumber());
            }
            if (contact.getLinkPrecedence() == ContactEnum.SECONDARY) {
                secondaryContactIds.add(contact.getId());
            }
        }

        return new ContactResponse(primaryContact.getId(), new ArrayList<>(emails), new ArrayList<>(phoneNumbers), secondaryContactIds);
    }

    public List<Contact> fetchContacts(){
        return contactRepository.findAll();
    }
}
