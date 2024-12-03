package ib.develop.matstore.dto;

public interface DebtDTO {
    String getClientName();

    String getClientPhone();

    long getInvoicesCount();

    Double getRemainingBalance();
}
