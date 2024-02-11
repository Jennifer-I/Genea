package com.genea.dto;

import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsRequest {
    private String mailTo;
    private String  mailFrom;
    private String subject;
    private String text;



}
//    private String contentType;
//    private String thymeLeafTemplate;
//    private Context thymeLeafContext;
//    private Map<String , ClassPathResource> inlineResources;

