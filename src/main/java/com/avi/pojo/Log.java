package com.avi.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "log")
public class Log {
    @Id
    String id;
    String level;
    String message;
    String resourceId;
    Date timestamp;
    String spanId;
    String commit;
    MetaData metadata;
}
