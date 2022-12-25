package ma.enset.comptecqrses.commonapi.commands;

import lombok.Getter;

public class DebitAccountCommand extends BaseCommand<String>{
    @Getter private double debitAmount;
    @Getter private String currency;

    public DebitAccountCommand(String id, double debitAmount, String currency) {
        super(id);
        this.debitAmount = debitAmount;
        this.currency = currency;
    }
}