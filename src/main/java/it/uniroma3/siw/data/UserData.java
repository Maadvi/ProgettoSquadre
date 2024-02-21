package it.uniroma3.siw.data;

import lombok.Data;


@Data
public class UserData {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Boolean flgPresidente;

    private PresidenteData presidenteData;
}
