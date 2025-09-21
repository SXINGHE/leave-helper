package com.ocbc.ms.service;

import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;

public interface MaternityLeaveService {



    MaternityLeaveCalculateResponse calculateDate(DateCalculateRequest request);
}
