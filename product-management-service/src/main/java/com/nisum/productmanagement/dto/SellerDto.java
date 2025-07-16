package com.nisum.productmanagement.dto;

public class SellerDto {
    private Long id;
    private String sellerName;
    private String contactName;
    private String email;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String address; // Combined address field for backward compatibility

    public SellerDto() {}

    public SellerDto(Long id, String sellerName, String contactName, String email, String phoneNumber, String addressLine1, String addressLine2, String city, String state, String zipCode, String country) {
        this.id = id;
        this.sellerName = sellerName;
        this.contactName = contactName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        // Set combined address for backward compatibility
        this.address = combineAddressFields();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Helper method to combine address fields
    private String combineAddressFields() {
        StringBuilder combinedAddress = new StringBuilder();
        if (addressLine1 != null && !addressLine1.isEmpty()) {
            combinedAddress.append(addressLine1);
        }
        if (addressLine2 != null && !addressLine2.isEmpty()) {
            if (combinedAddress.length() > 0) combinedAddress.append(", ");
            combinedAddress.append(addressLine2);
        }
        if (city != null && !city.isEmpty()) {
            if (combinedAddress.length() > 0) combinedAddress.append(", ");
            combinedAddress.append(city);
        }
        if (state != null && !state.isEmpty()) {
            if (combinedAddress.length() > 0) combinedAddress.append(", ");
            combinedAddress.append(state);
        }
        if (zipCode != null && !zipCode.isEmpty()) {
            if (combinedAddress.length() > 0) combinedAddress.append(" ");
            combinedAddress.append(zipCode);
        }
        if (country != null && !country.isEmpty()) {
            if (combinedAddress.length() > 0) combinedAddress.append(", ");
            combinedAddress.append(country);
        }
        return combinedAddress.toString();
    }

    @Override
    public String toString() {
        return "SellerDto{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
