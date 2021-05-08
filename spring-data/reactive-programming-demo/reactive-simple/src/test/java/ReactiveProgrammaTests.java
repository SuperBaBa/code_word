import com.alibaba.fastjson.JSONObject;
import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.api.WstxOutputProperties;
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author marcus
 * @date 2020/8/28-15:56
 */

public class ReactiveProgrammaTests {
    private static final Logger logger = LoggerFactory.getLogger(ReactiveProgrammaTests.class);

    public static void main(String[] args) throws Exception {
        // 解析XML为JSONObject数组，然后再收集为一个Map，既能变成一个对象
        String routeInfo = " <ABCInfoList><ABCInfo><value>重庆市内包</value><code>FIRST_PARAM</code></ABCInfo><ABCInfo><value>18</value><code>SECOND_PARAM</code></ABCInfo><ABCInfo><value>F015-40</value><code>THIRD_PARAM</code></ABCInfo><ABCInfo><value>840</value><code>FOURTH_PARAM</code></ABCInfo><ABCInfo><value>重庆分拨中心</value><code>FIFTH_PARAM</code></ABCInfo><ABCInfo><value>义乌商城</value><code>SIXTH_PARAM</code></ABCInfo></ABCInfoList>";
        //设置JacksonXML解析器
        XMLInputFactory ifactory = new WstxInputFactory();
        ifactory.setProperty(WstxInputProperties.P_MAX_ATTRIBUTE_SIZE, 32);
        XMLOutputFactory ofactory = new WstxOutputFactory();
        ofactory.setProperty(WstxOutputProperties.P_OUTPUT_CDATA_AS_TEXT, false);
        XmlFactory xf = new XmlFactory(ifactory, ofactory);
        //将XML解析工厂放入XmlMapper
        XmlMapper xmlMapper = new XmlMapper(xf);
        JSONObject[] routeInfoJsonObjectArray = xmlMapper.readValue(routeInfo, JSONObject[].class);
        //将JSONObject数组转成流进行处理
        Map<String, String> paramMap = Arrays.stream(routeInfoJsonObjectArray)
                .filter(jsonObject -> !jsonObject.containsKey("null") && !jsonObject.containsValue("null"))
                .collect(Collectors.toMap(key -> String.valueOf(key.get("code")), value -> String.valueOf(value.get("value"))));
        logger.info(String.valueOf(paramMap));
        //响应式编程，转成Flux序列
        Map<String, String> paramMapFlux = new HashMap<>();
        paramMapFlux = Flux.fromArray(routeInfoJsonObjectArray)
                .filter(jsonObject -> !jsonObject.containsKey("null") && !jsonObject.containsValue("null"))
                //这里将JSONObject数组转成Map序列，如果是flatMap可以将两个序列融合
                .map(JSONObject::getInnerMap)
                .collectMap(key -> String.valueOf(key.get("code")), value -> String.valueOf(value.get("value")))
                //因为要获取返回值，所以必须在最后进行阻塞获取，这块异步获取值不太好处理
                .block();
        logger.info(String.valueOf(paramMapFlux));

    }
}