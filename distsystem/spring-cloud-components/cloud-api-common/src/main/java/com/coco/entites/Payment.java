package com.coco.entites;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
