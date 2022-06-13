package com.sandbox.braintree.Service;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.NotFoundException;
import com.sandbox.braintree.Config.BraintreeGatewayConfig;
import com.sandbox.braintree.Repository.TransactionRepository;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.model.TransactionEntity;
import com.sandbox.braintree.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private BraintreeGatewayConfig braintreeGatewayConfig;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public PaymentMethodResponse addTransaction(TransactionEntity transactions, Integer cus_id,String settlement) {
        TransactionRequest request = new TransactionRequest()
                .amount(transactions.getAmount())
                .paymentMethodToken(transactions.getTokenId())
                .options()
                .submitForSettlement(Boolean.valueOf(settlement))
                .done();

        Result<Transaction> result=braintreeGatewayConfig.getBraintreeGatewayInstance().transaction().sale(request);
        TransactionEntity transactionObj=new TransactionEntity();
        String transactionId=result.getTarget().getId();
        String encodedString = Base64.getEncoder().encodeToString(transactions.getTokenId().getBytes());
        try {
            transactionObj.setAmount(result.getTarget().getAmount());
            transactionObj.setTransactionDate(String.valueOf(result.getTarget().getCreatedAt().getTime()));
            transactionObj.setCustomerId(cus_id);
            transactionObj.setStatus(String.valueOf(result.getTarget().getStatus()));
            transactionObj.setPaymentType(result.getTarget().getPaymentInstrumentType());
            transactionObj.setTokenId(encodedString);
            transactionObj.setTransactionId(transactionId);
            transactionRepository.save(transactionObj);
        }
        catch (NullPointerException e){
            System.out.println(e);
        }
        catch (NotFoundException e){
            System.out.println(e);
        }
        PaymentMethodResponse response=new PaymentMethodResponse();
        if (result.isSuccess()) {
            response.setMessage("Transaction successful");
        } else {
            response.setMessage("Transaction failed");
        }
        return response;


    }

    @Override
    public TransactionResponse captureTransaction(String transactionId) {
        Result<Transaction> result = braintreeGatewayConfig.getBraintreeGatewayInstance().transaction().submitForSettlement(transactionId);
        TransactionResponse response=new TransactionResponse();
        if (result.isSuccess()) {
            Transaction settledTransaction = result.getTarget();
            response.setTransactionId(transactionId);
            TransactionEntity transobj=transactionRepository.findByTransactionId(transactionId);
            transobj.setStatus(String.valueOf(result.getTarget().getStatus()));
            transactionRepository.save(transobj);
        } else {
            System.out.println(result.getErrors());
        }
        return response;
    }

    @Override
    public TransactionResponse refundTransaction(String transactionId) {
        Result<Transaction> result = braintreeGatewayConfig.getBraintreeGatewayInstance().transaction().refund(transactionId);
        if(result.isSuccess()) {
            TransactionEntity transobj=transactionRepository.findByTransactionId(transactionId);
            transobj.setStatus(String.valueOf(result.getTarget().getStatus()));
            transactionRepository.save(transobj);
        } else {
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.out.println(error.getMessage());
            }
        }
        return null;
    }

    @Override
    public TransactionResponse voidTransaction(String transactionId) {
        Result<Transaction> result = braintreeGatewayConfig.getBraintreeGatewayInstance().transaction().voidTransaction(transactionId);
        if (result.isSuccess()) {
            TransactionEntity transobj=transactionRepository.findByTransactionId(transactionId);
            transobj.setStatus(String.valueOf(result.getTarget().getStatus()));
            transactionRepository.save(transobj);
        } else {
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                System.out.println(error.getMessage());
            }
        }
        return null;
    }

}
