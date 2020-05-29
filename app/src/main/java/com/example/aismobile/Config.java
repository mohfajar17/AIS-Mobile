package com.example.aismobile;

public class Config {
//    public static final String DATA_URL = "http://192.168.43.119/ais_web_service/";   //IP thetering hp
    public static final String DATA_URL = "http://192.168.1.24/ais_web_service/";   //IP asuka lt 3
//    public static final String DATA_URL = "https://ais.asukaindonesia.co.id/mobile/";   //IP server
    public static final String DATA_URL_LOGIN = DATA_URL+"apiAis.php?apicall=login";
    public static final String DATA_URL_EMPLOYEE_DATA = DATA_URL+"apiAis.php?apicall=employeeData";
    public static final String DATA_URL_EMPLOYEE_LEAVE = DATA_URL+"apiAis.php?apicall=employeeLeave";
    public static final String DATA_URL_EMPLOYEE_MONEYBOX = DATA_URL+"apiAis.php?apicall=employeeMoneybox";
    public static final String DATA_URL_EDIT_ACCOUNT = DATA_URL+"apiAis.php?apicall=editAccount";
    public static final String DATA_URL_EDIT_PERSONAL = DATA_URL+"apiAis.php?apicall=editPersonalData";
    public static final String DATA_URL_LIST_SALES_QUOTATION = DATA_URL+"apiAis.php?apicall=listSalesQuotation";
    public static final String DATA_URL_LIST_EMPLOYEE = DATA_URL+"apiAis.php?apicall=listEmployee";
    public static final String DATA_URL_LIST_WORKBASE = DATA_URL+"apiAis.php?apicall=listWorkbase";
    public static final String DATA_URL_LIST_DEPARTMEN = DATA_URL+"apiAis.php?apicall=listDepartmen";
    public static final String DATA_URL_LIST_SALES_ORDER = DATA_URL+"apiAis.php?apicall=listSalesOrder";
    public static final String DATA_URL_UPDATE_PHOTO_PROFILE = DATA_URL+"apiAis.php?apicall=photoProfile";
    public static final String DATA_URL_PHOTO_PROFILE = DATA_URL+"photo/";
}