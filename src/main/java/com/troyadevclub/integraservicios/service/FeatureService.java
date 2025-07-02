package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.AssociateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.CreateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.GetAssociationsProjection;
import com.troyadevclub.integraservicios.entity.FeatureEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FeatureService {

    APIResponseDTO<Object> create(CreateFeatureRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<FeatureEntity>> getAll(HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> associateFeature(AssociateFeatureRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetAssociationsProjection>> getAssociations(String unitUserId, String resourceTypeId, HttpServletRequest httpRequest, String trace);

}
