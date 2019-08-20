package com.example.ciws.model;

public class SupplierInfo {
    private String supplierId;
    private String supplierName;
    private char cbType;
    private String batchNoPrefix;
    private int batchTruckCounts;
    private String remark;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public char getCbType() {
        return cbType;
    }

    public void setCbType(char cbType) {
        this.cbType = cbType;
    }

    public String getBatchNoPrefix() {
        return batchNoPrefix;
    }

    public void setBatchNoPrefix(String batchNoPrefix) {
        this.batchNoPrefix = batchNoPrefix;
    }

    public int getBatchTruckCounts() {
        return batchTruckCounts;
    }

    public void setBatchTruckCounts(int batchTruckCounts) {
        this.batchTruckCounts = batchTruckCounts;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
