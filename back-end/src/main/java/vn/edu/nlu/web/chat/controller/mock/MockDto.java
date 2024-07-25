//package vn.edu.nlu.web.chat.controller.mock;
//
//import vn.edu.nlu.web.chat.dto.contact.*;
//import vn.edu.nlu.web.chat.model.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MockDto {
//    public static List<ContactDTO> getContactDto(){
//        ContactDTO contact1 = new ContactDTO(
//                "614ecab426f59ce2863e106e",
//                "Sanford",
//                "Phelps",
//                null,
//                "If several languages coalesce, the grammar of the resulting.",
//                "adc@123.com",
//                "California, USA",
//                "ACTIVE",
//                Arrays.asList(
//                        new ChannelDTO("61665bcb9a456823e282afa7", "Landing Design"),
//                        new ChannelDTO("61665bcb9a41b4e8352ba610", "Design Phase 2")
//                ),
//                new MediaDTO(
//                        17,
//                        Arrays.asList(
//                                new MediaItemDTO(1, "img1_url"),
//                                new MediaItemDTO(2, "img2_url")
//                        )
//                ),
//                new AttachedFilesDTO(
//                        4,
//                        Arrays.asList(
//                                new AttachedFileDTO(3, "Image-2.jpg", "3.1 MB", "#", "bx bx-image"),
//                                new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
//                        )
//                )
//        );
//
//        ContactDTO contact2 = new ContactDTO(
//                "614ecab4aeecaa03e8244d57",
//                "Carla",
//                "Serrano",
//                "avatar1_url",
//                "If several languages coalesce, the grammar of the resulting.",
//                "adc@123.com",
//                "California, USA",
//                "ACTIVE",
//                Arrays.asList(
//                        new ChannelDTO("61665bcb9a456823e282afa7", "Landing Design")
//                ),
//                new MediaDTO(
//                        17,
//                        Arrays.asList(
//                                new MediaItemDTO(1, "img1_url"),
//                                new MediaItemDTO(2, "img2_url")
//                        )
//                ),
//                new AttachedFilesDTO(
//                        4,
//                        Arrays.asList(
//                                new AttachedFileDTO(1, "design-phase-1-approved.pdf", "12.5 MB", "#", "bx bx-file"),
//                                new AttachedFileDTO(4, "Landing-A.zip", "6.7 MB", "#", "bx bx-file")
//                        )
//                )
//        );
//
//        return List.of(contact1,contact2);
//    }
//
//}
