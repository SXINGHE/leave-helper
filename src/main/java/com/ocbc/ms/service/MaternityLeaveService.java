package com.ocbc.ms.service;

import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;
import com.ocbc.ms.dto.MoneyCalculateRequest;

public interface MaternityLeaveService {



    MaternityLeaveCalculateResponse calculateDate(DateCalculateRequest dateCalculateRequest);


    MaternityLeaveCalculateResponse calculateMoney(MoneyCalculateRequest moneyCalculateRequest);
}
