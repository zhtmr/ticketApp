package com.sw.dto;



import lombok.Builder;

import java.util.Date;

@Builder
public record RsvResponseDto(
    int no,
    String name,
    String title,
    String location,
    int count,
    int totalPrice,
    Date eventStartDate,
    Date eventEndDate,
    String status,
    Date createdDate
) {
}
