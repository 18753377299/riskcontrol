package com.picc.riskctrl.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class MappingFastJsonHttpMessageConverter extends AbstractHttpMessageConverter {

    public SerializerFeature[] getSerializerFeature() {
        return serializerFeature;
    }

    public void setSerializerFeature(SerializerFeature[] serializerFeature) {
        this.serializerFeature = (SerializerFeature[]) serializerFeature.clone();
    }

    public MappingFastJsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return true;
    }

    @Override
    protected boolean supports(Class clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int i;
            while ((i = inputMessage.getBody().read()) != -1) {
                baos.write(i);
            }
            /*if(List.class.isAssignableFrom(clazz)){
            	return JSON.parseArray(baos.toString(), clazz);
            };
            List o = JSON.parseArray(baos.toString(), clazz);
            if(o!=null){
             return  o.get(0);
            }


            return null;*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //controller获取json乱码问题 add by wangwenjie 2019/7/15
        return JSON.parseObject(new String(baos.toByteArray(), getContentTypeCharset(inputMessage.getHeaders().getContentType()).name()), clazz);
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }else{
            return DEFAULT_CHARSET;
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
//        String jsonString = JSON.toJSONString(o, serializerFeature);
        try {
            String jsonString = JSON.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss", serializerFeature);
            if (jsonString.charAt(0) == '"') {
                jsonString = jsonString.substring(0, jsonString.length() - 1).replaceFirst("\"", "").replaceAll("\\\\", "");
            }
//    		 System.out.println(jsonString);
            OutputStream out = outputMessage.getBody();
            out.write(jsonString.getBytes(DEFAULT_CHARSET.name()));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private SerializerFeature[] serializerFeature =
		{SerializerFeature.WriteMapNullValue,SerializerFeature.QuoteFieldNames,
    		   SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteNullStringAsEmpty};


}
