package bitespeed.identify_reconcillation.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ContactResponse {
    int primaryContatctId;
    List<String> emails;
    List<String> phoneNumbers;
    List<Integer> secondaryContactIds;
}
