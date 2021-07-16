package com.jhmarryme.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/*
 * Description:
 * @author JiaHao Wang
 * @date 2020/9/10 19:56
 * Modified By:
 */
@Component
@Data
public class DeferredResultHolder {
	
	private Map<String, DeferredResult<String>> map = new HashMap<>();

}