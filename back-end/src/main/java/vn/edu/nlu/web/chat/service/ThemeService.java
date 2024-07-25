package vn.edu.nlu.web.chat.service;

import vn.edu.nlu.web.chat.dto.theme.request.ThemeUpdateRequest;
import vn.edu.nlu.web.chat.model.Theme;

public interface ThemeService {
    void update(Long id, ThemeUpdateRequest request);

    void delete(Long id);

    Theme Search(Long themeId);
}
