package com.ocbc.ms.service;

import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.CalculateResponse;
import com.ocbc.ms.dto.MoneyCalculateRequest;

public interface MaternityLeaveService {



    CalculateResponse calculateDate(DateCalculateRequest dateCalculateRequest);


    CalculateResponse calculateMoney(MoneyCalculateRequest moneyCalculateRequest);
}
