package ma.enset.comptecqrses.commonapi.commands;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String>{
    @Getter private double creditAmount;
    @Getter private String currency;

    public CreditAccountCommand(String id, double creditAmount, String currency) {
        super(id);
        this.creditAmount = creditAmount;
        this.currency = currency;
    }
}