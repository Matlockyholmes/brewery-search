package digital.formica.training.elastic.brewery.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Brewery {
    private String name;
    private String street;
    private String houseNumber;
    private String boxNumber;
    private String postalCode;
    private String city;
    private String province;
    private String description;
    private String website;

    protected Brewery() {
    }

    public Brewery(String name, String street, String houseNumber, String boxNumber, String postalCode, String city, String province, String description, String website) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.boxNumber = boxNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
        this.description = description;
        this.website = website;
    }
    @JsonGetter("Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonGetter("street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    @JsonGetter("house_number")
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @JsonGetter("box_number")
    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }
    @JsonGetter("postal_ code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    @JsonGetter("city_name")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @JsonGetter("province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    @JsonGetter("description_nl")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonGetter("Website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Brewery{" +
                "\n" + "name='" + name + '\'' +
                "\n" + "street='" + street + '\'' +
                "\n" + "houseNumber='" + houseNumber + '\'' +
                "\n" + "boxNumber='" + boxNumber + '\'' +
                "\n" + "postalCode='" + postalCode + '\'' +
                "\n" + "city='" + city + '\'' +
                "\n" + "province='" + province + '\'' +
                "\n" + "description='" + description + '\'' +
                "\n" + "website='" + website + '\'' +
                '}';
    }
}
