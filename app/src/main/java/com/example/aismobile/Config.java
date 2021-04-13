package com.example.aismobile;

public class Config {
//    public static final String DATA_URL = "http://192.168.43.183/ais_web_service/";   //IP thetering hp
//    public static final String DATA_URL = "http://192.168.88.212/ais_web_service/";   //IP asuka lt 3
//    public static final String DATA_URL = "http://192.168.0.109/ais_web_service/";   //IP home wifi
    public static final String DATA_URL = "https://ais.asukaindonesia.co.id/mobile/";   //IP server

    public static final String DATA_URL_APPROVAL = DATA_URL + "apiAisApproval.php?apicall=";
    public static final String URL_APPROVAL_TOTAL = DATA_URL_APPROVAL +"getTotalSum";
    public static final String URL_APPROVAL_ALLOW = DATA_URL_APPROVAL +"getApprovalAccess";
    public static final String URL_APPROVAL_MATERIAL_REQUEST = DATA_URL_APPROVAL +"getMaterialRequisition";
    public static final String URL_UPDATE_APPROVAL_MATERIAL_REQUEST = DATA_URL_APPROVAL +"updateMaterialRequisition";
    public static final String URL_UPDATE_ID_APPROVAL_MATERIAL_REQUEST = DATA_URL_APPROVAL +"updateMaterialRequisitionId";
    public static final String URL_APPROVAL_WORK_REQUEST = DATA_URL_APPROVAL +"getWorkOrder";
    public static final String URL_UPDATE_APPROVAL_WORK_REQUEST = DATA_URL_APPROVAL +"updateWorkOrder";
    public static final String URL_UPDATE_ID_APPROVAL_WORK_REQUEST = DATA_URL_APPROVAL +"updateWorkOrderId";
    public static final String URL_APPROVAL_SPKL = DATA_URL_APPROVAL +"getSpkl";
    public static final String URL_UPDATE_APPROVAL_SPKL = DATA_URL_APPROVAL +"updateSpkl";
    public static final String URL_UPDATE_ID_APPROVAL_SPKL = DATA_URL_APPROVAL +"updateSpklId";
    public static final String URL_APPROVAL_PROPOSED_BUDGET = DATA_URL_APPROVAL +"getProposedBudget";
    public static final String URL_UPDATE_APPROVAL_PROPOSED_BUDGET = DATA_URL_APPROVAL +"UpdateProposedBudget";
    public static final String URL_UPDATE_ID_APPROVAL_PROPOSED_BUDGET = DATA_URL_APPROVAL +"UpdateProposedBudgetId";
    public static final String URL_APPROVAL_CASH_PROJECT_REPORT = DATA_URL_APPROVAL +"getCashProjectReport";
    public static final String URL_UPDATE_APPROVAL_CPR = DATA_URL_APPROVAL +"updateCashProjectReport";
    public static final String URL_UPDATE_ID_APPROVAL_CPR = DATA_URL_APPROVAL +"updateCashProjectReportId";
    public static final String URL_APPROVAL_TUNJANGAN_KARYAWAN = DATA_URL_APPROVAL +"getTunjanganKaryawan";
    public static final String URL_UPDATE_APPROVAL_TUN_KARYAWAN = DATA_URL_APPROVAL +"updateTunjanganKaryawan";
    public static final String URL_UPDATE_ID_APPROVAL_TUN_KARYAWAN = DATA_URL_APPROVAL +"updateTunjanganKaryawanId";
    public static final String URL_APPROVAL_TUNJANGAN_TEMPORARY = DATA_URL_APPROVAL +"getTunjanganTemporary";
    public static final String URL_UPDATE_APPROVAL_TUN_TEMPORARY = DATA_URL_APPROVAL +"updateTunjanganTemporary";
    public static final String URL_UPDATE_ID_APPROVAL_TUN_TEMPORARY = DATA_URL_APPROVAL +"updateTunjanganTemporaryId";
    public static final String URL_APPROVAL_PURCHASE_ORDER = DATA_URL_APPROVAL +"getPurchaseOrder";
    public static final String URL_UPDATE_APPROVAL_PURCHASE_ORDER = DATA_URL_APPROVAL +"updatePurchaseOrder";
    public static final String URL_UPDATE_ID_APPROVAL_PURCHASE_ORDER = DATA_URL_APPROVAL +"updatePurchaseOrderId";
    public static final String URL_APPROVAL_WORK_ORDER = DATA_URL_APPROVAL +"getPurchaseService";
    public static final String URL_UPDATE_APPROVAL_WORK_ORDER = DATA_URL_APPROVAL +"updatePurchaseService";
    public static final String URL_UPDATE_ID_APPROVAL_WORK_ORDER = DATA_URL_APPROVAL +"updatePurchaseServiceId";
    public static final String URL_APPROVAL_CASH_ON_DELIVERY = DATA_URL_APPROVAL +"getCashOnDelivery";
    public static final String URL_UPDATE_APPROVAL_CASH_ON_DELIVERY = DATA_URL_APPROVAL +"updateCashOnDelivery";
    public static final String URL_UPDATE_ID_APPROVAL_CASH_ON_DELIVERY = DATA_URL_APPROVAL +"updateCashOnDeliveryId";
    public static final String URL_APPROVAL_MATERIAL_RETURN = DATA_URL_APPROVAL +"getMaterialReturn";
    public static final String URL_UPDATE_ID_APPROVAL_MATERIAL_RETURN = DATA_URL_APPROVAL +"updateMaterialReturnId";
    public static final String URL_APPROVAL_STOCK_ADJUSMENT = DATA_URL_APPROVAL +"getStockAdjustment";
    public static final String URL_UPDATE_APPROVAL_STOCK_ADJUSMENT = DATA_URL_APPROVAL +"updateStockAdjustment";
    public static final String URL_UPDATE_ID_APPROVAL_STOCK_ADJUSMENT = DATA_URL_APPROVAL +"updateStockAdjustmentId";
    public static final String URL_APPROVAL_BUDGETING = DATA_URL_APPROVAL +"getBudgeting";
    public static final String URL_UPDATE_APPROVAL_BUDGETING = DATA_URL_APPROVAL +"updateBudgeting";
    public static final String URL_UPDATE_ID_APPROVAL_BUDGETING = DATA_URL_APPROVAL +"updateBudgetingId";
    public static final String URL_APPROVAL_PAYMENT_SUPPLIER = DATA_URL_APPROVAL +"getPaymentSupplier";
    public static final String URL_UPDATE_APPROVAL_PAYMENT_SUPPLIER = DATA_URL_APPROVAL +"updatePaymentSupplier";
    public static final String URL_UPDATE_ID_APPROVAL_PAYMENT_SUPPLIER = DATA_URL_APPROVAL +"updatePaymentSupplierId";
    public static final String URL_APPROVAL_BANK_TRANSACTION = DATA_URL_APPROVAL +"getBankTransaction";
    public static final String URL_UPDATE_APPROVAL_BANK_TRANSACTION = DATA_URL_APPROVAL +"updateBankTransaction";
    public static final String URL_UPDATE_ID_APPROVAL_BANK_TRANSACTION = DATA_URL_APPROVAL +"updateBankTransactionId";
    public static final String URL_APPROVAL_EXPENSES = DATA_URL_APPROVAL +"getExpense";
    public static final String URL_UPDATE_APPROVAL_EXPENSES = DATA_URL_APPROVAL +"updateExpense";
    public static final String URL_UPDATE_ID_APPROVAL_EXPENSES = DATA_URL_APPROVAL +"updateExpenseId";
    public static final String URL_APPROVAL_CASH_ADVANCE = DATA_URL_APPROVAL +"getCashAdvance";
    public static final String URL_UPDATE_APPROVAL_CASH_ADVANCE = DATA_URL_APPROVAL +"updateCashAdvance";
    public static final String URL_APPROVAL_ASSIGN_ID = DATA_URL_APPROVAL +"getApprovalAssign";

    public static final String DATA_URL_AIS = DATA_URL + "apiAis.php?apicall=";
    public static final String DATA_URL_LOGIN = DATA_URL_AIS +"login";
    public static final String DATA_URL_MOBILE_IS_ACTIVE = DATA_URL_AIS +"loginIsMobile";
    public static final String DATA_URL_APPROVAL_ALLOW = DATA_URL_AIS +"getApprovalAllow";
    public static final String DATA_URL_VIEW_ACCESS = DATA_URL_AIS +"checkViewAccess";
    public static final String DATA_URL_USER_ACCESS = DATA_URL_AIS +"accessAllowModul";
    public static final String DATA_URL_USER_ISMOBILE = DATA_URL_AIS +"userActiveMobile";
    public static final String DATA_URL_EMPLOYEE_DATA = DATA_URL_AIS +"employeeData";
    public static final String DATA_URL_EMPLOYEE_LEAVE = DATA_URL_AIS +"employeeLeave";
    public static final String DATA_URL_EMPLOYEE_MONEYBOX = DATA_URL_AIS +"employeeMoneybox";
    public static final String DATA_URL_CUSTOMER_INVOICE = DATA_URL_AIS +"getInv";
    public static final String DATA_URL_SUPPLIER_INVOICE = DATA_URL_AIS +"getSinv";
    public static final String DATA_URL_BANK = DATA_URL_AIS +"getBank";
    public static final String DATA_URL_SALES_QUOTATION = DATA_URL_AIS +"getSalesQuot";
    public static final String DATA_URL_INVENTORY = DATA_URL_AIS +"getInventory";
    public static final String DATA_URL_PURCHASE_PO_WO_COD = DATA_URL_AIS +"getPurchaseToday";
    public static final String DATA_URL_EDIT_ACCOUNT = DATA_URL_AIS +"editAccount";
    public static final String DATA_URL_EDIT_PERSONAL = DATA_URL_AIS +"editPersonalData";
    public static final String DATA_URL_CALENDAR = DATA_URL_AIS +"getHoliday";
    public static final String DATA_URL_ACCESS_FINANCE = DATA_URL_AIS +"accessAllowFinance";
    public static final String DATA_URL_ACCESS_HRD = DATA_URL_AIS +"accessAllowHR";
    public static final String DATA_URL_ACCESS_INVENTORY = DATA_URL_AIS +"accessAllowInventory";
    public static final String DATA_URL_ACCESS_MARKETING = DATA_URL_AIS +"accessAllowMarketing";
    public static final String DATA_URL_ACCESS_PROJECT = DATA_URL_AIS +"accessAllowProject";
    public static final String DATA_URL_ACCESS_PURCHASING = DATA_URL_AIS +"accessAllowPurchasing";
    public static final String DATA_URL_ACCESS_CRM = DATA_URL_AIS +"accessAllowCRM";
    public static final String DATA_URL_ACCESS_HSE = DATA_URL_AIS +"accessAllowHSE";
    public static final String DATA_URL_ACCESS_CONTACT = DATA_URL_AIS +"accessAllowContact";
    public static final String DATA_URL_JOB_ORDER_LIST = DATA_URL_AIS +"getJobOrder";
    public static final String DATA_URL_JOB_ORDER_TOTAL_DATA = DATA_URL_AIS +"getTotalDetailJO";
    public static final String DATA_URL_WORK_COMPLETION_LIST = DATA_URL_AIS +"getWorkCompletion";
    public static final String DATA_URL_WORK_COMPLETION_DETAIL_LIST = DATA_URL_AIS +"getWorkCompletionDetail";
    public static final String DATA_URL_MATERIAL_REQUISITION_LIST = DATA_URL_AIS +"getMaterialRequisition";
    public static final String DATA_URL_MATERIAL_REQUISITION_DETAIL = DATA_URL_AIS +"getMaterialReqDetail";
    public static final String DATA_URL_MATERIAL_REQUISITION_PICKUP = DATA_URL_AIS +"getMaterialReqPickup";
    public static final String DATA_URL_WORK_ORDER_LIST = DATA_URL_AIS +"getWorkOrder";
    public static final String DATA_URL_WORK_ORDER_DETAIL_LIST = DATA_URL_AIS +"getWorkOrderDetail";
    public static final String DATA_URL_PICKUP_LIST = DATA_URL_AIS +"getPickup";
    public static final String DATA_URL_PICKUP_DETAIL_LIST = DATA_URL_AIS +"getPickupDetail";
    public static final String DATA_URL_TOOLS_REQUEST_LIST = DATA_URL_AIS +"getResourcesRequest";
    public static final String DATA_URL_PROPOSE_BUDGET_LIST = DATA_URL_AIS +"getProposedBudget";
    public static final String DATA_URL_PROPOSE_BUDGET_DETAIL_LIST = DATA_URL_AIS +"getProposedBudgetDetail";
    public static final String DATA_URL_CASH_PROJECT_LIST = DATA_URL_AIS +"getCashProjectReport";
    public static final String DATA_URL_CASH_PROJECT_DETAIL_LIST = DATA_URL_AIS +"getCashProjectReportDetail";
    public static final String DATA_URL_CASH_PROJECT_DETAIL_PB_LIST = DATA_URL_AIS +"getCashProjectReportPbReceived";
    public static final String DATA_URL_SPKL_LIST = DATA_URL_AIS +"getSpkl";
    public static final String DATA_URL_TUNJANGAN_KARYAWAN_LIST = DATA_URL_AIS +"getTunjanganKaryawan";
    public static final String DATA_URL_TUNJANGAN_TEMPORARY_LIST = DATA_URL_AIS +"getTunjanganTemporary";
    public static final String DATA_URL_PURCHASE_ORDER_LIST = DATA_URL_AIS +"getPurchaseOrder";
    public static final String DATA_URL_PURCHASE_ORDER_DETAIL_LIST = DATA_URL_AIS +"getPurchaseOrderDetail";
    public static final String DATA_URL_PURCHASE_SERVICE_LIST = DATA_URL_AIS +"getPurchaseService";
    public static final String DATA_URL_PURCHASE_SERVICE_DETAIL_LIST = DATA_URL_AIS +"getPurchaseServiceDetail";
    public static final String DATA_URL_CASH_ON_DELIVERY_LIST = DATA_URL_AIS +"getCashOnDelivery";
    public static final String DATA_URL_CASH_ON_DELIVERY_DETAIL_LIST = DATA_URL_AIS +"getCashOnDeliveryDetail";
    public static final String DATA_URL_CONTTRACT_AGREEMENT_LIST = DATA_URL_AIS +"getContractAgreement";
    public static final String DATA_URL_GRN_LIST = DATA_URL_AIS +"getGoodReceivedNote";
    public static final String DATA_URL_GRN_DETAIL_LIST = DATA_URL_AIS +"getGoodReceivedNoteDetail";
    public static final String DATA_URL_PURCHASING_DETAIL_SI_LIST = DATA_URL_AIS +"getPurchasingDetailSi";
    public static final String DATA_URL_WORK_HANDOVER_LIST = DATA_URL_AIS +"getWorkHandover";
    public static final String DATA_URL_WORK_HANDOVER_DETAIL_LIST = DATA_URL_AIS +"getWorkHandoverDetail";
    public static final String DATA_URL_SERCICES_RECEIPT_LIST = DATA_URL_AIS +"getServicesReceipt";
    public static final String DATA_URL_SERCICES_RECEIPT_DETAIL_LIST = DATA_URL_AIS +"getServicesReceiptDetail";
    public static final String DATA_URL_CONTACT_LIST = DATA_URL_AIS +"getContact";
    public static final String DATA_URL_SUPPLIER_LIST = DATA_URL_AIS +"getSupplier";
    public static final String DATA_URL_SUPPLIER_DETAIL_LIST = DATA_URL_AIS +"getSupplierDetail";
    public static final String DATA_URL_COMPANY_LIST = DATA_URL_AIS +"getCompany";
    public static final String DATA_URL_COMPANY_CONTACT_LIST = DATA_URL_AIS +"getCompanyDetContact";
    public static final String DATA_URL_COMPANY_INVOICE_LIST = DATA_URL_AIS +"getCompanyDetInvoice";
    public static final String DATA_URL_ACCESS_REQUEST_LIST = DATA_URL_AIS +"getAccessRequest";
    public static final String DATA_URL_ACCESS_REQUEST_DETAIL_LIST = DATA_URL_AIS +"getAccessRequestDetail";
    public static final String DATA_URL_NEWS_LIST = DATA_URL_AIS +"getNews";
    public static final String DATA_URL_SUPPLIER_INVOICE_LIST = DATA_URL_AIS +"getSupplierInvoice";
    public static final String DATA_URL_SUPPLIER_INVOICE_DETAIL_LIST = DATA_URL_AIS +"getSupplierInvoiceDetail";
    public static final String DATA_URL_CUSTOMER_INVOICE_LIST = DATA_URL_AIS +"getCustomerInvoices";
    public static final String DATA_URL_CUSTOMER_INVOICE_DETAIL_LIST = DATA_URL_AIS +"getCiDetWorkCompletion";
    public static final String DATA_URL_BANK_TRANSACTION_LIST = DATA_URL_AIS +"getBankTransaction";
    public static final String DATA_URL_BANK_TRANSACTION_DETAIL_LIST = DATA_URL_AIS +"getBankTransactionDetail";
    public static final String DATA_URL_EXPENSE_LIST = DATA_URL_AIS +"getExpense";
    public static final String DATA_URL_EXPENSE_DETAIL_LIST = DATA_URL_AIS +"getExpenseDetail";
    public static final String DATA_URL_CASH_ADVANCE_LIST = DATA_URL_AIS +"getCashAdvance";
    public static final String DATA_URL_BUDGETING_LIST = DATA_URL_AIS +"getBudgeting";
    public static final String DATA_URL_BUDGETING_DETAIL_LIST = DATA_URL_AIS +"getBudgetingDetail";
    public static final String DATA_URL_PAYMENT_SUPPLIER_LIST = DATA_URL_AIS +"getPaymentSupplier";
    public static final String DATA_URL_PAYMENT_SUPPLIER_DETAIL_LIST = DATA_URL_AIS +"getPaymentSupplierDetail";
    public static final String DATA_URL_BANK_ACCOUNT_LIST = DATA_URL_AIS +"getBankAccount";
    public static final String DATA_URL_DAFTAR_AKUN_LIST = DATA_URL_AIS +"getDaftarAkun";
    public static final String DATA_URL_EKSPEDISI_LIST = DATA_URL_AIS +"getEkspedisi";
    public static final String DATA_URL_CUSTOMER_RECEIVES_LIST = DATA_URL_AIS +"getCustomerReceives";
    public static final String DATA_URL_CUSTOMER_RECEIVES_DETAIL_LIST = DATA_URL_AIS +"getCustomerReceivesDetail";
    public static final String DATA_URL_SALES_QUOTATION_LIST = DATA_URL_AIS +"getSalesQuotation";
    public static final String DATA_URL_SALES_ORDER_LIST = DATA_URL_AIS +"getSalesOrder";
    public static final String DATA_URL_WORK_ACCIDENT_LIST = DATA_URL_AIS +"getWorkAccident";
    public static final String DATA_URL_GENBA_SAFETY_LIST = DATA_URL_AIS +"getGenbaSafety";
    public static final String DATA_URL_GENBA_SAFETY_DETAIL_LIST = DATA_URL_AIS +"getGenbaSafetyDetail";
    public static final String DATA_URL_SAFETY_FILE_REPORT_LIST = DATA_URL_AIS +"getJobOrderSafety";
    public static final String DATA_URL_CUSTOMER_FEEDBACK_LIST = DATA_URL_AIS +"getCustomerFeedback";
    public static final String DATA_URL_QUESTION_LIST = DATA_URL_AIS +"getQuestions";
    public static final String DATA_URL_KUESIONER_LIST = DATA_URL_AIS +"getKuesioner";
    public static final String DATA_URL_KUESIONER_DETAIL_LIST = DATA_URL_AIS +"getKuesionerDetail";
    public static final String DATA_URL_LEAD_LIST = DATA_URL_AIS +"getLead";
    public static final String DATA_URL_FOLLOWUP_LIST = DATA_URL_AIS +"getFollowup";
    public static final String DATA_URL_EVENT_LIST = DATA_URL_AIS +"getEvent";
    public static final String DATA_URL_SCHEDULEVISIT_LIST = DATA_URL_AIS +"getScheduleVisits";
    public static final String DATA_URL_ITEM_LIST = DATA_URL_AIS +"getItem";
    public static final String DATA_URL_KELOMPOK_ITEM_LIST = DATA_URL_AIS +"getItemGroup";
    public static final String DATA_URL_KATEGORI_ITEM_LIST = DATA_URL_AIS +"getItemCategory";
    public static final String DATA_URL_TYPE_ITEM_LIST = DATA_URL_AIS +"getItemType";
    public static final String DATA_URL_ASET_LIST = DATA_URL_AIS +"getAsset";
    public static final String DATA_URL_ASET_RENTAL_LIST = DATA_URL_AIS +"getAssetRental";
    public static final String DATA_URL_STOCK_ADJUSMENT_LIST = DATA_URL_AIS +"getStockAdjustment";
    public static final String DATA_URL_STOCK_ADJUSMENT_DETAIL_LIST = DATA_URL_AIS +"getStockAdjustmentDetail";
    public static final String DATA_URL_MATERIAL_RETURN_LIST = DATA_URL_AIS +"getMaterialReturn";
    public static final String DATA_URL_MATERIAL_RETURN_DETAIL_LIST = DATA_URL_AIS +"getMaterialReturnDetail";
    public static final String DATA_URL_CHECK_CLOCK_LIST = DATA_URL_AIS +"getEmployeeCheckClock";
    public static final String DATA_URL_CHECK_CLOCK_DETAIL_LIST = DATA_URL_AIS +"getEmployeeCheckClockDetail";
    public static final String DATA_URL_CUTI_LIST = DATA_URL_AIS +"getEmployeeLeave";
    public static final String DATA_URL_CUTI_DETAIL_LIST = DATA_URL_AIS +"getEmployeeLeaveDetail";
    public static final String DATA_URL_PENDIDIKAN_LIST = DATA_URL_AIS +"getEmployeeEducation";
    public static final String DATA_URL_KELUARGA_LIST = DATA_URL_AIS +"getEmployeeFamily";
    public static final String DATA_URL_EMPLOYEE_HISTORY_DETAIL_LIST = DATA_URL_AIS +"getEmploymentHistoryDetail";
    public static final String DATA_URL_TRAINING_LIST = DATA_URL_AIS +"getTrainingList";
    public static final String DATA_URL_WORK_EXPERIENCE_LIST = DATA_URL_AIS +"getWorkExperience";
    public static final String DATA_URL_ACHIEVEMENT_LIST = DATA_URL_AIS +"getEmployeeAchievement";
    public static final String DATA_URL_EMPLOYEE_HISTORY_LIST = DATA_URL_AIS +"getEmploymentHistory";
    public static final String DATA_URL_EMPLOYEE_NOTICE_LIST = DATA_URL_AIS +"getEmployeeNotice";
    public static final String DATA_URL_HISTORY_CONTACT_LIST = DATA_URL_AIS +"getHistoryContract";
    public static final String DATA_URL_HARI_LIBUR_LIST = DATA_URL_AIS +"getHariLibur";
    public static final String DATA_URL_EMPLOYEE_DEDUCTION_LIST = DATA_URL_AIS +"getEmployeeDeduction";
    public static final String DATA_URL_EMPLOYEE_GRADE_ALLOWANCE_LIST = DATA_URL_AIS +"getEmployeeGradeAllowance";
    public static final String DATA_URL_DEDUCTION_LIST = DATA_URL_AIS +"getDeduction";
    public static final String DATA_URL_ALLOWANCE_LIST = DATA_URL_AIS +"getAllowance";
    public static final String DATA_URL_SALARY_GRADE_LIST = DATA_URL_AIS +"getSalaryGrade";
    public static final String DATA_URL_MARITAL_STATUS_LIST = DATA_URL_AIS +"getMaritalStatus";
    public static final String DATA_URL_SALARY_CORRECTION_LIST = DATA_URL_AIS +"getSalaryCorrection";
    public static final String DATA_URL_LATE_DEDUCTION_LIST = DATA_URL_AIS +"getLateDeduction";
    public static final String DATA_URL_REMAIN_LEAVE_LIST = DATA_URL_AIS +"getRemainLeave";
    public static final String DATA_URL_KABUPATEN_LIST = DATA_URL_AIS +"getKabupaten";
    public static final String DATA_URL_PROVINSI_LIST = DATA_URL_AIS +"getProvinsi";
    public static final String DATA_URL_KARYAWAN_LIST = DATA_URL_AIS +"getEmployee";
    public static final String DATA_URL_KARYAWAN_DETAIL_LIST = DATA_URL_AIS +"getEmployeeDetailFamily";
    public static final String DATA_URL_KARYAWAN_ACHIEVEMENT_LIST = DATA_URL_AIS +"getEmployeeDetailAchievement";
    public static final String DATA_URL_KARYAWAN_TRAINING_LIST = DATA_URL_AIS +"getEmployeeDetailTraining";
    public static final String DATA_URL_KARYAWAN_EXPERIENCE_LIST = DATA_URL_AIS +"getEmployeeDetailExperience";
    public static final String DATA_URL_KARYAWAN_EDUCATION_LIST = DATA_URL_AIS +"getEmployeeDetailEducation";
    public static final String DATA_URL_KARYAWAN_HISTORY_LIST = DATA_URL_AIS +"getEmployeeDetailHistory";
    public static final String DATA_URL_KARYAWAN_POTONGAN_LIST = DATA_URL_AIS +"getEmployeeDetailPotongan";
    public static final String DATA_URL_KARYAWAN_TUNJANGAN_LIST = DATA_URL_AIS +"getEmployeeDetailTunjangan";
    public static final String DATA_URL_KARYAWAN_FILE_LIST = DATA_URL_AIS +"getEmployeeDetailFile";
    public static final String DATA_URL_KARYAWAN_LEAVE_LIST = DATA_URL_AIS +"getEmployeeDetailLeave";
    public static final String DATA_URL_KARYAWAN_KERJA_LIST = DATA_URL_AIS +"getEmployeeDetailKerja";
    public static final String DATA_URL_DEPARTEMEN_LIST = DATA_URL_AIS +"getDepartment";
    public static final String DATA_URL_DEPARTEMEN_DETAIL_LIST = DATA_URL_AIS +"getDepartmentDetail";
    public static final String DATA_URL_EMPLOYEE_GRADE_LIST = DATA_URL_AIS +"getEmployeeGrade";
    public static final String DATA_URL_EMPLOYEE_GRADE_DETAIL_LIST = DATA_URL_AIS +"getEmployeeGradeDetail";
    public static final String DATA_URL_JOB_TITLE_LIST = DATA_URL_AIS +"getJobTitle";
    public static final String DATA_URL_PERSONALIA_NEWS_LIST = DATA_URL_AIS +"getPersonaliaNews";
    public static final String DATA_URL_JOB_GRADE_LIST = DATA_URL_AIS +"getJobGrade";
    public static final String DATA_URL_EMPLOYEE_REPORT_LIST = DATA_URL_AIS +"getEmployeeReport";
    public static final String DATA_URL_EMPLOYEE_REPORT_DETAIL = DATA_URL_AIS +"getEmployeeReportDetail";
    public static final String DATA_URL_DETAIL_JO_MR_LIST = DATA_URL_AIS +"getDetailJOMR";
    public static final String DATA_URL_DETAIL_JO_PR_LIST = DATA_URL_AIS +"getDetailJOPR";
    public static final String DATA_URL_DETAIL_JO_TR_LIST = DATA_URL_AIS +"getDetailJOTR";
    public static final String DATA_URL_DETAIL_JO_COD_LIST = DATA_URL_AIS +"getDetailJOCOD";
    public static final String DATA_URL_DETAIL_JO_EXPENSES_LIST = DATA_URL_AIS +"getDetailJOExpenses";
    public static final String DATA_URL_DETAIL_JO_INVOICE_LIST = DATA_URL_AIS +"getDetailJOInvoice";
    public static final String DATA_URL_DETAIL_JO_MP_PERMANEN_LIST = DATA_URL_AIS +"getDetailJOManPowerPermanenKontrak";
    public static final String DATA_URL_DETAIL_JO_MP_TEMP_LIST = DATA_URL_AIS +"getDetailJOManPowerTemp";
    public static final String DATA_URL_DETAIL_JO_WO_LIST = DATA_URL_AIS +"getDetailJOWO";
    public static final String DATA_URL_DETAIL_JO_PB_LIST = DATA_URL_AIS +"getDetailJOPB";
    public static final String DATA_URL_DETAIL_JO_PB_HALF_LIST = DATA_URL_AIS +"getDetailJOPBHalf";
    public static final String DATA_URL_DETAIL_JO_PB_REST_LIST = DATA_URL_AIS +"getDetailJOPBRestFrom";
    public static final String DATA_URL_DETAIL_JO_CPR_LIST = DATA_URL_AIS +"getDetailJOCPR";
    public static final String DATA_URL_DETAIL_JO_CPR_REST_LIST = DATA_URL_AIS +"getDetailJOCPRRestFrom";
    public static final String DATA_URL_DETAIL_JO_MATRET_LIST = DATA_URL_AIS +"getDetailJOMaterialReturn";
    public static final String DATA_URL_DETAIL_JO_MATRET_REST_LIST = DATA_URL_AIS +"getDetailJOMaterialReturnDetail";
    public static final String DATA_URL_DETAIL_SPKL_LIST = DATA_URL_AIS +"getDetailSpkl";
    public static final String DATA_URL_LIST_DETAIL_SPKL = DATA_URL_AIS +"getListDetailSpkl";
    public static final String DATA_URL_LIST_SALES_QUOTATION = DATA_URL_AIS +"listSalesQuotation";
    public static final String DATA_URL_LIST_SALES_QUOTATION_UPDATE = DATA_URL_AIS +"listSalesQuotationUpdate";
    public static final String DATA_URL_LIST_EMPLOYEE = DATA_URL_AIS +"listEmployee";
    public static final String DATA_URL_LIST_WORKBASE = DATA_URL_AIS +"listWorkbase";
    public static final String DATA_URL_LIST_DEPARTMEN = DATA_URL_AIS +"listDepartmen";
    public static final String DATA_URL_LIST_SALES_ORDER = DATA_URL_AIS +"listSalesOrder";
    public static final String DATA_URL_LIST_SALES_ORDER_DETAIL = DATA_URL_AIS +"getSalesOrderDetail";
    public static final String DATA_URL_LIST_JOB_ORDER = DATA_URL_AIS +"listJobOrder";
    public static final String DATA_URL_SPKL_NUMBER = DATA_URL_AIS +"getSpklNumber";
    public static final String DATA_URL_CREATE_SPKL = DATA_URL_AIS +"addSpkl";
    public static final String DATA_URL_UPDATE_SPKL = DATA_URL_AIS +"updateSpkl";
    public static final String DATA_URL_JOB_ORDER_NUMBER = DATA_URL_AIS +"getJobOrderNumber";
    public static final String DATA_URL_CREATE_JOB_ORDER = DATA_URL_AIS +"addJobOrder";
    public static final String DATA_URL_UPDATE_JOB_ORDER = DATA_URL_AIS +"updateJobOrder";
    public static final String DATA_URL_DATA_JOB_ORDER = DATA_URL_AIS +"getDataJobOrder";
    public static final String DATA_URL_PHOTO_PROFILE = "https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/";
    public static final String DATA_URL_IMAGE = "https://ais.asukaindonesia.co.id/protected/attachments/news/";
}