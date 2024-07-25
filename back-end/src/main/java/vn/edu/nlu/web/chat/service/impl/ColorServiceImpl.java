package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.dto.color.request.ColorCreateRequest;
import vn.edu.nlu.web.chat.dto.color.request.ColorUpdateRequest;
import vn.edu.nlu.web.chat.dto.color.response.ColorCreateResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorDetailsResponse;
import vn.edu.nlu.web.chat.dto.color.response.ColorUpdateResponse;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ApiRequestException;
import vn.edu.nlu.web.chat.model.Color;
import vn.edu.nlu.web.chat.repository.ColorRepository;
import vn.edu.nlu.web.chat.service.ColorService;
import vn.edu.nlu.web.chat.utils.DataUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public ColorCreateResponse create(ColorCreateRequest request) {
        log.info(request.getValue());
        if (colorRepository.findColorByValue(request.getValue()).isPresent())
            throw new ApiRequestException("Color existed");

        var newColor = DataUtils.copyProperties(request, Color.class);
        colorRepository.save(newColor);
        return DataUtils.copyProperties(newColor, ColorCreateResponse.class);
    }

    @Override
    public List<ColorDetailsResponse> search() {
        List<ColorDetailsResponse> list = colorRepository.getAllByAndEntityStatusIsNot(EntityStatus.DELETED)
                .stream().map(i -> DataUtils.copyProperties(i, ColorDetailsResponse.class)).toList();
        return list;
    }

    @Override
    public ColorDetailsResponse getColor(Long id) {
        ColorDetailsResponse res = DataUtils.copyProperties(colorRepository.findColorById(id).get(), ColorDetailsResponse.class);
        return res;
    }

    @Override
    public ColorUpdateResponse update(Long id, ColorUpdateRequest value) {
        if (colorRepository.findColorByValue(value.getValue()).isPresent())
            throw new ApiRequestException("value is existed");
        var color = getColorById(id);
        color.setValue(value.getValue());
        colorRepository.save(color);
        return DataUtils.copyProperties(color, ColorUpdateResponse.class);
    }

    @Override
    public void delete(Long id) {
        Color color = getColorById(id);
        color.setEntityStatus(EntityStatus.DELETED);
        colorRepository.save(color);
    }

    private Color getColorById(Long id) {
        return colorRepository.findColorById(id)
                .orElseThrow(() -> new ApiRequestException("value is existed"));
    }
}
