package bitespeed.identify_reconcillation.entity;

import bitespeed.identify_reconcillation.entity.Contact;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "linked_contact")
public class LinkedContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    private Contact primaryContact;

    @Getter
    @ManyToOne
    private Contact linkedContact;

}
