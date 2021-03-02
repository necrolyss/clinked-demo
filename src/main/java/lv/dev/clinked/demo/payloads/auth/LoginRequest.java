package lv.dev.clinked.demo.payloads.auth;

import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String email, @NotBlank String password) {

}
