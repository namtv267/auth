package com.shopwebapp.auth.payload.request;

import com.shopwebapp.auth.model.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^0\\d{9}$")
    private String phone;

    private String alias;

    private Set<String> roles = Set.of(ERole.ROLE_USER.toString());
}
