package at.spengergasse.FotoAlbum.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static at.spengergasse.FotoAlbum.foundation.Guard.ensureNotNull;

@Data
@NoArgsConstructor

@Entity
@Table(name = "photographers")
public class Photographer extends User {

    public static final int LENGTH_EMAIL_ADDRESS = 64;

    @Embedded
    private @Valid Address studioAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "mobile_country_code", nullable = false)),
            @AttributeOverride(name = "areaCode", column = @Column(name = "mobile_area_code", nullable = false)),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "mobile_serial_number", nullable = false, length = PhoneNumber.LENGTH_SERIAL_NUMBER))
    })
    private @NotNull
    @Valid PhoneNumber mobilePhoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "studio_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "studio_area_code")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "studio_serial_number", length = PhoneNumber.LENGTH_SERIAL_NUMBER))
    })
    private @Valid PhoneNumber officePhoneNumber;

    @Setter(AccessLevel.PRIVATE)
    @ElementCollection
    @JoinTable(name = "photographer_emails", foreignKey = @ForeignKey(name = "FK_photographer_emails_2_photographers"))
    @Column(name = "email", nullable = false, length = LENGTH_EMAIL_ADDRESS)
    private Set<EmailAddress> emailAddresses;

    @Builder
    public Photographer(EmailAddress username, String password, String firstName, String lastName,
                        Address studioAddress, PhoneNumber mobilePhoneNumber, Set<EmailAddress> emailAddresses) {
        super(username, password, firstName, lastName);
        this.studioAddress = studioAddress;
        this.mobilePhoneNumber = ensureNotNull(mobilePhoneNumber);
        this.emailAddresses = (emailAddresses != null) ? new HashSet<>(emailAddresses) : new HashSet<>(3);
    }

    public Set<EmailAddress> getEmailAddresses() {
        return Collections.unmodifiableSet(emailAddresses);
    }

    public Photographer addEmails(EmailAddress... emailAddresses) {
        Arrays.stream(emailAddresses).forEach(this::addEmail);
        return this;
    }

    public Photographer addEmail(EmailAddress emailAddress) {
        emailAddresses.add(emailAddress);
        return this;
    }

    public Photographer clearMails() {
        emailAddresses.clear();
        return this;
    }

    public Photographer removeEmail(EmailAddress emailAddress) {
        emailAddresses.remove(emailAddress);
        return this;
    }
}
