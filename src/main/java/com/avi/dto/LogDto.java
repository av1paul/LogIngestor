package com.avi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogDto {
    String level;
    String message;
    String resourceId;
    ZonedDateTime timestamp;
    String spanId;
    String commit;
    MetaDataDto metadata;
}
