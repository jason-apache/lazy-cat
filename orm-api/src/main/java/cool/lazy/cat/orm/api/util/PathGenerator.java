package cool.lazy.cat.orm.api.util;

import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: mahao
 * @date: 2021/3/5 19:58
 */
public class PathGenerator {

    public static String path(String nameSpace, String path, RequestMethod type) {
        return nameSpace + path + ApiConstant.PATH_SYMBOL + ":" + type;
    }

    public static String format(String path) {
        if (StringUtil.isBlank(path)) {
            return StringUtil.EMPTY;
        }
        if (path.length() < 2) {
            return ApiConstant.PATH_SYMBOL + "";
        }
        int length = path.length();
        int headOffset = 0;
        int modifyCount = 0;
        int tailOffset = path.length();
        for (int i = 0; i < length; i++) {
            if (path.charAt(i) != ApiConstant.PATH_SYMBOL) {
                break;
            } else {
                headOffset = i + 1;
                modifyCount ++;
            }
        }
        if (modifyCount == 1) {
            headOffset = 0;
        } else if (headOffset != 0) {
            headOffset --;
        } else {
            path = ApiConstant.PATH_SYMBOL + path;
            length ++;
            tailOffset = length;
        }
        for (int i = length - 1; i > 0; i--) {
            if (path.charAt(i) != ApiConstant.PATH_SYMBOL) {
                break;
            } else {
                tailOffset = i;
            }
        }
        if (headOffset != 0) {
            path = path.substring(headOffset);
        }
        if (tailOffset != length) {
            if(headOffset != 0) {
                tailOffset = tailOffset - headOffset;
            }
            path = path.substring(0, tailOffset);
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(format("/"));
        System.out.println(format("/qqsd"));
        System.out.println(format("kbv/a"));
        System.out.println(format("////aaaa/"));
        System.out.println(format("//890/"));
        System.out.println(format("///a//"));
        System.out.println(format("/a"));
    }
}
