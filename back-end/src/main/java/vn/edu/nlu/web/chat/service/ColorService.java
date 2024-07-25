package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorUpdateRequest;
import vn.edu.nlu.web.chat.dto.color.response.ColorCreateResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorDetailsResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorUpdateResponse;
import vn.edu.nlu.web.chat.model.Color;

import java.util.List;

public interface ColorService {

    ColorCreateResponse create(ColorCreateRequest request);

    List<ColorDetailsResponse> search();

    ColorDetailsResponse getColor(Long id);

    ColorUpdateResponse update(Long id, ColorUpdateRequest value);

    void delete(Long id);
}
