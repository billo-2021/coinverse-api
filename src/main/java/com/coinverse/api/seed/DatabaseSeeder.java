package com.coinverse.api.seed;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.services.CryptoCurrencyKeyGenerator;
import com.coinverse.api.common.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final UserService userService;
    private final AccountStatusRepository accountStatusRepository;
    private final NotificationChannelRepository notificationChannelRepository;
    private final AccountTokenTypeRepository accountTokenTypeRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final CryptoCurrencyKeyGenerator cryptoCurrencyKeyGenerator;

    private final AccountRepository accountRepository;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final WalletRepository walletRepository;
    private final StableCoinRepository stableCoinRepository;
    private final CryptoCurrencyExchangeRateRepository cryptoCurrencyExchangeRateRepository;

    private List<AccountStatus> accountStatuses;
    private List<NotificationChannel> notificationChannels;
    private List<AccountTokenType> accountTokenTypes;
    private List<MessageStatus> messageStatuses;
    private List<Currency> currencies;
    private List<Country> countries;
    private List<Role> roles;

    private List<CryptoCurrency> cryptoCurrencies;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        logger.info("Executing : Database seed start");
        try {
            if (roleRepository.findByAuthority("admin").isPresent()) {
                logger.info("Database already seeded");
                return;
            }

            addAccountStatuses();
            addNotificationChannels();
            addAccountTokenTypes();
            addMessageStatuses();
            addCountries();
            addCurrencies();
            addRoles();
            addCryptoCurrencies();
            addUsers();
            addSystemAccount();
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    private void addAccountStatuses() {
        accountStatuses = new ArrayList<>();
        accountStatuses.addAll(List.of(
                AccountStatus
                        .builder()
                        .name("pending_verification")
                        .build(),
                AccountStatus
                        .builder()
                        .name("verified")
                        .build(),
                AccountStatus
                        .builder()
                        .name("locked")
                        .build(),
                AccountStatus
                        .builder()
                        .name("credentials_expired")
                        .build(),
                AccountStatus
                        .builder()
                        .name("expired")
                        .build(),
                AccountStatus
                        .builder()
                        .name("deleted")
                        .build()
        ));
        accountStatusRepository.saveAll(accountStatuses);
    }

    private AccountStatus getAccountStatusByName(@NotNull final String statusName) {
        return accountStatuses.stream().filter(status ->
                status.getName().equalsIgnoreCase(statusName))
                .findFirst()
                .orElseThrow();
    }

    private void addNotificationChannels() {
        notificationChannels = new ArrayList<>();
        notificationChannels.addAll(List.of(
                NotificationChannel
                        .builder()
                        .name("email")
                        .build(),
                NotificationChannel
                        .builder()
                        .name("sms")
                        .build()
        ));
        notificationChannelRepository.saveAll(notificationChannels);
    }

    private void addAccountTokenTypes() {
        accountTokenTypes = new ArrayList<>();
        accountTokenTypes.addAll(List.of(
                AccountTokenType
                        .builder()
                        .name("verification_token")
                        .build(),
                AccountTokenType
                        .builder()
                        .name("reset_password_link_token")
                        .build()
        ));
        accountTokenTypeRepository.saveAll(accountTokenTypes);
    }

    private void addMessageStatuses() {
        messageStatuses = new ArrayList<>();
        messageStatuses.addAll(List.of(
                MessageStatus.builder()
                        .name("created")
                        .build(),
                MessageStatus.builder()
                        .name("read")
                        .build(),
                MessageStatus.builder()
                        .name("deleted")
                        .build()
        ));
        messageStatusRepository.saveAll(messageStatuses);
    }

    private void addCountries() {
        countries = new ArrayList<>();

        countries.addAll(List.of(
                Country.builder().code("ZAR").name("South Africa").build(),
                Country.builder().code("USA").name("United States of America").build(),
                Country.builder().code("GBR").name("United Kingdom").build(),
                Country.builder().code("JPN").name("Japan").build()
        ));
        countryRepository.saveAll(countries);
    }

    private Country getCountryByCode(@NotNull final String code) {
        return countries.stream()
                .filter(country ->
                        country.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCurrencies() {
        currencies = new ArrayList<>();
        final Country southAfrica = getCountryByCode("zar");

        currencies.addAll(List.of(
                Currency.builder()
                        .code("USD")
                        .name("United States dollar")
                        .symbol("$")
                        .country(southAfrica)
                        .build(),
                Currency.builder()
                        .code("EUR")
                        .name("Euro")
                        .symbol("€")
                        .country(southAfrica)
                        .build(),
                Currency.builder()
                        .code("GBP")
                        .name("Pounds sterling")
                        .symbol("£")
                        .country(southAfrica)
                        .build(),
                Currency.builder()
                        .code("JPY")
                        .name("Japanese yen")
                        .symbol("¥")
                        .country(southAfrica)
                        .build(),
                Currency.builder()
                        .code("ZAR")
                        .name("South african rand")
                        .symbol("R")
                        .country(southAfrica)
                        .build()
        ));
        currencyRepository.saveAll(currencies);
    }

    private Currency getCurrencyByCode(@NotNull final String code) {
        return currencies.stream()
                .filter(currency ->
                        currency.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addRoles() {
        roles = new ArrayList<>();
        roles.addAll(List.of(
                Role.builder()
                        .authority("system")
                        .build(),
                Role.builder()
                        .authority("admin")
                        .build(),
                Role.builder()
                        .authority("customer")
                        .build()
        ));
        roleRepository.saveAll(roles);
    }

    private Role getRoleByName(@NotNull final String roleName) {
        return roles.stream().filter(role -> role.getAuthority().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow();
    }

    private void addCryptoCurrencies() {
        cryptoCurrencies = new ArrayList<>();

        cryptoCurrencies.addAll(List.of(
                CryptoCurrency
                        .builder()
                        .name("Bitcoin")
                        .code("btc")
                        .symbol("btc")
                        .circulatingSupply(19405818L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("Ethereum")
                        .code("eth")
                        .symbol("eth")
                        .circulatingSupply(120210867L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("Tether")
                        .code("usdt")
                        .symbol("usdt")
                        .circulatingSupply(83173876278L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("BNB")
                        .code("bnb")
                        .symbol("bnb")
                        .circulatingSupply(155853355L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("USD Coin")
                        .code("usdc")
                        .symbol("usdc")
                        .circulatingSupply(28344421516L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("XRP")
                        .code("xrp")
                        .symbol("xrp")
                        .circulatingSupply(51987017573L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("Cardano")
                        .code("ada")
                        .symbol("ada")
                        .circulatingSupply(34922156953L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("Dogecoin")
                        .code("doge")
                        .symbol("doge")
                        .circulatingSupply(139821316384L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("TRON")
                        .code("trx")
                        .symbol("trx")
                        .circulatingSupply(90041438209L)
                        .build(),
                CryptoCurrency
                        .builder()
                        .name("Solana")
                        .code("sol")
                        .symbol("sol")
                        .circulatingSupply(399367781L)
                        .build()
        ));

        cryptoCurrencyRepository.saveAll(cryptoCurrencies);
    }

    private void addCurrencies() {

    }

    private void addCryptoCurrencyPairs() {
        List<CryptoCurrencyPair> cryptoCurrencyPairs = List.of(

        );
    }

    private void addCurrencyQuotes() {
        final List<StableCoin> stableCoins
    }

    private void addUsers() {
        final AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("address line")
                .street("street")
                .countryCode("zar")
                .province("province")
                .city("city")
                .postalCode("postal code")
                .build();

        final Set<String> notificationMethodsRequest = Set.of("email", "sms");

        final UserPreferenceRequest userPreferenceRequest =
                UserPreferenceRequest.builder()
                        .currencyCode("zar")
                        .notificationMethods(notificationMethodsRequest)
                        .build();

        final AccountRequest adminAccountRequest = AccountRequest.builder()
                .username("admin@coinverse.com")
                .password("123")
                .roles(Set.of("admin"))
                .build();

        final UserRequest adminUserRequest = UserRequest.builder()
                .emailAddress("admin@coinverse.com")
                .firstName("Admin")
                .lastName("AdminSurname")
                .phoneNumber("0733001026")
                .account(adminAccountRequest)
                .address(addressRequest)
                .preference(userPreferenceRequest)
                .build();

        final AccountRequest customerAccountRequest = AccountRequest.builder()
                .username("customer@coinverse.com")
                .password("123")
                .roles(Set.of("customer"))
                .build();

        final UserRequest customerUserRequest = UserRequest.builder()
                .emailAddress("customer@coinverse.com")
                .firstName("Customer")
                .lastName("CustomerSurname")
                .phoneNumber("0733001026")
                .account(customerAccountRequest)
                .address(addressRequest)
                .preference(userPreferenceRequest)
                .build();

        userService.addUser(adminUserRequest);
        userService.addUser(customerUserRequest);
    }

    @Transactional
    private void addSystemAccount() {
        Role role = getRoleByName("system");

        final String verifiedStatusName = AccountStatusEnum.VERIFIED.getName();
        final AccountStatus accountStatus = getAccountStatusByName(verifiedStatusName);

        Account account = Account.builder()
                .username("system@coinverse.com")
                .password(passwordEncoder.encode("system"))
                .roles(Set.of(role))
                .status(accountStatus)
                .isEnabled(true)
                .loginAttempts(0)
                .build();

        final List<Wallet> wallets = cryptoCurrencies.stream()
                .map((cryptoCurrency -> {
                    final CryptoCurrencyKey currencyKey = cryptoCurrencyKeyGenerator.generate();
                    double randomBalance = ((Math.random() * 900000000) + 10000) / 10.0;

                    return Wallet.
                            builder()
                            .account(account)
                            .currency(cryptoCurrency)
                            .privateKey(currencyKey.getPrivateKey())
                            .publicKey(currencyKey.getPublicKey())
                            .address(currencyKey.getAddress())
                            .balance(BigDecimal.valueOf(randomBalance))
                            .build();
                })).toList();

        accountRepository.save(account);
        walletRepository.saveAll(wallets);
    }
}
