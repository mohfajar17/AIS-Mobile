package com.example.aismobile;

public class Config {
//    public static final String DATA_URL = "http://192.168.43.183/ais_web_service/apiAis.php?apicall=";   //IP thetering hp
//    public static final String DATA_URL = "http://192.168.1.77/ais_web_service/apiAis.php?apicall=";   //IP asuka lt 3
//    public static final String DATA_URL = "http://192.168.0.105/ais_web_service/apiAis.php?apicall=";   //IP home wifi
    public static final String DATA_URL = "https://ais.asukaindonesia.co.id/mobile/apiAis.php?apicall=";   //IP server
    public static final String DATA_URL_LOGIN = DATA_URL+"login";
    public static final String DATA_URL_USER_ACCESS = DATA_URL+"accessAllowModul";
    public static final String DATA_URL_USER_ISMOBILE = DATA_URL+"userActiveMobile";
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
    public static final String DATA_URL_ACCESS_FINANCE = DATA_URL+"accessAllowFinance";
    public static final String DATA_URL_ACCESS_HRD = DATA_URL+"accessAllowHR";
    public static final String DATA_URL_ACCESS_INVENTORY = DATA_URL+"accessAllowInventory";
    public static final String DATA_URL_ACCESS_MARKETING = DATA_URL+"accessAllowMarketing";
    public static final String DATA_URL_ACCESS_PROJECT = DATA_URL+"accessAllowProject";
    public static final String DATA_URL_ACCESS_PURCHASING = DATA_URL+"accessAllowPurchasing";
    public static final String DATA_URL_ACCESS_CRM = DATA_URL+"accessAllowCRM";
    public static final String DATA_URL_ACCESS_HSE = DATA_URL+"accessAllowHSE";
    public static final String DATA_URL_ACCESS_CONTACT = DATA_URL+"accessAllowContact";
    public static final String DATA_URL_JOB_ORDER_LIST = DATA_URL+"getJobOrder";
    public static final String DATA_URL_WORK_COMPLETION_LIST = DATA_URL+"getWorkCompletion";
    public static final String DATA_URL_MATERIAL_REQUISITION_LIST = DATA_URL+"getMaterialRequisition";
    public static final String DATA_URL_WORK_ORDER_LIST = DATA_URL+"getWorkOrder";
    public static final String DATA_URL_PICKUP_LIST = DATA_URL+"getPickup";
    public static final String DATA_URL_PROPOSE_BUDGET_LIST = DATA_URL+"getProposedBudget";
    public static final String DATA_URL_CASH_PROJECT_LIST = DATA_URL+"getCashProjectReport";
    public static final String DATA_URL_SPKL_LIST = DATA_URL+"getSpkl";
    public static final String DATA_URL_TUNJANGAN_KARYAWAN_LIST = DATA_URL+"getTunjanganKaryawan";
    public static final String DATA_URL_TUNJANGAN_TEMPORARY_LIST = DATA_URL+"getTunjanganTemporary";
    public static final String DATA_URL_PURCHASE_ORDER_LIST = DATA_URL+"getPurchaseOrder";
    public static final String DATA_URL_PURCHASE_SERVICE_LIST = DATA_URL+"getPurchaseService";
    public static final String DATA_URL_CASH_ON_DELIVERY_LIST = DATA_URL+"getCashOnDelivery";
    public static final String DATA_URL_CONTTRACT_AGREEMENT_LIST = DATA_URL+"getContractAgreement";
    public static final String DATA_URL_GRN_LIST = DATA_URL+"getGoodReceivedNote";
    public static final String DATA_URL_WORK_HANDOVER_LIST = DATA_URL+"getWorkHandover";
    public static final String DATA_URL_SERCICES_RECEIPT_LIST = DATA_URL+"getServicesReceipt";
    public static final String DATA_URL_CONTACT_LIST = DATA_URL+"getContact";
    public static final String DATA_URL_SUPPLIER_LIST = DATA_URL+"getSupplier";
    public static final String DATA_URL_COMPANY_LIST = DATA_URL+"getCompany";
    public static final String DATA_URL_ACCESS_REQUEST_LIST = DATA_URL+"getAccessRequest";
    public static final String DATA_URL_NEWS_LIST = DATA_URL+"getNews";
    public static final String DATA_URL_SUPPLIER_INVOICE_LIST = DATA_URL+"getSupplierInvoice";
    public static final String DATA_URL_CUSTOMER_INVOICE_LIST = DATA_URL+"getCustomerInvoices";
    public static final String DATA_URL_BANK_TRANSACTION_LIST = DATA_URL+"getBankTransaction";
    public static final String DATA_URL_EXPENSE_LIST = DATA_URL+"getExpense";
    public static final String DATA_URL_CASH_ADVANCE_LIST = DATA_URL+"getCashAdvance";
    public static final String DATA_URL_BUDGETING_LIST = DATA_URL+"getBudgeting";
    public static final String DATA_URL_PAYMENT_SUPPLIER_LIST = DATA_URL+"getPaymentSupplier";
    public static final String DATA_URL_BANK_ACCOUNT_LIST = DATA_URL+"getBankAccount";
    public static final String DATA_URL_DAFTAR_AKUN_LIST = DATA_URL+"getDaftarAkun";
    public static final String DATA_URL_SALES_QUOTATION_LIST = DATA_URL+"getSalesQuotation";
    public static final String DATA_URL_SALES_ORDER_LIST = DATA_URL+"getSalesOrder";
    public static final String DATA_URL_WORK_ACCIDENT_LIST = DATA_URL+"getWorkAccident";
    public static final String DATA_URL_GENBA_SAFETY_LIST = DATA_URL+"getGenbaSafety";
    public static final String DATA_URL_SAFETY_FILE_REPORT_LIST = DATA_URL+"getJobOrderSafety";
    public static final String DATA_URL_CUSTOMER_FEEDBACK_LIST = DATA_URL+"getCustomerFeedback";
    public static final String DATA_URL_QUESTION_LIST = DATA_URL+"getQuestions";
    public static final String DATA_URL_KUESIONER_LIST = DATA_URL+"getKuesioner";
    public static final String DATA_URL_LEAD_LIST = DATA_URL+"getLead";
    public static final String DATA_URL_FOLLOWUP_LIST = DATA_URL+"getFollowup";
    public static final String DATA_URL_EVENT_LIST = DATA_URL+"getEvent";
    public static final String DATA_URL_SCHEDULEVISIT_LIST = DATA_URL+"getScheduleVisits";
    public static final String DATA_URL_ITEM_LIST = DATA_URL+"getItem";
    public static final String DATA_URL_KELOMPOK_ITEM_LIST = DATA_URL+"getItemGroup";
    public static final String DATA_URL_KATEGORI_ITEM_LIST = DATA_URL+"getItemCategory";
    public static final String DATA_URL_TYPE_ITEM_LIST = DATA_URL+"getItemType";
    public static final String DATA_URL_ASET_LIST = DATA_URL+"getAsset";
    public static final String DATA_URL_ASET_RENTAL_LIST = DATA_URL+"getAssetRental";
    public static final String DATA_URL_STOCK_ADJUSMENT_LIST = DATA_URL+"getStockAdjustment";
    public static final String DATA_URL_MATERIAL_RETURN_LIST = DATA_URL+"getMaterialReturn";
    public static final String DATA_URL_CHECK_CLOCK_LIST = DATA_URL+"getEmployeeCheckClock";
    public static final String DATA_URL_CUTI_LIST = DATA_URL+"getEmployeeLeave";
    public static final String DATA_URL_PENDIDIKAN_LIST = DATA_URL+"getEmployeeEducation";
    public static final String DATA_URL_KELUARGA_LIST = DATA_URL+"getEmployeeFamily";
    public static final String DATA_URL_TRAINING_LIST = DATA_URL+"getTrainingList";
    public static final String DATA_URL_WORK_EXPERIENCE_LIST = DATA_URL+"getWorkExperience";
    public static final String DATA_URL_ACHIEVEMENT_LIST = DATA_URL+"getEmployeeAchievement";
    public static final String DATA_URL_EMPLOYEE_HISTORY_LIST = DATA_URL+"getEmploymentHistory";
    public static final String DATA_URL_EMPLOYEE_NOTICE_LIST = DATA_URL+"getEmployeeNotice";
    public static final String DATA_URL_HISTORY_CONTACT_LIST = DATA_URL+"getHistoryContract";
    public static final String DATA_URL_HARI_LIBUR_LIST = DATA_URL+"getHariLibur";
    public static final String DATA_URL_EMPLOYEE_DEDUCTION_LIST = DATA_URL+"getEmployeeDeduction";
    public static final String DATA_URL_EMPLOYEE_GRADE_ALLOWANCE_LIST = DATA_URL+"getEmployeeGradeAllowance";
    public static final String DATA_URL_DEDUCTION_LIST = DATA_URL+"getDeduction";
    public static final String DATA_URL_ALLOWANCE_LIST = DATA_URL+"getAllowance";
    public static final String DATA_URL_KARYAWAN_LIST = DATA_URL+"getEmployee";
    public static final String DATA_URL_DEPARTEMEN_LIST = DATA_URL+"getDepartment";
    public static final String DATA_URL_EMPLOYEE_GRADE_LIST = DATA_URL+"getEmployeeGrade";
    public static final String DATA_URL_JOB_TITLE_LIST = DATA_URL+"getJobTitle";
    public static final String DATA_URL_PERSONALIA_NEWS_LIST = DATA_URL+"getPersonaliaNews";
    public static final String DATA_URL_JOB_GRADE_LIST = DATA_URL+"getJobGrade";
    public static final String DATA_URL_EMPLOYEE_REPORT_LIST = DATA_URL+"getEmployeeReport";
    public static final String DATA_URL_PHOTO_PROFILE = "https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/";
    public static final String DATA_URL_IMAGE = "https://ais.asukaindonesia.co.id/protected/attachments/news/";
}