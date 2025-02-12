package bitespeed.identify_reconcillation.dao;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactRequest {
    private String email;
    private String phoneNumber;

    public boolean isEmpty(){
        return (this.getEmail() == null && this.getPhoneNumber() == null);
    }
}
