package com.avi.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetaData {
    String parentResourceId;
}
