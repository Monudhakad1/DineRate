package com.dinerate.elastic.dinerate_backened.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class UserDto {
    private String id;

    private String username;

    private String firstName;

    private String lastName;

}
