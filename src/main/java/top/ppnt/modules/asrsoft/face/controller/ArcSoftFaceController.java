package top.ppnt.modules.asrsoft.face.controller;

import org.apache.xmlbeans.impl.util.Base64;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.upload.UploadFile;

import top.ppnt.arcsoft.face.utils.FaceEngineUtils;
import top.ppnt.arcsoft.face.utils.FaceResult;
import top.ppnt.jfinal.swaager.api.annotation.ActionApi;
import top.ppnt.jfinal.swaager.api.annotation.Api;
import top.ppnt.jfinal.swaager.api.annotation.Param;
import top.ppnt.jfinal.swaager.api.annotation.Params;
import top.ppnt.jfinal.swaager.api.annotation.Responses;

/**
 * @author Ping E Lee
 *
 */
// @Api(tag = "/api/arcsoft/face", remark = "人脸识别接口", sort = 1, outerRemark =
// "查看更多关于首页", outerUrl = "http://www.ppnt.top")
// @Api(tag = "/api/arcsoft/face")
@Api(tag = "/api/arcsoft/face", remark = "人脸识别接口", sort = 1, outerRemark = "查看更多关于首页", outerUrl = "http://www.ppnt.top")
@Path("api/arcsoft/face")
public class ArcSoftFaceController extends Controller {
  /**
   * 检查服务是否有效
   * @return
   */
  @ActionApi(remark = "检查服务是否有效", summary = "检查服务是否有效", sort = 1)
  @Responses(key = "{code:1}", remark = "json格式数据")
  public void check() {
    renderJson(FaceEngineUtils.check());
  }

  /**
   * 获取人脸特征
   * @param file
   * @return
   * @throws Exception
   */
  @ActionApi(remark = "获取人脸特征数据",summary = "获取人脸特征数据",httpMethod = "post", sort = 2)
  @Params({ @Param(name = "file", remark = "上传图片文件", dataType = "file", required = true) })
  @Responses(key = "{code:1}", remark = "json格式数据")
  public void getFaceFeature() throws Exception {
    UploadFile uploadFile = getFile();
    renderJson(FaceEngineUtils.getFaceFeature(uploadFile.getFile()));
  }
  
  @ActionApi(remark = "获取人脸特征数据Base64格式",summary = "获取人脸特征数据Base64格式",httpMethod = "post", sort = 2)
  @Params({ @Param(name = "file", remark = "上传图片文件", dataType = "file", required = true) })
  @Responses(key = "{code:1}", remark = "json格式数据")
  public void getFaceFeatureString() throws Exception {
    UploadFile uploadFile = getFile();
    FaceResult<FaceFeature> faceFeature = FaceEngineUtils.getFaceFeature((uploadFile.getFile()));
    byte[] featureData = faceFeature.getData().getFeatureData();
    byte[] encode = Base64.encode(featureData);
    String string = new String(encode);
    
    FaceResult<String> retval = new FaceResult<String>();
    retval.setData(string);
    renderJson(retval);
  }

  /**
   * 人脸特征比对
   * @param srcByte
   * @param dstByte
   * @return
   */
  @ActionApi(remark = "人脸特征比对", summary = "人脸特征比对",httpMethod = "post", sort = 3)
  @Params({ 
      @Param(name = "srcByte", remark = "人脸特征数据", dataType = "byte", required = true),
      @Param(name = "dstByte", remark = "人脸特征数据", dataType = "byte", required = true) 
      })
  public void compare(byte[] srcByte, byte[] dstByte) {
    FaceFeature src = new FaceFeature(srcByte);
    FaceFeature dst = new FaceFeature(dstByte);
    FaceSimilar faceSimilar = FaceEngineUtils.compare(src, dst);
    renderJson(new FaceResult<>(faceSimilar));
  }

  /**
   * 人脸特征比对
   */
  @ActionApi(remark = "人脸特征比对Base64格式",summary = "人脸特征比对Base64格式", httpMethod = "post", sort = 3)
  @Params({ 
      @Param(name = "srcStr", remark = "Base64编码之后的人脸特征数据", dataType = "string", required = true),
      @Param(name = "dstStr", remark = "Base64编码之后的人脸特征数据", dataType = "string", required = true) 
      })
  public void compareString(String srcStr, String dstStr) {
    byte[] srcByte = org.apache.commons.codec.binary.Base64.decodeBase64(srcStr);
    byte[] dstByte = org.apache.commons.codec.binary.Base64.decodeBase64(dstStr);
    FaceFeature src = new FaceFeature(srcByte);
    FaceFeature dst = new FaceFeature(dstByte);
    FaceSimilar faceSimilar = FaceEngineUtils.compare(src, dst);
    renderJson(new FaceResult<>(faceSimilar));
  }
}
