package com.sandbox.braintree.Service;

import com.braintreegateway.*;
import com.sandbox.braintree.Config.BraintreeGatewayConfig;
import com.sandbox.braintree.Repository.DeletePaymentMethod;
import com.sandbox.braintree.model.PaymentEntity;
import com.sandbox.braintree.model.PaymentMethodResponse;
import com.sandbox.braintree.Repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class InitializeServiceImpl implements InitializeService {

    @Autowired
    private BraintreeGatewayConfig braintreeGatewayConfig;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private DeletePaymentMethod deletePaymentMethod;

    @Override
    @ResponseBody
    public PaymentMethodResponse onboard(PaymentEntity payment, @RequestParam("payment_method_nonce") String nonce, Integer id) {
        System.out.println(nonce);
        PaymentMethodResponse response = new PaymentMethodResponse();
        BraintreeGateway braintreeGatewayInstance = braintreeGatewayConfig.getBraintreeGatewayInstance();
        String id1 = String.valueOf(id);
        PaymentMethodRequest paymentMethodRequest =
                new PaymentMethodRequest()
                        .customerId(id1)
                        .paymentMethodNonce(nonce)
                        .options()
                        .makeDefault(payment.isIs_default())
                        .done();

        Result<? extends PaymentMethod> paymentMethodResult =
                braintreeGatewayConfig.getBraintreeGatewayInstance().paymentMethod()
                        .create(paymentMethodRequest);
        String permanentPaymentToken = paymentMethodResult.getTarget().getToken();
        PaymentMethod paymentMethod = braintreeGatewayConfig.getBraintreeGatewayInstance().paymentMethod().find(permanentPaymentToken);
        String type = String.valueOf(paymentMethod.getClass());
        PaymentEntity payobj = paymentMethodRepository.findByCustomersId(id);
//        if(payobj==null){
//            throw new NoDataAvailableException();
//        }
//        else {
            payobj.setIs_default(paymentMethodResult.getTarget().isDefault());
            payobj.setPayment_type(type);
//            paymentMethodRepository.save(payobj);
//        }
        if (paymentMethodResult.isSuccess()) {
            response.setCode(200);
            response.setMessage("Ok");
            response.setCustomerId(id);
        } else {
            response.setCode(400);
            response.setMessage("Not Ok");
            response.setCustomerId(id);
        }
        return response;
    }

    @Override
    public PaymentMethodResponse addCustomer(PaymentEntity payment) {
        PaymentMethodResponse response = new PaymentMethodResponse();
        CustomerRequest customerRequest = new CustomerRequest()
                .firstName(payment.getFirst_name())
                .lastName(payment.getLast_name())
                .company(payment.getCompany())
                .email(payment.getEmail())
                .phone(payment.getPhone());
        PaymentEntity payobj = new PaymentEntity();

        Result<Customer> customerResult =
                braintreeGatewayConfig.getBraintreeGatewayInstance().customer()
                        .create(customerRequest);
        int braintreeId;
        if (customerResult.isSuccess()) {
            braintreeId = Integer.parseInt(customerResult.getTarget().getId());
            response.setCode(200);
            response.setMessage("Ok");
        } else {
            response.setCode(400);
            response.setMessage("Not Ok");
            throw new RuntimeException("Failed to create Customer!!");
        }
        try {
        payobj.setCustomersId(braintreeId);
        payobj.setFirst_name(customerResult.getTarget().getFirstName());
        payobj.setLast_name(customerResult.getTarget().getLastName());
        payobj.setEmail(customerResult.getTarget().getEmail());
        payobj.setCompany(customerResult.getTarget().getCompany());
        payobj.setPhone(customerResult.getTarget().getPhone());
        paymentMethodRepository.save(payobj);
    }
        catch (NullPointerException e) {
            System.out.println(e);
        }
        response.setCustomerId(braintreeId);
        response.setFirstname(customerResult.getTarget().getFirstName());
        response.setLastname(customerResult.getTarget().getLastName());
        return response;
    }


    @Override
    public PaymentMethodResponse deleteCustomer(Integer id) {
        PaymentEntity payobj = paymentMethodRepository.findByCustomersId(id);
        paymentMethodRepository.delete(payobj);
        return null;
    }

    @Override
    public PaymentMethodResponse seachCustomer(Integer cus_id) {
        PaymentEntity payobj = paymentMethodRepository.findByCustomersId(cus_id);
        PaymentMethodResponse response = new PaymentMethodResponse();
        response.setCustomerId(payobj.getCustomersId());
        response.setFirstname(payobj.getFirst_name());
        response.setLastname(payobj.getLast_name());
        return response;
    }
}
