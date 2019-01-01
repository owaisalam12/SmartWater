package com.example.oalam.smartwater;

public class userData {
    String Name;
    String PhoneNumber;
    String DeviceToken;

    public userData(String name, String phoneNumber, String deviceToken) {
        Name = name;
        PhoneNumber = phoneNumber;
        DeviceToken = deviceToken;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }
}
