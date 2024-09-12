package at.spengergasse.FotoAlbum;



import at.spengergasse.FotoAlbum.domain.*;

import java.time.LocalDateTime;

public class TestFixtures {

    private static Country austria;

    public static Country austria() {
        // TODO - fix detached entity persist error
        // if (austria == null) {
        austria = Country.builder()
                         .name("Austria")
                         .iso2Code("AT")
                         .topLevelDomain(".at")
                         .countryCode(43)
                         .build();
        // }

        return austria;
    }

    public static PhoneNumber phoneNumber(String serialNumber) {
        return phoneNumber(43, 1, serialNumber);
    }

    public static PhoneNumber phoneNumber(int areaCode, String serialNumber) {
        return phoneNumber(43, areaCode, serialNumber);
    }

    public static PhoneNumber phoneNumber(int countryCode, int areaCode, String serialNumber) {
        return PhoneNumber.builder()
                          .countryCode(countryCode)
                          .areaCode(areaCode)
                          .serialNumber(serialNumber)
                          .build();
    }

    public static Location spengergasse() {
        return Location.builder().longitude(16.0d).latitude(48.0d).build();
    }

    public static Location location(double longitude, double latitude) {
        return Location.builder().longitude(longitude).latitude(latitude).build();
    }

    public static Address address(String streetNumber) {
        return address(streetNumber, "1050", "Vienna", austria());
    }

    public static Address address(String streetNumber, String zipCode, String city, Country country) {
        return Address.builder()
                      .streetNumber(streetNumber)
                      .zipCode(zipCode)
                      .city(city)
                      .country(country)
                      .build();
    }

    public static Person uk() {
        return Person.builder()
                     .username(new EmailAddress("uk@spg.at"))
                     .password("!SehrGeheim2024")
                     .firstName("Klaus")
                     .lastName("Unger")
                     .nickName("uk")
                     .key("key")
                     .build();
    }

    public static Person ac() {
        return Person.builder()
                     .username(new EmailAddress("ac@spg.at"))
                     .password("!SehrGeheim2024")
                     .firstName("Andreas")
                     .lastName("Chwatal")
                     .nickName("ac")
                     .build();
    }

    public static Person person(String firstName, String lastName) {
        return person(firstName, lastName, "!SehrGeheim2024");
    }

    public static Person person(String firstName, String lastName, String password) {
        return Person.builder()
                     .username(new EmailAddress("%s.%s@spg.at".formatted(firstName, lastName)))
                     .password(password)
                     .firstName(firstName)
                     .lastName(lastName)
                     .nickName("%s%s".formatted(firstName.charAt(0), lastName.charAt(0)))
                     .build();
    }

    public static Photographer acPhotographer() {
        return Photographer.builder()
                           .firstName("Andreas")
                           .lastName("Chwatal")
                           .username(new EmailAddress("ac@spg.at"))
                           .password("!SehrGeheim2024")
                           .studioAddress(address("Spengergasse 20"))
                           .mobilePhoneNumber(phoneNumber("12341234"))
                           .build();
    }

    public static Photo photo(String name) {
        return photo(name, 640, 480, spengergasse(), acPhotographer());
    }

    public static Photo photo(String name, Integer width, Integer height) {
        return photo(name, width, height, spengergasse(), acPhotographer());
    }

    public static Photo photo(String name, Integer width, Integer height, Location location, Photographer photographer) {
        return Photo.builder()
                    .name(name)
                    .location(location)
                    .creationTimeStamp(LocalDateTime.now())
                    .width(width)
                    .height(height)
                    .orientation((width > height) ? Orientation.LANDSCAPE : (width < height) ? Orientation.PORTRAIT : Orientation.SQUARE)
                    .photographer(photographer)
                    .build();
    }

    public static Album album(String name) {
        return album(name, acPhotographer());
    }

    public static Album album(String name, Photographer photographer) {
        return Album.builder()
                    .name(name)
                    .type(AlbumType.DIGITAL)
                    .owner(photographer)
                    .build();
    }
}
