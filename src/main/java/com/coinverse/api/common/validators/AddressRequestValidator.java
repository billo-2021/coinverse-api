package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Address;
import com.coinverse.api.common.entities.Country;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.AddressRequest;
import com.coinverse.api.common.repositories.CountryRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressRequestValidator implements RequestValidator<AddressRequest, Address> {
    private final CountryRepository countryRepository;

    @Override
    public Address validate(@NotNull final AddressRequest addressRequest) throws InvalidRequestException, MappingException {
        final Country addressCountry = countryRepository.findByCode(addressRequest.getCountryCode())
                .orElseThrow(() ->
                        new ValidationException("Invalid address country '" +
                                addressRequest.getCountryCode() + "'", "address.countryCode")
                );

        return Address.builder()
                .addressLine(addressRequest.getAddressLine())
                .street(addressRequest.getStreet())
                .country(addressCountry)
                .province(addressRequest.getProvince())
                .city(addressRequest.getCity())
                .postalCode(addressRequest.getPostalCode())
                .build();
    }
}
