package ma.enset.comptecqrses.commonapi.commands;

import lombok.Data;
import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String>{
    private double initialBalance;
    private String currency;

    public CreateAccountCommand(String id, double accountBalance, String currency) {
        super(id);
        this.initialBalance = accountBalance;
        this.currency = currency;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public String getCurrency() {
        return currency;
    }
}