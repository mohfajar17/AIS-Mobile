package com.example.aismobile;

public class Config {
//    public static final String DATA_URL = "http://192.168.43.119/ais_web_service/apiAis.php?apicall=";   //IP thetering hp
//    public static final String DATA_URL = "http://192.168.1.24/ais_web_service/apiAis.php?apicall=";   //IP asuka lt 3
    public static final String DATA_URL = "https://ais.asukaindonesia.co.id/mobile/apiAis.php?apicall=";   //IP server
    public static final String DATA_URL_LOGIN = DATA_URL+"login";
    public static final String DATA_URL_USER_ACCESS = DATA_URL+"accessAllowModul";
    public static final String DATA_URL_EMPLOYEE_DATA = DATA_URL+"employeeData";
    public static final String DATA_URL_EMPLOYEE_LEAVE = DATA_URL+"employeeLeave";
    public static final String DATA_URL_EMPLOYEE_MONEYBOX = DATA_URL+"employeeMoneybox";
    public static final String DATA_URL_CUSTOMER_INVOICE = DATA_URL+"getInv";
    public static final String DATA_URL_SUPPLIER_INVOICE = DATA_URL+"getSinv";
    public static final String DATA_URL_BANK = DATA_URL+"getBank";
    public static final String DATA_URL_SALES_QUOTATION = DATA_URL+"getSalesQuot";
    public static final String DATA_URL_INVENTORY = DATA_URL+"getInventory";
    public static final String DATA_URL_EDIT_ACCOUNT = DATA_URL+"editAccount";
    public static final String DATA_URL_EDIT_PERSONAL = DATA_URL+"editPersonalData";
    public static final String DATA_URL_CALENDAR = DATA_URL+"getHoliday";
    public static final String DATA_URL_LIST_SALES_QUOTATION = DATA_URL+"listSalesQuotation";
    public static final String DATA_URL_LIST_EMPLOYEE = DATA_URL+"listEmployee";
    public static final String DATA_URL_LIST_WORKBASE = DATA_URL+"listWorkbase";
    public static final String DATA_URL_LIST_DEPARTMEN = DATA_URL+"listDepartmen";
    public static final String DATA_URL_LIST_SALES_ORDER = DATA_URL+"listSalesOrder";
    public static final String DATA_URL_PHOTO_PROFILE = "https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/";
}