package com.cody.repository.business.interaction;

import com.cody.repository.Domain;
import com.cody.repository.business.bean.ImageBean;
import com.cody.repository.business.bean.ImageListBean;
import com.cody.repository.business.interaction.constant.UploadUrl;
import com.cody.repository.framework.interaction.ICallback;
import com.cody.repository.framework.interaction.QueryCallBack;
import com.cody.repository.framework.interaction.QueryClass;
import com.cody.repository.framework.interaction.QueryImage;
import com.cody.repository.framework.interaction.QueryImages;
import com.cody.repository.framework.interaction.QueryString;
import com.cody.repository.framework.interaction.QueryTag;
import com.cody.repository.framework.interaction.RequestMapping;
import com.cody.repository.framework.interaction.RequestMethod;
import com.cody.repository.framework.interaction.ResultType;
import com.cody.repository.framework.interaction.Server;

import java.util.List;

/**
 * Created by shijia on 2018/1/18.
 * 上传图片
 */
@Server(Domain.UPLOAD_FILE)
public interface UploadInteraction {
    @RequestMapping(
            value = UploadUrl.FILE_UPLOAD,
            method = RequestMethod.POST,
            type = ResultType.UPLOAD_BEAN)
    void uploadFile(@QueryTag Object tag, @QueryImage String path, @QueryString("appName") String appName, @QueryClass Class<?> clazz, @QueryCallBack
            ICallback<ImageBean> callback);

    @RequestMapping(
            value = UploadUrl.FILES_UPLOAD,
            method = RequestMethod.POST,
            type = ResultType.UPLOAD_LIST_BEAN)
    void uploadFiles(@QueryTag Object tag, @QueryImages List<String> paths,@QueryString("appName") String appName, @QueryClass Class<?> clazz,
                     @QueryCallBack ICallback<ImageListBean> callback);

}
