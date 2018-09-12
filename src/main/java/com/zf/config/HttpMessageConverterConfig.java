package com.zf.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpMessageConverterConfig {

	public static List<HttpMessageConverter<?>> httpMessageConverters() {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		// 1.定义一个converters转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastMediaTypes.add(MediaType.TEXT_HTML);
		fastMediaTypes.add(MediaType.TEXT_PLAIN);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		// 3.在converter中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4.将converter赋值给HttpMessageConverter
		HttpMessageConverter<?> converter = fastConverter;
		// 5.返回HttpMessageConverters对象
		List<HttpMessageConverter<?>> list = new ArrayList<>();
		list.add(stringConverter);
		list.add(converter);
		return list;
	}
}