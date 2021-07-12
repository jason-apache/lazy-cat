package cool.lazy.cat.orm.api.web.entrust.executor.holder;

import cool.lazy.cat.orm.api.exception.UnableToAdaptResponseDataWriterException;
import cool.lazy.cat.orm.api.web.entrust.executor.writer.ResponseDataWriter;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:16
 */
public class DefaultResponseDataWriterHolder implements ResponseDataWriterHolder {

    protected final List<ResponseDataWriter> writerList;

    public DefaultResponseDataWriterHolder(List<ResponseDataWriter> writerList) {
        this.writerList = writerList;
    }

    public ResponseDataWriter lookup(MethodInfo methodInfo) {
        for (ResponseDataWriter writer : writerList) {
            if (writer.support(methodInfo)) {
                return writer;
            }
        }
        throw new UnableToAdaptResponseDataWriterException("无法响应数据, 缺少必要的ResponseDataWriter");
    }

    @Override
    public void doResponse(Object data, HttpServletResponse response, ApiMethodEntry methodEntry) {
        ResponseDataWriter writer = this.lookup(methodEntry.getBusinessMethod());
        writer.doResponse(data, response, methodEntry);
    }
}
