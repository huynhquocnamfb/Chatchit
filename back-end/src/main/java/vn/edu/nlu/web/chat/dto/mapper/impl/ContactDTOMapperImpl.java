package vn.edu.nlu.web.chat.dto.mapper.impl;

import org.springframework.stereotype.Component;
import vn.edu.nlu.web.chat.dto.contact.*;
import vn.edu.nlu.web.chat.enums.SocialStatus;
import vn.edu.nlu.web.chat.model.*;
import vn.edu.nlu.web.chat.dto.mapper.ContactDTOMapper;

import java.util.Arrays;

/*
@Component: Đây là annotation chung nhất và thường được sử dụng để đánh dấu
một class là một Spring Bean. Nó chỉ ra rằng class được chú
 thích là một thành phần của ứng dụng Spring.

 */
@Component
public class ContactDTOMapperImpl implements ContactDTOMapper {

    @Override
    public ContactDTO apply(User user) {
        if (user == null) {
            return null;
        }
        ContactDTO contactDTO = ContactDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .about(user.getDescription())
                .location("California, USA")
                .avatarUrl(user.getAvatarUrl())
                .status(user.getSocialStatus() == SocialStatus.ONLINE ? "Active" : "Away")
                .media(DUMMY_MEDIA_DTO)
                .attachedFiles(ATTACHED_FILES_DTO)
                .build();
        return contactDTO;
    }

    private static final MediaDTO DUMMY_MEDIA_DTO = new MediaDTO(
            17,
            Arrays.asList(
                    new MediaItemDTO(1, "img1_url"),
                    new MediaItemDTO(2, "img2_url")
            )
    );
    private static final AttachedFilesDTO ATTACHED_FILES_DTO = new AttachedFilesDTO(
            4,
            Arrays.asList(
                    new AttachedFileDTO(1, "design-phase-1-approved.pdf", "12.5 MB", "#", "bx bx-file"),
                    new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
            )
    );
}