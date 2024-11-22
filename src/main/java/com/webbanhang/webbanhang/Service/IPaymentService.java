package com.webbanhang.webbanhang.Service;

import com.webbanhang.webbanhang.Model.PaymentModel;

import java.util.List;

public interface IPaymentService {
    List<PaymentModel> getAllPayment();

    boolean addPayment(PaymentModel a);

    boolean updatePayment(PaymentModel a);
}
