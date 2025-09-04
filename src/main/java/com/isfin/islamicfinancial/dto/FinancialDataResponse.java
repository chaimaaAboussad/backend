package com.isfin.islamicfinancial.dto;

import java.util.List;

public class FinancialDataResponse {
    private QuoteSummary quoteSummary;

    public QuoteSummary getQuoteSummary() { return quoteSummary; }
    public void setQuoteSummary(QuoteSummary quoteSummary) { this.quoteSummary = quoteSummary; }

    public static class QuoteSummary {
        private List<Result> result;

        public List<Result> getResult() { return result; }
        public void setResult(List<Result> result) { this.result = result; }
    }

    public static class Result {
        private FinancialData financialData;
        private DefaultKeyStatistics defaultKeyStatistics;
        private BalanceSheetHistory balanceSheetHistory;

        public FinancialData getFinancialData() { return financialData; }
        public void setFinancialData(FinancialData financialData) { this.financialData = financialData; }

        public DefaultKeyStatistics getDefaultKeyStatistics() { return defaultKeyStatistics; }
        public void setDefaultKeyStatistics(DefaultKeyStatistics defaultKeyStatistics) { this.defaultKeyStatistics = defaultKeyStatistics; }

        public BalanceSheetHistory getBalanceSheetHistory() { return balanceSheetHistory; }
        public void setBalanceSheetHistory(BalanceSheetHistory balanceSheetHistory) { this.balanceSheetHistory = balanceSheetHistory; }
    }

    public static class FinancialData {
        private Value totalDebt;
        private Value totalRevenue;

        public Value getTotalDebt() { return totalDebt; }
        public void setTotalDebt(Value totalDebt) { this.totalDebt = totalDebt; }

        public Value getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(Value totalRevenue) { this.totalRevenue = totalRevenue; }
    }

    public static class DefaultKeyStatistics {
        private Value enterpriseValue;
        private Value sharesOutstanding;

        public Value getEnterpriseValue() { return enterpriseValue; }
        public void setEnterpriseValue(Value enterpriseValue) { this.enterpriseValue = enterpriseValue; }

        public Value getSharesOutstanding() { return sharesOutstanding; }
        public void setSharesOutstanding(Value sharesOutstanding) { this.sharesOutstanding = sharesOutstanding; }
    }

    public static class BalanceSheetHistory {
        private List<BalanceSheetStatements> balanceSheetStatements;

        public List<BalanceSheetStatements> getBalanceSheetStatements() { return balanceSheetStatements; }
        public void setBalanceSheetStatements(List<BalanceSheetStatements> balanceSheetStatements) { this.balanceSheetStatements = balanceSheetStatements; }
    }

    public static class BalanceSheetStatements {
        private Value totalAssets;
        private Value totalLiabilities;

        public Value getTotalAssets() { return totalAssets; }
        public void setTotalAssets(Value totalAssets) { this.totalAssets = totalAssets; }

        public Value getTotalLiabilities() { return totalLiabilities; }
        public void setTotalLiabilities(Value totalLiabilities) { this.totalLiabilities = totalLiabilities; }
    }

    public static class Value {
        private Double raw;

        public Double getRaw() { return raw; }
        public void setRaw(Double raw) { this.raw = raw; }
    }
}
