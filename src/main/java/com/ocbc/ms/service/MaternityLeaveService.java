package com.ocbc.ms.service;

import com.ocbc.ms.model.MaternityLeaveCalculateRequest;
import com.ocbc.ms.model.MaternityLeaveCalculateResponse;

public interface MaternityLeaveService {

    MaternityLeaveCalculateResponse calculateMaternityLeave(MaternityLeaveCalculateRequest maternityLeaveCalculateRequest);
}
