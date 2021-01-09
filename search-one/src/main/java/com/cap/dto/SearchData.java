package com.cap.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchData {
private String esId;//esId
private String cnName;//中文名
private String enName;//外文名
private String slug;//缩略名
private String content;//内容
}
