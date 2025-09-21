package com.ocbc.ms.service;

import com.ocbc.ms.dto.MaternityLeaveCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;

public interface MaternityLeaveService {



    MaternityLeaveCalculateResponse calculateDate(MaternityLeaveCalculateRequest request);
}
