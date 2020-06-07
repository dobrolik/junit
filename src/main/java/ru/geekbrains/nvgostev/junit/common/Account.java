package ru.geekbrains.nvgostev.junit.common;

public class Account {

    private String firstName;
    private String lastName;
    private String addrFirstName;
    private String addrLastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private String stateId;
    private String zip;
    private String countryId;
    private String mobilePhone;
    private String addressAlias;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddrFirstName() {
        return addrFirstName;
    }

    public String getAddrLastName() {
        return addrLastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getStateId() {
        return stateId;
    }

    public String getZip() {
        return zip;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getAddressAlias() {
        return addressAlias;
    }

    public Account(String firstName, String lastName, String email, String password, String address, String city, int stateId, String zip, int countryId, String mobilePhone, String addressAlias) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addrFirstName = firstName;
        this.addrLastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.stateId = Integer.toString(stateId);
        this.zip = zip;
        this.countryId = Integer.toString(countryId);
        this.mobilePhone = mobilePhone;
        this.addressAlias = addressAlias;
    }

    @Override
    public String toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setField(Field f, String text) {
        switch (f) {
            case FIRST_NAME:
                firstName = text;
                break;
            case LAST_NAME:
                lastName = text;
                break;
            case EMAIL:
                email = text;
                break;
            case PASSWD:
                password = text;
                break;
            case ADDR_FIRST_NAME:
                addrFirstName = text;
                break;
            case ADDR_LAST_NAME:
                addrLastName = text;
                break;
            case ADDRESS:
                address = text;
                break;
            case CITY:
                city = text;
                break;
            case STATE:
                stateId = text;
                break;
            case ZIP:
                zip = text;
                break;
            case COUNTRY:
                countryId = text;
                break;
            case PHONE:
                mobilePhone = text;
                break;
            case ADDR_ALIAS:
                addressAlias = text;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + f);
        }
    }

    public enum Field {
        FIRST_NAME,
        LAST_NAME,
        EMAIL,
        PASSWD,
        ADDR_FIRST_NAME,
        ADDR_LAST_NAME,
        ADDRESS,
        CITY,
        STATE,
        ZIP,
        COUNTRY,
        PHONE,
        ADDR_ALIAS
    }
}
