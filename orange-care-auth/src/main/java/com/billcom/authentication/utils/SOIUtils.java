package com.billcom.authentication.utils;

import com.lhs.ccb.cfw.cda.session.*;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ws.beans.BaseSOIBean;
import com.lhs.ws.beans.BeanSOIConverter;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Constructor;

@Log4j2
public class SOIUtils {

    public static String getSecuritySOIName() {
        return ApplicationSettings.instance().getAttribute("SoiNameForSecurity");
    }

    public static String getSecuritySOIVersion() {
        return ApplicationSettings.instance().getAttribute("SoiVersionForSecurity");
    }

    public static BaseSOIBean convertToSOIBean(SVLObject svlObject, Class<?> convertClass) throws CMSException {
        try {
            Constructor<?> constructor = convertClass.getConstructor(SVLObject.class);
            return (BaseSOIBean)constructor.newInstance(svlObject);
        } catch (Exception var6) {
            throw new CMSException("Data error", "Unable to read out response from command ", var6);
        }
    }

    public static SVLObject convertToSVLObject(BaseSOIBean baseSOIBean) {
        return BeanSOIConverter.getSVLObject(baseSOIBean);
    }


}
