package ac.za.cput.factory;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.util.Helper;
import java.time.LocalDateTime;

public class PaymentMethodFactory {
    public static PaymentMethod createPaymentMethod(PaymentType type,   LocalDateTime paymentDate) {
        if (type == null || paymentDate == null) {
            return null;
        }
        String paymentMethodId = Helper.generateId();
        return new PaymentMethod.Builder()
                .setPaymentMethodId(paymentMethodId)
                .setType(type)
                .setPaymentDate(paymentDate)
                .build();
    }
}