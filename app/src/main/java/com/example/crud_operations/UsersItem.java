

package com.example.crud_operations;

public class UsersItem {

    String userID;
    String userName;
    String userDes;
    String pDate;

    public UsersItem() {
    }

    public UsersItem(String userID, String userName, String userDes, String pDate) {
        this.userID = userID;
        this.userName = userName;
        this.userDes = userDes;
        this.pDate = pDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
    }

    public String getUserDate() {
        return pDate;
    }

    public void setUserDate(String pDate) {
        this.pDate = pDate;
    }


}