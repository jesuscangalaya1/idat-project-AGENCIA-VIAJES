package edu.idat.pe.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OriginResponse {
    private Long id;
    private String city;
    private String country;
    private String airport;
}

