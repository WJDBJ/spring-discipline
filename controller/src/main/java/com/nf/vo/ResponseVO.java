package com.nf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cj
 * @date 2020/1/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO {
    private String code;
    private String msg;
    private Object data;
}
