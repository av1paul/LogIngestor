package com.avi.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

import static com.avi.constants.Constants.DOCUMENT_NAME;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = DOCUMENT_NAME)
public class Log {
    @Id
    String id;
    @TextIndexed
    String level;
    @TextIndexed
    String message;
    @TextIndexed
    String resourceId;
    @TextIndexed
    Date timestamp;
    @TextIndexed
    String spanId;
    @TextIndexed
    String commit;
    @TextIndexed
    MetaData metadata;
}
