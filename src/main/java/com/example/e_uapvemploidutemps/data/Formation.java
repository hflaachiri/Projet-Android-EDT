package com.example.e_uapvemploidutemps.data;

public class Formation {

    private String code;
    private String name;
    private String searchString;

    public Formation() {
        super();
    }

    public Formation(String code, String name, String searchString){

        this.code = code;
        this.name = name;
        this.searchString = searchString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", searchString='" + searchString + '\'' +
                '}';
    }
}
