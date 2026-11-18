package com.banking.model;

import java.math.BigDecimal;

public interface InterestBearing {

    BigDecimal calculateInterest();

    void payInterest();
}