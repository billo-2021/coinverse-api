package com.coinverse.api.features.authentication.models;

import com.coinverse.api.common.validators.EnumValidator;
import com.coinverse.api.features.messaging.models.MessagingChannel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({
        "username", "messagingChannel"
})
public class TokenRequest {
    @NotBlank(message = "username is required")
    @Email(message = "Invalid email")
    private String username;

    @EnumValidator(message = "messagingChannel is required", target = MessagingChannel.class)
    private String messagingChannel;
}
