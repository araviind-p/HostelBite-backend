package com.hostelbite.hostelbite_backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    private String name;
    private String email;
}
