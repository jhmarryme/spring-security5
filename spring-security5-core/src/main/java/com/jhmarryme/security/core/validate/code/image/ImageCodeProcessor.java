package com.jhmarryme.security.core.validate.code.image;

import com.jhmarryme.security.core.validate.code.base.ImageCode;
import com.jhmarryme.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 图形验证码处理器
 * @author JiaHao Wang
 * @date 2020/12/3 20:53
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
