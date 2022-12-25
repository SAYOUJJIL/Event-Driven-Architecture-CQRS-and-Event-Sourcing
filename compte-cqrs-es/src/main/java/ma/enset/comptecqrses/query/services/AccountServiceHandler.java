package ma.enset.comptecqrses.query.services;

import lombok.AllArgsConstructor;
import ma.enset.comptecqrses.commonapi.enums.OperationType;
import ma.enset.comptecqrses.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrses.commonapi.queries.GetAccountByIdQuery;
import ma.enset.comptecqrses.commonapi.queries.GetAllAccountsQuery;
import ma.enset.comptecqrses.query.entities.Account;
import ma.enset.comptecqrses.query.entities.Operation;
import ma.enset.comptecqrses.query.repositories.AccountRepository;
import ma.enset.comptecqrses.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("AccountCreatedEvent ");
        Account account =  new Account();
        account.setId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getInitialBalance());
        account.setAccountStatus(accountCreatedEvent.getAccountStatus()) ;
        account.setCurrency(accountCreatedEvent.getCurrency());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info(" AccountActivatedEvent ");
        Account account = accountRepository.findById(accountActivatedEvent.getId()).get();
        account.setAccountStatus(accountActivatedEvent.getAccountStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("AccountDebitedEvent ");
        Account account = accountRepository.findById(accountDebitedEvent.getId()).get();
        Operation operation = new Operation();
          operation.setAmount(accountDebitedEvent.getAmount());
          operation.setDate(new Date());
          operation.setType(OperationType.DEBIT);
          operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - accountDebitedEvent.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("AccountCreditedEvent ");
        Account account = accountRepository.findById(accountCreditedEvent.getId()).get();
        Operation operation = Operation.builder()
                .amount(accountCreditedEvent.getAmount())
                .date(new Date())
                .type(OperationType.DEBIT)
                .account(account)
                .build();
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + accountCreditedEvent.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery getAllAccountQuery){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery getAccountQuery){
        return accountRepository.findById(getAccountQuery.getId()).get();
    }
}