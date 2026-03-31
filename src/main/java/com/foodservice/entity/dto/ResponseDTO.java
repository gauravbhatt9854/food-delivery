package com.foodservice.entity.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Integer statusCode;
    private String message;
    private Object data;
//    public ResponseDTO() {}
//    
//        public ResponseDTO(int statusCode, String message, Object data) {
//            this.statusCode = statusCode;
//            this.message = message;
//            this.data = data;
//        }
//
//        public int getStatusCode() {
//            return statusCode;
//        }
//
//        public void setStatusCode(int statusCode) {
//            this.statusCode = statusCode;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public Object getData() {
//            return data;
//        }
//
//        public void setData(Object data) {
//            this.data = data;
//        }
    
    
    
}
