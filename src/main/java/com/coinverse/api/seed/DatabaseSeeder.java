package com.coinverse.api.seed;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.services.WalletKeyGenerator;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.transfer.config.TransferProperties;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final TransferProperties transferProperties;
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
    private final CurrencyTypeRepository currencyTypeRepository;
    private final WalletKeyGenerator walletKeyGenerator;

    private final AccountRepository accountRepository;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final WalletRepository walletRepository;
    private final CurrencyPairTypeRepository currencyPairTypeRepository;
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final CurrencyTransactionActionRepository currencyTransactionActionRepository;
    private final CurrencyTransactionStatusRepository currencyTransactionStatusRepository;
    private final PaymentActionRepository paymentActionRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    private final EventTypeRepository eventTypeRepository;

    private static List<AccountStatus> accountStatuses;
    private static List<NotificationChannel> notificationChannels;
    private static List<AccountTokenType> accountTokenTypes;
    private static List<MessageStatus> messageStatuses;
    private static List<CurrencyType> currencyTypes;
    private static List<Currency> currencies;
    private static List<Country> countries;
    private static List<Role> roles;

    private static List<CryptoCurrency> cryptoCurrencies;
    private static List<CurrencyPairType> currencyPairTypes;
    private static List<CurrencyPair> currencyPairs;
    private static List<CurrencyExchangeRate> currencyExchangeRates;
    private static List<CurrencyTransactionAction> currencyTransactionActions;
    private static List<CurrencyTransactionStatus> currencyTransactionStatuses;
    private static List<PaymentMethod> paymentMethods;
    private static List<PaymentAction> paymentActions;
    private static List<PaymentStatus> paymentStatuses;

    static {
        accountStatuses = new ArrayList<>();
        notificationChannels = new ArrayList<>();
        accountTokenTypes = new ArrayList<>();
        messageStatuses = new ArrayList<>();
        currencyTypes = new ArrayList<>();
        currencies = new ArrayList<>();
        countries = new ArrayList<>();
        roles = new ArrayList<>();

        cryptoCurrencies = new ArrayList<>();
        currencyPairTypes = new ArrayList<>();
        currencyPairs = new ArrayList<>();
        currencyExchangeRates = new ArrayList<>();
        currencyTransactionActions = new ArrayList<>();
        currencyTransactionStatuses = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        paymentActions = new ArrayList<>();
        paymentStatuses = new ArrayList<>();
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        logger.info("Executing : Database seed start");
        try {
            if (roleRepository.findByAuthorityIgnoreCase("admin").isPresent()) {
                logger.info("Database already seeded");
                return;
            }

            addAccountStatuses();
            addNotificationChannels();
            addAccountTokenTypes();
            addMessageStatuses();
            addCountries();
            addCurrencyTypes();
            addCurrencies();
            addCryptoCurrencies();
            addCurrencyPairTypes();
            addCurrencyPairs();
            addCurrencyExchangeRates();
            addCurrencyTransactionActions();
            addCurrencyTransactionStatuses();
            addPaymentMethods();
            addPaymentActions();
            addPaymentStatuses();
            addEventTypes();
            addRoles();
            addUsers();
            addSystemAccount();
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
    }

    private void addAccountStatuses() {
        final AccountStatusEnum[] accountStatusEnums = AccountStatusEnum.values();

        accountStatuses.addAll(Arrays.stream(accountStatusEnums)
                        .map(accountStatusEnum ->
                                AccountStatus
                                        .builder()
                                        .code(accountStatusEnum.getCode())
                                        .name(accountStatusEnum.getName())
                                        .build()
                        ).toList()
        );

        accountStatusRepository.saveAll(accountStatuses);
    }

    private AccountStatus getAccountStatusByName(String statusName) {
        return accountStatuses.stream().filter(status ->
                status.getName().equalsIgnoreCase(statusName))
                .findFirst()
                .orElseThrow();
    }

    private void addNotificationChannels() {
        final NotificationChannelEnum[] notificationChannelEnums = NotificationChannelEnum.values();

        notificationChannels.addAll(Arrays.stream(notificationChannelEnums)
                .map(notificationChannelEnum ->
                        NotificationChannel
                                .builder()
                                .code(notificationChannelEnum.getCode())
                                .name(notificationChannelEnum.getName())
                                .build()
                ).toList()
        );

        notificationChannelRepository.saveAll(notificationChannels);
    }

    private void addAccountTokenTypes() {
        final AccountTokenTypeEnum[] accountTokenTypeEnums = AccountTokenTypeEnum.values();

        accountTokenTypes.addAll(Arrays.stream(accountTokenTypeEnums)
                .map(accountTokenTypeEnum ->
                                AccountTokenType
                                        .builder()
                                        .code(accountTokenTypeEnum.getCode())
                                        .name(accountTokenTypeEnum.getName())
                                        .build()
                        ).toList()
        );

        accountTokenTypeRepository.saveAll(accountTokenTypes);
    }

    private void addMessageStatuses() {
        final MessageStatusEnum[] messageStatusEnums = MessageStatusEnum.values();

        messageStatuses.addAll(Arrays.stream(messageStatusEnums)
                .map(messageStatusEnum ->
                        MessageStatus
                                .builder()
                                .code(messageStatusEnum.getCode())
                                .name(messageStatusEnum.getName())
                                .build()
                ).toList()
        );

        messageStatusRepository.saveAll(messageStatuses);
    }

    private void addCountries() {
        countries.addAll(List.of(
                Country.builder().code("ZAF").name("South Africa").build(),
                Country.builder().code("USA").name("United States of America").build(),
                Country.builder().code("GBR").name("United Kingdom").build(),
                Country.builder().code("JPN").name("Japan").build(),
                Country.builder().code("DEU").name("Germany").build(),
                Country.builder().code("FRA").name("France").build()
        ));
        countryRepository.saveAll(countries);
    }

    private Country getCountryByCode(String code) {
        return countries.stream()
                .filter(country ->
                        country.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCurrencyTypes() {
        CurrencyTypeEnum[] currencyTypeEnums = CurrencyTypeEnum.values();

        currencyTypes.addAll(Arrays.stream(currencyTypeEnums)
                .map(currencyTypeEnum ->
                        CurrencyType
                                .builder()
                                .code(currencyTypeEnum.getCode())
                                .name(currencyTypeEnum.getName())
                                .build()
                ).toList()
        );

        currencyTypeRepository.saveAll(currencyTypes);
    }

    private CurrencyType getCurrencyTypeByCode(String code) {
        return currencyTypes.stream()
                .filter(currencyType ->
                        currencyType.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCurrencies() {
        final CurrencyType fiatCurrency = getCurrencyTypeByCode("fiat");
        final CurrencyType cryptoCurrency = getCurrencyTypeByCode("crypto");

        final Country southAfrica = getCountryByCode("ZAF");
        final Country unitedStates = getCountryByCode("USA");
        final Country unitedKingdom = getCountryByCode("GBR");
        final Country japan = getCountryByCode("JPN");
        final Country germany = getCountryByCode("DEU");
        final Country france = getCountryByCode("FRA");

        currencies.addAll(List.of(
                Currency.builder().type(fiatCurrency).code("USD").name("United States dollar").symbol("$").countries(Set.of(unitedStates)).build(),
                Currency.builder().type(fiatCurrency).code("EUR").name("Euro").symbol("€").countries(Set.of(germany, france)).build(),
                Currency.builder().type(fiatCurrency).code("GBP").name("Pounds sterling").symbol("£").countries(Set.of(unitedKingdom)).build(),
                Currency.builder().type(fiatCurrency).code("JPY").name("Japanese yen").symbol("¥").countries(Set.of(japan)).build(),
                Currency.builder().type(fiatCurrency).code("ZAR").name("South African rand").symbol("R").countries(Set.of(southAfrica)).build(),
                Currency.builder().type(cryptoCurrency).code("BTC").name("Bitcoin").symbol("BTC").build(),
                Currency.builder().type(cryptoCurrency).code("ETH").name("Ethereum").symbol("ETH").build(),
                Currency.builder().type(cryptoCurrency).code("USDT").name("Tether").symbol("USDT").build(),
                Currency.builder().type(cryptoCurrency).code("BNB").name("BNB").symbol("BNB").build(),
                Currency.builder().type(cryptoCurrency).code("USDC").name("USD Coin").symbol("USDC").build(),
                Currency.builder().type(cryptoCurrency).code("XRP").name("XRP").symbol("XRP").build(),
                Currency.builder().type(cryptoCurrency).code("ADA").name("Cardano").symbol("ADA").build(),
                Currency.builder().type(cryptoCurrency).code("DOGE").name("Dogecoin").symbol("DOGE").build(),
                Currency.builder().type(cryptoCurrency).code("TRX").name("TRON").symbol("TRX").build(),
                Currency.builder().type(cryptoCurrency).code("SOL").name("Solana").symbol("SOL").build()
        ));
        currencyRepository.saveAll(currencies);
    }

    private Currency getCurrencyByCode(String code) {
        return currencies.stream()
                .filter(currency ->
                        currency.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCryptoCurrencies() {
        cryptoCurrencies.addAll(List.of(
                CryptoCurrency.builder().currency(getCurrencyByCode("BTC")).circulatingSupply(19405818L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("ETH")).circulatingSupply(120210867L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("USDT")).circulatingSupply(83173876278L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("BNB")).circulatingSupply(155853355L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("USDC")).circulatingSupply(28344421516L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("XRP")).circulatingSupply(51987017573L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("ADA")).circulatingSupply(34922156953L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("DOGE")).circulatingSupply(139821316384L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("TRX")).circulatingSupply(90041438209L).build(),
                CryptoCurrency.builder().currency(getCurrencyByCode("SOL")).circulatingSupply(399367781L).build()
        ));

        cryptoCurrencyRepository.saveAll(cryptoCurrencies);
    }

    private void addCurrencyPairTypes() {
        final CurrencyPairTypeEnum[] currencyPairTypeEnums = CurrencyPairTypeEnum.values();

        currencyPairTypes.addAll(Arrays.stream(currencyPairTypeEnums)
                .map(currencyPairTypeEnum ->
                        CurrencyPairType
                                .builder()
                                .code(currencyPairTypeEnum.getCode())
                                .name(currencyPairTypeEnum.getName())
                                .build()
                ).toList()
        );

        currencyPairTypeRepository.saveAll(currencyPairTypes);
    }

    private CurrencyPairType getCurrencyPairTypeByCode(String code) {
        return currencyPairTypes.stream()
                .filter(currencyPairType ->
                        currencyPairType.getCode().equals(code)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCurrencyPairs() {
        final CurrencyPairType forexCurrencyPairType = getCurrencyPairTypeByCode("forex");
        final CurrencyPairType cryptoCurrencyPairType = getCurrencyPairTypeByCode("crypto");

        final Currency mainCurrency = getCurrencyByCode("USD");

        currencyPairs.addAll(List.of(
                CurrencyPair.builder().type(forexCurrencyPairType).name("EUR/USD").baseCurrency(getCurrencyByCode("EUR"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(forexCurrencyPairType).name("USD/JPY").baseCurrency(mainCurrency)
                        .quoteCurrency(getCurrencyByCode("JPY")).build(),
                CurrencyPair.builder().type(forexCurrencyPairType).name("GBP/USD").baseCurrency(getCurrencyByCode("GBP"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(forexCurrencyPairType).name("USD/ZAR").baseCurrency(mainCurrency)
                        .quoteCurrency(getCurrencyByCode("ZAR")).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("BTC/USD").baseCurrency(getCurrencyByCode("BTC"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("ETH/USD").baseCurrency(getCurrencyByCode("ETH"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("USDT/USD").baseCurrency(getCurrencyByCode("USDT"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("BNB/USD").baseCurrency(getCurrencyByCode("BNB"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("USDC/USD").baseCurrency(getCurrencyByCode("USDC"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("XRP/USD").baseCurrency(getCurrencyByCode("XRP"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("ADA/USD").baseCurrency(getCurrencyByCode("ADA"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("DOGE/USD").baseCurrency(getCurrencyByCode("DOGE"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("TRX/USD").baseCurrency(getCurrencyByCode("TRX"))
                        .quoteCurrency(mainCurrency).build(),
                CurrencyPair.builder().type(cryptoCurrencyPairType).name("SOL/USD").baseCurrency(getCurrencyByCode("SOL"))
                        .quoteCurrency(mainCurrency).build()
        ));

        currencyPairRepository.saveAll(currencyPairs);
    }

    private CurrencyPair getCurrencyPairByName(String name) {
        return currencyPairs.stream()
                .filter(currencyPair ->
                        currencyPair.getName().equals(name)
                )
                .findFirst()
                .orElseThrow();
    }

    private void addCurrencyExchangeRates() {
        final Integer timeToLive = 3600000;

        currencyExchangeRates.addAll(List.of(
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("EUR/USD"))
                        .bidRate(BigDecimal.valueOf(1.08906)).askRate(BigDecimal.valueOf(1.08914)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("USD/JPY"))
                        .bidRate(BigDecimal.valueOf(143.786)).askRate(BigDecimal.valueOf(143.796)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("GBP/USD"))
                        .bidRate(BigDecimal.valueOf(1.27178)).askRate(BigDecimal.valueOf(1.27191)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("USD/ZAR"))
                        .bidRate(BigDecimal.valueOf(18.7373)).askRate(BigDecimal.valueOf(18.7458)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("BTC/USD"))
                        .bidRate(BigDecimal.valueOf(30726.07)).askRate(BigDecimal.valueOf(30816.28)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("ETH/USD"))
                        .bidRate(BigDecimal.valueOf(1894.33)).askRate(BigDecimal.valueOf(1899.57)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("USDT/USD"))
                        .bidRate(BigDecimal.valueOf(1.0008)).askRate(BigDecimal.valueOf(1.0008)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("BNB/USD"))
                        .bidRate(BigDecimal.valueOf(245.70)).askRate(BigDecimal.valueOf(245.80)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("USDC/USD"))
                        .bidRate(BigDecimal.valueOf(0.9995)).askRate(BigDecimal.valueOf(0.9996)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("XRP/USD"))
                        .bidRate(BigDecimal.valueOf(0.49342)).askRate(BigDecimal.valueOf(0.49343)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("ADA/USD"))
                        .bidRate(BigDecimal.valueOf(0.29405)).askRate(BigDecimal.valueOf(0.29796)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("DOGE/USD"))
                        .bidRate(BigDecimal.valueOf(0.067980)).askRate(BigDecimal.valueOf(0.067990)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("TRX/USD"))
                        .bidRate(BigDecimal.valueOf(0.07262)).askRate(BigDecimal.valueOf(0.07263)).timeToLive(timeToLive).build(),
                CurrencyExchangeRate.builder().currencyPair(getCurrencyPairByName("SOL/USD"))
                        .bidRate(BigDecimal.valueOf(17.32)).askRate(BigDecimal.valueOf(17.63)).timeToLive(timeToLive).build()
        ));

        currencyExchangeRateRepository.saveAll(currencyExchangeRates);
    }

    private void addCurrencyTransactionActions() {
        final CurrencyTransactionActionEnum[] currencyTransactionActionEnums = CurrencyTransactionActionEnum.values();

        currencyTransactionActions.addAll(Arrays.stream(currencyTransactionActionEnums)
                .map(currencyTransactionActionEnum ->
                        CurrencyTransactionAction
                                .builder()
                                .code(currencyTransactionActionEnum.getCode())
                                .name(currencyTransactionActionEnum.getName())
                                .build()
                ).toList()
        );

        currencyTransactionActionRepository.saveAll(currencyTransactionActions);
    }

    private void addCurrencyTransactionStatuses() {
        final CurrencyTransactionStatusEnum[] currencyTransactionStatusEnums = CurrencyTransactionStatusEnum.values();

        currencyTransactionStatuses.addAll(Arrays.stream(currencyTransactionStatusEnums)
                .map(currencyTransactionStatusEnum ->
                        CurrencyTransactionStatus
                                .builder()
                                .code(currencyTransactionStatusEnum.getCode())
                                .name(currencyTransactionStatusEnum.getName())
                                .build()
                ).toList()
        );

        currencyTransactionStatusRepository.saveAll(currencyTransactionStatuses);
    }

    private void addPaymentMethods() {
        final PaymentMethodEnum[] paymentMethodEnums = PaymentMethodEnum.values();

        paymentMethods.addAll(Arrays.stream(paymentMethodEnums)
                .map(paymentMethodEnum ->
                        PaymentMethod
                                .builder()
                                .code(paymentMethodEnum.getCode())
                                .name(paymentMethodEnum.getName())
                                .build()
                ).toList()
        );

        paymentMethodRepository.saveAll(paymentMethods);
    }

    private void addPaymentActions() {
        final PaymentActionEnum[] paymentActionEnums = PaymentActionEnum.values();

        paymentActions.addAll(Arrays.stream(paymentActionEnums)
                .map(paymentActionEnum ->
                        PaymentAction
                                .builder()
                                .code(paymentActionEnum.getCode())
                                .name(paymentActionEnum.getName())
                                .build()
                ).toList()
        );

        paymentActionRepository.saveAll(paymentActions);
    }

    private void addPaymentStatuses() {
        final PaymentStatusEnum[] paymentStatusEnums = PaymentStatusEnum.values();

        paymentStatuses.addAll(Arrays.stream(paymentStatusEnums)
                .map(paymentStatusEnum ->
                        PaymentStatus
                                .builder()
                                .code(paymentStatusEnum.getCode())
                                .name(paymentStatusEnum.getName())
                                .build()
                ).toList()
        );

        paymentStatusRepository.saveAll(paymentStatuses);
    }

    private void addEventTypes() {
        final UserAccountEventTypeEnum[] userAccountEventTypeEnums = UserAccountEventTypeEnum.values();

        List<EventType> eventTypes = Arrays.stream(userAccountEventTypeEnums)
                .map(userAccountEventTypeEnum ->
                        EventType
                                .builder()
                                .code(userAccountEventTypeEnum.getCode())
                                .name(userAccountEventTypeEnum.getName())
                                .build()
                ).toList();

        eventTypeRepository.saveAll(eventTypes);
    }

    private void addRoles() {
        final RoleEnum[] roleEnums = RoleEnum.values();

        roles.addAll(Arrays.stream(roleEnums)
                .map(roleEnum ->
                        Role
                                .builder()
                                .authority(roleEnum.getAuthority())
                                .name(roleEnum.getName())
                                .build()
                ).toList()
        );

        roleRepository.saveAll(roles);
    }

    private Role getRoleByAuthority(String authority) {
        return roles.stream().filter(role -> role.getAuthority().equalsIgnoreCase(authority))
                .findFirst()
                .orElseThrow();
    }

    private void addUsers() {
        final Role customerRole = getRoleByAuthority("customer");
        final Role adminRole = getRoleByAuthority("admin");
        final Country zafCountry = getCountryByCode("ZAF");
        final Currency zarCurrency = getCurrencyByCode("ZAR");
        final NotificationMethodEnum emailNotificationMethodEnum = NotificationMethodEnum.EMAIL;
        final NotificationMethodEnum smsNotificationMethodEnum = NotificationMethodEnum.SMS;


        final AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("address line").street("street").countryCode(zafCountry.getCode()).province("province")
                .city("city").postalCode("postal code").build();

        final Set<String> notificationMethodsRequest = Set.of(emailNotificationMethodEnum.getCode(),
                smsNotificationMethodEnum.getCode());

        final UserPreferenceRequest userPreferenceRequest =
                UserPreferenceRequest.builder().currencyCode(zarCurrency.getCode()).notificationMethods(notificationMethodsRequest)
                        .build();

        final AccountRequest adminAccountRequest = AccountRequest.builder()
                .username("admin@coinverse.com").password("123").roles(Set.of(adminRole.getAuthority())).build();

        final UserRequest adminUserRequest = UserRequest.builder()
                .emailAddress("admin@coinverse.com").firstName("Admin").lastName("AdminSurname").phoneNumber("0733001026")
                .account(adminAccountRequest).address(addressRequest).preference(userPreferenceRequest)
                .build();

        final AccountRequest customerAccountRequest = AccountRequest.builder()
                .username("customer@coinverse.com").password("123").roles(Set.of(customerRole.getAuthority())).build();

        final UserRequest customerUserRequest = UserRequest.builder()
                .emailAddress("customer@coinverse.com").firstName("Customer").lastName("CustomerSurname")
                .phoneNumber("0733001026").account(customerAccountRequest).address(addressRequest)
                .preference(userPreferenceRequest).build();

        userService.addUser(adminUserRequest);
        userService.addUser(customerUserRequest);
    }

    @Transactional
    private void addSystemAccount() {
        Role systemRole = getRoleByAuthority("system");

        final String verifiedStatusName = AccountStatusEnum.VERIFIED.getName();
        final AccountStatus accountStatus = getAccountStatusByName(verifiedStatusName);

        Account account = Account.builder()
                .username(transferProperties.systemUsername()).password(passwordEncoder.encode(transferProperties.systemPassword()))
                .roles(Set.of(systemRole)).status(accountStatus).isEnabled(true).loginAttempts(0)
                .build();

        final List<Wallet> wallets = currencies.stream()
                .map((currency -> {
                    final WalletKey walletKey = walletKeyGenerator.generate();
                    double randomBalance = ((Math.random() * 900000000) + 10000) / 10.0;

                    return Wallet.
                            builder()
                            .account(account)
                            .currency(currency)
                            .privateKey(walletKey.getPrivateKey())
                            .publicKey(walletKey.getPublicKey())
                            .address(walletKey.getAddress())
                            .balance(BigDecimal.valueOf(randomBalance))
                            .build();
                })).toList();

        accountRepository.save(account);
        walletRepository.saveAll(wallets);
    }
}
