package bitespeed.identify_reconcillation.entity;

import bitespeed.identify_reconcillation.enums.ContactEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private ContactEnum linkPrecedence;

    @ManyToOne
    @JoinColumn(name = "linked_id")
    private Contact linkedContact;

    @CreationTimestamp
    private LocalDateTime createdAt;
    public Contact(){}

    public Contact(String phoneNumber, String email, ContactEnum linkPrecedence, Contact linkedContact, LocalDateTime createdAt) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.linkPrecedence = linkPrecedence;
        this.linkedContact = linkedContact;
        this.createdAt = createdAt;
    }
}

