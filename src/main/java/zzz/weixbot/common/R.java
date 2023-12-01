package zzz.weixbot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static zzz.weixbot.common.Constant.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private int code;
    private String msg;
    private Object data;

//    success
    public static R ok(){
        return new R(SUCCESS_CODE,SUCCESS_MSG,null);
    }
    public static R ok(Object data){
        return new R(SUCCESS_CODE,SUCCESS_MSG,data);
    }
    public static R ok(String msg,Object data){
        return new R(SUCCESS_CODE,msg,data);
    }

//    error
    public static R error(){
        return new R(ERROR_CODE,Constant.ERROR_MSG,null);
    }
    public static R error(Object data){
        return new R(ERROR_CODE,ERROR_MSG,data);
    }
    public static R error(String msg,Object data){
        return new R(ERROR_CODE,msg,data);
    }
}
