package org.zerock.wego.domain.common;

import lombok.Data;

@Data
public class MailDTO {
    private String from;
    private String to;
    private String subject;
    private String content;
    private String format;
} // end class
