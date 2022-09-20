package myjob.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionInfoPrintUtil {
    public static String getExceptionInfo(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
