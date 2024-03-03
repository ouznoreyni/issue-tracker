package sn.noreyni.issuetrackerbackend.shared.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseModel<T> {
    @Builder.Default
    String resultMessage ="Operation successful";
    @Builder.Default
    Integer resultCode = 200;
    @Builder.Default
    Boolean success = true;
    T data;
}