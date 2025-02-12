package bitespeed.identify_reconcillation.repository;

import bitespeed.identify_reconcillation.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByEmailOrPhoneNumber(String email, String phoneNumber);
}
